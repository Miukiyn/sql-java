package dao;

import model.Usuario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO extends DAO {
    public UsuarioDAO() {
        super();
        conectar(); 
    }

    public void finalize() {
        close(); 
    } 

    public boolean insert(Usuario usuario) {
        boolean status = false;
        try {
            // Certifica-se de que a conexão está disponível antes de prosseguir.
            if (conexao != null) {
                String sql = "INSERT INTO usuario (cpf, nome, email, senha, telefone) VALUES (?, ?, ?, ?, ?);";
                PreparedStatement st = conexao.prepareStatement(sql);
                st.setString(1, usuario.getCpf());
                st.setString(2, usuario.getNome());
                st.setString(3, usuario.getEmail());
                st.setString(4, usuario.getSenha());
                st.setString(5, usuario.getTelefone());
                st.executeUpdate();
                st.close();
                status = true;
            } else {
                System.err.println("Conexao nao inicializada.");
            }
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        return status;
    }


    public Usuario get(String cpf) {
        Usuario usuario = null;
        try {
            String sql = "SELECT * FROM usuario WHERE cpf=?";
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setString(1, cpf);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                usuario = new Usuario(rs.getString("cpfInput"), rs.getString("nomeInput"), rs.getString("emailInput"),
                        rs.getString("senhaInput"), rs.getString("phoneInput"));
            }
            st.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return usuario;
    }


    public List<Usuario> getAll() {
        List<Usuario> usuarios = new ArrayList<>();
        try {
            Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String sql = "SELECT * FROM usuario";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Usuario usuario = new Usuario(rs.getString("cpfInput"), rs.getString("nomeInput"), rs.getString("emailInput"),
                        rs.getString("senhaInput"), rs.getString("phoneInput"));
                usuarios.add(usuario);
            }
            st.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return usuarios;
    }
    
    public Usuario getUsuarioByEmailSenha(String email, String senha) {
                
        String sql = "SELECT * FROM usuario WHERE email = ? AND senha = ?";
        
        try (PreparedStatement st = conexao.prepareStatement(sql)) {
            st.setString(1, email);
            st.setString(2, senha);
            
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(rs.getString("cpf"), rs.getString("nome"), rs.getString("email"), rs.getString("senha"), rs.getString("telefone"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }


    public boolean update(Usuario usuario) {
        boolean status = false;
        try {
            String sql = "UPDATE usuario SET cpf = ?, nome = ?, email = ?, senha = ?, telefone = ? WHERE id = ?";
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setString(1, usuario.getCpf());
            st.setString(2, usuario.getNome());
            st.setString(3, usuario.getEmail());
            st.setString(4, usuario.getSenha());
            st.setString(5, usuario.getTelefone());
            st.executeUpdate();
            st.close();
            status = true;
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        return status;
    }


    public boolean delete(String cpf) {
        boolean status = false;
        try {
            String sql = "DELETE FROM usuario WHERE cpf = ?";
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setString(1, cpf);
            st.executeUpdate();
            st.close();
            status = true;
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        return status;
    }


}
