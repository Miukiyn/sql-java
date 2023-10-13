package model;

public class ONG {
    private String sigla;
    private String site_link;
    private String nome;
    private String descricao;
    private String email;
    private String email_comercial;
    private String senha;

    public ONG() {
        sigla = "";
        site_link = "";
        nome = "";
        descricao = "";
        email = "";
        senha = "";
        email_comercial = "";
    }

    public ONG(String sigla, String site_link, String nome, String descricao, String email, String email_comercial, String senha) {
        setSigla(sigla);
        setsite_link(site_link);
        setNome(nome);
        setDescricao(descricao);
        setEmail(email);
        setemail_comercial(email_comercial);
        setSenha(senha);
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getsite_link() {
        return site_link;
    }

    public void setsite_link(String site_link) {
        this.site_link = site_link;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getemail_comercial() {
        return email_comercial;
    }

    public void setemail_comercial(String email_comercial) {
        this.email_comercial = email_comercial;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}