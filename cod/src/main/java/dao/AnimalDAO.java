package dao;

import model.Animal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AnimalDAO extends DAO {
    public AnimalDAO() {
        super();
        conectar();
    }

    public void finalize() {
        close();
    }

    public boolean insert(Animal animal) {
        boolean status = false;
        try {
            if (conexao != null) {
                String sql = "INSERT INTO animal (nome, raca, ong, descricao, especie, cidade, estado, foto, caracteristica, tamanho) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement st = conexao.prepareStatement(sql);
                st.setString(1, animal.getNome());
                st.setString(2, animal.getRaca());
                st.setString(3, animal.getOngName());
                st.setString(4, animal.getDescricao());
                st.setString(5, animal.getEspecie());
                st.setString(6, animal.getCidade());
                st.setString(7, animal.getEstado());
                st.setString(8, animal.getFotoURL());
                st.setString(9, animal.getCaracteristica());
                st.setString(10, animal.getTamanho());
                st.executeUpdate();
                st.close();
                status = true;
            } else {
                System.err.println("Conexão não inicializada.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Trate a exceção apropriadamente em vez de lançar uma RuntimeException
        }
        return status;
    }

    public List<Animal> getRandomAnimals(int numberOfAnimals) {
        List<Animal> randomAnimals = new ArrayList<>();
        try {
            String sql = "SELECT * FROM animal ORDER BY RANDOM() LIMIT ?";
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setInt(1, numberOfAnimals);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
            	Animal animal = new Animal(
            		    rs.getString("nome"),
            		    rs.getString("raca"),
            		    rs.getString("ong"), 
            		    rs.getString("descricao"),
            		    rs.getString("especie"),
            		    rs.getString("cidade"),
            		    rs.getString("estado"),
            		    rs.getString("foto"),
            		    rs.getString("caracteristica"),
            		    rs.getString("tamanho")
            		);

                randomAnimals.add(animal);
            }
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Trate a exceção apropriadamente em vez de imprimir a mensagem
        }
        return randomAnimals;
    }

    public Animal get(int id) {
        Animal animal = null;
        try {
            String sql = "SELECT * FROM animal WHERE id = ?";
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                animal = new Animal(
                        rs.getString("nome"),
                        rs.getString("raca"),
                        rs.getString("ong"),
                        rs.getString("descricao"),
                        rs.getString("especie"),
                        rs.getString("cidade"),
                        rs.getString("estado"),
                        rs.getString("foto"),
                        rs.getString("caracteristica"),
                        rs.getString("tamanho")
                );
            }
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Trate a exceção apropriadamente em vez de imprimir a mensagem
        }
        return animal;
    }

    public List<Animal> getAll() {
        List<Animal> animais = new ArrayList<>();
        try {
            Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String sql = "SELECT * FROM animal";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Animal animal = new Animal(
                        rs.getString("nome"),
                        rs.getString("raca"),
                        rs.getString("ong"),
                        rs.getString("descricao"),
                        rs.getString("especie"),
                        rs.getString("cidade"),
                        rs.getString("estado"),
                        rs.getString("foto"),
                        rs.getString("caracteristica"),
                        rs.getString("tamanho")
                );
                animais.add(animal);
            }
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Trate a exceção apropriadamente em vez de imprimir a mensagem
        }
        return animais;
    }

    public boolean delete(int id) {
        boolean status = false;
        try {
            String sql = "DELETE FROM animal WHERE id = ?";
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setInt(1, id);
            st.executeUpdate();
            st.close();
            status = true;
        } catch (SQLException e) {
            e.printStackTrace();
            // Trate a exceção apropriadamente em vez de imprimir a mensagem
        }
        return status;
    }
}
