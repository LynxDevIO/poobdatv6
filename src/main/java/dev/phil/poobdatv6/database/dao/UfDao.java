package dev.phil.poobdatv6.database.dao;

import dev.phil.poobdatv6.database.DBConnection;
import dev.phil.poobdatv6.model.UF;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UfDao {
    private Connection connection;

    public UfDao() throws SQLException {
        connection = DBConnection.getInstance().getConnection();
    }

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