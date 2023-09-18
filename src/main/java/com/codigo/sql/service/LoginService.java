package com.codigo.sql.service;

import com.codigo.sql.model.Login;
import com.codigo.sql.dao.LoginDAO;

import java.util.Scanner;
import java.io.File;
import java.util.List;
import spark.Request;
import spark.Response;

public class LoginService {
	private LoginDAO loginDAO = new LoginDAO();
	private String form;
	private final int FORM_INSERT = 1;
	private final int FORM_DETAIL = 2;
	private final int FORM_UPDATE = 3;
	private final int FORM_ORDERBY_ID = 1;
	private final int FORM_ORDERBY_IDADE = 2;
	
	public LoginService() {
		makeForm();
	}

	
	public void makeForm() {
		makeForm(FORM_INSERT, new Login(), FORM_ORDERBY_ID);
	}

	
	public void makeForm(int orderBy) {
		makeForm(FORM_INSERT, new Login(), orderBy);
	}

	
	public void makeForm(int tipo, Login login, int orderBy) {
		String nomeArquivo = "form.html";
		form = "";
		try{
			Scanner entrada = new Scanner(new File(nomeArquivo));
		    while(entrada.hasNext()){
		    	form += (entrada.nextLine() + "\n");
		    }
		    entrada.close();
		}  catch (Exception e) { System.out.println(e.getMessage()); }
		
		String umLogin = "";
		if(tipo != FORM_INSERT) {
			umLogin += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umLogin += "\t\t<tr>";
			umLogin += "\t\t\t<td align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;<a href=\"/index.html/list/1\">Novo Login</a></b></font></td>";
			umLogin += "\t\t</tr>";
			umLogin += "\t</table>";
			umLogin += "\t<br>";			
		}
		
		if(tipo == FORM_INSERT || tipo == FORM_UPDATE) {
			String action = "/index.html/";
			String nome, email, buttonLabel;
			if (tipo == FORM_INSERT){
				action += "insert";
				nome = "Inserir nome";
				email = "email";
				buttonLabel = "Inserir";
			} else {
				action += "update/" + login.getId();
				nome = "Atualizar Login (ID " + login.getId() + ")";
				email = login.getEmail();
				buttonLabel = "Atualizar";
			}
			umLogin += "\t<form class=\"form--register\" action=\"" + action + "\" method=\"post\" id=\"form-add\">";
			umLogin += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umLogin += "\t\t<tr>";
			umLogin += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;" + nome + "</b></font></td>";
			umLogin += "\t\t</tr>";
			umLogin += "\t\t<tr>";
			umLogin += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umLogin += "\t\t</tr>";
			umLogin += "\t\t<tr>";
			umLogin += "\t\t\t<td>&nbsp;Email: <input class=\"input--register\" type=\"text\" name=\"email\" value=\""+ email +"\"></td>";
			
			
			umLogin += "\t\t</tr>";
			umLogin += "\t\t<tr>";
			umLogin += "\t\t\t<td align=\"center\"><input type=\"submit\" value=\""+ buttonLabel +"\" class=\"input--main__style input--button\"></td>";
			umLogin += "\t\t</tr>";
			umLogin += "\t</table>";
			umLogin += "\t</form>";		
		} else if (tipo == FORM_DETAIL){
			umLogin += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umLogin += "\t\t<tr>";
			umLogin += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Detalhar Nome (ID " + login.getId() + ")</b></font></td>";
			umLogin += "\t\t</tr>";
			umLogin += "\t\t<tr>";
			umLogin += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umLogin += "\t\t</tr>";
			umLogin += "\t\t<tr>";
			umLogin += "\t\t\t<td>&nbsp;Email: "+ login.getEmail() +"</td>";
			umLogin += "\t\t\t<td>Idade: "+ login.getIdade() +"</td>";
			umLogin += "\t\t</tr>";
			umLogin += "\t\t<tr>";
			umLogin += "\t\t\t<td>&nbsp;</td>";
			umLogin += "\t\t</tr>";
			umLogin += "\t</table>";		
		} else {
			System.out.println("ERRO! Tipo não identificado " + tipo);
		}
		
form = form.replaceFirst("<UM-LOGIN>", umLogin);
		
		String list = new String("<table width=\"80%\" align=\"center\" bgcolor=\"#f3f3f3\">");
		list += "\n<tr><td colspan=\"6\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Relação de Logins</b></font></td></tr>\n" +
				"\n<tr><td colspan=\"6\">&nbsp;</td></tr>\n" +
    			"\n<tr>\n" + 
        		"\t<td><a href=\"/index.html/list/" + FORM_ORDERBY_ID + "\"><b>ID</b></a></td>\n" +
        		"\t<td><a href=\"/index.html/list/" + FORM_ORDERBY_IDADE + "\"><b>Idade</b></a></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Detalhar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Atualizar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Excluir</b></td>\n" +
        		"</tr>\n";
		
		List<Login> logins;
		if (orderBy == FORM_ORDERBY_ID) {                 	logins = loginDAO.getOrderByID();
		} else if (orderBy == FORM_ORDERBY_IDADE) {		logins = loginDAO.getOrderByIdade();
		} else {											logins = loginDAO.get();
		}

		int i = 0;
		String bgcolor = "";
		for (Login p : logins) {
			bgcolor = (i++ % 2 == 0) ? "#fff5dd" : "#dddddd";
			list += "\n<tr bgcolor=\""+ bgcolor +"\">\n" + 
            		  "\t<td>" + p.getId() + "</td>\n" +
            		  "\t<td>" + p.getEmail() + "</td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/index.html/" + p.getId() + "\"><img src=\"/image/detail.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/index.html/update/" + p.getId() + "\"><img src=\"/image/update.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"javascript:confirmarDeleteLogin('" + p.getId() + "', '" + p.getEmail() + "', '" + "');\"><img src=\"/image/delete.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "</tr>\n";
		}
		list += "</table>";		
		form = form.replaceFirst("<LISTAR-LOGINS>", list);				
	}
	
	
	public Object insert(Request request, Response response) {
		String email = request.queryParams("email");
		String name = request.queryParams("nome");
		String password = request.queryParams("senha");
		int idade = Integer.parseInt(request.queryParams("idade"));
		
		String resp = "";
		
		Login login = new Login(-1, name, email, password, idade);
		
		if(loginDAO.insert(login) == true) {
            resp = "Login (" + email + ") inserido!";
            response.status(201); // 201 Created
		} else {
			resp = "Login (" + email + ") não inserido!";
			response.status(404); // 404 Not found
		}
			
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	
	public Object get(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));		
		Login login = (Login) loginDAO.get(id);
		
