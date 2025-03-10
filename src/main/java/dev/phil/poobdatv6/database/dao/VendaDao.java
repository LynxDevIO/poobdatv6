package dev.phil.poobdatv6.database.dao;

import dev.phil.poobdatv6.database.DBConnection;
import dev.phil.poobdatv6.model.Venda;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class VendaDao implements IVendaDao {
    private Connection connection;

    public VendaDao() throws SQLException {
        connection = DBConnection.getInstance().getConnection();
    }

    @Override
    public void criarTabela() throws SQLException {
        String sql = "create table if not exists venda (" +
                "id_venda int generated always as identity primary key, " +
                "total_venda numeric(10, 2) not null, " +
                "data_venda date not null, " +
                "fk_cliente int not null, " +
                "constraint fk_id_cliente foreign key (fk_cliente) references cliente (id_cliente)" +
                ")";
        Statement st = connection.createStatement();
        st.execute(sql);
        st.close();
    }

    @Override
    public void inserirVenda(Venda venda) throws SQLException {
        String sql = "insert into venda (total_venda, data_venda, fk_cliente) values (?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setFloat(1, venda.getTotalVenda());
        ps.setDate(2, (java.sql.Date) venda.getDataVenda());
        ps.setInt(3, venda.getClienteVenda().getIdCliente());
        ps.executeUpdate();
        ps.close();
    }

    @Override
    public void alterarVendaPorId(int id) throws SQLException {
        // Não é necessário implementar para a funcionalidade atual
    }

    @Override
    public void excluirVendaPorId(int id) throws SQLException {
        String sql = "delete from venda where id_venda = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        ps.close();
    }

    @Override
    public Venda buscarVendaPorId(int id) throws SQLException {
        String sql = "select * from venda where id_venda = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        Venda venda = null;
        
        if (rs.next()) {
            venda = new Venda();
            venda.setIdVenda(rs.getInt("id_venda"));
            venda.setTotalVenda(rs.getFloat("total_venda"));
            venda.setDataVenda(rs.getDate("data_venda"));
            
            // Buscar cliente
            IClienteDao clienteDao = new ClienteDao();
            venda.setClienteVenda(clienteDao.buscarClientePorId(rs.getInt("fk_cliente")));
        }
        
        rs.close();
        ps.close();
        return venda;
    }

    @Override
    public List<Venda> buscarVendas() throws SQLException {
        String sql = "select * from venda order by id_venda";
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        List<Venda> vendas = new ArrayList<>();
        
        while (rs.next()) {
            Venda venda = new Venda();
            venda.setIdVenda(rs.getInt("id_venda"));
            venda.setTotalVenda(rs.getFloat("total_venda"));
            venda.setDataVenda(rs.getDate("data_venda"));
            
            // Buscar cliente
            IClienteDao clienteDao = new ClienteDao();
            venda.setClienteVenda(clienteDao.buscarClientePorId(rs.getInt("fk_cliente")));
            
            vendas.add(venda);
        }
        
        rs.close();
        ps.close();
        return vendas;
    }
}
