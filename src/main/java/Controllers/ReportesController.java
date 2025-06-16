/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import Managers.MusComisionManager;
import Managers.MusValoracionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.LocalDate;
import java.util.Map;
import javafx.scene.control.Alert;
import java.util.List;



/**
 * FXML Controller class
 *
 * @author Kenneth
 */
public class ReportesController {

    @FXML
    private DatePicker dpInicio;
    @FXML
    private DatePicker dpFin;
    @FXML 
    private TableView<ComisionReporte> tablaComisiones;
    @FXML 
    private TableColumn<ComisionReporte, String> colTarjeta;
    @FXML 
    private TableColumn<ComisionReporte, Double> colMonto;
    @FXML
    private ListView<String> listaValoraciones;

    private final MusComisionManager comisionManager = new MusComisionManager();
    private final MusValoracionManager valoracionManager = new MusValoracionManager();
    /**
     * Initializes the controller class.
     */
    @FXML
    private void initialize() {
        colTarjeta.setCellValueFactory(new PropertyValueFactory<>("tarjeta"));
        colMonto.setCellValueFactory(new PropertyValueFactory<>("monto"));
    }

    @FXML
    private void generarReporteComisiones() {
        LocalDate inicio = dpInicio.getValue();
        LocalDate fin = dpFin.getValue();

        if (inicio == null || fin == null || inicio.isAfter(fin)) {
            mostrarAlerta("Rango de fechas invalido.");
            return;
        }

        Map<String, Double> datos = comisionManager.obtenerTotalComisionesPorTarjeta(inicio, fin);
        ObservableList<ComisionReporte> lista = FXCollections.observableArrayList();

        for (Map.Entry<String, Double> entry : datos.entrySet()) {
            lista.add(new ComisionReporte(entry.getKey(), entry.getValue()));
        }

        tablaComisiones.setItems(lista);
    }

    @FXML
    private void mostrarTopValoraciones() {
        List<String> lista = valoracionManager.getPromedioValoracionPorSala(false);
        int limite = Math.min(lista.size(), 10);
        listaValoraciones.getItems().setAll(lista.subList(0, limite));
    }

    @FXML
    private void mostrarBottomValoraciones() {
        List<String> lista = valoracionManager.getPromedioValoracionPorSala(true);
        int limite = Math.min(lista.size(), 10);
        listaValoraciones.getItems().setAll(lista.subList(0, limite));
    }

    private void mostrarAlerta(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Aviso");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    public static class ComisionReporte {
        private final String tarjeta;
        private final Double monto;

        public ComisionReporte(String tarjeta, Double monto) {
            this.tarjeta = tarjeta;
            this.monto = monto;
        }

        public String getTarjeta() { return tarjeta; }
        public Double getMonto() { return monto; }
    }
    
}
