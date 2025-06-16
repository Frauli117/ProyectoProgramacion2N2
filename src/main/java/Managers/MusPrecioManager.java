/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Managers;

import Database.MusPrecio;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

/**
 *
 * @author Kenneth
 */
public class MusPrecioManager {

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

    public void addPrecio(MusPrecio precio) {
        String sql = "INSERT INTO MUS_PRECIO (PR_SAID, PR_LUNSAB, PR_DOMINGO) VALUES (?, ?, ?)";

        try (Connection connect = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stm = connect.prepareStatement(sql)) {

            stm.setInt(1, precio.getPrSaid());
            stm.setDouble(2, precio.getPrLunsab());
            stm.setDouble(3, precio.getPrDomingo());

            stm.executeUpdate();
            closeConnection(connect);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<MusPrecio> getAllPrecios() {
        ObservableList<MusPrecio> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM MUS_PRECIO";

        try (Connection connect = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stm = connect.prepareStatement(sql);
             ResultSet rs = stm.executeQuery()) {

            while (rs.next()) {
                MusPrecio precio = new MusPrecio();
                precio.setPrId(rs.getInt("PR_ID"));
                precio.setPrSaid(rs.getInt("PR_SAID"));
                precio.setPrLunsab(rs.getDouble("PR_LUNSAB"));
                precio.setPrDomingo(rs.getDouble("PR_DOMINGO"));
                list.add(precio);
            }

            closeConnection(connect);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void deletePrecio(Integer id) {
        String sql = "DELETE FROM MUS_PRECIO WHERE PR_ID = ?";

        try (Connection connect = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stm = connect.prepareStatement(sql)) {

            stm.setInt(1, id);
            stm.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePrecio(MusPrecio precio) {
        String sql = "UPDATE MUS_PRECIO SET PR_SAID = ?, PR_LUNSAB = ?, PR_DOMINGO = ? WHERE PR_ID = ?";

        try (Connection connect = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stm = connect.prepareStatement(sql)) {

            stm.setInt(1, precio.getPrSaid());
            stm.setDouble(2, precio.getPrLunsab());
            stm.setDouble(3, precio.getPrDomingo());
            stm.setInt(4, precio.getPrId());

            stm.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public MusPrecio getPrecioPorSala(int idSala) {
        MusPrecio precio = null;
        String sql = "SELECT * FROM MUS_PRECIO WHERE PR_SAID = ?";

        try (Connection connect = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stm = connect.prepareStatement(sql)) {

            stm.setInt(1, idSala);
            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                precio = new MusPrecio();
                precio.setPrId(rs.getInt("PR_ID"));
                precio.setPrSaid(rs.getInt("PR_SAID"));
                precio.setPrLunsab(rs.getDouble("PR_LUNSAB"));
                precio.setPrDomingo(rs.getDouble("PR_DOMINGO"));
            }

            closeConnection(connect);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return precio;
        }

}

