/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agentes;

import dataFormat.Cliente;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import persistencia.IsaDB;
import utilities.Notificator;

/**
 *
 * @author frank
 */
public class AgenteVerificador extends Agent{
    
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
        addBehaviour(new Verificador());
  
    }
    
    private class Verificador extends Behaviour {
        
        @Override
        public void action() {
            ACLMessage mensaje = receive();    
            
            if (mensaje!= null) {
                try {
                    //System.out.println("pero men si tu mensaje");
                    Cliente cliente = (Cliente) mensaje.getContentObject();
                    IsaDB isaDB = IsaDB.getInstance();
                    Long documento = cliente.getDocumento();
                    boolean onDataBase =isaDB.consultarExistenciaCliente(documento);
                    System.out.println("ON DATABASE: " + onDataBase);
                    if(!onDataBase) {
                        AID aid = new AID();
                        aid.setLocalName("registrador");
                    
                        ACLMessage mensajeR = new ACLMessage(ACLMessage.REQUEST);
                    
                        mensajeR.setSender(getAID());
                        mensajeR.setLanguage("Español");
                        mensajeR.addReceiver(aid);

                        try {
                            mensajeR.setContentObject(cliente);
                            send(mensajeR);
                        } catch (IOException ex) {
                            Logger.getLogger(AgenteVerificador.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        AID aidC = new AID();
                        aidC.setLocalName("capturador");
                    
                        ACLMessage mensajeC = new ACLMessage(ACLMessage.REQUEST);
                    
                        mensajeC.setSender(getAID());
                        mensajeC.setLanguage("Español");
                        mensajeC.addReceiver(aidC);
                        mensaje.setContent("Registrado");
                        send(mensajeC);  
                        
                    } else {
                        Platform.runLater( () -> {
                            Notificator notificator = Notificator.getInstance();
                            notificator.notifyInformation("Registro de cliente", "El documento "
                                + cliente.getDocumento() + " ya ha sido registrado en la base de datos", 5);
                        });
                    }
                    //System.out.println("no tiene informacion que verificar xD");
                } catch (UnreadableException ex) {
                    Logger.getLogger(AgenteVerificador.class.getName()).log(Level.SEVERE, null, ex);
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
