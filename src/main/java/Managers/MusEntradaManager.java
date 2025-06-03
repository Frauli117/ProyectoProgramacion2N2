/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Managers;

import Database.MusEntrada;
import Database.MusComision;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

/**
 *
 * @author Kenneth
 */
public class MusEntradaManager {

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

    public void addEntrada(MusEntrada entrada) {
        String sql = "INSERT INTO MUS_ENTRADA (EN_COMID, EN_FECHA, EN_TOTAL, EN_QR, EN_NOMBRE) VALUES (?, ?, ?, ?, ?)";

        try (Connection connect = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stm = connect.prepareStatement(sql)) {

            stm.setInt(1, entrada.getEnComid().getCmId());
            stm.setDate(2, new java.sql.Date(entrada.getEnFecha().getTime()));
            stm.setDouble(3, entrada.getEnTotal());
            stm.setString(4, entrada.getEnQr());
            stm.setString(5, entrada.getEnNombre());

            stm.executeUpdate();
            closeConnection(connect);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<MusEntrada> getAllEntradas() {
        ObservableList<MusEntrada> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM MUS_ENTRADA";

        try (Connection connect = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stm = connect.prepareStatement(sql);
             ResultSet rs = stm.executeQuery()) {

            while (rs.next()) {
                MusEntrada entrada = new MusEntrada();

                entrada.setEnId(rs.getInt("EN_ID"));
                entrada.setEnFecha(rs.getDate("EN_FECHA"));
                entrada.setEnTotal(rs.getDouble("EN_TOTAL"));
                entrada.setEnQr(rs.getString("EN_QR"));
                entrada.setEnNombre(rs.getString("EN_NOMBRE"));

                MusComision comision = new MusComision();
                comision.setCmId(rs.getInt("EN_COMID"));
                entrada.setEnComid(comision);

                list.add(entrada);
            }

            closeConnection(connect);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void deleteEntrada(Integer id) {
        String sql = "DELETE FROM MUS_ENTRADA WHERE EN_ID = ?";

        try (Connection connect = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stm = connect.prepareStatement(sql)) {

            stm.setInt(1, id);
            stm.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateEntrada(MusEntrada entrada) {
        String sql = "UPDATE MUS_ENTRADA SET EN_COMID = ?, EN_FECHA = ?, EN_TOTAL = ?, EN_QR = ?, EN_NOMBRE = ? WHERE EN_ID = ?";

        try (Connection connect = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stm = connect.prepareStatement(sql)) {

            stm.setInt(1, entrada.getEnComid().getCmId());
            stm.setDate(2, new java.sql.Date(entrada.getEnFecha().getTime()));
            stm.setDouble(3, entrada.getEnTotal());
            stm.setString(4, entrada.getEnQr());
            stm.setString(5, entrada.getEnNombre());
            stm.setInt(6, entrada.getEnId());

            stm.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


