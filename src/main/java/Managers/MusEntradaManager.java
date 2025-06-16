/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Managers;

import Database.MusEntrada;
import Database.MusComision;
import Database.MusSala;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Controllers.VentaEntradaController;
import java.util.*;
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
    
    public void addEntradaConDetalle(MusEntrada entrada, List<VentaEntradaController.DetalleEntrada> detalles) {
        String sqlEntrada = "INSERT INTO MUS_ENTRADA (EN_COMID, EN_FECHA, EN_TOTAL, EN_QR, EN_NOMBRE) " +
                            "VALUES (?, ?, ?, ?, ?)";
        String sqlDetalle = "INSERT INTO MUS_DET_ENTRADA (DE_ENID, DE_SAID, DE_PRECIO) VALUES (?, ?, ?)";

        Connection conn = null;
        PreparedStatement psEntrada = null;
        PreparedStatement psDetalle = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(URL, USER, PASS);
            conn.setAutoCommit(false);

            psEntrada = conn.prepareStatement(sqlEntrada, new String[] { "EN_ID" });
            psEntrada.setInt(1, entrada.getEnComid().getCmId());
            psEntrada.setDate(2, new java.sql.Date(entrada.getEnFecha().getTime()));
            psEntrada.setDouble(3, entrada.getEnTotal());
            psEntrada.setString(4, entrada.getEnQr());
            psEntrada.setString(5, entrada.getEnNombre());
            psEntrada.executeUpdate();

            rs = psEntrada.getGeneratedKeys();
            int entradaId = -1;
            if (rs.next()) {
                entradaId = rs.getInt(1);
            }

            psDetalle = conn.prepareStatement(sqlDetalle);
            for (VentaEntradaController.DetalleEntrada det : detalles) {
                psDetalle.setInt(1, entradaId);
                psDetalle.setInt(2, det.getIdSala());
                psDetalle.setDouble(3, det.getPrecio());
                psDetalle.addBatch();
            }
            psDetalle.executeBatch();

            conn.commit();

        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (psEntrada != null) psEntrada.close(); } catch (Exception e) {}
            try { if (psDetalle != null) psDetalle.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
    }
    
    public MusEntrada buscarPorCodigo(String codigo) {
    MusEntrada entrada = null;
    String sql = "SELECT * FROM MUS_ENTRADA WHERE EN_QR = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, codigo);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                entrada = new MusEntrada();
                entrada.setEnId(rs.getInt("EN_ID"));
                entrada.setEnNombre(rs.getString("EN_NOMBRE"));
                entrada.setEnFecha(new java.util.Date(rs.getTimestamp("EN_FECHA").getTime()));
                entrada.setEnTotal(rs.getDouble("EN_TOTAL"));
                entrada.setEnQr(rs.getString("EN_QR"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return entrada;
    }
    
    public List<MusSala> getSalasPorEntrada(int entradaId) {
    List<MusSala> salas = new ArrayList<>();
    String sql = "SELECT S.SA_ID, S.SA_NOMBRE, S.SA_DESCRIPCION " +
                 "FROM MUS_SALA S " +
                 "JOIN MUS_DET_ENTRADA D ON S.SA_ID = D.DE_SAID " +
                 "WHERE D.DE_ENID = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, entradaId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                MusSala sala = new MusSala();
                sala.setSaId(rs.getInt("SA_ID"));
                sala.setSaNombre(rs.getString("SA_NOMBRE"));
                sala.setSaDescripcion(rs.getString("SA_DESCRIPCION"));
                salas.add(sala);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return salas;
    }



}