		if (login != null) {
			response.status(200); // success
			makeForm(FORM_DETAIL, login, FORM_ORDERBY_IDADE);
        } else {
            response.status(404); // 404 Not found
            String resp = "Usuario " + id + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}

	
	public Object getToUpdate(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));		
		Login login = (Login) loginDAO.get(id);
		
		if (login != null) {
			response.status(200); // success
			makeForm(FORM_UPDATE, login, FORM_ORDERBY_IDADE);
        } else {
            response.status(404); // 404 Not found
            String resp = "Produto " + id + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}
	
	
	public Object getAll(Request request, Response response) {
		int orderBy = Integer.parseInt(request.params(":orderby"));
		makeForm(orderBy);
	    response.header("Content-Type", "text/html");
	    response.header("Content-Encoding", "UTF-8");
		return form;
	}			
	
	public Object update(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
		Login login = loginDAO.get(id);
        String resp = "";       

        if (login != null) {
        	login.setEmail(request.queryParams("email"));
        	login.setIdade(Integer.parseInt(request.queryParams("idade")));
        	loginDAO.update(login);
        	response.status(200); // success
            resp = "Login (ID " + login.getId() + ") atualizado!";
        } else {
            response.status(404); // 404 Not found
            resp = "Login (ID \" + login.getId() + \") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	
	public Object delete(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        Login login = loginDAO.get(id);
        String resp = "";       

        if (login != null) {
            loginDAO.delete(id);
            response.status(200); // success
            resp = "Login (" + id + ") excluído!";
        } else {
            response.status(404); // 404 Not found
            resp = "Login (" + id + ") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}
}
