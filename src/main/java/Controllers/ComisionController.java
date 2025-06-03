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
import Database.MusComision;
import Managers.MusComisionManager;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.ObservableList;

/**
 * FXML Controller class
 *
 * @author Kenneth
 */
public class ComisionController implements Initializable {

    @FXML 
    private ComboBox<String> cbTipo;
    @FXML 
    private TextField txtPorcentaje;
    @FXML 
    private Button btnAgregar;
    @FXML 
    private Button btnModificar;
    @FXML 
    private Button btnEliminar;
    @FXML 
    private Button btnLimpiar;
    @FXML 
    private TableView<MusComision> tableComisiones;
    @FXML 
    private TableColumn<MusComision, Integer> colId;
    @FXML 
    private TableColumn<MusComision, String> colTipo;
    @FXML 
    private TableColumn<MusComision, Double> colPorcentaje;

    private final MusComisionManager comisionManager = new MusComisionManager();
    private MusComision comisionSeleccionada = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbTipo.getItems().addAll("VS", "MC", "AE", "DC", "UP");
        configurarTabla();
        cargarComisiones();

        tableComisiones.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                comisionSeleccionada = newVal;
                llenarFormulario(newVal);
            }
        });

        btnAgregar.setOnAction(e -> agregarComision());
        btnModificar.setOnAction(e -> modificarComision());
        btnEliminar.setOnAction(e -> eliminarComision());
        btnLimpiar.setOnAction(e -> limpiarCampos());
    }

    private void configurarTabla() {
        colId.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getCmId()));
        colTipo.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getCmTipo()));
        colPorcentaje.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getCmPorcentaje()));
    }

    private void cargarComisiones() {
        ObservableList<MusComision> comisiones = comisionManager.getAllComisiones();
        tableComisiones.setItems(comisiones);
    }

    private void llenarFormulario(MusComision c) {
        cbTipo.setValue(c.getCmTipo());
        txtPorcentaje.setText(String.valueOf(c.getCmPorcentaje()));
    }

    private void agregarComision() {
        MusComision c = new MusComision();
        c.setCmTipo(cbTipo.getValue());
        c.setCmPorcentaje(Double.parseDouble(txtPorcentaje.getText()));
        comisionManager.addComision(c);
        cargarComisiones();
        limpiarCampos();
    }

    private void modificarComision() {
        if (comisionSeleccionada != null) {
            comisionSeleccionada.setCmTipo(cbTipo.getValue());
            comisionSeleccionada.setCmPorcentaje(Double.parseDouble(txtPorcentaje.getText()));
            comisionManager.updateComision(comisionSeleccionada);
            cargarComisiones();
            limpiarCampos();
        }
    }

    private void eliminarComision() {
        if (comisionSeleccionada != null) {
            comisionManager.deleteComision(comisionSeleccionada.getCmId());
            cargarComisiones();
            limpiarCampos();
        }
    }

    private void limpiarCampos() {
        cbTipo.setValue(null);
        txtPorcentaje.clear();
        tableComisiones.getSelectionModel().clearSelection();
        comisionSeleccionada = null;
    }
}
