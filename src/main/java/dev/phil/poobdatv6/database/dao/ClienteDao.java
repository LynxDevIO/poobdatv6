package dev.phil.poobdatv6.database.dao;

import dev.phil.poobdatv6.database.DBConnection;
import dev.phil.poobdatv6.model.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDao implements IClienteDao {
    private Connection connection;

    public ClienteDao() throws SQLException {
        connection = DBConnection.getInstance().getConnection();
    }

    @Override
    public void criarTabela() throws SQLException {
        String sql = "create table if not exists cliente (" +
                "id_cliente int generated always as identity primary key, " +
                "nome varchar(255) not null, " +
                "email varchar(255) not null, " +
                "cpf varchar(11) unique not null, " +
                "logradouro varchar(100) not null, " +
                "bairro varchar(100) not null, " +
                "cidade varchar(100) not null, " +
                "estado varchar(2) not null, " +
                "data_nascimento date not null, " +
                "constraint valid_number check (id_cliente <= 10)" +
                ")";
        Statement st = connection.createStatement();
        st.execute(sql);
        st.close();
    }

    @Override
    public void inserirCliente(Cliente cliente) throws SQLException {
        String sql = "insert into cliente (nome, email, cpf, logradouro, bairro, cidade, estado, data_nascimento) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, cliente.getNomeCliente());
        ps.setString(2, cliente.getEmailCliente());
        ps.setString(3, cliente.getCpfCliente());
        ps.setString(4, cliente.getEnderecoCliente()[0]);
        ps.setString(5, cliente.getEnderecoCliente()[1]);
        ps.setString(6, cliente.getEnderecoCliente()[2]);
        ps.setString(7, cliente.getEnderecoCliente()[3]);
        ps.setDate(8, (java.sql.Date) cliente.getDataNascimento());
        ps.executeUpdate();
        ps.close();
    }

    @Override
    public void atualizarClientePorId(int id, Cliente cliente) throws SQLException {
        String sql = "update cliente " +
                "set nome = ?, " +
                "email = ?, " +
                "logradouro = ?, " +
                "bairro = ?, " +
                "cidade = ?, " +
                "estado = ?, " +
                "data_nascimento = ? " +
                "where id_cliente = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, cliente.getNomeCliente());
        ps.setString(2, cliente.getEmailCliente());
        ps.setString(3, cliente.getEnderecoCliente()[0]);
        ps.setString(4, cliente.getEnderecoCliente()[1]);
        ps.setString(5, cliente.getEnderecoCliente()[2]);
        ps.setString(6, cliente.getEnderecoCliente()[3]);
        ps.setDate(7, (java.sql.Date) cliente.getDataNascimento());
        ps.setInt(8, id);
        ps.executeUpdate();
        ps.close();
    }

    @Override
    public void excluirClientePorId(int id) throws SQLException {
        String sql = "delete from cliente where id_cliente = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        ps.close();
    }

    @Override
    public Cliente buscarClientePorId(int id) throws SQLException {
        String sql = "select * from cliente where id_cliente = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        Cliente cliente = null;
        if (rs.next()) {
            cliente = new Cliente();
            cliente.setIdCliente(rs.getInt("id_cliente"));
            cliente.setNomeCliente(rs.getString("nome"));
            cliente.setCpfCliente(rs.getString("cpf"));
            cliente.setEmailCliente(rs.getString("email"));
            cliente.setDataNascimento(rs.getDate("data_nascimento"));
            cliente.setEnderecoCliente(
                    new String[] {
                            rs.getString("logradouro"),
                            rs.getString("bairro"),
                            rs.getString("cidade"),
                            rs.getString("estado")
                    }
            );

        }
        ps.close();
        rs.close();
        return cliente;
    }

    @Override
    public List<Cliente> buscarClientes() throws SQLException {
        String sql = "select * from cliente";
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        List<Cliente> clientes = new ArrayList<>();
        while (rs.next()) {
            Cliente cliente = new Cliente();
            cliente.setIdCliente(rs.getInt("id_cliente"));
            cliente.setNomeCliente(rs.getString("nome"));
            cliente.setCpfCliente(rs.getString("cpf"));
            cliente.setEmailCliente(rs.getString("email"));
            cliente.setDataNascimento(rs.getDate("data_nascimento"));
            cliente.setEnderecoCliente(
                    new String[] {
                            rs.getString("logradouro"),
                            rs.getString("bairro"),
                            rs.getString("cidade"),
                            rs.getString("estado")
                    }
            );
            clientes.add(cliente);
        }
        ps.close();
        rs.close();
        return clientes;
    }
}
