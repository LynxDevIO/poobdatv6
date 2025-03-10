package dev.phil.poobdatv6.database.dao;

import dev.phil.poobdatv6.model.Venda;

import java.sql.SQLException;
import java.util.List;

public interface IVendaDao {
    void criarTabela() throws SQLException;
    void inserirVenda(Venda venda) throws SQLException;
    void alterarVendaPorId(int id) throws SQLException;
    void excluirVendaPorId(int id) throws SQLException;
    Venda buscarVendaPorId(int id) throws SQLException;
    List<Venda> buscarVendas() throws SQLException;
}
