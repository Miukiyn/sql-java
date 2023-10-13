package model;

public class Animal {
    private String nome;
    private String raca;
    private String ongName;
    private String descricao;
    private String especie;
    private String cidade;
    private String estado;
    private String fotoURL;
    private String caracteristica;
    private String tamanho;

    public Animal(String nome, String raca, String ongName, String descricao, String especie, String cidade, String estado, String fotoURL, String caracteristica, String tamanho) {
        this.nome = nome;
        this.raca = raca;
        this.ongName = ongName;
        this.descricao = descricao;
        this.especie = especie;
        this.cidade = cidade;
        this.estado = estado;
        this.fotoURL = fotoURL;
        this.caracteristica = caracteristica;
        this.tamanho = tamanho;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }
    
    public String getOngName() {
        return ongName;
    }

    public void setOngName(String ongName) {
        this.ongName = ongName;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFotoURL() {
        return fotoURL;
    }

    public void setFotoURL(String fotoURL) {
        this.fotoURL = fotoURL;
    }
    
    public String getCaracteristica() {
        return caracteristica;
    }

    public void setCaracteristica(String caracteristica) {
        this.caracteristica = caracteristica;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    @Override
    public String toString() {
        return "Animal " + nome + "   Raça: " + raca +
            "   Descrição: " + descricao + "   Espécie: " + especie +
            "   Cidade: " + cidade + "   Estado: " + estado +
            "   Característica: " + caracteristica + "   Tamanho: " + tamanho;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Animal animal = (Animal) obj;
        return nome.equals(animal.nome) &&
            raca.equals(animal.raca) &&
            ongName.equals(animal.ongName) &&
            descricao.equals(animal.descricao) &&
            especie.equals(animal.especie) &&
            cidade.equals(animal.cidade) &&
            estado.equals(animal.estado) &&
            fotoURL.equals(animal.fotoURL) &&
            caracteristica.equals(animal.caracteristica) &&
            tamanho.equals(animal.tamanho);
    }
}
