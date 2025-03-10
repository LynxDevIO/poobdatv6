package dev.phil.poobdatv6.database.dao;

import dev.phil.poobdatv6.model.UF;

import java.sql.SQLException;
import java.util.List;

public interface IUfDao {
    void criarTabela() throws SQLException;
    int inserir(UF uf) throws SQLException;
    void atualizar(UF uf) throws SQLException;
    void excluir(int id) throws SQLException;
    List<UF> buscarTodas() throws SQLException;
    UF buscarPorId(int id) throws SQLException;
    UF buscarPorSigla(String sigla) throws SQLException;
}
