package dev.phil.poobdatv6.model;

public class Municipio {
    private int id;
    private String nome;
    private int ufId;
    private UF uf;

    public Municipio() {
        // vazio
    }

    public Municipio(int id, String nome, int ufId) {
        this.id = id;
        this.nome = nome;
        this.ufId = ufId;
    }

    public Municipio(int id, String nome, UF uf) {
        this.id = id;
        this.nome = nome;
        this.uf = uf;
        this.ufId = uf.getId();
    }

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

    public int getUfId() {
        return ufId;
    }

    public void setUfId(int ufId) {
        this.ufId = ufId;
    }

    public UF getUf() {
        return uf;
    }

    public void setUf(UF uf) {
        this.uf = uf;
        if (uf != null) {
            this.ufId = uf.getId();
        }
    }

    @Override
    public String toString() {
        return nome;
    }
} 