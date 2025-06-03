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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import Database.MusEspecie;
import Database.MusColeccion;
import Managers.MusEspecieManager;
import Managers.MusColeccionManager;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.ObservableList;
import java.util.Date;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

/**
 * FXML Controller class
 *
 * @author Kenneth
 */
public class EspecieController implements Initializable {

    @FXML 
    private ComboBox<MusColeccion> cbColeccion;
    @FXML 
    private TextField txtNombreCientifico;
    @FXML 
    private TextField txtNombreComun;
    @FXML 
    private DatePicker dpExtincion;
    @FXML 
    private TextField txtEpoca;
    @FXML 
    private TextField txtPeso;
    @FXML 
    private TextField txtTamano;
    @FXML 
    private TextField txtCaracteristicas;
    @FXML 
    private Button btnAgregar;
    @FXML 
    private Button btnModificar;
    @FXML 
    private Button btnEliminar;
    @FXML 
    private Button btnLimpiar;
    @FXML 
    private TableView<MusEspecie> tableEspecies;
    @FXML 
    private TableColumn<MusEspecie, Integer> colId;
    @FXML 
    private TableColumn<MusEspecie, String> colCientifico;
    @FXML 
    private TableColumn<MusEspecie, String> colComun;
    @FXML 
    private TableColumn<MusEspecie, String> colEpoca;
    @FXML 
    private TableColumn<MusEspecie, Double> colPeso;
    @FXML 
    private TableColumn<MusEspecie, Double> colTamano;
    @FXML 
    private TableColumn<MusEspecie, Date> colExtincion;
    @FXML 
    private TableColumn<MusEspecie, String> colCaracteristicas;
    @FXML 
    private TableColumn<MusEspecie, String> colColeccion;

    private final MusEspecieManager especieManager = new MusEspecieManager();
    private final MusColeccionManager coleccionManager = new MusColeccionManager();
    private MusEspecie especieSeleccionada = null;
    private Map<Integer, String> mapaColecciones = new HashMap<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarColecciones();
        configurarTabla();
        cargarEspecies();

        tableEspecies.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                especieSeleccionada = newVal;
                llenarFormulario(newVal);
            }
        });

        btnAgregar.setOnAction(e -> agregarEspecie());
        btnModificar.setOnAction(e -> modificarEspecie());
        btnEliminar.setOnAction(e -> eliminarEspecie());
        btnLimpiar.setOnAction(e -> limpiarCampos());
    }

    private void cargarColecciones() {
        ObservableList<MusColeccion> colecciones = coleccionManager.getAllColecciones();
        cbColeccion.setItems(colecciones);

        mapaColecciones.clear();
        for (MusColeccion col : colecciones) {
            mapaColecciones.put(col.getCoId(), col.getCoNombre());
        }
    }

    private void configurarTabla() {
        colId.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getEsId()));
        colCientifico.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getEsNombreCient()));
        colComun.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getEsNombreComun()));
        colEpoca.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getEsEpoca()));
        colPeso.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getEsPeso()));
        colTamano.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getEsTamano()));
        colExtincion.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<java.util.Date>(cellData.getValue().getEsExtincion()));
        colCaracteristicas.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getEsCaracteristicas()));

        colColeccion.setCellValueFactory(cellData -> {
            Integer idColeccion = cellData.getValue().getEsCoid();
            String nombre = mapaColecciones.getOrDefault(idColeccion, "Sin colección");
            return new ReadOnlyStringWrapper(nombre);
        });
    }

    private void cargarEspecies() {
        ObservableList<MusEspecie> especies = especieManager.getAllEspecies();
        tableEspecies.setItems(especies);
    }

    private void llenarFormulario(MusEspecie e) {
        txtNombreCientifico.setText(e.getEsNombreCient());
        txtNombreComun.setText(e.getEsNombreComun());
        dpExtincion.setValue(e.getEsExtincion() != null ? e.getEsExtincion().toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null);
        txtEpoca.setText(e.getEsEpoca());
        txtPeso.setText(String.valueOf(e.getEsPeso()));
        txtTamano.setText(String.valueOf(e.getEsTamano()));
        txtCaracteristicas.setText(e.getEsCaracteristicas());

        for (MusColeccion col : cbColeccion.getItems()) {
            if (col.getCoId().equals(e.getEsCoid())) {
                cbColeccion.setValue(col);
                break;
            }
        }
    }

    private void agregarEspecie() {
        MusEspecie e = new MusEspecie();
        e.setEsCoid(cbColeccion.getValue().getCoId());
        e.setEsNombreCient(txtNombreCientifico.getText());
        e.setEsNombreComun(txtNombreComun.getText());
        e.setEsExtincion(dpExtincion.getValue() != null ? java.sql.Date.valueOf(dpExtincion.getValue()) : null);
        e.setEsEpoca(txtEpoca.getText());
        e.setEsPeso(Double.parseDouble(txtPeso.getText()));
        e.setEsTamano(Double.parseDouble(txtTamano.getText()));
        e.setEsCaracteristicas(txtCaracteristicas.getText());
        especieManager.addEspecie(e);
        cargarEspecies();
        limpiarCampos();
    }

    private void modificarEspecie() {
        if (especieSeleccionada != null) {
            especieSeleccionada.setEsCoid(cbColeccion.getValue().getCoId());
            especieSeleccionada.setEsNombreCient(txtNombreCientifico.getText());
            especieSeleccionada.setEsNombreComun(txtNombreComun.getText());
            especieSeleccionada.setEsExtincion(dpExtincion.getValue() != null ? java.sql.Date.valueOf(dpExtincion.getValue()) : null);
            especieSeleccionada.setEsEpoca(txtEpoca.getText());
            especieSeleccionada.setEsPeso(Double.parseDouble(txtPeso.getText()));
            especieSeleccionada.setEsTamano(Double.parseDouble(txtTamano.getText()));
            especieSeleccionada.setEsCaracteristicas(txtCaracteristicas.getText());
            especieManager.updateEspecie(especieSeleccionada);
            cargarEspecies();
            limpiarCampos();
        }
    }

    private void eliminarEspecie() {
        if (especieSeleccionada != null) {
            especieManager.deleteEspecie(especieSeleccionada.getEsId());
            cargarEspecies();
            limpiarCampos();
        }
    }

    private void limpiarCampos() {
        txtNombreCientifico.clear();
        txtNombreComun.clear();
        dpExtincion.setValue(null);
        txtEpoca.clear();
        txtPeso.clear();
        txtTamano.clear();
        txtCaracteristicas.clear();
        cbColeccion.setValue(null);
        tableEspecies.getSelectionModel().clearSelection();
        especieSeleccionada = null;
    }
}
