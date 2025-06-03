/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Managers;

import Database.MusValoracion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

/**
 *
 * @author Kenneth
 */
public class MusValoracionManager {

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

    public void addValoracion(MusValoracion valoracion) {
        String sql = "INSERT INTO MUS_VALORACION (VA_SAID, VA_ESTRELLAS, VA_COMENTARIO) VALUES (?, ?, ?)";

        try (Connection connect = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stm = connect.prepareStatement(sql)) {

            stm.setInt(1, valoracion.getVaSaid());
            stm.setInt(2, valoracion.getVaEstrellas());
            stm.setString(3, valoracion.getVaComentario());

            stm.executeUpdate();
            closeConnection(connect);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<MusValoracion> getAllValoraciones() {
        ObservableList<MusValoracion> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM MUS_VALORACION";

        try (Connection connect = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stm = connect.prepareStatement(sql);
             ResultSet rs = stm.executeQuery()) {

            while (rs.next()) {
                MusValoracion val = new MusValoracion();
                val.setVaId(rs.getInt("VA_ID"));
                val.setVaSaid(rs.getInt("VA_SAID"));
                val.setVaEstrellas(rs.getInt("VA_ESTRELLAS"));
                val.setVaComentario(rs.getString("VA_COMENTARIO"));
                list.add(val);
            }

            closeConnection(connect);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void deleteValoracion(Integer id) {
        String sql = "DELETE FROM MUS_VALORACION WHERE VA_ID = ?";

        try (Connection connect = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stm = connect.prepareStatement(sql)) {

            stm.setInt(1, id);
            stm.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateValoracion(MusValoracion valoracion) {
        String sql = "UPDATE MUS_VALORACION SET VA_SAID = ?, VA_ESTRELLAS = ?, VA_COMENTARIO = ? WHERE VA_ID = ?";

        try (Connection connect = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stm = connect.prepareStatement(sql)) {

            stm.setInt(1, valoracion.getVaSaid());
            stm.setInt(2, valoracion.getVaEstrellas());
            stm.setString(3, valoracion.getVaComentario());
            stm.setInt(4, valoracion.getVaId());

            stm.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

