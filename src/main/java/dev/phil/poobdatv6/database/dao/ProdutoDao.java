package dev.phil.poobdatv6.database.dao;

import dev.phil.poobdatv6.database.DBConnection;
import dev.phil.poobdatv6.model.Produto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDao implements IProdutoDao{
    private Connection connection;

    public ProdutoDao() throws SQLException {
        this.connection = DBConnection.getInstance().getConnection();
    }

    @Override
    public void criarTabela() throws SQLException {
        String sql = "create table if not exists produto (" +
                "id_produto int generated always as identity primary key, " +
                "nome varchar(255) not null, " +
                "descricao varchar (255) not null, " +
                "preco_venda numeric(10, 2) not null, " +
                "preco_custo numeric(10, 2) not null, " +
                "estoque int not null, " +
                "markup numeric(10, 2) not null" +
                ")";

        Statement st = connection.createStatement();
        st.execute(sql);
        st.close();
    }

    @Override
    public void inserirProduto(Produto produto) throws SQLException {
        String sql = "insert into produto (nome, descricao, preco_venda, preco_custo, estoque, markup) " +
                "values (?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, produto.getNomeProduto());
        ps.setString(2, produto.getDescricaoProduto());
        ps.setFloat(3, produto.getPrecoVendaProduto());
        ps.setFloat(4, produto.getPrecoCustoProduto());
        ps.setInt(5, produto.getQuantidadeEstocada());
        ps.setFloat(6, produto.getPercentualMarkup());
        ps.executeUpdate();
        ps.close();
    }

    @Override
    public void alterarProdutoPorId(int id, Produto produto) throws SQLException {
        String sql = "update produto " +
                "set nome = ?, descricao = ?, preco_venda = ?, preco_custo = ?, estoque = ?, markup = ? " +
                "where id_produto = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, produto.getNomeProduto());
        ps.setString(2, produto.getDescricaoProduto());
        ps.setFloat(3, produto.getPrecoVendaProduto());
        ps.setFloat(4, produto.getPrecoCustoProduto());
        ps.setInt(5, produto.getQuantidadeEstocada());
        ps.setFloat(6, produto.getPercentualMarkup());
        ps.setInt(7, id);
        ps.executeUpdate();
        ps.close();
    }

    @Override
    public void excluirProdutoPorId(int id) throws SQLException {
        String sql = "delete from produto where id_produto = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        ps.close();
    }

    @Override
    public Produto buscarProdutoPorId(int id) throws SQLException {
        String sql = "select * from produto where id_produto = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        Produto produto = null;
        if (rs.next()) {
            produto = new Produto();
            produto.setIdProduto(rs.getInt("id_produto"));
            produto.setNomeProduto(rs.getString("nome"));
            produto.setDescricaoProduto(rs.getString("descricao"));
            produto.setPrecoVendaProduto(rs.getFloat("preco_venda"));
            produto.setPrecoCustoProduto(rs.getFloat("preco_custo"));
            produto.setQuantidadeEstocada(rs.getInt("estoque"));
            produto.setPercentualMarkup(rs.getFloat("markup"));
        }
        ps.close();
        rs.close();
        return produto;
    }

    @Override
    public List<Produto> buscarProdutos() throws SQLException {
        String sql = "select * from produto";
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        List<Produto> produtos = new ArrayList<>();
        while (rs.next()) {
            Produto produto = new Produto();
            produto.setIdProduto(rs.getInt("id_produto"));
            produto.setNomeProduto(rs.getString("nome"));
            produto.setDescricaoProduto(rs.getString("descricao"));
            produto.setPrecoVendaProduto(rs.getFloat("preco_venda"));
            produto.setPrecoCustoProduto(rs.getFloat("preco_custo"));
            produto.setQuantidadeEstocada(rs.getInt("estoque"));
            produto.setPercentualMarkup(rs.getFloat("markup"));
            produtos.add(produto);
        }
        ps.close();
        rs.close();
        return produtos;
    }
}
