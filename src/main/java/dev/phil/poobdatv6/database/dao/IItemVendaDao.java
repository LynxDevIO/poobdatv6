package dev.phil.poobdatv6.database.dao;

import dev.phil.poobdatv6.model.ItemVenda;

import java.sql.SQLException;
import java.util.List;

public interface IItemVendaDao {
    void criarTabela() throws SQLException;
    void inserirItem(ItemVenda item) throws SQLException;
    void alterarItemPorId(int id, ItemVenda item) throws SQLException;
    void excluirItemPorId(int id) throws SQLException;
    ItemVenda buscarItemPorId(int id) throws SQLException;
    List<ItemVenda> buscarItens() throws SQLException;
}
