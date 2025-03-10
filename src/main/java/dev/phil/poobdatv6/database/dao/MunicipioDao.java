package dev.phil.poobdatv6.database.dao;

import dev.phil.poobdatv6.database.DBConnection;
import dev.phil.poobdatv6.model.Municipio;
import dev.phil.poobdatv6.model.UF;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MunicipioDao {
    private Connection connection;
    private UfDao ufDao;

    public MunicipioDao() throws SQLException {
        connection = DBConnection.getInstance().getConnection();
        ufDao = new UfDao();
    }

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

                UF uf = ufDao.buscarPorId(municipio.getUfId());
                municipio.setUf(uf);

                municipios.add(municipio);
            }
        }
        
        return municipios;
    }

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

                    UF uf = ufDao.buscarPorId(municipio.getUfId());
                    municipio.setUf(uf);

                    municipios.add(municipio);
                }
            }
        }
        
        return municipios;
    }

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

                    UF uf = ufDao.buscarPorId(municipio.getUfId());
                    municipio.setUf(uf);
                    
                    return municipio;
                }
            }
        }
        
        return null;
    }
} 