/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Managers;

import Database.MusSala;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

/**
 *
 * @author Kenneth
 */
public class MusSalaManager {

    private final String URL = "jdbc:oracle:thin:@localhost:1521:FREE";
    private final String USER = "MUSEO";
    private final String PASS = "8454";

    public void closeConnection(Connection connect) {
        try {
            if (connect != null) {
                connect.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addSala(MusSala sala) {
        String sql = "INSERT INTO MUS_SALA (SA_MUID, SA_NOMBRE, SA_DESCRIPCION) VALUES (?, ?, ?)";

        try (Connection connect = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stm = connect.prepareStatement(sql)) {

            stm.setInt(1, sala.getSaMuid());
            stm.setString(2, sala.getSaNombre());
            stm.setString(3, sala.getSaDescripcion());

            stm.executeUpdate();
            closeConnection(connect);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<MusSala> getAllSalas() {
        ObservableList<MusSala> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM MUS_SALA";

        try (Connection connect = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stm = connect.prepareStatement(sql);
             ResultSet rs = stm.executeQuery()) {

            while (rs.next()) {
                MusSala sala = new MusSala();
                sala.setSaId(rs.getInt("SA_ID"));
                sala.setSaMuid(rs.getInt("SA_MUID"));
                sala.setSaNombre(rs.getString("SA_NOMBRE"));
                sala.setSaDescripcion(rs.getString("SA_DESCRIPCION"));
                list.add(sala);
            }

            closeConnection(connect);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void deleteSala(Integer id) {
        String sql = "DELETE FROM MUS_SALA WHERE SA_ID = ?";

        try (Connection connect = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stm = connect.prepareStatement(sql)) {

            stm.setInt(1, id);
            stm.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateSala(MusSala sala) {
        String sql = "UPDATE MUS_SALA SET SA_MUID = ?, SA_NOMBRE = ?, SA_DESCRIPCION = ? WHERE SA_ID = ?";

        try (Connection connect = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stm = connect.prepareStatement(sql)) {

            stm.setInt(1, sala.getSaMuid());
            stm.setString(2, sala.getSaNombre());
            stm.setString(3, sala.getSaDescripcion());
            stm.setInt(4, sala.getSaId());

            stm.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
