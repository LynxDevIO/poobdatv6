package dev.phil.poobdatv6.database.dao;

import dev.phil.poobdatv6.model.Municipio;

import java.sql.SQLException;
import java.util.List;

public interface IMunicipioDao {
    void criarTabela() throws SQLException;
    int inserir(Municipio municipio) throws SQLException;
    void atualizar(Municipio municipio) throws SQLException;
    void excluir(int id) throws SQLException;
    List<Municipio> buscarTodos() throws SQLException;
    List<Municipio> buscarPorUf(int ufId) throws SQLException;
    Municipio buscarPorId(int id) throws SQLException;
    List<Municipio> buscarPorNome(String nome) throws SQLException;
}
