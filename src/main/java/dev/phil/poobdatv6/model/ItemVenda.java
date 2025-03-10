package dev.phil.poobdatv6.model;

public class ItemVenda {
    private int id;
    private Venda venda;
    private Produto produto;
    private int quantidade;
    private float valorTotal;

    public ItemVenda() {
        // vazio
    }

    public ItemVenda(
            int id,
            Venda venda,
            Produto produto,
            int quantidade,
            float valorTotal
    ) {
        this.id = id;
        this.venda = venda;
        this.produto = produto;
        this.quantidade = quantidade;
        this.valorTotal = valorTotal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public float getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(float valorTotal) {
        this.valorTotal = valorTotal;
    }
}