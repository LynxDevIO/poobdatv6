package dev.phil.poobdatv6.database.dao;

import dev.phil.poobdatv6.database.DBConnection;
import dev.phil.poobdatv6.model.Municipio;
import dev.phil.poobdatv6.model.UF;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MunicipioDao implements IMunicipioDao {
    private Connection connection;
    private IUfDao ufDao;

    public MunicipioDao() throws SQLException {
        connection = DBConnection.getInstance().getConnection();
        ufDao = new UfDao();
    }

    @Override
    public void criarTabela() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS municipio (" +
                "id SERIAL PRIMARY KEY, " +
                "ufid INT NOT NULL REFERENCES uf(id) ON DELETE CASCADE, " +
                "nome VARCHAR(255) NOT NULL" +
                ")";
        
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        }
    }

    @Override
    public int inserir(Municipio municipio) throws SQLException {
        String sql = "INSERT INTO municipio (ufid, nome) VALUES (?, ?) RETURNING id";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, municipio.getUfId());
            ps.setString(2, municipio.getNome());
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        }
        
        return -1; // Erro ao inserir
    }

    @Override
    public void atualizar(Municipio municipio) throws SQLException {
        String sql = "UPDATE municipio SET ufid = ?, nome = ? WHERE id = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, municipio.getUfId());
            ps.setString(2, municipio.getNome());
            ps.setInt(3, municipio.getId());
            
            ps.executeUpdate();
        }
    }

    @Override
    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM municipio WHERE id = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            
            ps.executeUpdate();
        }
    }

    @Override
    public List<Municipio> buscarTodos() throws SQLException {
        List<Municipio> municipios = new ArrayList<>();
        String sql = "SELECT id, nome, ufid FROM municipio ORDER BY nome";
        
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Municipio municipio = new Municipio();
                municipio.setId(rs.getInt("id"));
                municipio.setNome(rs.getString("nome"));
                municipio.setUfId(rs.getInt("ufid"));
                
                // Buscar a UF relacionada
                UF uf = ufDao.buscarPorId(municipio.getUfId());
                municipio.setUf(uf);
                
                municipios.add(municipio);
            }
        }
        
        return municipios;
    }
    
    @Override
    public List<Municipio> buscarPorUf(int ufId) throws SQLException {
        List<Municipio> municipios = new ArrayList<>();
        String sql = "SELECT id, nome, ufid FROM municipio WHERE ufid = ? ORDER BY nome";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, ufId);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Municipio municipio = new Municipio();
                    municipio.setId(rs.getInt("id"));
                    municipio.setNome(rs.getString("nome"));
                    municipio.setUfId(rs.getInt("ufid"));
                    
                    // Buscar a UF relacionada
                    UF uf = ufDao.buscarPorId(municipio.getUfId());
                    municipio.setUf(uf);
                    
                    municipios.add(municipio);
                }
            }
        }
        
        return municipios;
    }
    
    @Override
    public Municipio buscarPorId(int id) throws SQLException {
        String sql = "SELECT id, nome, ufid FROM municipio WHERE id = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Municipio municipio = new Municipio();
                    municipio.setId(rs.getInt("id"));
                    municipio.setNome(rs.getString("nome"));
                    municipio.setUfId(rs.getInt("ufid"));
                    
                    // Buscar a UF relacionada
                    UF uf = ufDao.buscarPorId(municipio.getUfId());
                    municipio.setUf(uf);
                    
                    return municipio;
                }
            }
        }
        
        return null;
    }
    
    @Override
    public List<Municipio> buscarPorNome(String nome) throws SQLException {
        List<Municipio> municipios = new ArrayList<>();
        String sql = "SELECT id, nome, ufid FROM municipio WHERE nome ILIKE ? ORDER BY nome";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + nome + "%");
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Municipio municipio = new Municipio();
                    municipio.setId(rs.getInt("id"));
                    municipio.setNome(rs.getString("nome"));
                    municipio.setUfId(rs.getInt("ufid"));
                    
                    // Buscar a UF relacionada
                    UF uf = ufDao.buscarPorId(municipio.getUfId());
                    municipio.setUf(uf);
                    
                    municipios.add(municipio);
                }
            }
        }
        
        return municipios;
    }
} 