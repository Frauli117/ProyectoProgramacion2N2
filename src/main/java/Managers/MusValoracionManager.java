/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Managers;

import Database.MusValoracion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;
import java.util.ArrayList;



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
    
    public boolean guardarValoracion(int salaId, int estrellas, String comentario) {
        String sql = "INSERT INTO MUS_VALORACION (VA_SAID, VA_ESTRELLAS, VA_COMENTARIO) VALUES (?, ?, ?)";

        try (Connection connect = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stm = connect.prepareStatement(sql)) {

            stm.setInt(1, salaId);
            stm.setInt(2, estrellas);
            stm.setString(3, comentario);

            stm.executeUpdate();
            closeConnection(connect);
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<String> getPromedioValoracionPorSala(boolean ascendente) {
    List<String> lista = new ArrayList<>();

        String orden = ascendente ? "ASC" : "DESC";
        String sql = "SELECT S.SA_ID, S.SA_NOMBRE, ROUND(AVG(V.VA_ESTRELLAS), 2) AS PROMEDIO " +
                     "FROM MUS_SALA S " +
                     "JOIN MUS_VALORACION V ON S.SA_ID = V.VA_SAID " +
                     "GROUP BY S.SA_ID, S.SA_NOMBRE " +
                     "ORDER BY PROMEDIO " + orden;

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("SA_ID");
                String nombre = rs.getString("SA_NOMBRE");
                double promedio = rs.getDouble("PROMEDIO");

                String info = "Sala: " + nombre + " | ID: " + id + " | Promedio: " + promedio;
                lista.add(info);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }


}

