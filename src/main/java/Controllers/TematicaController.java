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
import Database.MusTematica;
import Database.MusSala;
import Managers.MusTematicaManager;
import Managers.MusSalaManager;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.ObservableList;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.control.Alert;

/**
 * FXML Controller class
 *
 * @author Kenneth
 */
public class TematicaController implements Initializable {

    @FXML 
    private ComboBox<MusSala> cbSala;
    @FXML 
    private TextField txtNombre;
    @FXML 
    private TextField txtCaracteristicas;
    @FXML 
    private TextField txtEpoca;
    @FXML 
    private Button btnAgregar;
    @FXML 
    private Button btnModificar;
    @FXML 
    private Button btnEliminar;
    @FXML 
    private Button btnLimpiar;
    @FXML 
    private TableView<MusTematica> tableTematicas;
    @FXML 
    private TableColumn<MusTematica, Integer> colId;
    @FXML 
    private TableColumn<MusTematica, String> colNombre;
    @FXML 
    private TableColumn<MusTematica, String> colCaracteristicas;
    @FXML 
    private TableColumn<MusTematica, String> colEpoca;
    @FXML 
    private TableColumn<MusTematica, String> colSala;

    private final MusTematicaManager tematicaManager = new MusTematicaManager();
    private final MusSalaManager salaManager = new MusSalaManager();

    private MusTematica tematicaSeleccionada = null;
    private Map<Integer, String> mapaSalas = new HashMap<>();
    @FXML
    private Button btnGuardar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarSalas();
        configurarTabla();
        cargarTematicas();

        btnAgregar.setOnAction(e -> agregarTematica());
        btnModificar.setOnAction(e -> modificarTematica());
        btnEliminar.setOnAction(e -> eliminarTematica());
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
        colId.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getTeId()));
        colNombre.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getTeNombre()));
        colCaracteristicas.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getTeCaracteristicas()));
        colEpoca.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getTeEpoca()));

        colSala.setCellValueFactory(cellData -> {
            Integer idSala = cellData.getValue().getTeSaid();
            String nombre = mapaSalas.getOrDefault(idSala, "Sin sala");
            return new ReadOnlyStringWrapper(nombre);
        });
    }

    private void cargarTematicas() {
        ObservableList<MusTematica> tematicas = tematicaManager.getAllTematicas();
        tableTematicas.setItems(tematicas);
    }

    private void llenarFormulario(MusTematica t) {
        txtNombre.setText(t.getTeNombre());
        txtCaracteristicas.setText(t.getTeCaracteristicas());
        txtEpoca.setText(t.getTeEpoca());

        for (MusSala sala : cbSala.getItems()) {
            if (sala.getSaId().equals(t.getTeSaid())) {
                cbSala.setValue(sala);
                break;
            }
        }
    }

    private void agregarTematica() {
        MusTematica t = new MusTematica();
        t.setTeNombre(txtNombre.getText());
        t.setTeCaracteristicas(txtCaracteristicas.getText());
        t.setTeEpoca(txtEpoca.getText());
        t.setTeSaid(cbSala.getValue().getSaId());
        tematicaManager.addTematica(t);
        cargarTematicas();
        limpiarCampos();
    }

    private void modificarTematica() {
        MusTematica seleccionada = tableTematicas.getSelectionModel().getSelectedItem();
        if (seleccionada != null) {
            tematicaSeleccionada = seleccionada;
            llenarFormulario(seleccionada);
        } else {
            mostrarAlerta("Debe seleccionar una tematica para modificar.");
        }
    }
    
    private void guardarCambios() {
        if (tematicaSeleccionada == null) {
            mostrarAlerta("Debe seleccionar una tematica para guardar los cambios.");
            return;
        }

        tematicaSeleccionada.setTeNombre(txtNombre.getText());
        tematicaSeleccionada.setTeCaracteristicas(txtCaracteristicas.getText());
        tematicaSeleccionada.setTeEpoca(txtEpoca.getText());
        tematicaSeleccionada.setTeSaid(cbSala.getValue().getSaId());

        tematicaManager.updateTematica(tematicaSeleccionada);
        cargarTematicas();
        tableTematicas.refresh();
        limpiarCampos();
    }

    private void eliminarTematica() {
        if (tematicaSeleccionada != null) {
            tematicaManager.deleteTematica(tematicaSeleccionada.getTeId());
            cargarTematicas();
            limpiarCampos();
        }
    }

    private void limpiarCampos() {
        txtNombre.clear();
        txtCaracteristicas.clear();
        txtEpoca.clear();
        cbSala.setValue(null);
        tableTematicas.getSelectionModel().clearSelection();
        tematicaSeleccionada = null;
    }
    
    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Advertencia");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
