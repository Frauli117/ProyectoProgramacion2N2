/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Managers;

import Database.MusDetEntrada;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

/**
 *
 * @author Kenneth
 */
public class MusDetEntradaManager {

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

    public void addDetalle(MusDetEntrada detalle) {
        String sql = "INSERT INTO MUS_DET_ENTRADA (DE_ENID, DE_SAID, DE_PRECIO) VALUES (?, ?, ?)";

        try (Connection connect = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stm = connect.prepareStatement(sql)) {

            stm.setInt(1, detalle.getDeEnid());
            stm.setInt(2, detalle.getDeSaid());
            stm.setDouble(3, detalle.getDePrecio());

            stm.executeUpdate();
            closeConnection(connect);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<MusDetEntrada> getAllDetalles() {
        ObservableList<MusDetEntrada> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM MUS_DET_ENTRADA";

        try (Connection connect = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stm = connect.prepareStatement(sql);
             ResultSet rs = stm.executeQuery()) {

            while (rs.next()) {
                MusDetEntrada detalle = new MusDetEntrada();
                detalle.setDeId(rs.getInt("DE_ID"));
                detalle.setDeEnid(rs.getInt("DE_ENID"));
                detalle.setDeSaid(rs.getInt("DE_SAID"));
                detalle.setDePrecio(rs.getDouble("DE_PRECIO"));
                list.add(detalle);
            }

            closeConnection(connect);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void deleteDetalle(Integer id) {
        String sql = "DELETE FROM MUS_DET_ENTRADA WHERE DE_ID = ?";

        try (Connection connect = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stm = connect.prepareStatement(sql)) {

            stm.setInt(1, id);
            stm.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateDetalle(MusDetEntrada detalle) {
        String sql = "UPDATE MUS_DET_ENTRADA SET DE_ENID = ?, DE_SAID = ?, DE_PRECIO = ? WHERE DE_ID = ?";

        try (Connection connect = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stm = connect.prepareStatement(sql)) {

            stm.setInt(1, detalle.getDeEnid());
            stm.setInt(2, detalle.getDeSaid());
            stm.setDouble(3, detalle.getDePrecio());
            stm.setInt(4, detalle.getDeId());

            stm.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


