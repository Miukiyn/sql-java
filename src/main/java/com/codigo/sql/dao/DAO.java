package com.codigo.sql.dao;

//Bibliotecas
import java.sql.*;

public class DAO {
	
	//Objeto
	protected Connection conexao;
	
	//Construtor
	public DAO() {
		conexao = null;
	}
	
	//Metodo de conexao
	public boolean conectar() {
		//Dados de acesso ao Banco de dados
		String driverName = "org.postgresql.Driver";                    
		String serverName = "localhost";
		String mydatabase = "login"; //Criar um database no pgAdmin como metodo de login criptografado
		int porta = 5432;
		String url = "jdbc:postgresql://" + serverName + ":" + porta +"/" + mydatabase;
		String username = "postgres"; //Trocar o username e a senha para o meu
		String password = "";
		boolean status = false;
		
		//iniciar a conexao
		try {
			Class.forName(driverName);
			conexao = DriverManager.getConnection(url, username, password);
			status = (conexao == null);
			System.out.println("Conexão efetuada com o postgres!");
		} catch (ClassNotFoundException e) { 
			System.err.println("Conexão NÃO efetuada com o postgres -- Driver não encontrado -- " + e.getMessage());
		} catch (SQLException e) {
			System.err.println("Conexão NÃO efetuada com o postgres -- " + e.getMessage());
		}

		return status;
	}
	
	//Fechar a conexao
	public boolean close() {
		boolean status = false;
		
		try {
			conexao.close();
			status = true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return status;
	}
}
