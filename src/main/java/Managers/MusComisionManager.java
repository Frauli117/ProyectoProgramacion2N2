/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Managers;

import Database.MusComision;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

/**
 *
 * @author Kenneth
 */
public class MusComisionManager {

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

    public void addComision(MusComision comision) {
        String sql = "INSERT INTO MUS_COMISION (CM_TIPO, CM_PORCENTAJE) VALUES (?, ?)";

        try (Connection connect = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stm = connect.prepareStatement(sql)) {

            stm.setString(1, comision.getCmTipo());
            stm.setDouble(2, comision.getCmPorcentaje());

            stm.executeUpdate();
            closeConnection(connect);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<MusComision> getAllComisiones() {
        ObservableList<MusComision> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM MUS_COMISION";

        try (Connection connect = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stm = connect.prepareStatement(sql);
             ResultSet rs = stm.executeQuery()) {

            while (rs.next()) {
                MusComision comision = new MusComision();
                comision.setCmId(rs.getInt("CM_ID"));
                comision.setCmTipo(rs.getString("CM_TIPO"));
                comision.setCmPorcentaje(rs.getDouble("CM_PORCENTAJE"));
                list.add(comision);
            }

            closeConnection(connect);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void deleteComision(Integer id) {
        String sql = "DELETE FROM MUS_COMISION WHERE CM_ID = ?";

        try (Connection connect = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stm = connect.prepareStatement(sql)) {

            stm.setInt(1, id);
            stm.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateComision(MusComision comision) {
        String sql = "UPDATE MUS_COMISION SET CM_TIPO = ?, CM_PORCENTAJE = ? WHERE CM_ID = ?";

        try (Connection connect = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stm = connect.prepareStatement(sql)) {

            stm.setString(1, comision.getCmTipo());
            stm.setDouble(2, comision.getCmPorcentaje());
            stm.setInt(3, comision.getCmId());

            stm.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

