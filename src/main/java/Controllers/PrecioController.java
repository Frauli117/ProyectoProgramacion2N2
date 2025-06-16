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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import Database.MusPrecio;
import Database.MusSala;
import Managers.MusPrecioManager;
import Managers.MusSalaManager;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.ObservableList;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.control.Alert;

/**
 * FXML Controller class
 *
 * @author Kenneth
 */
public class PrecioController implements Initializable {

    @FXML 
    private ComboBox<MusSala> cbSala;
    @FXML 
    private TextField txtPrecioLunSab;
    @FXML 
    private TextField txtPrecioDomingo;
    @FXML 
    private Button btnAgregar;
    @FXML 
    private Button btnModificar;
    @FXML 
    private Button btnEliminar;
    @FXML 
    private Button btnLimpiar;
    @FXML 
    private TableView<MusPrecio> tablePrecios;
    @FXML 
    private TableColumn<MusPrecio, Integer> colId;
    @FXML 
    private TableColumn<MusPrecio, String> colSala;
    @FXML 
    private TableColumn<MusPrecio, Double> colLunSab;
    @FXML 
    private TableColumn<MusPrecio, Double> colDomingo;

    private final MusSalaManager salaManager = new MusSalaManager();
    private final MusPrecioManager precioManager = new MusPrecioManager();

    private MusPrecio precioSeleccionado = null;
    private Map<Integer, String> mapaSalas = new HashMap<>();
    @FXML
    private Button btnGuardar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarSalas();
        configurarTabla();
        cargarPrecios();

        btnAgregar.setOnAction(e -> agregarPrecio());
        btnModificar.setOnAction(e -> modificarPrecio());
        btnEliminar.setOnAction(e -> eliminarPrecio());
        btnLimpiar.setOnAction(e -> limpiarCampos());
        btnGuardar.setOnAction(e -> guardarCambios());
    }

    private void cargarSalas() {
        ObservableList<MusSala> salas = salaManager.getAllSalas();
        cbSala.setItems(salas);

        mapaSalas.clear();
        for (MusSala sala : salas) {
            mapaSalas.put(sala.getSaId(), sala.getSaNombre());
        }
    }

    private void configurarTabla() {
        colId.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getPrId()));
        colLunSab.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getPrLunsab()));
        colDomingo.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getPrDomingo()));

        colSala.setCellValueFactory(cellData -> {
            Integer idSala = cellData.getValue().getPrSaid();
            String nombreSala = mapaSalas.getOrDefault(idSala, "Sin sala");
            return new ReadOnlyStringWrapper(nombreSala);
        });
    }

    private void cargarPrecios() {
        ObservableList<MusPrecio> precios = precioManager.getAllPrecios();
        tablePrecios.setItems(precios);
    }

    private void llenarFormulario(MusPrecio p) {
        txtPrecioLunSab.setText(String.valueOf(p.getPrLunsab()));
        txtPrecioDomingo.setText(String.valueOf(p.getPrDomingo()));

        for (MusSala sala : cbSala.getItems()) {
            if (sala.getSaId().equals(p.getPrSaid())) {
                cbSala.setValue(sala);
                break;
            }
        }
    }

    private void agregarPrecio() {
        MusPrecio p = new MusPrecio();
        p.setPrSaid(cbSala.getValue().getSaId());
        p.setPrLunsab(Double.parseDouble(txtPrecioLunSab.getText()));
        p.setPrDomingo(Double.parseDouble(txtPrecioDomingo.getText()));
        precioManager.addPrecio(p);
        cargarPrecios();
        limpiarCampos();
    }

    private void modificarPrecio() {
        MusPrecio seleccionado = tablePrecios.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            precioSeleccionado = seleccionado;
            llenarFormulario(precioSeleccionado);
        } else {
            mostrarAlerta("Debe seleccionar un precio para modificar.");
        }
    }
    
    private void guardarCambios() {
        if (precioSeleccionado == null) {
            mostrarAlerta("Debe seleccionar un precio para guardar los cambios.");
            return;
        }

        precioSeleccionado.setPrSaid(cbSala.getValue().getSaId());
        precioSeleccionado.setPrLunsab(Double.parseDouble(txtPrecioLunSab.getText()));
        precioSeleccionado.setPrDomingo(Double.parseDouble(txtPrecioDomingo.getText()));

        precioManager.updatePrecio(precioSeleccionado);
        cargarPrecios();
        tablePrecios.refresh();
        limpiarCampos();
    }

    private void eliminarPrecio() {
        if (precioSeleccionado != null) {
            precioManager.deletePrecio(precioSeleccionado.getPrId());
            cargarPrecios();
            limpiarCampos();
        }
    }

    private void limpiarCampos() {
        txtPrecioLunSab.clear();
        txtPrecioDomingo.clear();
        cbSala.setValue(null);
        tablePrecios.getSelectionModel().clearSelection();
        precioSeleccionado = null;
    }
    
    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Advertencia");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
