package dev.phil.poobdatv6.model;

import java.util.Date;
import java.util.List;

public class Venda {
    private int idVenda;
    private Cliente clienteVenda;
    private List<Produto> produtosVenda;
    private float totalVenda;
    private Date dataVenda;

    public Venda() {
        // vazio
    }

    public Venda(
            int idVenda,
            Cliente clienteVenda,
            List<Produto> produtosVenda,
            float totalVenda,
            Date dataVenda
    ) {
        this.idVenda = idVenda;
        this.clienteVenda = clienteVenda;
        this.produtosVenda = produtosVenda;
        this.totalVenda = totalVenda;
        this.dataVenda = dataVenda;
    }

    public int getIdVenda() {
        return idVenda;
    }

    public void setIdVenda(int idVenda) {
        this.idVenda = idVenda;
    }

    public Cliente getClienteVenda() {
        return clienteVenda;
    }

    public void setClienteVenda(Cliente clienteVenda) {
        this.clienteVenda = clienteVenda;
    }

    public List<Produto> getProdutosVenda() {
        return produtosVenda;
    }

    public void setProdutosVenda(List<Produto> produtosVenda) {
        this.produtosVenda = produtosVenda;
    }

    public float getTotalVenda() {
        return totalVenda;
    }

    public void setTotalVenda(float totalVenda) {
        this.totalVenda = totalVenda;
    }

    public Date getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(Date dataVenda) {
        this.dataVenda = dataVenda;
    }
}
