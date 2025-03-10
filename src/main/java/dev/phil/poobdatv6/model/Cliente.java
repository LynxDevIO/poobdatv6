package dev.phil.poobdatv6.model;

import java.util.Date;

public class Cliente {
    private int idCliente;
    private int idUltimoItemComprado;
    private String nomeCliente;
    private String cpfCliente;
    private String emailCliente;
    private String[] enderecoCliente; // logradouro, bairro, cidade e estado
    private String[] produtosPreferidos;
    private Date dataNascimento;

    public Cliente() {
        // vazio
    }

    public Cliente(
            int id,
            String nome,
            String cpf,
            String email,
            Date dataNascimento,
            String[] endereco,
            int ultimoProdutoComprado,
            String[] produtosPreferidos
    ) {
        this.idCliente = id;
        this.nomeCliente = nome;
        this.cpfCliente = cpf;
        this.emailCliente = email;
        this.dataNascimento = dataNascimento;
        this.enderecoCliente = endereco;
        this.idUltimoItemComprado = ultimoProdutoComprado;
        this.produtosPreferidos = produtosPreferidos;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public int getIdUltimoItemComprado() {
        return idUltimoItemComprado;
    }

    public void setIdUltimoItemComprado(int idUltimoItemComprado) {
        this.idUltimoItemComprado = idUltimoItemComprado;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getCpfCliente() {
        return cpfCliente;
    }

    public void setCpfCliente(String cpfCliente) {
        this.cpfCliente = cpfCliente;
    }

    public String getEmailCliente() {
        return emailCliente;
    }

    public void setEmailCliente(String emailCliente) {
        this.emailCliente = emailCliente;
    }

    public String[] getEnderecoCliente() {
        return enderecoCliente;
    }

    public void setEnderecoCliente(String[] enderecoCliente) {
        this.enderecoCliente = enderecoCliente;
    }

    public String[] getProdutosPreferidos() {
        return produtosPreferidos;
    }

    public void setProdutosPreferidos(String[] produtosPreferidos) {
        this.produtosPreferidos = produtosPreferidos;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
}
