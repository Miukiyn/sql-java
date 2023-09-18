package com.codigo.sql.dao;

import com.codigo.sql.model.Login;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

//Criar uma classe entendida de DAO.java 
public class LoginDAO extends DAO {
	
	public LoginDAO() {	
		super();
		conectar();
	}
	
	public void finalize() {
		close();
	}
	
	public boolean insert(Login login) {
		boolean status = false;
		try {
			String sql = "INSERT INTO usuarios (id, nome, email, senha, idade) "
		               + "VALUES ('" + login.getId() + "', "
		               + login.getNome() + ", " + login.getEmail() + login.getSenha() +", ?, ?);";
			PreparedStatement st = conexao.prepareStatement(sql);
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public Login get(int id) {
		Login login = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM usuarios WHERE id="+id;
			ResultSet rs = st.executeQuery(sql);	
	        if(rs.next()){            
	        	 login = new Login(rs.getInt("id"), rs.getString("Nome"), rs.getString("Email"), rs.getString("senha"), rs.getInt("idade"));
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return login;
	}
	
	public List<Login> get() {
		return get("");
	}

	
	public List<Login> getOrderByID() {
		return get("id");		
	}
	
	
	public List<Login> getOrderByIdade() {
		return get("idade");		
	}
	
	private List<Login> get(String orderBy) {
		List<Login> login = new ArrayList<Login>();
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM usuarios" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
			ResultSet rs = st.executeQuery(sql);	           
	        while(rs.next()) {	            	
	        	Login p = new Login(rs.getInt("id"), rs.getString("Nome"), rs.getString("Email"), rs.getString("senha"), rs.getInt("idade"));
	            login.add(p);
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return login;
	}
	
	public boolean update(Login login) {
		boolean status = false;
		try {  
			String sql = "UPDATE usuarios SET email = '" + login.getEmail() + "', "
					   + "senha = " + login.getSenha() + ", " 
					   + "idade = " + login.getIdade() + ",";
			PreparedStatement st = conexao.prepareStatement(sql);
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public boolean delete(int id) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM usuarios WHERE id = " + id);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}

}
