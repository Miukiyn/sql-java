package service;

import dao.ONGDAO;
import dao.UsuarioDAO;
import model.Animal;
import dao.AnimalDAO;
import model.ONG;
import model.OngLogada;
import model.Usuario;
import spark.Request;
import spark.Response;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginService {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private ONGDAO ongDAO = new ONGDAO();
    private AnimalDAO animalDAO = new AnimalDAO();

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
    
    public Usuario getUsuarioByEmailSenha(String email, String senha) { 
        return usuarioDAO.getUsuarioByEmailSenha(email, senha); 
    }
    
    public ONG getONGByEmailSenha(String email, String senha) { 
        return ongDAO.getONGByEmailSenha(email, senha); 
    }
    

    public Object login(Request request, Response response) {
        String email = request.queryParams("emailInput");
        String senha = request.queryParams("senhaInput");

        if (email == null || email.isEmpty() || senha == null || senha.isEmpty()) {
            response.status(400);
            return "Por favor, preencha todos os campos de email e senha.";
        }

        senha = hashMD5(senha);

        ONG ong = getONGByEmailSenha(email, senha);
        Usuario usuario = getUsuarioByEmailSenha(email, senha);

        if (ong != null) {
            OngLogada ongLogada = new OngLogada(ong);
            request.session().attribute("ongLogada", ongLogada);
            response.redirect("/index.html?user=" + ong.getNome());
            return null;
        } else if (usuario != null) {
        	// Configure a sessão com o usuário autenticado
            request.session().attribute("usuario", usuario);

            // Redirecione para a página de perfil com o CPF como parte da URL
            response.redirect("/index.html?user=" + usuario.getCpf());
            return null;
        } else {
            response.status(401);
            return "Credenciais inválidas. Por favor, verifique seu email e senha.";
        }
    }

    public String getLoged(Request request) {
        Object ongLogada = request.session().attribute("ongLogada");
        Object usuario = request.session().attribute("usuario");

        if (ongLogada != null && usuario == null) {
            return "ONG: " + ((OngLogada) ongLogada).getNome();
        } else if (usuario != null && ongLogada == null) {
            return "User: " + ((Usuario) usuario).getNome();
        }

        return null;
    }

    public String nomeUserOrONG(Request request, Response response) {
        String nomeLogado = getLoged(request);
        String conteudoDoFormulario = "";

        if (nomeLogado != null) {
            conteudoDoFormulario = nomeLogado + " Bem-vindo(a)";
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
    
 // Método para inserir um novo animal
    public String insertAnimal(Request request, Response response) {
        // Verifique se uma ONG está logada
        String logedUser = getLoged(request);
        if (logedUser != null && logedUser.startsWith("ONG:")) {
            // Extraia o nome da ONG do retorno da função getLoged usando split
            String[] parts = logedUser.split(":");
            if (parts.length == 2) {
                String ongName = parts[1].trim();

                // Obtém os parâmetros do formulário
                String nome = request.queryParams("nomeInput");
                String raca = request.queryParams("racaInput");
                String descricao = request.queryParams("descricaoInput");
                String caracteristica = request.queryParams("caracteristicaInput");
                String tamanho = request.queryParams("tamanhoInput");
                String especie = request.queryParams("especieInput");
                String estado = request.queryParams("estadoInput");
                String cidade = request.queryParams("cidadeInput");
                String fotoURL = request.queryParams("fotoInput");

                // Verifica se todos os campos obrigatórios estão preenchidos
                if (nome == null || nome.isEmpty() || raca == null || raca.isEmpty() ||
                    descricao == null || descricao.isEmpty() || caracteristica == null || caracteristica.isEmpty() ||
                    tamanho == null || tamanho.isEmpty() || especie == null || especie.isEmpty() ||
                    estado == null || estado.isEmpty() || cidade == null || cidade.isEmpty() ||
                    fotoURL == null || fotoURL.isEmpty()) {
                    response.status(400);
                    return "Preencha todos os campos obrigatórios.";
                }

                // Crie um novo objeto de animal e configure os valores
                Animal novoAnimal = new Animal(nome, raca, ongName, descricao, especie, cidade, estado, fotoURL, caracteristica, tamanho);

                boolean insercaoSucesso = animalDAO.insert(novoAnimal);

                if (insercaoSucesso) {
                    return "Inserção bem-sucedida para a ONG: " + ongName;
                } else {
                    response.status(500);
                    return "A inserção falhou.";
                }
            }
        }

        response.status(401);
        return "Apenas ONGs podem inserir animais.";
    }


}
