/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tallermultiagentesia;

import agentes.AgenteCapturador;
import agentes.AgenteRegistrador;
import agentes.AgenteVerificador;
import java.util.Optional;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author frank
 */
public class TallerMultiAgentesIA extends Application{
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        //loader.setLocation(getClass().getResource("/vista/RegistroFXML.fxml"));
        loader.setLocation(getClass().getResource("/vista/MainFXML.fxml"));
        Parent root = loader.load();
        System.out.println("LOADING " + getClass().getResource("/vista/MainFXML.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Registro de clientes");
        primaryStage.getIcons().add(new Image("/vista/icons/windowIcon.png"));
        primaryStage.setOnCloseRequest(confirmCloseEventHandler);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    
    private EventHandler<WindowEvent> confirmCloseEventHandler = event -> {
        Alert closeConfirmation = new Alert(
                Alert.AlertType.CONFIRMATION
                
        );
        Button exitButton = (Button) closeConfirmation.getDialogPane().lookupButton(
                ButtonType.OK
        );
        exitButton.setText("Salir");
        closeConfirmation.setHeaderText("Desea salir?");
        closeConfirmation.initModality(Modality.APPLICATION_MODAL);
        DialogPane dialogPane = closeConfirmation.getDialogPane();
        Stage dialog = (Stage) dialogPane.getScene().getWindow();
        Optional<ButtonType> closeResponse = closeConfirmation.showAndWait();
        if (!ButtonType.OK.equals(closeResponse.get())) {
            event.consume();
        } else {
            AgenteCapturador.killAgent();
            AgenteVerificador.killAgent();
            AgenteRegistrador.killAgent();
            Runtime.getRuntime().exit(0);
        }
    };
    
}
