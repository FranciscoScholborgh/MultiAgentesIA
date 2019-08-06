/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import jade.Boot;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author frank
 */
public class MainFXMLController implements Initializable{
    
    @FXML
    private Label label;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String[] bootArgs = new String[4];
        bootArgs[0] = "-gui";
        bootArgs[1] = "-services";
        bootArgs[2] = " ";
        bootArgs[3] = "capturador:agentes.AgenteCapturador;"
                + "verificador:agentes.AgenteVerificador;"
                + "registrador:agentes.AgenteRegistrador;";
        Boot.main(bootArgs);
    }
    
    @FXML
    private void registrarCliente(ActionEvent event) {
        try {
            Stage stage = (Stage) this.label.getScene().getWindow();
            stage.hide();
            FXMLLoader loader = new FXMLLoader();
            //loader.setLocation(getClass().getResource("/vista/RegistroFXML.fxml"));
            loader.setLocation(getClass().getResource("/vista/RegistroFXML.fxml"));
            Parent root = loader.load();
            System.out.println("LOADING " + getClass().getResource("/vista/RegistroFXML.fxml"));
            Scene scene = new Scene(root);
            Stage registerStage = new Stage();
            registerStage.setScene(scene);
            registerStage.setTitle("Registro de clientes");
            registerStage.getIcons().add(new Image("/vista/icons/windowIcon.png"));
            registerStage.setResizable(false);
            registerStage.showAndWait();
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void consultarCliente(ActionEvent event) {
        try {
            Stage stage = (Stage) this.label.getScene().getWindow();
            stage.hide();
            FXMLLoader loader = new FXMLLoader();
            //loader.setLocation(getClass().getResource("/vista/RegistroFXML.fxml"));
            loader.setLocation(getClass().getResource("/vista/ConsultaFXML.fxml"));
            Parent root = loader.load();
            System.out.println("LOADING " + getClass().getResource("/vista/ConsultaFXML.fxml"));
            Scene scene = new Scene(root);
            Stage registerStage = new Stage();
            registerStage.setScene(scene);
            registerStage.setTitle("Consulta de clientes");
            registerStage.getIcons().add(new Image("/vista/icons/windowIcon.png"));
            registerStage.setResizable(false);
            registerStage.showAndWait();
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
