package service;

import dao.UsuarioDAO;
import model.Usuario;
import spark.Request;
import spark.Response;
import com.google.gson.Gson;




import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;



public class UsuarioService {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

 // Mapa para armazenar usuários autenticados com o CPF como chave
    private Map<String, Usuario> usuariosAutenticados = new HashMap<>();
    private Map<String, Usuario> usuariosLogados = new ConcurrentHashMap<>();
    
 

 // Método para inserir um novo usuário
    public boolean insertUsuario(Request request, Response response) {
        String cpf = request.queryParams("cpfInput");
        String nome = request.queryParams("nameInput");
        String email = request.queryParams("emailInput");
        String senha = request.queryParams("senhaInput");
        String telefone = request.queryParams("phoneInput");

        // Verifica se todos os campos estão preenchidos
        if (cpf == null || cpf.isEmpty() || nome == null || nome.isEmpty() || email == null || email.isEmpty() || senha == null || senha.isEmpty() || telefone == null || telefone.isEmpty()) {
            response.status(400);
            return false; // Retorna false em caso de falha
        }

        // Criptografar a senha usando MD5
        senha = hashMD5(senha);

        // Crie um novo objeto de usuário e tente inseri-lo no sistema
        Usuario novoUsuario = new Usuario(cpf, nome, email, senha, telefone);
        boolean insercaoSucesso = usuarioDAO.insert(novoUsuario);

        if (insercaoSucesso) {
            return true; // Retorna true em caso de sucesso
        } else {
            response.status(500);
            return false; // Retorna false em caso de falha
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

    

    // Método para obter a lista de todos os usuários
    public String getAllUsuarios(Request request, Response response) {
        // Obtém a lista de todos os usuários
        List<Usuario> usuarios = usuarioDAO.getAll();

        // Cria uma representação de texto da lista de usuários
        StringBuilder result = new StringBuilder("Lista de todos os usuários:\n");
        usuarios.forEach(usuario -> {
            result.append(usuario.getNome()).append(" - ").append(usuario.getEmail()).append("\n");
        });

        return result.toString();
    }

    // Método para obter um usuário para atualização
    public String getUsuarioToUpdate(Request request, Response response) {
        String cpf = request.params(":cpf");

        // Tente obter o usuário com o CPF especificado
        Usuario usuarioParaAtualizar = usuarioDAO.get(cpf);

        if (usuarioParaAtualizar != null) {
            return "Usuário encontrado para atualização: " + usuarioParaAtualizar.getNome();
        } else {
            response.status(404);
            return "Usuário com CPF " + cpf + " não encontrado para atualização.";
        }
    }

    // Método para atualizar as informações de um usuário
    public boolean updateUsuario(Request request, Response response) {
        String cpf = request.params(":cpf");
        String novoNome = request.queryParams("nome");
        String novoEmail = request.queryParams("email");
        String novaSenha = request.queryParams("senha");
        String novoTelefone = request.queryParams("phoneInput");

        // Crie um novo objeto de usuário com as informações atualizadas
        Usuario usuarioAtualizado = new Usuario(cpf, novoNome, novoEmail, novaSenha, novoTelefone);
        boolean atualizacaoSucesso = usuarioDAO.update(usuarioAtualizado);

        if (atualizacaoSucesso) {
            return true; // Retorna true em caso de sucesso
        } else {
            response.status(500);
            return false; // Retorna false em caso de falha
        }
    }

    // Método para excluir um usuário
    public boolean deleteUsuario(Request request, Response response) {
        String cpf = request.params(":cpf");

        // Tente excluir o usuário com o CPF especificado
        boolean exclusaoSucesso = usuarioDAO.delete(cpf);

        if (exclusaoSucesso) {
            return true; // Retorna true em caso de sucesso
        } else {
            response.status(500);
            return false; // Retorna false em caso de falha
        }
    }
    
    public Usuario getUsuarioByEmailSenha(String email, String senha) { 
        return usuarioDAO.getUsuarioByEmailSenha(email, senha); 
    }
    
    public Usuario getUsuarioLoged(String email, String nome, String telefone) { 
        return usuarioDAO.getUsuarioLoged(email, nome, telefone); 
    }
    
    public Object loginUsuario(Request request, Response response) {
        String email = request.queryParams("emailInput");
        String senha = request.queryParams("senhaInput");

        if (email == null || email.isEmpty() || senha == null || senha.isEmpty()) {
            response.status(400);
            return "Por favor, preencha todos os campos de email e senha.";
        }

        // Criptografa a senha fornecida pelo usuário para verificar a correspondência no banco de dados
        senha = hashMD5(senha);

        Usuario usuario = getUsuarioByEmailSenha(email, senha);

        if (usuario != null) {
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
    
    public String getNomeUsuarioLogado(Request request) {
        Object usuarioLogado = request.session().attribute("usuario");
        if (usuarioLogado instanceof Usuario) {
            return ((Usuario) usuarioLogado).getNome();
        }
        return null; // Ou retorne uma string vazia se o usuário não estiver logado
    }



    
    
    
    public String getUsuarioByCPF(Request request, Response response) {
        // Verifique se o usuário está autenticado
        Usuario usuarioLogado = request.session().attribute("usuario");
        if (usuarioLogado == null) {
            response.status(401); // Não autorizado
            return "Você não está autenticado. Faça login para acessar esta página.";
        }

        // Obtenha o CPF do usuário autenticado
        String cpfUsuarioLogado = usuarioLogado.getCpf();

        // Obtenha o CPF da URL
        String cpf = request.params(":cpf");

        // Verifique se o CPF na URL corresponde ao CPF do usuário autenticado
        if (!cpfUsuarioLogado.equals(cpf)) {
            response.status(403); // Acesso negado
            return "Acesso negado. Você não tem permissão para acessar esses dados.";
        }

        // Se o CPF corresponder, obtenha os dados do usuário a partir do banco de dados
        Usuario usuarioEncontrado = usuarioDAO.get(cpf);

        if (usuarioEncontrado != null) {
            // Aqui você pode retornar os dados do usuário em um formato adequado (por exemplo, JSON)
            Gson gson = new Gson();
            String jsonUsuario = gson.toJson(usuarioEncontrado);
            response.type("application/json");
            return jsonUsuario;
        } else {
            response.status(404);
            return "Usuário com CPF " + cpf + " não encontrado.";
        }
    }
    
    public void armazenarUsuarioAutenticado(String cpf, Usuario usuario) {
        usuariosAutenticados.put(cpf, usuario);
    }

    public Usuario obterUsuarioAutenticado(String cpf) {
        return usuariosAutenticados.get(cpf);
    }
    
 // Método para adicionar um usuário logado à estrutura de dados
    public void adicionarUsuarioLogado(String cpf, Usuario usuario) {
        usuariosLogados.put(cpf, usuario);
    }

    // Método para obter um usuário logado a partir do CPF
    public Usuario getUsuarioLogado(String cpf) {
        return usuariosLogados.get(cpf);
    }
    
    public String suasOperacoesDoCRUD(Request request, Response response) {
        // Verifique se o usuário está autenticado
        Usuario usuarioLogado = request.session().attribute("usuario");
        if (usuarioLogado == null) {
            response.status(401); // Não autorizado
            return "";
        }

        // Obtenha o CPF do usuário autenticado
        String cpfUsuarioLogado = usuarioLogado.getCpf();

        // Agora você pode usar os dados do usuário para operações do CRUD
        String nomeUsuario = usuarioLogado.getNome();
        String emailUsuario = usuarioLogado.getEmail();
        // E assim por diante...

        // Execute as operações do seu CRUD aqui e use os dados do usuário conforme necessário
        // ...

        // Por exemplo, você pode retornar os dados do usuário na resposta
        Gson gson = new Gson();
        String jsonUsuario = gson.toJson(usuarioLogado);
        response.type("application/json");
        return jsonUsuario;
    }
    
    public String nomeUsuarioLogado(Request request, Response response) {
    	String nomeUsuarioLogado = getNomeUsuarioLogado(request);
    	String conteudoDoFormulario = "";
    	
    	if (nomeUsuarioLogado != null) {
            conteudoDoFormulario = "Bem-vindo(a), " + nomeUsuarioLogado + "!";
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

