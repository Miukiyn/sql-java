package dao;

import model.ONG;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ONGDAO extends DAO {
    public ONGDAO() {
        super();
        conectar();
    }

    public void finalize() {
        close();
    }

    public boolean insert(ONG ong) {
        boolean status = false;
        try {
            String sql = "INSERT INTO ong (sigla, site_link, nome, descricao, email, email_comercial, senha) VALUES (?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setString(1, ong.getSigla());
            st.setString(2, ong.getsite_link());
            st.setString(3, ong.getNome());
            st.setString(4, ong.getDescricao());
            st.setString(5, ong.getEmail());
            st.setString(6, ong.getemail_comercial());
            st.setString(7, ong.getSenha());
            st.executeUpdate();
            st.close();
            status = true;
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        return status;
    }


    public ONG get(String sigla) {
        ONG ong = null;
        try {
            String sql = "SELECT * FROM ong WHERE sigla=?";
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setString(1, sigla);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                ong = new ONG(rs.getString("sigla"), rs.getString("site_link"), rs.getString("nome"),
                        rs.getString("descricao"), rs.getString("email"), rs.getString("email_comercial"), rs.getString("senha"));
            }
            st.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return ong;
    }


    public List<ONG> getAll() {
        List<ONG> ongs = new ArrayList<>();
        try {
            Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String sql = "SELECT * FROM ong";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                ONG ong = new ONG(rs.getString("sigla"), rs.getString("site_link"), rs.getString("nome"),
                        rs.getString("descricao"), rs.getString("email"), rs.getString("email_comercial"), rs.getString("senha"));
                ongs.add(ong);
            }
            st.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return ongs;
    }
    
    public static ONG getONGByEmailSenha(String email, String senha) {
                
        String sql = "SELECT * FROM ong WHERE email = ? AND senha = ?";
        
        try (PreparedStatement st = conexao.prepareStatement(sql)) {
            st.setString(1, email);
            st.setString(2, senha);
            
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return new ONG(rs.getString("sigla"), rs.getString("site_link"), rs.getString("nome"),
                        rs.getString("descricao"), rs.getString("email"), rs.getString("email_comercial"), rs.getString("senha"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }


    public boolean update(ONG ong) {
        boolean status = false;
        try {
            String sql = "UPDATE ong SET site_link = ?, nome = ?, descricao = ?, email = ?, email_comercial = ?, senha = ? WHERE sigla = ?";
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setString(1, ong.getsite_link());
            st.setString(2, ong.getNome());
            st.setString(3, ong.getDescricao());
            st.setString(4, ong.getEmail());
            st.setString(5, ong.getemail_comercial());
            st.setString(6, ong.getSenha());
            st.setString(7, ong.getSigla());
            st.executeUpdate();
            st.close();
            status = true;
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        return status;
    }


    public boolean delete(String sigla) {
        boolean status = false;
        try {
            String sql = "DELETE FROM ong WHERE sigla = ?";
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setString(1, sigla);
            st.executeUpdate();
            st.close();
            status = true;
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        return status;
    }


}
