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
import Database.MusMuseo;
import Managers.MusMuseoManager;
import javafx.collections.ObservableList;
import java.time.ZoneId;
import java.util.Date;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Kenneth
 */
public class MuseoController implements Initializable {

    @FXML
    private TextField txtNombre;
    @FXML
    private ComboBox<String> cbTipo;
    @FXML
    private TextField txtUbicacion;
    @FXML
    private DatePicker dpFundacion;
    @FXML
    private TextField txtDirector;
    @FXML 
    private TextField txtWeb;
    @FXML 
    private Button btnAgregar;
    @FXML 
    private Button btnModificar;
    @FXML 
    private Button btnEliminar;
    @FXML 
    private Button btnLimpiar;
    @FXML 
    private TableView<MusMuseo> tableMuseos;
    @FXML 
    private TableColumn<MusMuseo, Integer> colId;
    @FXML 
    private TableColumn<MusMuseo, String> colNombre;
    @FXML 
    private TableColumn<MusMuseo, String> colTipo;
    @FXML 
    private TableColumn<MusMuseo, String> colUbicacion;
    @FXML 
    private TableColumn<MusMuseo, Date> colFundacion;
    @FXML 
    private TableColumn<MusMuseo, String> colDirector;
    @FXML 
    private TableColumn<MusMuseo, String> colWeb;

    private final MusMuseoManager museoManager = new MusMuseoManager();
    private MusMuseo museoSeleccionado = null;
    @FXML
    private Button btnGuardar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbTipo.getItems().addAll("AR", "HI", "MU", "MI");

        colId.setCellValueFactory(new PropertyValueFactory<>("muId"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("muNombre"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("muTipo"));
        colUbicacion.setCellValueFactory(new PropertyValueFactory<>("muUbicacion"));
        colFundacion.setCellValueFactory(new PropertyValueFactory<>("muFechaFun"));
        colDirector.setCellValueFactory(new PropertyValueFactory<>("muDirector"));
        colWeb.setCellValueFactory(new PropertyValueFactory<>("muSitioWep"));

        cargarMuseos();

        btnAgregar.setOnAction(e -> agregarMuseo());
        btnModificar.setOnAction(e -> modificarMuseo());
        btnEliminar.setOnAction(e -> eliminarMuseo());
        btnLimpiar.setOnAction(e -> limpiarCampos());
        btnGuardar.setOnAction(e -> guardarCambios());
    }

    private void cargarMuseos() {
        ObservableList<MusMuseo> museos = museoManager.getAllMuseos();
        tableMuseos.setItems(museos);
    }

    private void agregarMuseo() {
        MusMuseo m = new MusMuseo();
        m.setMuNombre(txtNombre.getText());
        m.setMuTipo(cbTipo.getValue());
        m.setMuUbicacion(txtUbicacion.getText());
        m.setMuFechaFun(dpFundacion.getValue() != null ? Date.from(dpFundacion.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()) : null);
        m.setMuDirector(txtDirector.getText());
        m.setMuSitioWep(txtWeb.getText());

        museoManager.addMuseo(m);
        cargarMuseos();
        limpiarCampos();
    }
    
    private void llenarFormulario(MusMuseo m) {
        txtNombre.setText(m.getMuNombre());
        cbTipo.setValue(m.getMuTipo());
        txtUbicacion.setText(m.getMuUbicacion());
        dpFundacion.setValue(m.getMuFechaFun() != null 
        ? ((java.sql.Date) m.getMuFechaFun()).toLocalDate() 
        : null);
        txtDirector.setText(m.getMuDirector());
        txtWeb.setText(m.getMuSitioWep());
    }

    private void modificarMuseo() {
        MusMuseo seleccionado = tableMuseos.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            museoSeleccionado = seleccionado;
            llenarFormulario(seleccionado);
        } else {
            mostrarAlerta("Debe seleccionar un museo para editar.");
        }
    }


    private void eliminarMuseo() {
        if (museoSeleccionado != null) {
            museoManager.deleteMuseo(museoSeleccionado.getMuId());
            cargarMuseos();
            limpiarCampos();
        }
    }

    private void limpiarCampos() {
        txtNombre.clear();
        cbTipo.setValue(null);
        txtUbicacion.clear();
        dpFundacion.setValue(null);
        txtDirector.clear();
        txtWeb.clear();
        museoSeleccionado = null;
        tableMuseos.getSelectionModel().clearSelection();
    }
    
    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Advertencia");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    private void guardarCambios() {
        if (museoSeleccionado == null) {
            mostrarAlerta("Debe seleccionar un museo para guardar los cambios.");
            return;
        }

        museoSeleccionado.setMuNombre(txtNombre.getText());
        museoSeleccionado.setMuTipo(cbTipo.getValue());
        museoSeleccionado.setMuUbicacion(txtUbicacion.getText());
        museoSeleccionado.setMuFechaFun(dpFundacion.getValue() != null
            ? Date.from(dpFundacion.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant())
            : null);
        museoSeleccionado.setMuDirector(txtDirector.getText());
        museoSeleccionado.setMuSitioWep(txtWeb.getText());

        museoManager.updateMuseo(museoSeleccionado);
        cargarMuseos();
        tableMuseos.refresh();
        limpiarCampos();
    }
}
