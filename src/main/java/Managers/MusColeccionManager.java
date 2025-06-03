/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Managers;

import Database.MusColeccion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

/**
 *
 * @author Kenneth
 */
public class MusColeccionManager {

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

    public void addColeccion(MusColeccion coleccion) {
        String sql = "INSERT INTO MUS_COLECCION (CO_SAID, CO_NOMBRE, CO_SIGLO, CO_DESCRIPCION) VALUES (?, ?, ?, ?)";

        try (Connection connect = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stm = connect.prepareStatement(sql)) {

            stm.setInt(1, coleccion.getCoSaid());
            stm.setString(2, coleccion.getCoNombre());
            stm.setString(3, coleccion.getCoSiglo());
            stm.setString(4, coleccion.getCoDescripcion());

            stm.executeUpdate();
            closeConnection(connect);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<MusColeccion> getAllColecciones() {
        ObservableList<MusColeccion> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM MUS_COLECCION";

        try (Connection connect = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stm = connect.prepareStatement(sql);
             ResultSet rs = stm.executeQuery()) {

            while (rs.next()) {
                MusColeccion col = new MusColeccion();
                col.setCoId(rs.getInt("CO_ID"));
                col.setCoSaid(rs.getInt("CO_SAID"));
                col.setCoNombre(rs.getString("CO_NOMBRE"));
                col.setCoSiglo(rs.getString("CO_SIGLO"));
                col.setCoDescripcion(rs.getString("CO_DESCRIPCION"));
                list.add(col);
            }

            closeConnection(connect);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void deleteColeccion(Integer id) {
        String sql = "DELETE FROM MUS_COLECCION WHERE CO_ID = ?";

        try (Connection connect = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stm = connect.prepareStatement(sql)) {

            stm.setInt(1, id);
            stm.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateColeccion(MusColeccion coleccion) {
        String sql = "UPDATE MUS_COLECCION SET CO_SAID = ?, CO_NOMBRE = ?, CO_SIGLO = ?, CO_DESCRIPCION = ? WHERE CO_ID = ?";

        try (Connection connect = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stm = connect.prepareStatement(sql)) {

            stm.setInt(1, coleccion.getCoSaid());
            stm.setString(2, coleccion.getCoNombre());
            stm.setString(3, coleccion.getCoSiglo());
            stm.setString(4, coleccion.getCoDescripcion());
            stm.setInt(5, coleccion.getCoId());

            stm.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

