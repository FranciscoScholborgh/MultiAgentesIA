/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataFormat;

import dataFormat.converter.ArrayListConverter;
import java.io.Serializable;
import java.util.ArrayList;
import javafx.collections.ObservableList;

/**
 *
 * @author frank
 */
public class Cliente implements Serializable{
    
    private Long documento;
    private String nombres;
    private String apellidos;
    private String genero;
    private String nacimiento;
    private ArrayList<String> telefonos;
    private ArrayList<String> correos;
    private ArrayList<String> direcciones;
    private byte[] imgAsBytes;
    
    public Cliente(Long documento, String nombres, String apellidos, String genero, String nacimiento, 
            ObservableList<String> telefonos, ObservableList<String> correos, ObservableList<String> direcciones, 
            byte[] imgAsBytesr) {
        this.documento = documento;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.genero = genero;
        this.nacimiento = nacimiento;
        this.telefonos = (ArrayList<String>) ArrayListConverter.convertObservableListToArrayList(telefonos);
        this.correos = (ArrayList<String>) ArrayListConverter.convertObservableListToArrayList(correos);
        this.direcciones = (ArrayList<String>) ArrayListConverter.convertObservableListToArrayList(direcciones);
        this.imgAsBytes = imgAsBytesr;
    }

    public Cliente(Long documento, String nombres, String apellidos, String genero, String nacimiento, ArrayList<String> telefonos, ArrayList<String> correos, ArrayList<String> direcciones, byte[] imgAsBytes) {
        this.documento = documento;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.genero = genero;
        this.nacimiento = nacimiento;
        this.telefonos = telefonos;
        this.correos = correos;
        this.direcciones = direcciones;
        this.imgAsBytes = imgAsBytes;
    }
    
    
    
    public Long getDocumento() {
        return documento;
    }

    public void setDocumento(Long documento) {
        this.documento = documento;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(String nacimiento) {
        this.nacimiento = nacimiento;
    }

    public ArrayList<String> getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(ArrayList<String> telefonos) {
        this.telefonos = telefonos;
    }

    public ArrayList<String> getCorreos() {
        return correos;
    }

    public void setCorreos(ArrayList<String> correos) {
        this.correos = correos;
    }

    public ArrayList<String> getDirecciones() {
        return direcciones;
    }

    public void setDirecciones(ArrayList<String> direcciones) {
        this.direcciones = direcciones;
    }

    public byte[] getImgAsBytes() {
        return imgAsBytes;
    }

    public void setImgAsBytes(byte[] imgAsBytesr) {
        this.imgAsBytes = imgAsBytesr;
    }
    
}
