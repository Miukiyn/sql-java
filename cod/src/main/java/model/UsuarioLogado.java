package model;

public class UsuarioLogado {
    private Usuario usuario;

    public UsuarioLogado(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getNome() {
        return usuario.getNome();
    }

    public String getEmail() {
        return usuario.getEmail();
    }

    public String getTelefone() {
        return usuario.getTelefone();
    }
}
