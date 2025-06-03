/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Managers;

import Database.MusMuseo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.Date;

/**
 *
 * @author Kenneth
 */
public class MusMuseoManager {

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

    public void addMuseo(MusMuseo museo) {
        String sql = "INSERT INTO MUS_MUSEO (MU_NOMBRE, MU_TIPO, MU_UBICACION, MU_FECHA_FUN, MU_DIRECTOR, MU_SITIO_WEP) " + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connect = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stm = connect.prepareStatement(sql)) {

            stm.setString(1, museo.getMuNombre());
            stm.setString(2, museo.getMuTipo());
            stm.setString(3, museo.getMuUbicacion());
            if (museo.getMuFechaFun() != null) {
                stm.setDate(4, new java.sql.Date(museo.getMuFechaFun().getTime()));
            } else {
                stm.setNull(4, Types.DATE);
            }
            stm.setString(5, museo.getMuDirector());
            stm.setString(6, museo.getMuSitioWep());

            stm.executeUpdate();
            closeConnection(connect);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<MusMuseo> getAllMuseos() {
        ObservableList<MusMuseo> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM MUS_MUSEO";

        try (Connection connect = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stm = connect.prepareStatement(sql);
             ResultSet rs = stm.executeQuery()) {

            while (rs.next()) {
                MusMuseo museo = new MusMuseo();
                museo.setMuId(rs.getInt("MU_ID"));
                museo.setMuNombre(rs.getString("MU_NOMBRE"));
                museo.setMuTipo(rs.getString("MU_TIPO"));
                museo.setMuUbicacion(rs.getString("MU_UBICACION"));
                museo.setMuFechaFun(rs.getDate("MU_FECHA_FUN"));
                museo.setMuDirector(rs.getString("MU_DIRECTOR"));
                museo.setMuSitioWep(rs.getString("MU_SITIO_WEP"));
                list.add(museo);
            }

            closeConnection(connect);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void deleteMuseo(Integer id) {
        String sql = "DELETE FROM MUS_MUSEO WHERE MU_ID = ?";

        try (Connection connect = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stm = connect.prepareStatement(sql)) {

            stm.setInt(1, id);
            stm.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateMuseo(MusMuseo museo) {
        String sql = "UPDATE MUS_MUSEO SET MU_NOMBRE = ?, MU_TIPO = ?, MU_UBICACION = ?, MU_FECHA_FUN = ?, MU_DIRECTOR = ?, MU_SITIO_WEP = ? " + "WHERE MU_ID = ?";

        try (Connection connect = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stm = connect.prepareStatement(sql)) {

            stm.setString(1, museo.getMuNombre());
            stm.setString(2, museo.getMuTipo());
            stm.setString(3, museo.getMuUbicacion());
            if (museo.getMuFechaFun() != null) {
                stm.setDate(4, new java.sql.Date(museo.getMuFechaFun().getTime()));
            } else {
                stm.setNull(4, Types.DATE);
            }
            stm.setString(5, museo.getMuDirector());
            stm.setString(6, museo.getMuSitioWep());
            stm.setInt(7, museo.getMuId());

            stm.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

