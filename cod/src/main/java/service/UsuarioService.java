package service;

import dao.UsuarioDAO;
import model.Usuario;

import java.util.List;

public class UsuarioService {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public boolean insertUsuario(Usuario usuario) {
        return usuarioDAO.insert(usuario);
    }

    public Usuario getUsuarioByCPF(String cpf) { 
        return usuarioDAO.get(cpf); 
    }
    
    public Usuario getUsuarioByEmailSenha(String email, String senha) { 
        return usuarioDAO.getUsuarioByEmailSenha(email, senha); 
    }

    public List<Usuario> getAllUsuarios() {
        return usuarioDAO.getAll();
    }

    public boolean updateUsuario(Usuario usuario) {
        return usuarioDAO.update(usuario);
    }

    public boolean deleteUsuario(String cpf) { 
        return usuarioDAO.delete(cpf); 
    }

}
