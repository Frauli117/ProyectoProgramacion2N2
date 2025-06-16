/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Kenneth
 */
public class MainController {

    @FXML
    private AnchorPane contenidoCentral;

    /**
     * Initializes the controller class.
     */

    private void cargarVista(String rutaFXML) {
        try {
            Node vista = FXMLLoader.load(getClass().getResource(rutaFXML));
            contenidoCentral.getChildren().setAll(vista);
            AnchorPane.setTopAnchor(vista, 0.0);
            AnchorPane.setBottomAnchor(vista, 0.0);
            AnchorPane.setLeftAnchor(vista, 0.0);
            AnchorPane.setRightAnchor(vista, 0.0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML private void mostrarMuseos() {
        cargarVista("/fxml/Museo.fxml");
    }

    @FXML private void mostrarSalas() {
        cargarVista("/fxml/Sala.fxml");
    }

    @FXML private void mostrarColecciones() {
        cargarVista("/fxml/Coleccion.fxml");
    }

    @FXML private void mostrarEspecies() {
        cargarVista("/fxml/Especie.fxml");
    }

    @FXML private void mostrarTematicas() {
        cargarVista("/fxml/Tematica.fxml");
    }

    @FXML private void mostrarPrecios() {
        cargarVista("/fxml/Precio.fxml");
    }

    @FXML private void mostrarComisiones() {
        cargarVista("/fxml/Comision.fxml");
    }

    @FXML private void mostrarVenta() {
        cargarVista("/fxml/VentaEntrada.fxml");
    }

    @FXML private void mostrarValidacion() {
        cargarVista("/fxml/ValidarEntrada.fxml");
    }

    @FXML private void mostrarValoracion() {
        cargarVista("/fxml/Valoraciones.fxml");
    }

    @FXML private void mostrarReportes() {
        cargarVista("/fxml/Reportes.fxml");
    }
    
}
