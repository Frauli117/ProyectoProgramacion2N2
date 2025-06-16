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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import Database.MusEntrada;
import Database.MusSala;
import Managers.MusEntradaManager;
import Managers.MusValoracionManager;
import java.util.List;
import java.time.LocalDate;

/**
 * FXML Controller class
 *
 * @author Kenneth
 */
public class ValoracionesController {

    @FXML
    private TextField txtCodigo;
    @FXML
    private Label lblResultado;
    @FXML
    private VBox contenedorValoracion;
    @FXML
    private Label lblSalaNombre;
    @FXML
    private Label lblSalaDescripcion;
    @FXML
    private ChoiceBox<Integer> cbEstrellas;
    @FXML
    private TextArea txtObservacion;
    @FXML
    private Label lblConfirmacion;
    
    private final MusEntradaManager entradaManager = new MusEntradaManager();
    private final MusValoracionManager valoracionManager = new MusValoracionManager();

    private MusEntrada entradaActual;
    private MusSala salaActual;

    /**
     * Initializes the controller class.
     */
    @FXML
    private void buscarEntrada() {
        lblResultado.setText("");
        contenedorValoracion.setVisible(false);
        lblConfirmacion.setText("");

        String codigo = txtCodigo.getText().trim();
        if (codigo.isEmpty()) {
            lblResultado.setText("Ingrese un codigo.");
            return;
        }

        entradaActual = entradaManager.buscarPorCodigo(codigo);
        if (entradaActual == null) {
            lblResultado.setText("Codigo no valido.");
            return;
        }

        LocalDate hoy = LocalDate.now();
        LocalDate fecha = entradaActual.getEnFecha().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        if (!fecha.equals(hoy)) {
            lblResultado.setText("Solo se permite valorar el mismo dia de la visita.");
            return;
        }

        List<MusSala> salas = entradaManager.getSalasPorEntrada(entradaActual.getEnId());
        if (salas.isEmpty()) {
            lblResultado.setText("No hay salas asociadas a esta entrada.");
            return;
        }

        salaActual = salas.get(0);
        lblSalaNombre.setText("Sala: " + salaActual.getSaNombre());
        lblSalaDescripcion.setText(salaActual.getSaDescripcion());

        cbEstrellas.setValue(null);
        txtObservacion.clear();

        contenedorValoracion.setVisible(true);
    }

    @FXML
    private void enviarValoracion() {
        lblConfirmacion.setText("");

        if (salaActual == null || cbEstrellas.getValue() == null) {
            lblConfirmacion.setText("Debe seleccionar una calificacion.");
            return;
        }

        int estrellas = cbEstrellas.getValue();
        String observacion = txtObservacion.getText().trim();

        boolean exito = valoracionManager.guardarValoracion(salaActual.getSaId(), estrellas, observacion);

        if (exito) {
            lblConfirmacion.setStyle("-fx-text-fill: green;");
            lblConfirmacion.setText("Valoracion registrada con exito.");
        } else {
            lblConfirmacion.setStyle("-fx-text-fill: red;");
            lblConfirmacion.setText("Error al guardar la valoracion.");
        }
    }
    
}
