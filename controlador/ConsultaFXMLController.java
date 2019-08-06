/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dataFormat.Cliente;
import dataFormat.converter.ImageConverter;
import java.net.URI;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import persistencia.IsaDB;
import utilities.Notificator;

/**
 *
 * @author frank
 */
public class ConsultaFXMLController implements Initializable{
    
    @FXML
    private ImageView imgCliente;

    @FXML
    private TextField documentoCliente;

    @FXML
    private TextField nombresCliente;

    @FXML
    private TextField apellidosCliente;

    @FXML
    private TextField generoCliente;

    @FXML
    private DatePicker nacimientoCliente;

    @FXML
    private ListView<String> telefonosView;

    @FXML
    private ListView<String> correosView;

    @FXML
    private ListView<String> direccionesView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
    }
    
    private void clearFields() {
        documentoCliente.clear();
        nombresCliente.clear();
        apellidosCliente.clear();
        generoCliente.clear();
        nacimientoCliente.getEditor().clear();
        telefonosView.getItems().clear();
        correosView.getItems().clear();
        direccionesView.getItems().clear();
        URI toURI;
        Image image = new Image("/vista/icons/iconfinder_user_1608727.png");
        imgCliente.setImage(image); 
    }

    @FXML
    void consultarCliente(ActionEvent event) {
        
        if(!documentoCliente.getText().trim().isEmpty()) {
            String doc = documentoCliente.getText();
            Long documento = Long.parseLong(doc);
            IsaDB isaDB = IsaDB.getInstance();
            Cliente cliente = isaDB.informacionCliente(documento);
            if (cliente != null) {
                clearFields();
                documentoCliente.setText(doc);
                byte[] imgAsBytes = cliente.getImgAsBytes();
                Image img = ImageConverter.convertByteArrayToImage(imgAsBytes);
                imgCliente.setImage(img);
                String nombres = cliente.getNombres();
                nombresCliente.setText(nombres);
                String apellidos = cliente.getApellidos();
                apellidosCliente.setText(apellidos);
                String genero = cliente.getGenero();
                generoCliente.setText(genero);
                String nacimiento = cliente.getNacimiento();
                String[] arrOfStr = nacimiento.split("-");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
                String fecha = arrOfStr[2] + "/" + arrOfStr[1] + "/" + arrOfStr[0];
                LocalDate date = LocalDate.parse(fecha, formatter);
                nacimientoCliente.setValue(date);
                ArrayList<String> telefonos = cliente.getTelefonos();
                telefonos.forEach((telefono) -> {
                    telefonosView.getItems().add(telefono);
                });
                ArrayList<String> correos = cliente.getCorreos();
                correos.forEach((correo) -> {
                    correosView.getItems().add(correo);
                });
                ArrayList<String> direcciones = cliente.getDirecciones();
                direcciones.forEach((direccion) -> {
                    direccionesView.getItems().add(direccion);
                });
            } else {
                Notificator notificator = Notificator.getInstance();
                notificator.notifyInformation("Busqueda de usuario", "No se ha encontrado un cliente"
                        + " registrado con el numero de documento " + documento, 5);
            }
                    
            
        } else {
            Notificator notificator = Notificator.getInstance();
            notificator.notifyInformation("Busqueda de usuario", "No ha ingresado ningun dato", 5);
        }
    }
    
}
