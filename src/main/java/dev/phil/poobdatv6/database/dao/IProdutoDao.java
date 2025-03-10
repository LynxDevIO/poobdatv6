package dev.phil.poobdatv6.database.dao;

import dev.phil.poobdatv6.model.Produto;

import java.sql.SQLException;
import java.util.List;

public interface IProdutoDao {
    void criarTabela() throws SQLException;
    void inserirProduto(Produto produto) throws SQLException;
    void alterarProdutoPorId(int id, Produto produto) throws SQLException;
    void excluirProdutoPorId(int id) throws SQLException;
    Produto buscarProdutoPorId(int id) throws SQLException;
    List<Produto> buscarProdutos() throws SQLException;
}
