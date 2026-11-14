/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.canchapro.clasesdelsistema.gestionamiento;
import com.mycompany.canchapro.clasesdelsistema.Cancha;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;
import javax.swing.JOptionPane;
/**
 *
 * @author josue
 */
public class GestionamientoCancha {
    
    private Cancha[] Canchas = new Cancha[25];
    private int cantidad = 0;
    
    public GestionamientoCancha(){
        CargarArchivoCancha();
    }
    
    public boolean GuardarDatosCancha(Cancha canchas){
        if(BuscarDatosCancha(canchas.getCodigo())){
            return false;
        }
        if(cantidad < Canchas.length){
            Canchas[cantidad] = canchas;
            cantidad ++;
            ArchivoCancha();
            return true;
        }
        return false;
    }
    public boolean BuscarDatosCancha(int BuscarCancha){
        boolean Encontrado = false;
        int num = 0;
        
        while((num < cantidad) && (Encontrado == false)){
            if(Canchas[num].getCodigo() == BuscarCancha){
                Encontrado = true;
        }
        num ++;    
        }
        return Encontrado;
    }
    public void ModificarDatosCancha(){
        int codigo = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el codigo a modidicar: "));
        int posicion = 0;
        while(posicion < cantidad && Canchas[posicion].getCodigo() != codigo){ 
            posicion++;
        } 
        if(posicion  >= cantidad){
            JOptionPane.showMessageDialog(null, "La cancha no esta registrada no existe");
        }else{
            String NuevoNombre = JOptionPane.showInputDialog("Nuevo Nombre: ");
            int NuevoCodigo = Integer.parseInt(JOptionPane.showInputDialog("Nuevo Codigo: "));
            String Nuevotipo = JOptionPane.showInputDialog("Nuevo Tipo: ");
            int NuevoCapacidad = Integer.parseInt(JOptionPane.showInputDialog("Nuevo Capacidad: "));
            String NuevoEstado = JOptionPane.showInputDialog("Nuevo Estado: ");
            for(int i = 0; i < cantidad; i++){
                if(i != posicion && Canchas[i].getCodigo() == NuevoCodigo){
                    JOptionPane.showMessageDialog(null, "Ese codigo ya existe");
                    return;
                }
            }
            Canchas[posicion].setNombre(NuevoNombre);
            Canchas[posicion].setCodigo(NuevoCodigo);
            Canchas[posicion].setTipo(Nuevotipo);
            Canchas[posicion].setCapacidad(NuevoCapacidad);
            Canchas[posicion].setEstado(NuevoEstado);
            JOptionPane.showMessageDialog(null, "Cancha modificada con exito");
            
        }
    }
    public boolean ModificarDatosCanchas(Cancha canchas){
        for(int i = 0; i < cantidad; i++){
            if(Canchas[i].getCodigo() == canchas.getCodigo()){
                Canchas[i].setNombre(canchas.getNombre());
                Canchas[i].setTipo(canchas.getTipo());
                Canchas[i].setCapacidad(canchas.getCapacidad());
                Canchas[i].setEstado(canchas.getEstado());
                Canchas[i].setHoraapertura(canchas.getHoraapertura());
                Canchas[i].setHoracierre(canchas.getHoracierre());
                ArchivoCancha();
                return true;
            }
        }
        return false;
    }
    public boolean EliminarDatosCancha(int codigo){
        int posicion = 0;
        while(posicion < cantidad && Canchas[posicion].getCodigo() != codigo){ 
            posicion++;
        }       
        if( posicion  >= cantidad){
            return false;
        }else{
           for(int i = posicion; i < cantidad - 1; i++){
           Canchas[i] = Canchas[i + 1]; 
        }
            
            Canchas[cantidad - 1] = null;
            cantidad--;
            ArchivoCancha();
            return true;
 
        }
    }
    public void MostrarDatosCancha(){
        for(int i = 0; i < cantidad; i++){
        System.out.println("Nombre: " + Canchas[i].getNombre());
        System.out.println("Codigo: " + Canchas[i].getCodigo());
        System.out.println("Tipo: " + Canchas[i].getTipo());
        System.out.println("Capacidad: " + Canchas[i].getCapacidad());
        System.out.println("Estado: " + Canchas[i].getEstado());
        }
    }
    public Cancha BuscandoCanchaNom(String nombre){
        for(int i = 0; i < cantidad; i++){
            if(Canchas[i].getNombre().equals(nombre)){
                return Canchas[i];
            }
        }
        return null;
    }
    public Cancha CanchaCodigo(int codigo){
        for(int i = 0; i < cantidad; i++){
            if(Canchas[i].getCodigo() == codigo){
                return Canchas[i];
            }
        }
        return null;
    }
    public int getCantidad(){
        return cantidad;
    }
    public Cancha CanchaObtenida(int posicion){
           return Canchas[posicion];
    }
    public void CargarArchivoCancha(){
        try{
            cantidad = 0;
            FileReader RF = new FileReader("cancha.txt");
            BufferedReader RB = new BufferedReader(RF);
            String linea = RB.readLine();
            linea = RB.readLine();
        
        while(linea != null && cantidad < Canchas.length){    
            String[] info = linea.split(",", -1);
            if(info.length == 8){
            try{
            int codigo = Integer.parseInt(info[0]); 
            String nombre = info[1];
            String tipo = info[2];
            int capacidad = Integer.parseInt(info[3]);
            String estado = info[4];
            double precio = Double.parseDouble(info[5]);
            int apertura = Integer.parseInt(info[6]);
            int cierre = Integer.parseInt(info[7]);
            
            Cancha nueva = new Cancha(codigo, nombre, tipo, capacidad, estado, precio, apertura, cierre);
            if(!BuscarDatosCancha(codigo)){
            Canchas[cantidad] = nueva;
            cantidad++;
            }
            
            }catch(NumberFormatException e){
                    System.out.println("Hay datos incorrectos en la cancha" + linea);
                    }
            }else{
                 System.out.println("Linea ignorada (no tiene 8 columnas): " + linea);
            }
            linea = RB.readLine();
        }
        RB.close();
        System.out.println("Cancha CArgada al archivo" + cantidad);
        }catch(IOException e){
            System.out.println("No existe cancha.csv. Se creara el archivo");
            ArchivoCancha();
        }
    }
    public void ArchivoCancha(){
        try{
            FileWriter WF = new FileWriter("cancha.txt");
            BufferedWriter WB = new BufferedWriter(WF);
            
            WB.write("Codigo,  Nombre,  Tipo,  Capacidad,  Estado,  PrecioBase,  HoraApertura,  HoraCierre");
            WB.newLine();
            for(int i = 0; i < cantidad; i++){
                WB.write(Canchas[i].getCodigo() + "," + Canchas[i].getNombre() + "," + Canchas[i].getTipo() + "," + Canchas[i].getCapacidad() + "," + Canchas[i].getEstado() + "," + Canchas[i].getPreciobase() +  "," + Canchas[i].getHoraapertura() + "," +  Canchas[i].getHoracierre());
                WB.newLine();
            }
            WB.close();
            System.out.println("No te preocupes cancha guardada");
        }catch(IOException e){
            System.out.println("ERROR al guardar canchas: " + e.getMessage());
        }
    }

}


