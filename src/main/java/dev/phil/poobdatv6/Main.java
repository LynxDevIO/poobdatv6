package dev.phil.poobdatv6;

import dev.phil.poobdatv6.database.DBConnection;
import dev.phil.poobdatv6.view.MainView;

import java.sql.SQLException;

public class Main extends MainView {
    public static void main(String[] args) {
        try {
            DBConnection.getInstance();
            System.out.println("Conexão com o banco de dados estabelecida com sucesso!");
            System.out.println("Banco de dados inicializado com sucesso!");
            MainView.launch();
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
