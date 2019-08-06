/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agentes;

import controlador.RegistroFXMLController;
import dataFormat.Cliente;
import dataFormat.converter.ByteConverter;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import utilities.Notificator;

/**
 *
 * @author frank
 */
public class AgenteCapturador extends Agent{
    
    private static RegistroFXMLController subject;
    private static boolean activo = true;
    
    public static void link(RegistroFXMLController register) {
        System.out.println("linked?");
        subject = register;
    }

    public static void killAgent() {
        activo = false;
    }
    
    @Override
    public void setup(){

        System.out.println("Iniciando "+getAID().getName()+" : "+getAID());
         
        
        addBehaviour(new CyclicBehaviour(this) {
          @Override
          public void action() {
              
          }           
        });
        addBehaviour(new UIObserver());
        addBehaviour(new ClearUI());
  
    }
    
    private class ClearUI extends Behaviour {
        @Override
        public void action() {
            ACLMessage mensaje = receive();    
            
            if (mensaje!= null) {
                Platform.runLater(() -> {
                    subject.clearFields();
                });
            }
        }

        public void setEstado(boolean estado) {
            activo = estado;
        }
        
        @Override
        public boolean done() {
            return !activo;
        }
        
        @Override
        public int onEnd() {
            System.out.println("Agent capturador teorical killed");
            myAgent.doDelete();
            return super.onEnd();
        } 
    }
    
    private class UIObserver extends Behaviour {
        
        @Override
        public void action() {
            //System.out.println("iteracion?");
            if (RegistroFXMLController.isTrigger()) {
                System.out.println("hacking in");
                subject.setTrigger(false);
                Long id = subject.getIdentificador();
                String nombres = subject.getNombres();
                String apellidos = subject.getApellidos();
                String genero = subject.getGenero();
                String nacimiento = subject.getnacimientoCliente();
                ObservableList<String> telefonos = subject.getTelefonos();
                ObservableList<String> correos = subject.getCorreos();
                ObservableList<String> direcciones = subject.getDirecciones();
                File file = subject.getImgClientBackUp();
                byte[] imgAsBytes = ByteConverter.convertImageFileToByte(file);
                if(id != null && nombres != null && apellidos != null && genero != null && 
                        nacimiento != null && telefonos.size() > 0 && correos.size() > 0 && direcciones.size() > 0) {
                    Cliente cliente = new Cliente(id, nombres, apellidos, genero, nacimiento, 
                            telefonos, correos, direcciones, imgAsBytes);
                    try {
                        AID aid = new AID();
                        aid.setLocalName("verificador");
                    
                        ACLMessage mensaje = new ACLMessage(ACLMessage.REQUEST);
                    
                        mensaje.setSender(getAID());
                        mensaje.setLanguage("Español");
                        mensaje.addReceiver(aid);
                        mensaje.setContentObject(cliente);
                        
                        send(mensaje);
                        
                    } catch (IOException ex) {
                        Logger.getLogger(AgenteCapturador.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                } else {
                    Platform.runLater(() -> {
                        Notificator notificator = Notificator.getInstance();
                        notificator.notifyInformation("Registro cliente", "Faltan campos por llenar", 5);
                    });
                }
            }          
        }

        public void setEstado(boolean estado) {
            activo = estado;
        }
        
        @Override
        public boolean done() {
            return !activo;
        }
        
        @Override
        public int onEnd() {
            System.out.println("Agent capturador teorical killed");
            myAgent.doDelete();
            return super.onEnd();
        }   
    }
    
}
