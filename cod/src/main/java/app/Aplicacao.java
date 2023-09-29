package app;

import static spark.Spark.*;

import model.Usuario;
import service.UsuarioService;
import spark.Request;
import spark.Response;

public class Aplicacao {

    private static UsuarioService usuarioService = new UsuarioService();

    public static void main(String[] args) {
        port(6789);

        staticFiles.location("/public");

        post("/usuario/insert", (request, response) -> {
            String cpf = request.queryParams("cpfInput");
            String nome = request.queryParams("nameInput");
            String email = request.queryParams("emailInput");
            String senha = request.queryParams("senhaInput");
            String telefone = request.queryParams("phoneInput");

            if (cpf == null || cpf.isEmpty() || nome == null || nome.isEmpty() || email == null || email.isEmpty() || senha == null || senha.isEmpty() || telefone == null || telefone.isEmpty()) {
                response.status(400);
                return "Por favor, preencha todos os campos antes de registrar.";
            }

            Usuario novoUsuario = new Usuario(cpf, nome, email, senha, telefone);
            boolean insercaoSucesso = usuarioService.insertUsuario(novoUsuario);

            if (insercaoSucesso) {
                return "Usuário inserido com sucesso!";
            } else {
                response.status(500);
                return "Falha ao inserir o usuário.";
            }
        });

        get("/usuario/:cpf", (request, response) -> {
            String cpf = request.params(":cpf");
            Usuario usuarioEncontrado = usuarioService.getUsuarioByCPF(cpf);

            if (usuarioEncontrado != null) {
                return "Usuário encontrado: " + usuarioEncontrado.getNome();
            } else {
                response.status(404);
                return "Usuário com CPF " + cpf + " não encontrado.";
            }
        });

        get("/usuario/list", (request, response) -> {
            StringBuilder result = new StringBuilder("Lista de todos os usuários:\n");
            usuarioService.getAllUsuarios().forEach(usuario -> {
                result.append(usuario.getNome()).append(" - ").append(usuario.getEmail()).append("\n");
            });

            return result.toString();
        });

        get("/usuario/update/:cpf", (request, response) -> {
            String cpf = request.params(":cpf");
            Usuario usuarioParaAtualizar = usuarioService.getUsuarioByCPF(cpf);

            if (usuarioParaAtualizar != null) {
                return "Usuário encontrado para atualização: " + usuarioParaAtualizar.getNome();
            } else {
                response.status(404);
                return "Usuário com CPF " + cpf + " não encontrado para atualização.";
            }
        });

        post("/usuario/update/:cpf", (request, response) -> {
            String cpf = request.params(":cpf");
            String novoNome = request.queryParams("nome");
            String novoEmail = request.queryParams("email");
            String novaSenha = request.queryParams("senha");
            String novoTelefone = request.queryParams("phoneInput");

            Usuario usuarioAtualizado = new Usuario(cpf, novoNome, novoEmail, novaSenha, novoTelefone);
            boolean atualizacaoSucesso = usuarioService.updateUsuario(usuarioAtualizado);

            if (atualizacaoSucesso) {
                return "Usuário atualizado com sucesso!";
            } else {
                response.status(500);
                return "Falha ao atualizar o usuário.";
            }
        });

        get("/usuario/delete/:cpf", (request, response) -> {
            String cpf = request.params(":cpf");
            boolean exclusaoSucesso = usuarioService.deleteUsuario(cpf);

            if (exclusaoSucesso) {
                return "Usuário excluído com sucesso!";
            } else {
                response.status(500);
                return "Falha ao excluir o usuário.";
            }
        });
    }
}
