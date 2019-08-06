/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import agentes.AgenteCapturador;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author frank
 */
public class RegistroFXMLController implements Initializable{
    
    @FXML
    private ImageView imgCliente;
    
    @FXML
    private TextField documentoCliente;

    @FXML
    private TextField nombresCliente;

    @FXML
    private TextField apellidosCliente;

    @FXML
    private ChoiceBox<String> generoCliente;

    @FXML
    private DatePicker nacimientoCliente;

    @FXML
    private ListView<String> telefonosView;

    @FXML
    private ListView<String> correosView;

    @FXML
    private ListView<String> direccionesView;

    private static boolean trigger = false;
    
    private File imgClientBackUp;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        AgenteCapturador.link(this);
             
        try {
            URI toURI = getClass().getResource("/vista/icons/iconfinder_user_1608727.png").toURI();
            imgClientBackUp = new File(toURI);
        } catch (URISyntaxException ex) {
            System.out.println("El Shumagorat");
            Logger.getLogger(RegistroFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        documentoCliente.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            try {
                if (!newValue.matches("\\d*")) {
                    documentoCliente.setText(newValue.replaceAll("[^\\d]", ""));
                 }
            } catch (NullPointerException npe) {
                System.out.println("cleaned");
            }
        });
        
        documentoCliente.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                if (documentoCliente.getText().length() > 20) {
                    String s = documentoCliente.getText().substring(0, 20);
                    documentoCliente.setText(s);
                }
            }
        });
        
        nombresCliente.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\sa-zA-Z*")) {
                nombresCliente.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
            }
        });
        
        apellidosCliente.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\sa-zA-Z*")) {
                apellidosCliente.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
            }
        });
        
        generoCliente.setItems(FXCollections.observableArrayList("Femenino", "Masculino"));
        
        nacimientoCliente.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.compareTo(today) > 0 );
            }
        });
    }
    
    public static boolean isTrigger() {
        return trigger;
    }

    public void setTrigger(boolean trigger) {
        RegistroFXMLController.trigger = trigger;
    }
    
    public Long getIdentificador() {
        Long id;
        if (!documentoCliente.getText().trim().isEmpty()) {
            try {
                id =  Long.parseLong(documentoCliente.getText());
            } catch (NumberFormatException e) {
                id = null;
            }
            return id;
        }
        return null;
    }
    
    public String getNombres() {
        if (!nombresCliente.getText().trim().isEmpty()) {
            return nombresCliente.getText();
        }
        return null;
    }
    
    public String getApellidos() {
        if (!apellidosCliente.getText().trim().isEmpty()) {
            return apellidosCliente.getText();
        }
        return null;
    }
    
    public String getnacimientoCliente() {
        //nacimientoCliente
        LocalDate nacimiento = nacimientoCliente.getValue();
        if (nacimiento != null) {
            return nacimiento.toString();
        }
        return null; 
    }
    
    public String getGenero() {
        return generoCliente.getSelectionModel().getSelectedItem();
    }
    
    public ObservableList<String> getTelefonos() {
        return telefonosView.getItems();
    }
    
    public ObservableList<String> getCorreos() {
        return correosView.getItems();
    }
    
    public ObservableList<String> getDirecciones() {
        return direccionesView.getItems();
    }
    
    public Image getImagen() {
        return imgCliente.getImage();
    }

    public File getImgClientBackUp() {
        return imgClientBackUp;
    }

    public void setImgClientBackUp(File imgClientBackUp) {
        this.imgClientBackUp = imgClientBackUp;
    }
    
    public void clearFields() {
        documentoCliente.clear();
        nombresCliente.clear();
        apellidosCliente.clear();
        generoCliente.getSelectionModel().clearSelection();
        nacimientoCliente.getEditor().clear();
        telefonosView.getItems().clear();
        correosView.getItems().clear();
        direccionesView.getItems().clear();
        URI toURI;
        try {
            toURI = getClass().getResource("/vista/icons/iconfinder_user_1608727.png").toURI();
            imgClientBackUp = new File(toURI);
            Image image = new Image("/vista/icons/iconfinder_user_1608727.png");
            imgCliente.setImage(image);
        } catch (URISyntaxException ex) {
            Logger.getLogger(RegistroFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    
    private ObservableList<String> cloneObservableList(ObservableList<String> toClone) {
        ObservableList<String> values = FXCollections.observableArrayList();
        toClone.forEach((item) -> {
            values.add(item);
        });
        return values;
    }
    
    @FXML
    void editarCorreos(ActionEvent event) throws IOException {
        ObservableList<String> values = cloneObservableList(correosView.getItems());
        loadScenario("Correos electronicos", values, correosView, false);
    }
    
    @FXML
    void editarDirecciones(ActionEvent event) throws IOException {
        ObservableList<String> values = cloneObservableList(direccionesView.getItems());
        loadScenario("Direcciones", values, direccionesView, false);
    }
    
    @FXML
    void editarTelefonos(ActionEvent event) throws IOException {
        ObservableList<String> values = cloneObservableList(telefonosView.getItems());
        loadScenario("Numeros de telefono", values, telefonosView, true);
    }
    
    private void loadScenario (String dato, ObservableList<String> values, 
            ListView<String>  copy, boolean isNumeric) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/vista/PlanillaInfoFXML.fxml"));
        Parent root = loader.load();
        System.out.println("LOADING " + getClass().getResource("/vista/PlanillaInfoFXML.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle(dato);
        PlanillaInfoFXMLController controller = loader.getController();
        controller.load(dato, values, copy, isNumeric);
        stage.getIcons().add(new Image("/vista/icons/windowIcon.png"));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.showAndWait();
    }
    
    @FXML
    void uploadImg(ActionEvent event) {
        Stage stage = (Stage) imgCliente.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");
        fileChooser.getExtensionFilters().add(imageFilter);
        imgClientBackUp = fileChooser.showOpenDialog(stage);
        try {
            String imgPath = imgClientBackUp.toURI().toString();
            Image userIMG = new Image(imgPath);
            imgCliente.setImage(userIMG);
        } catch(NullPointerException npe) {
            Logger.getLogger(RegistroFXMLController.class.getName()).log(Level.SEVERE, null, npe);
        }
    }
    
    @FXML
    void registarCliente(ActionEvent event) throws IOException {
        RegistroFXMLController.trigger = true;
        System.out.println("que mas quieres de mi?");
    }
    
}
