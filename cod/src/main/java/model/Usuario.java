package model;

public class Usuario {
    private String cpf; 
    private String nome;
    private String email;
    private String senha;
    private String telefone;

    public Usuario() {
        cpf = "";
        nome = "";
        email = "";
        senha = "";
        telefone = "";
    }

    public Usuario(String cpf, String nome, String email, String senha, String telefone) {
        setCpf(cpf);
        setNome(nome);
        setEmail(email);
        setSenha(senha);
        setTelefone(telefone);
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) { 
        
        this.cpf = cpf;
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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    
    /**
	 * Método sobreposto da classe Object. É executado quando um objeto precisa
	 * ser exibido na forma de String.
	 */
    @Override
    public String toString() {
        return "Contato: " + nome + " CPF: " + cpf + " Email: " + email + "   Telefone: " + telefone;
    }

    @Override
    public boolean equals(Object obj) {
        return this.getCpf().equals(((Usuario) obj).getCpf());
    }
}
