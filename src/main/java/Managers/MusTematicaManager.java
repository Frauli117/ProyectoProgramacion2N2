/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Managers;

import Database.MusTematica;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

/**
 *
 * @author Kenneth
 */
public class MusTematicaManager {

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

    public void addTematica(MusTematica tematica) {
        String sql = "INSERT INTO MUS_TEMATICA (TE_SAID, TE_NOMBRE, TE_CARACTERISTICAS, TE_EPOCA) VALUES (?, ?, ?, ?)";

        try (Connection connect = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stm = connect.prepareStatement(sql)) {

            stm.setInt(1, tematica.getTeSaid());
            stm.setString(2, tematica.getTeNombre());
            stm.setString(3, tematica.getTeCaracteristicas());
            stm.setString(4, tematica.getTeEpoca());

            stm.executeUpdate();
            closeConnection(connect);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<MusTematica> getAllTematicas() {
        ObservableList<MusTematica> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM MUS_TEMATICA";

        try (Connection connect = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stm = connect.prepareStatement(sql);
             ResultSet rs = stm.executeQuery()) {

            while (rs.next()) {
                MusTematica tematica = new MusTematica();
                tematica.setTeId(rs.getInt("TE_ID"));
                tematica.setTeSaid(rs.getInt("TE_SAID"));
                tematica.setTeNombre(rs.getString("TE_NOMBRE"));
                tematica.setTeCaracteristicas(rs.getString("TE_CARACTERISTICAS"));
                tematica.setTeEpoca(rs.getString("TE_EPOCA"));
                list.add(tematica);
            }

            closeConnection(connect);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void deleteTematica(Integer id) {
        String sql = "DELETE FROM MUS_TEMATICA WHERE TE_ID = ?";

        try (Connection connect = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stm = connect.prepareStatement(sql)) {

            stm.setInt(1, id);
            stm.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateTematica(MusTematica tematica) {
        String sql = "UPDATE MUS_TEMATICA SET TE_SAID = ?, TE_NOMBRE = ?, TE_CARACTERISTICAS = ?, TE_EPOCA = ? WHERE TE_ID = ?";

        try (Connection connect = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stm = connect.prepareStatement(sql)) {

            stm.setInt(1, tematica.getTeSaid());
            stm.setString(2, tematica.getTeNombre());
            stm.setString(3, tematica.getTeCaracteristicas());
            stm.setString(4, tematica.getTeEpoca());
            stm.setInt(5, tematica.getTeId());

            stm.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
