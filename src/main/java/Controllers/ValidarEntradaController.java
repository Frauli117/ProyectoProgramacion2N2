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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import Database.MusEntrada;
import Database.MusSala;
import Managers.MusEntradaManager;
import java.time.LocalDate;
import java.util.List;
import javafx.scene.control.*;

/**
 * FXML Controller class
 *
 * @author Kenneth
 */
public class ValidarEntradaController {

    @FXML
    private TextField txtCodigo;
    @FXML
    private Label lblResultado;
    @FXML
    private VBox contenedorInfo;
    @FXML
    private Label lblNombre;
    @FXML
    private Label lblFecha;
    @FXML
    private Label lblTotal;
    
    private final MusEntradaManager entradaManager = new MusEntradaManager();
    @FXML
    private ListView<String> listSalas;

    /**
     * Initializes the controller class.
     */

    @FXML
    private void validarEntrada() {
        String codigo = txtCodigo.getText().trim();
        contenedorInfo.setVisible(false);
        lblResultado.setStyle("-fx-text-fill: red;");

        if (codigo.isEmpty()) {
            lblResultado.setText("Ingrese un codigo para validar.");
            return;
        }

        MusEntrada entrada = entradaManager.buscarPorCodigo(codigo);
        if (entrada == null) {
            lblResultado.setText("Codigo invalido. Entrada no encontrada.");
            return;
        }

        LocalDate fechaEntrada = entrada.getEnFecha().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        LocalDate hoy = LocalDate.now();

        if (!fechaEntrada.isEqual(hoy)) {
            lblResultado.setText("Entrada fuera de fecha. Solo es valida para hoy.");
            return;
        }

        lblResultado.setStyle("-fx-text-fill: green;");
        lblResultado.setText("Entrada valida para hoy");

        lblNombre.setText("Nombre del visitante: " + entrada.getEnNombre());
        lblFecha.setText("Fecha de compra: " + fechaEntrada);
        lblTotal.setText("Total pagado: ₡" + String.format("%.2f", entrada.getEnTotal()));

        List<MusSala> salas = entradaManager.getSalasPorEntrada(entrada.getEnId());
        listSalas.getItems().clear();
        for (MusSala sala : salas) {
            listSalas.getItems().add(sala.getSaNombre());
        }

        contenedorInfo.setVisible(true);
    }
}
