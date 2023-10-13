package service;

import dao.ONGDAO;
import model.ONG;
import model.OngLogada;
import model.Usuario;

import java.util.List;


import spark.Request;
import spark.Response;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class ONGService {
	
    private ONGDAO ongDAO = new ONGDAO();

    public boolean insertONG(ONG ong) {
        return ongDAO.insert(ong);
    }

    public ONG getONGBySigla(String sigla) {
        return ongDAO.get(sigla);
    }

    public ONG getONGByEmailSenha(String email, String senha) {
        return ongDAO.getONGByEmailSenha(email, senha);
    }

    public List<ONG> getAllONGs() {
        return ongDAO.getAll();
    }

    public boolean updateONG(ONG ong) {
        return ongDAO.update(ong);
    }

    public boolean deleteONG(String sigla) {
        return ongDAO.delete(sigla);
    }
 // Método público para inserir uma nova ONG
    public String insertONG(Request request, Response response) {
        String sigla = request.queryParams("siglaInput");
        String siteLink = request.queryParams("siteLinkInput");
        String nome = request.queryParams("nomeInput");
        String descricao = request.queryParams("descricaoInput");
        String email = request.queryParams("emailInput");
        String emailComercial = request.queryParams("emailComercialInput");
        String senha = request.queryParams("senhaInput");

        // Verifica se todos os campos obrigatórios estão preenchidos
        if (sigla == null || sigla.isEmpty() || nome == null || nome.isEmpty() || descricao == null || descricao.isEmpty() || email == null || email.isEmpty() || senha == null || senha.isEmpty()) {
            response.status(400);
            return "Por favor, preencha todos os campos obrigatórios. {Sigla, Nome, Descricao, email, senha}";
        }

        // Criptografa a senha usando MD5
        senha = hashMD5(senha);

        // Crie um novo objeto ONG e tente inseri-lo no sistema
        ONG novaOng = new ONG(sigla, siteLink, nome, descricao, email, emailComercial, senha);
        boolean insercaoSucesso = ongDAO.insert(novaOng);

        if (insercaoSucesso) {
            return "Registro efetuado com sucesso"; // Retorna uma mensagem de sucesso
        } else {
            response.status(500);
            return "Houve algum erro ao fazer o registro"; // Retorna uma mensagem de erro
        }
    }

    // Método para criar um hash MD5 da senha
    private String hashMD5(String senha) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(senha.getBytes());

            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                String hex = Integer.toHexString(0xFF & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null; // Em caso de erro na criptografia
        }
    }


    // Método público para obter uma ONG com base na sigla
    public String getONGBySigla(Request request, Response response) {
        String sigla = request.params(":sigla");
        ONG ongEncontrada = ongDAO.get(sigla);

        if (ongEncontrada != null) {
            return "ONG encontrada: " + ongEncontrada.getNome();
        } else {
            response.status(404);
            return "ONG com sigla " + sigla + " não encontrada.";
        }
    }
    
    
    
    
    public Object loginOng(Request request, Response response) {
        String email = request.queryParams("emailInput");
        String senha = request.queryParams("senhaInput");

        if (email == null || email.isEmpty() || senha == null || senha.isEmpty()) {
            response.status(400);
            return "Por favor, preencha todos os campos de email e senha.";
        }

        // Criptografa a senha fornecida pelo usuário para verificar a correspondência no banco de dados
        senha = hashMD5(senha);

        ONG ong = getONGByEmailSenha(email, senha); // Substitua 'getOngByEmailSenha' pela sua lógica de busca de ONG por email e senha

        if (ong != null) {
            // Configure a sessão com a ONG autenticada
            OngLogada ongLogada = new OngLogada(ong);
            request.session().attribute("ongLogada", ongLogada);

            // Redirecione para a página do perfil da ONG ou outra página apropriada
            response.redirect("/index.html?user=" + ong.getNome());
            return null;
        } else {
            response.status(401);
            return "Credenciais inválidas. Por favor, verifique seu email e senha.";
        }
    }

    
    public String getNomeOngLogada(Request request) {
        Object ongLogada = request.session().attribute("ongLogada");
        if (ongLogada instanceof OngLogada) {
            return ((OngLogada) ongLogada).getNome();
        }
        return null; // Ou retorne uma string vazia se a ONG não estiver logada
    }

    
    public String nomeOngLogada(Request request, Response response) {
        String nomeOngLogada = getNomeOngLogada(request);
        String conteudoDoFormulario = "";

        if (nomeOngLogada != null) {
            conteudoDoFormulario = "Bem-vindo(a), " + nomeOngLogada + "!";
        } else {
            response.status(401);
            conteudoDoFormulario = "";
        }

        String paginaHTML = "<html><body>" +
                "<form method='GET' action='/alguma-rota'>" +
                conteudoDoFormulario +
                "</form></body></html>";

        return paginaHTML;
    }


}



































