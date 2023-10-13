package model;

public class OngLogada {
    private ONG ong;

    public OngLogada(ONG ong) {
        this.ong = ong;
    }

    public String getSigla() {
        return ong.getSigla();
    }

    public String getNome() {
        return ong.getNome();
    }

    public String getDescricao() {
        return ong.getDescricao();
    }

    public String getEmail() {
        return ong.getEmail();
    }

    public String getEmailComercial() {
        return ong.getemail_comercial();
    }
}
