package dev.phil.poobdatv6.database.dao;

import dev.phil.poobdatv6.database.DBConnection;
import dev.phil.poobdatv6.model.ItemVenda;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemVendaDao implements IItemVendaDao{
    private Connection connection;
    private IVendaDao vendaDao;
    private IProdutoDao produtoDao;

    public ItemVendaDao() throws SQLException {
        connection = DBConnection.getInstance().getConnection();
        vendaDao = new VendaDao();
        produtoDao = new ProdutoDao();
    }


    @Override
    public void criarTabela() throws SQLException {
        String sql = "create table if not exists item_venda (" +
                "id_item_venda int generated always as identity primary key, " +
                "quantidade_item_venda int not null, " +
                "valor_total_item_venda numeric(10,2) not null, " +
                "fk_id_venda int not null, " +
                "fk_id_produto int not null, " +
                "constraint fk_venda foreign key (fk_id_venda) references venda (id_venda), " +
                "constraint fk_produto foreign key (fk_id_produto) references produto (id_produto)" +
                ")";
        Statement st = connection.createStatement();
        st.execute(sql);
        st.close();
    }

    @Override
    public void inserirItem(ItemVenda item) throws SQLException {
        String sql = "insert into item_venda " +
                "(quantidade_item_venda," +
                "valor_total_item_venda," +
                "fk_id_venda," +
                "fk_id_produto) " +
                "values (?,?,?,?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, item.getQuantidade());
        ps.setDouble(2, item.getValorTotal());
        ps.setInt(3, item.getVenda().getIdVenda());
        ps.setInt(4, item.getProduto().getIdProduto());
        ps.executeUpdate();
        ps.close();
    }

    @Override
    public void alterarItemPorId(int id, ItemVenda item) throws SQLException {
        String sql = "update item_venda " +
                "set quantidade_item_venda = ?, " +
                "valor_total_item_venda = ? " +
                "where id_item_venda = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, item.getQuantidade());
        ps.setDouble(2, item.getValorTotal());
        ps.setInt(3, id);
        ps.executeUpdate();
        ps.close();
    }

    @Override
    public void excluirItemPorId(int id) throws SQLException {
        String sql = "delete from item_venda where id_item_venda = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        ps.close();
    }

    @Override
    public ItemVenda buscarItemPorId(int id) throws SQLException {
        String sql = "select * from item_venda where id_item_venda = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        ItemVenda item = null;
        while (rs.next()) {
            item = new ItemVenda();
            item.setId(rs.getInt("id_item_venda"));
            item.setQuantidade(rs.getInt("quantidade_item_venda"));
            item.setValorTotal(rs.getFloat("valor_total_item_venda"));
            item.setVenda(
                    // Buscar venda por id
                    vendaDao.buscarVendaPorId(rs.getInt("fk_id_venda"))
            );
            item.setProduto(
                    // Buscar produto por id
                    produtoDao.buscarProdutoPorId(rs.getInt("fk_id_produto"))
            );
        }
        rs.close();
        ps.close();
        return item;
    }

    @Override
    public List<ItemVenda> buscarItens() throws SQLException {
        String sql = "select * from item_venda";
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        List<ItemVenda> itemVendas = new ArrayList<>();
        while (rs.next()) {
            ItemVenda item = new ItemVenda();
            item.setId(rs.getInt("id_item_venda"));
            item.setQuantidade(rs.getInt("quantidade_item_venda"));
            item.setValorTotal(rs.getFloat("valor_total_item_venda"));
            item.setVenda(
                    // Buscar venda por id
                    vendaDao.buscarVendaPorId(rs.getInt("fk_id_venda"))
            );
            item.setProduto(
                    // Buscar produto por id
                    produtoDao.buscarProdutoPorId(rs.getInt("fk_id_produto"))
            );
            itemVendas.add(item);
        }
        return itemVendas;
    }
}
