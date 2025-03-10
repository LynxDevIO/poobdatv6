package dev.phil.poobdatv6.model;

public class Produto {
    private int idProduto;
    private float precoVendaProduto;
    private float precoCustoProduto;
    private int quantidadeEstocada;
    private float percentualMarkup;
    private String nomeProduto;
    private String descricaoProduto;

    public Produto() {
        // vazio
    }

    public Produto(
            int id,
            String nome,
            String descricao,
            int precoVenda,
            int precoCusto,
            int estoque,
            float markup
    ) {
        this.idProduto = id;
        this.nomeProduto = nome;
        this.descricaoProduto = descricao;
        this.precoVendaProduto = precoVenda;
        this.precoCustoProduto = precoCusto;
        this.quantidadeEstocada = estoque;
        this.percentualMarkup = markup;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public float getPrecoVendaProduto() {
        return precoVendaProduto;
    }

    public void setPrecoVendaProduto(float precoVendaProduto) {
        this.precoVendaProduto = precoVendaProduto;
    }

    public float getPrecoCustoProduto() {
        return precoCustoProduto;
    }

    public void setPrecoCustoProduto(float precoCustoProduto) {
        this.precoCustoProduto = precoCustoProduto;
    }

    public int getQuantidadeEstocada() {
        return quantidadeEstocada;
    }

    public void setQuantidadeEstocada(int quantidadeEstocada) {
        this.quantidadeEstocada = quantidadeEstocada;
    }

    public float getPercentualMarkup() {
        return percentualMarkup;
    }

    public void setPercentualMarkup(float percentualMarkup) {
        this.percentualMarkup = percentualMarkup;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public String getDescricaoProduto() {
        return descricaoProduto;
    }

    public void setDescricaoProduto(String descricaoProduto) {
        this.descricaoProduto = descricaoProduto;
    }

    @Override
    public String toString() {
        return nomeProduto + " - R$ " + String.format("%.2f", precoVendaProduto);
    }
}
