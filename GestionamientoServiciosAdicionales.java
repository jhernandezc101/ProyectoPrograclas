/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.canchapro.clasesdelsistema.gestionamiento;
import com.mycompany.canchapro.clasesdelsistema.ServicioAdicional;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;
/**
 *
 * @author josue
 */
public class GestionamientoServiciosAdicionales {
    
    private ServicioAdicional[] servad = new ServicioAdicional[100];
    private int cantidad = 0;
    
    public GestionamientoServiciosAdicionales(){
        CargarArchivoServiciosAdicionales();
    }
    public int BuscarServicioAdicional(String codigo){
        for(int i = 0; i < cantidad; i++){
            if(servad[i].getCodigo().equals(codigo)){
                return i;
            }
        }
        return -1;
    }
    public boolean ServicioAdicionalRegistrado(ServicioAdicional nuevoservicio){
        if(nuevoservicio == null || nuevoservicio.getCodigo().isEmpty()){
           return false;
        }
        if(!nuevoservicio.ValidezDelPrecio()){
                return false;
        }
        if(BuscarServicioAdicional(nuevoservicio.getCodigo()) != -1){
                return false;
        }
        if(cantidad >= servad.length){
            return false;
        }
        servad[cantidad] = nuevoservicio;
        cantidad++;
        ArchivoServicioAdicional();
        return true;
    }
    public boolean ModificarServicioAdicional(ServicioAdicional modificarservicio){
        if(modificarservicio == null){
            return false;
        }
        int posicion = BuscarServicioAdicional(modificarservicio.getCodigo());
        if(posicion == -1 || !modificarservicio.ValidezDelPrecio()){
            return false;
        }
        servad[posicion].setNombre(modificarservicio.getNombre());
        servad[posicion].setPrecio(modificarservicio.getPrecio());
        ArchivoServicioAdicional();
        return true;
    }
    public ServicioAdicional ServicioAdicionalObtenido(int posicion){
        if(posicion >= 0 && posicion < cantidad){
            return servad[posicion];
        }
        return null;
    }
    public int getCantidad(){
        return cantidad;
    }
    public void ArchivoServicioAdicional(){
        try{
            FileWriter WF = new FileWriter("servicios.txt");
            BufferedWriter WB = new BufferedWriter(WF);
            
            WB.write("Codigo,Nombre,Precio");
            WB.newLine();
            for(int i = 0; i < cantidad; i++){
                ServicioAdicional SERVAD = servad[i];
                    
                WB.write(SERVAD.getCodigo() + "," + SERVAD.getNombre() + "," + SERVAD.getPrecio());
                WB.newLine();
            }
            WB.close();
            WF.close();
        }catch(IOException e){
            System.out.println("ERROR: " + e.getMessage());
            }
        }
    public void CargarArchivoServiciosAdicionales(){
        cantidad = 0;
        try{
        FileReader RF = new FileReader("servicios.txt");
        BufferedReader RB = new BufferedReader(RF);
        String linea = RB.readLine();
        
        while((linea = RB.readLine()) != null){    
            String[] info = linea.split(",", -1);
            if(info.length == 3 && cantidad < servad.length){
                String codigo = info[0]; 
                String nombre = info[1];
                double precio = Double.parseDouble(info[2]);
       
                if(BuscarServicioAdicional(codigo) == -1){
                    servad[cantidad] = new ServicioAdicional(codigo, nombre, precio);
                    cantidad++;
                }
            }
        }
    RB.close();
    RF.close();
    System.out.println("Se recuperaron los servicios: " + cantidad);
    }catch(IOException e){
        System.out.println("ERROR: " + e.getMessage());
        }
    }
}
