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
import Database.MusSala;
import Database.MusMuseo;
import Managers.MusSalaManager;
import Managers.MusMuseoManager;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ObservableList;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.control.Alert;

/**
 * FXML Controller class
 *
 * @author Kenneth
 */
public class SalaController implements Initializable {

    @FXML 
    private ComboBox<MusMuseo> cbMuseo;
    @FXML 
    private TextField txtNombre;
    @FXML 
    private TextField txtDescripcion;
    @FXML 
    private Button btnAgregar;
    @FXML 
    private Button btnModificar;
    @FXML 
    private Button btnEliminar;
    @FXML 
    private Button btnLimpiar;
    @FXML 
    private TableView<MusSala> tableSalas;
    @FXML 
    private TableColumn<MusSala, Integer> colId;
    @FXML 
    private TableColumn<MusSala, String> colNombre;
    @FXML 
    private TableColumn<MusSala, String> colDescripcion;
    @FXML 
    private TableColumn<MusSala, String> colMuseo;

    private final MusSalaManager salaManager = new MusSalaManager();
    private final MusMuseoManager museoManager = new MusMuseoManager();
    private Map<Integer, String> mapaMuseos = new HashMap<>();

    private MusSala salaSeleccionada = null;
    @FXML
    private Button btnGuardar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarMuseos();
        configurarTabla();
        cargarSalas();

        btnAgregar.setOnAction(e -> agregarSala());
        btnModificar.setOnAction(e -> modificarSala());
        btnEliminar.setOnAction(e -> eliminarSala());
        btnLimpiar.setOnAction(e -> limpiarCampos());
        btnGuardar.setOnAction(e -> guardarCambios());
    }

    private void cargarMuseos() {
        ObservableList<MusMuseo> museos = museoManager.getAllMuseos();
        cbMuseo.setItems(museos);

        mapaMuseos.clear();
        for (MusMuseo m : museos) {
            mapaMuseos.put(m.getMuId(), m.getMuNombre());
        }
    }

    private void configurarTabla() {
        colId.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getSaId()));
        colNombre.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getSaNombre()));
        colDescripcion.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getSaDescripcion()));

        colMuseo.setCellValueFactory(cellData -> {
            Integer idMuseo = cellData.getValue().getSaMuid();
            String nombre = mapaMuseos.getOrDefault(idMuseo, "Sin museo");
            return new ReadOnlyStringWrapper(nombre);
        });
    }

    private void cargarSalas() {
        ObservableList<MusSala> salas = salaManager.getAllSalas();
        tableSalas.setItems(salas);
    }

    private void llenarFormulario(MusSala sala) {
        txtNombre.setText(sala.getSaNombre());
        txtDescripcion.setText(sala.getSaDescripcion());

        for (MusMuseo museo : cbMuseo.getItems()) {
            if (museo.getMuId().equals(sala.getSaMuid())) {
                cbMuseo.setValue(museo);
                break;
            }
        }
    }

    private void agregarSala() {
        MusSala sala = new MusSala();
        sala.setSaNombre(txtNombre.getText());
        sala.setSaDescripcion(txtDescripcion.getText());
        sala.setSaMuid(cbMuseo.getValue().getMuId());
        salaManager.addSala(sala);
        cargarSalas();
        limpiarCampos();
    }

    private void modificarSala() {
        MusSala seleccionada = tableSalas.getSelectionModel().getSelectedItem();
        if (seleccionada != null) {
            salaSeleccionada = seleccionada;
            llenarFormulario(seleccionada);
        } else {
            mostrarAlerta("Debe seleccionar una sala para modificar.");
        }
    }

    private void eliminarSala() {
        if (salaSeleccionada != null) {
            salaManager.deleteSala(salaSeleccionada.getSaId());
            cargarSalas();
            limpiarCampos();
        }
    }

    private void limpiarCampos() {
        txtNombre.clear();
        txtDescripcion.clear();
        cbMuseo.setValue(null);
        tableSalas.getSelectionModel().clearSelection();
        salaSeleccionada = null;
    }
    
    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Advertencia");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    
    private void guardarCambios() {
        if (salaSeleccionada == null) {
            mostrarAlerta("Debe seleccionar una sala para guardar los cambios.");
            return;
        }

        salaSeleccionada.setSaNombre(txtNombre.getText());
        salaSeleccionada.setSaDescripcion(txtDescripcion.getText());
        salaSeleccionada.setSaMuid(cbMuseo.getValue().getMuId());

        salaManager.updateSala(salaSeleccionada);
        cargarSalas();
        tableSalas.refresh();
        limpiarCampos();
    }
}
