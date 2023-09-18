package com.codigo.sql.model;

public class Login {
	// Objetos
	private int id;
	private String nome;
	private int idade;
	private String email;
	private String senha;
	
	
	// Construtor vazio
	public Login() {
		id = -1;
		nome = "";
		email = "";
		senha = "";
		idade = 0;
	}
	
	// Construtor com valores
	public Login(int id, String nome, String email, String senha, int idade) {
		setId(id);
		setNome(nome);
		setEmail(email);
		setSenha(senha);
		setIdade(idade);
	}
	
	// Metodos get and set
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	
	public int getIdade() {
		return idade;
	}
	public void setIdade(int idade) {
		this.idade = idade;
	}
	
	//Metodos para escrever os dados como String quando for preciso
	@Override
	public String toString() {
		return "Id: " + id + "   Nome: " + nome + "   Email: " + email + "   Senha: "
				+ senha  + "   Idade " + idade;
	}	
}
