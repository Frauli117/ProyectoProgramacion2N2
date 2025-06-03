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
import Database.MusColeccion;
import Database.MusSala;
import Managers.MusColeccionManager;
import Managers.MusSalaManager;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.ObservableList;
import java.util.HashMap;
import java.util.Map;

/**
 * FXML Controller class
 *
 * @author Kenneth
 */
public class ColeccionController implements Initializable {

    @FXML 
    private ComboBox<MusSala> cbSala;
    @FXML 
    private TextField txtNombre;
    @FXML 
    private TextField txtSiglo;
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
    private TableView<MusColeccion> tableColecciones;
    @FXML 
    private TableColumn<MusColeccion, Integer> colId;
    @FXML 
    private TableColumn<MusColeccion, String> colNombre;
    @FXML 
    private TableColumn<MusColeccion, String> colSiglo;
    @FXML 
    private TableColumn<MusColeccion, String> colDescripcion;
    @FXML 
    private TableColumn<MusColeccion, String> colSala;

    private final MusSalaManager salaManager = new MusSalaManager();
    private final MusColeccionManager coleccionManager = new MusColeccionManager();

    private MusColeccion coleccionSeleccionada = null;
    private Map<Integer, String> mapaSalas = new HashMap<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarSalas();
        configurarTabla();
        cargarColecciones();

        tableColecciones.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                coleccionSeleccionada = newVal;
                llenarFormulario(newVal);
            }
        });

        btnAgregar.setOnAction(e -> agregarColeccion());
        btnModificar.setOnAction(e -> modificarColeccion());
        btnEliminar.setOnAction(e -> eliminarColeccion());
        btnLimpiar.setOnAction(e -> limpiarCampos());
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
        colId.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getCoId()));
        colNombre.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getCoNombre()));
        colSiglo.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getCoSiglo()));
        colDescripcion.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getCoDescripcion()));

        colSala.setCellValueFactory(cellData -> {
            Integer idSala = cellData.getValue().getCoSaid();
            String nombreSala = mapaSalas.getOrDefault(idSala, "Sin sala");
            return new ReadOnlyStringWrapper(nombreSala);
        });
    }

    private void cargarColecciones() {
        ObservableList<MusColeccion> colecciones = coleccionManager.getAllColecciones();
        tableColecciones.setItems(colecciones);
    }

    private void llenarFormulario(MusColeccion col) {
        txtNombre.setText(col.getCoNombre());
        txtSiglo.setText(col.getCoSiglo());
        txtDescripcion.setText(col.getCoDescripcion());

        for (MusSala sala : cbSala.getItems()) {
            if (sala.getSaId().equals(col.getCoSaid())) {
                cbSala.setValue(sala);
                break;
            }
        }
    }

    private void agregarColeccion() {
        MusColeccion col = new MusColeccion();
        col.setCoNombre(txtNombre.getText());
        col.setCoSiglo(txtSiglo.getText());
        col.setCoDescripcion(txtDescripcion.getText());
        col.setCoSaid(cbSala.getValue().getSaId());
        coleccionManager.addColeccion(col);
        cargarColecciones();
        limpiarCampos();
    }

    private void modificarColeccion() {
        if (coleccionSeleccionada != null) {
            coleccionSeleccionada.setCoNombre(txtNombre.getText());
            coleccionSeleccionada.setCoSiglo(txtSiglo.getText());
            coleccionSeleccionada.setCoDescripcion(txtDescripcion.getText());
            coleccionSeleccionada.setCoSaid(cbSala.getValue().getSaId());
            coleccionManager.updateColeccion(coleccionSeleccionada);
            cargarColecciones();
            limpiarCampos();
        }
    }

    private void eliminarColeccion() {
        if (coleccionSeleccionada != null) {
            coleccionManager.deleteColeccion(coleccionSeleccionada.getCoId());
            cargarColecciones();
            limpiarCampos();
        }
    }

    private void limpiarCampos() {
        txtNombre.clear();
        txtSiglo.clear();
        txtDescripcion.clear();
        cbSala.setValue(null);
        tableColecciones.getSelectionModel().clearSelection();
        coleccionSeleccionada = null;
    }
}
