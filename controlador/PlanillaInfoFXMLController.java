/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author frank
 */
public class PlanillaInfoFXMLController implements Initializable{
    
    @FXML
    private Label infoToAdd;
    
    @FXML
    private ListView<String> infoListView;
    
    @FXML
    private TextArea infoProvider;
    
    private ListView<String> originalCopy;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        infoListView.setEditable(true);
        infoListView.setCellFactory(TextFieldListCell.forListView());
        infoProvider.textProperty().addListener((final ObservableValue<? extends String> ov, final String oldValue, final String newValue) -> {
            if (infoProvider.getText().length() > 50) {
                String s = infoProvider.getText().substring(0, 50);
                infoProvider.setText(s);
            }
        });
    }
    
    ChangeListener<String> infoProvidernumerticFormat = new ChangeListener<String>()
    {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            try {
                if (!newValue.matches("\\d*")) {
                    infoProvider.setText(newValue.replaceAll("[^\\d]", ""));
                 }
            } catch (NullPointerException npe) {
                System.out.println("cleaned");
            }
        }
        
    };
      
    public void load(String dato, ObservableList<String> values, ListView<String> copy, boolean isNumeric) {
        infoListView.getItems().clear();
        infoToAdd.setText(dato);
        infoListView.setItems(values);
        originalCopy = copy;
        if(isNumeric) {
            infoProvider.textProperty().addListener(infoProvidernumerticFormat);
        } else {
            infoProvider.textProperty().removeListener(infoProvidernumerticFormat);
        }
    }
    
    @FXML
    void addElement(ActionEvent event) {
        if (!infoProvider.getText().trim().isEmpty()) {
            String toAdd = infoProvider.getText();
            infoListView.getItems().add(toAdd);
        }
        infoProvider.setText("");
    }

    @FXML
    void removeElement(ActionEvent event) {
        String toRemove = infoListView.getSelectionModel().getSelectedItem();
        infoListView.getItems().remove(toRemove);
    }
  
    @FXML
    void applyChanges(MouseEvent event) {
        Alert closeConfirmation = new Alert(
                Alert.AlertType.CONFIRMATION
                
        );
        Button exitButton = (Button) closeConfirmation.getDialogPane().lookupButton(
                ButtonType.OK
        );
        exitButton.setText("Aplicar");
        closeConfirmation.setHeaderText("Desea aplicar los cambios?");
        closeConfirmation.initModality(Modality.APPLICATION_MODAL);
        DialogPane dialogPane = closeConfirmation.getDialogPane();
        Stage dialog = (Stage) dialogPane.getScene().getWindow();
        Optional<ButtonType> closeResponse = closeConfirmation.showAndWait();
        if (ButtonType.OK.equals(closeResponse.get())) {
            ObservableList newValues = infoListView.getItems();
            originalCopy.setItems(newValues);
            Stage stage  = (Stage) infoToAdd.getScene().getWindow();
            stage.close();
        } else {
            event.consume();
        }
    }
    
    @FXML
    void cancel(MouseEvent event) {
        Alert closeConfirmation = new Alert(
                Alert.AlertType.CONFIRMATION
                
        );
        Button exitButton = (Button) closeConfirmation.getDialogPane().lookupButton(
                ButtonType.OK
        );
        exitButton.setText("Salir");
        closeConfirmation.setHeaderText("Desea cancelar los cambios?");
        closeConfirmation.initModality(Modality.APPLICATION_MODAL);
        DialogPane dialogPane = closeConfirmation.getDialogPane();
        Stage dialog = (Stage) dialogPane.getScene().getWindow();
        Optional<ButtonType> closeResponse = closeConfirmation.showAndWait();
        if (ButtonType.OK.equals(closeResponse.get())) {
            Stage stage  = (Stage) infoToAdd.getScene().getWindow();
            stage.close();
        } else {
            event.consume();
        }
    }
    
}
