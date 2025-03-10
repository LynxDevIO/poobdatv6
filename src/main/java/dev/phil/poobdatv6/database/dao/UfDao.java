package dev.phil.poobdatv6.database.dao;

import dev.phil.poobdatv6.database.DBConnection;
import dev.phil.poobdatv6.model.UF;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UfDao implements IUfDao {
    private Connection connection;

    public UfDao() throws SQLException {
        connection = DBConnection.getInstance().getConnection();
    }

    @Override
    public void criarTabela() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS uf (" +
                "id SERIAL PRIMARY KEY, " +
                "nome VARCHAR(255) NOT NULL, " +
                "sigla CHAR(2) NOT NULL UNIQUE" +
                ")";
        
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        }
    }

    @Override
    public int inserir(UF uf) throws SQLException {
        String sql = "INSERT INTO uf (nome, sigla) VALUES (?, ?) RETURNING id";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, uf.getNome());
            ps.setString(2, uf.getSigla());
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        }
        
        return -1; // Erro ao inserir
    }

    @Override
    public void atualizar(UF uf) throws SQLException {
        String sql = "UPDATE uf SET nome = ?, sigla = ? WHERE id = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, uf.getNome());
            ps.setString(2, uf.getSigla());
            ps.setInt(3, uf.getId());
            
            ps.executeUpdate();
        }
    }

    @Override
    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM uf WHERE id = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            
            ps.executeUpdate();
        }
    }

    @Override
    public List<UF> buscarTodas() throws SQLException {
        List<UF> ufs = new ArrayList<>();
        String sql = "SELECT id, nome, sigla FROM uf ORDER BY nome";
        
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                UF uf = new UF();
                uf.setId(rs.getInt("id"));
                uf.setNome(rs.getString("nome"));
                uf.setSigla(rs.getString("sigla"));
                ufs.add(uf);
            }
        }
        
        return ufs;
    }
    
    @Override
    public UF buscarPorId(int id) throws SQLException {
        String sql = "SELECT id, nome, sigla FROM uf WHERE id = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    UF uf = new UF();
                    uf.setId(rs.getInt("id"));
                    uf.setNome(rs.getString("nome"));
                    uf.setSigla(rs.getString("sigla"));
                    return uf;
                }
            }
        }
        
        return null;
    }
    
    @Override
    public UF buscarPorSigla(String sigla) throws SQLException {
        String sql = "SELECT id, nome, sigla FROM uf WHERE sigla = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, sigla);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    UF uf = new UF();
                    uf.setId(rs.getInt("id"));
                    uf.setNome(rs.getString("nome"));
                    uf.setSigla(rs.getString("sigla"));
                    return uf;
                }
            }
        }
        
        return null;
    }
} 