package dev.phil.poobdatv6.database.dao;

import dev.phil.poobdatv6.model.Cliente;

import java.sql.SQLException;
import java.util.List;

public interface IClienteDao {
    void criarTabela() throws SQLException;
    void inserirCliente(Cliente cliente) throws SQLException;
    void atualizarClientePorId(int id, Cliente cliente) throws SQLException;
    void excluirClientePorId(int id) throws SQLException;
    Cliente buscarClientePorId(int id) throws SQLException;
    List<Cliente> buscarClientes() throws SQLException;
}
