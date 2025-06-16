/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import Database.MusComision;
import Database.MusEntrada;
import Database.MusPrecio;
import Database.MusSala;
import Managers.MusComisionManager;
import Managers.MusEntradaManager;
import Managers.MusSalaManager;
import Managers.MusPrecioManager;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.DayOfWeek;
import java.time.LocalDate;
import javafx.scene.control.*;
import java.util.*;
import javafx.scene.control.Alert;

/**
 * FXML Controller class
 *
 * @author Kenneth
 */
public class VentaEntradaController implements Initializable {

    @FXML 
    private TextField txtNombre;
    @FXML 
    private ComboBox<MusSala> cbSala;
    @FXML 
    private ComboBox<MusComision> cbTarjeta;
    @FXML 
    private DatePicker dpFecha;
    @FXML 
    private TableView<DetalleEntrada> tableDetalle;
    @FXML 
    private TableColumn<DetalleEntrada, String> colSala;
    @FXML 
    private TableColumn<DetalleEntrada, LocalDate> colFecha;
    @FXML 
    private TableColumn<DetalleEntrada, Double> colPrecio;
    @FXML 
    private Label lblSubtotal;
    @FXML 
    private Label lblIVA;
    @FXML 
    private Label lblTotal;
    @FXML 
    private Button btnAgregar;
    @FXML 
    private Button btnVender;
    
    private final MusSalaManager salaManager = new MusSalaManager();
    private final MusComisionManager comisionManager = new MusComisionManager();
    private final MusEntradaManager entradaManager = new MusEntradaManager();
    private final MusPrecioManager precioManager = new MusPrecioManager();
    
    private final ObservableList<DetalleEntrada> listaDetalle = FXCollections.observableArrayList();
    @FXML
    private Label lblCodigoGenerado;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarSalas();
        cargarComisiones();
        configurarTabla();

        btnAgregar.setOnAction(e -> agregarDetalle());
        btnVender.setOnAction(e -> confirmarVenta());
    }

    private void cargarSalas() {
        cbSala.setItems(salaManager.getAllSalas());
    }

    private void cargarComisiones() {
        cbTarjeta.setItems(comisionManager.getAllComisiones());
    }

    private void configurarTabla() {
        colSala.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getNombreSala()));
        colFecha.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getFecha()));
        colPrecio.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getPrecio()));
        tableDetalle.setItems(listaDetalle);
    }
    
    private void agregarDetalle() {
        MusSala sala = cbSala.getValue();
        LocalDate fecha = dpFecha.getValue();

        if (sala == null || fecha == null) return;

        double precioBase = obtenerPrecioSala(sala, fecha);
        listaDetalle.add(new DetalleEntrada(sala.getSaId(), sala.getSaNombre(), fecha, precioBase));

        actualizarTotales();
    }

    private double obtenerPrecioSala(MusSala sala, LocalDate fecha) {
        MusPrecio precio = precioManager.getPrecioPorSala(sala.getSaId());
        if (precio == null) return 0.0;
        return (fecha.getDayOfWeek() == DayOfWeek.SUNDAY)
                ? precio.getPrDomingo()
                : precio.getPrLunsab();
    }


    private void actualizarTotales() {
        double subtotal = listaDetalle.stream().mapToDouble(DetalleEntrada::getPrecio).sum();
        double iva = subtotal * 0.13;
        double total = subtotal + iva;

        lblSubtotal.setText("₡" + String.format("%.2f", subtotal));
        lblIVA.setText("₡" + String.format("%.2f", iva));
        lblTotal.setText("₡" + String.format("%.2f", total));
    }

    private void confirmarVenta() {
        if (txtNombre.getText().isEmpty() || cbTarjeta.getValue() == null || listaDetalle.isEmpty()) return;

        double subtotal = listaDetalle.stream().mapToDouble(DetalleEntrada::getPrecio).sum();
        double iva = subtotal * 0.13;
        double total = subtotal + iva;
        String qrCode = String.format("%04d", (int)(Math.random() * 10000));

        MusEntrada entrada = new MusEntrada();
        entrada.setEnNombre(txtNombre.getText());
        entrada.setEnFecha(new java.sql.Date(System.currentTimeMillis()));
        entrada.setEnTotal(total);
        entrada.setEnQr(qrCode);
        entrada.setEnComid(cbTarjeta.getValue());

        entradaManager.addEntradaConDetalle(entrada, listaDetalle);
        
        lblCodigoGenerado.setText("Código generado: " + qrCode);

        Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
        mensaje.setTitle("Codigo generado");
        mensaje.setHeaderText("Venta realizada con exito");
        mensaje.setContentText("El ccdigo de entrada es: " + qrCode);
        mensaje.showAndWait();
        listaDetalle.clear();
        actualizarTotales();
        limpiarFormulario();
    }

    private void limpiarFormulario() {
        txtNombre.clear();
        lblCodigoGenerado.setText("");
        cbSala.setValue(null);
        cbTarjeta.setValue(null);
        dpFecha.setValue(null);
    }

    public static class DetalleEntrada {
        private final int idSala;
        private final String nombreSala;
        private final LocalDate fecha;
        private final double precio;

        public DetalleEntrada(int idSala, String nombreSala, LocalDate fecha, double precio) {
            this.idSala = idSala;
            this.nombreSala = nombreSala;
            this.fecha = fecha;
            this.precio = precio;
        }

        public int getIdSala() { return idSala; }
        public String getNombreSala() { return nombreSala; }
        public LocalDate getFecha() { return fecha; }
        public double getPrecio() { return precio; }
    }
}

