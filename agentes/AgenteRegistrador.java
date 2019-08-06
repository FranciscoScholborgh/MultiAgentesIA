/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agentes;

import dataFormat.Cliente;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import persistencia.IsaDB;
import utilities.Notificator;

/**
 *
 * @author frank
 */
public class AgenteRegistrador extends Agent{
    
    private static boolean activo = true;
    
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
        addBehaviour(new Registrador());
  
    }
    
    private class Registrador extends Behaviour {
        
        @Override
        public void action() {
            ACLMessage mensaje = receive();    
            
            if (mensaje!= null) { 
                try {
                    Cliente cliente = (Cliente) mensaje.getContentObject();
                    IsaDB isaDB = IsaDB.getInstance();
                    boolean registrado = isaDB.registrarCliente(cliente);
                    System.out.println("Do i get here? registro?: " + registrado);
                    if (registrado) {
                        Platform.runLater( () -> {
                            Notificator notificator = Notificator.getInstance();
                            notificator.notifyInformation("Registro de cliente", "El cliente "
                                + cliente.getNombres() + " " + cliente.getApellidos() 
                                + " ha sido registrado con exito en la base de datos", 5);
                        });
                    } else {
                        Platform.runLater( () -> {
                            Notificator notificator = Notificator.getInstance();
                            notificator.notifyInformation("Registro de cliente", "El cliente "
                                + cliente.getNombres() + " " + cliente.getApellidos() 
                                + " no se registro al cliente, revisar la conexion con la base de datos", 5);
                        });  
                    }
                    //guarda la info
                } catch (UnreadableException ex) {
                    Logger.getLogger(AgenteRegistrador.class.getName()).log(Level.SEVERE, null, ex);
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
            System.out.println("Agent verificador teorical killed");
            myAgent.doDelete();
            return super.onEnd();
        } 
        
    }
}
