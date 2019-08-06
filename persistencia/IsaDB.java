/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import dataFormat.Cliente;
import java.io.ByteArrayInputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author frank
 */
public class IsaDB {
    
    private static IsaDB instance;
    
    private Connection isaDBConnection;
    
    private IsaDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            isaDBConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/isadb?serverTimezone=UTC", "root", "");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(IsaDB.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public static IsaDB getInstance() {
        if(instance == null) {
            instance = new IsaDB();
        }
        return instance;    
    }
    
    public boolean consultarExistenciaCliente(Long documento) {
        boolean result = false;
        try {
            String quary = "SELECT * FROM cliente WHERE documento =" + documento;
            Statement statement = isaDBConnection.createStatement();
            ResultSet consulta = statement.executeQuery(quary);
            //System.out.println("resultados: " + consulta);
            while (consulta.next()) {
                result = true;
                System.out.println("hacking in");
                String nombres = consulta.getString("nombres");
                String apellidos = consulta.getString("apellidos");
                System.out.println(nombres + " " + apellidos);
            }
        } catch (SQLException ex) {
            System.out.println("quedate aqui y defiendelo");
            //Logger.getLogger(IsaDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    private void registrarTelefonos(Long documento, ArrayList<String> telefonos) {
        telefonos.forEach((telefono) -> {
            try {
                Long numero = Long.parseLong(telefono);
                String quary = "INSERT INTO telefono VALUES (?, ?)";
                PreparedStatement statement = isaDBConnection.prepareStatement(quary);
                statement.setLong(1, documento);
                statement.setLong(2, numero);
                statement.executeUpdate();
                statement.close();
            } catch (SQLException ex) {
                Logger.getLogger(IsaDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        });  
    }
    
    private void registrarCorreos(Long documento, ArrayList<String> correos) {
        correos.forEach((correo) -> {
            try {
                String quary = "INSERT INTO correo VALUES (?, ?)";
                PreparedStatement statement = isaDBConnection.prepareStatement(quary);
                statement.setLong(1, documento);
                statement.setString(2, correo);
                statement.executeUpdate();
                statement.close();
            } catch (SQLException ex) {
                Logger.getLogger(IsaDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        });  
    }
    
    private void registrarDirreciones(Long documento, ArrayList<String> dirreciones) {
        dirreciones.forEach((dirrecion) -> {
            try {
                String quary = "INSERT INTO direccion VALUES (?, ?)";
                PreparedStatement statement = isaDBConnection.prepareStatement(quary);
                statement.setLong(1, documento);
                statement.setString(2, dirrecion);
                statement.executeUpdate();
                statement.close();
            } catch (SQLException ex) {
                Logger.getLogger(IsaDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        });  
    }
    
    public boolean registrarCliente(Cliente cliente) {
        try {
            String quary = "INSERT INTO cliente VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = isaDBConnection.prepareStatement(quary);
            Long documento = cliente.getDocumento();
            String nombres = cliente.getNombres();
            String apellidos = cliente.getApellidos();
            String genero = cliente.getGenero();
            String nacimiento = cliente.getNacimiento();
            
            byte [] imgAsBytes = cliente.getImgAsBytes();
            ByteArrayInputStream bais = new ByteArrayInputStream(imgAsBytes);
            statement.setLong(1, documento);
            statement.setString(2, nombres);
            statement.setString(3, apellidos);
            statement.setString(4, genero);
            statement.setString(5, nacimiento);
            statement.setBinaryStream(6, bais, imgAsBytes.length);
            statement.executeUpdate();
            statement.close();
            
            ArrayList<String> telefonos = cliente.getTelefonos();
            ArrayList<String> correos = cliente.getCorreos();
            ArrayList<String> direcciones = cliente.getDirecciones();
            registrarTelefonos(documento, telefonos);
            registrarCorreos(documento, correos);
            registrarDirreciones(documento, direcciones);
            //statement.executeUpdate(quary);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(IsaDB.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    private ArrayList<String> consultarTelefonos(Long documento) {
        try {
            String quary = "SELECT telefono FROM telefono WHERE cliente =" + documento;
            Statement statement = isaDBConnection.createStatement();
            ResultSet consulta = statement.executeQuery(quary);
            ArrayList<String> telefonos = new ArrayList<>();
            while (consulta.next()) {
                Long number = consulta.getLong("telefono");
                String telefono = String.valueOf(number);
                telefonos.add(telefono);
            }
            return telefonos;
        } catch (SQLException ex) {
            Logger.getLogger(IsaDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ArrayList<>();
    }
    
    private ArrayList<String> consultarCorreos(Long documento) {
        try {
            String quary = "SELECT correo FROM correo WHERE cliente =" + documento;
            Statement statement = isaDBConnection.createStatement();
            ResultSet consulta = statement.executeQuery(quary);
            ArrayList<String> correos = new ArrayList<>();
            while (consulta.next()) {
                String correo = consulta.getString("correo");
                correos.add(correo);
            }
            return correos;
        } catch (SQLException ex) {
            Logger.getLogger(IsaDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ArrayList<>();
    }
    
    private ArrayList<String> consultarDirecciones(Long documento) {
        try {
            String quary = "SELECT direccion FROM direccion WHERE cliente =" + documento;
            Statement statement = isaDBConnection.createStatement();
            ResultSet consulta = statement.executeQuery(quary);
            ArrayList<String> direcciones = new ArrayList<>();
            while (consulta.next()) {
                String direccion = consulta.getString("direccion");
                direcciones.add(direccion);
            }
            return direcciones;
        } catch (SQLException ex) {
            Logger.getLogger(IsaDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ArrayList<>();
    }
    
    public Cliente informacionCliente(Long documento) {
        try {
            String quary = "SELECT * FROM cliente WHERE documento  =" + documento;
            Statement statement = isaDBConnection.createStatement();
            ResultSet consulta = statement.executeQuery(quary);
            //System.out.println("resultados: " + consulta);
            while (consulta.next()) {
                System.out.println("Did you find it?");
                Long documentoCliente = consulta.getLong("documento");
                String nombres = consulta.getString("nombres");
                String apellidos = consulta.getString("apellidos");
                String genero = consulta.getString("genero");
                String nacimiento = consulta.getString("fecha_nacimiento");
                Blob blobImage = consulta.getBlob("image");
                byte[] imagebytes = blobImage.getBytes(1, (int) blobImage.length());
                
                ArrayList<String> telefonos = consultarTelefonos(documento);
                ArrayList<String> correos =  consultarCorreos(documento);
                ArrayList<String> direcciones =  consultarDirecciones(documento);
                
                Cliente cliente = new Cliente(documentoCliente, 
                        nombres, apellidos, genero, nacimiento, 
                        telefonos, correos, direcciones, imagebytes);
                return cliente;
            }
        } catch (SQLException ex) {
            System.out.println("quedate aqui y defiendelo");
            Logger.getLogger(IsaDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
