/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Managers;

import Database.MusEspecie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

/**
 *
 * @author Kenneth
 */
public class MusEspecieManager {

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

    public void addEspecie(MusEspecie especie) {
        String sql = "INSERT INTO MUS_ESPECIE (ES_COID, ES_NOMBRE_CIENT, ES_NOMBRE_COMUN, ES_EXTINCION, ES_EPOCA, ES_PESO, ES_TAMANO, ES_CARACTERISTICAS) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connect = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stm = connect.prepareStatement(sql)) {

            stm.setInt(1, especie.getEsCoid());
            stm.setString(2, especie.getEsNombreCient());
            stm.setString(3, especie.getEsNombreComun());

            if (especie.getEsExtincion() != null) {
                stm.setDate(4, new java.sql.Date(especie.getEsExtincion().getTime()));
            } else {
                stm.setNull(4, Types.DATE);
            }

            stm.setString(5, especie.getEsEpoca());
            stm.setDouble(6, especie.getEsPeso());
            stm.setDouble(7, especie.getEsTamano());
            stm.setString(8, especie.getEsCaracteristicas());

            stm.executeUpdate();
            closeConnection(connect);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<MusEspecie> getAllEspecies() {
        ObservableList<MusEspecie> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM MUS_ESPECIE";

        try (Connection connect = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stm = connect.prepareStatement(sql);
             ResultSet rs = stm.executeQuery()) {

            while (rs.next()) {
                MusEspecie especie = new MusEspecie();
                especie.setEsId(rs.getInt("ES_ID"));
                especie.setEsCoid(rs.getInt("ES_COID"));
                especie.setEsNombreCient(rs.getString("ES_NOMBRE_CIENT"));
                especie.setEsNombreComun(rs.getString("ES_NOMBRE_COMUN"));
                especie.setEsExtincion(rs.getDate("ES_EXTINCION"));
                especie.setEsEpoca(rs.getString("ES_EPOCA"));
                especie.setEsPeso(rs.getDouble("ES_PESO"));
                especie.setEsTamano(rs.getDouble("ES_TAMANO"));
                especie.setEsCaracteristicas(rs.getString("ES_CARACTERISTICAS"));
                list.add(especie);
            }

            closeConnection(connect);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void deleteEspecie(Integer id) {
        String sql = "DELETE FROM MUS_ESPECIE WHERE ES_ID = ?";

        try (Connection connect = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stm = connect.prepareStatement(sql)) {

            stm.setInt(1, id);
            stm.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateEspecie(MusEspecie especie) {
        String sql = "UPDATE MUS_ESPECIE SET ES_COID = ?, ES_NOMBRE_CIENT = ?, ES_NOMBRE_COMUN = ?, ES_EXTINCION = ?, ES_EPOCA = ?, ES_PESO = ?, ES_TAMANO = ?, ES_CARACTERISTICAS = ? " + "WHERE ES_ID = ?";

        try (Connection connect = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stm = connect.prepareStatement(sql)) {

            stm.setInt(1, especie.getEsCoid());
            stm.setString(2, especie.getEsNombreCient());
            stm.setString(3, especie.getEsNombreComun());

            if (especie.getEsExtincion() != null) {
                stm.setDate(4, new java.sql.Date(especie.getEsExtincion().getTime()));
            } else {
                stm.setNull(4, Types.DATE);
            }

            stm.setString(5, especie.getEsEpoca());
            stm.setDouble(6, especie.getEsPeso());
            stm.setDouble(7, especie.getEsTamano());
            stm.setString(8, especie.getEsCaracteristicas());
            stm.setInt(9, especie.getEsId());

            stm.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
