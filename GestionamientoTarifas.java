/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.canchapro.clasesdelsistema.gestionamiento;
import com.mycompany.canchapro.clasesdelsistema.Tarifa;
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
public class GestionamientoTarifas {
    private Tarifa[] tar = new Tarifa[50];
    private int cantidad = 0;
    
    public GestionamientoTarifas(){
        CargarArchivoTarifa();
    }
    
    public boolean TarifaGuardada(Tarifa tarifanueva){
        if(tarifanueva == null || cantidad >= tar.length){
            return false;
        }
        for(int i = 0; i < cantidad; i++){
            if(tar[i].getDia().equalsIgnoreCase(tarifanueva.getDia()) && tar[i].getHorario().equalsIgnoreCase(tarifanueva.getHorario())){
                return false;
            }
        }
        tar[cantidad] = tarifanueva;
        cantidad++;
        ArchivoTarifa();
        return true;
    }
    public int getCantidad(){
        return cantidad;
    }
    public Tarifa ObtenemosTarifa(int posicion){
        if(posicion >= 0 && posicion < cantidad){
            return tar[posicion];
        }
        return null;
    }
    public double Tarifas(String dia, int HoraInicio, int HoraFinal){
        double total = 0;
        if(dia.equalsIgnoreCase("LUNES") || dia.equalsIgnoreCase("MARTES") || dia.equalsIgnoreCase("MIERCOLES") || dia.equalsIgnoreCase("JUEVES") || dia.equalsIgnoreCase("VIERNES") || dia.equalsIgnoreCase("SABADO") || dia.equalsIgnoreCase("DOMINGO")){
        }else{
            System.out.println("El dia ingresado no es valido");
            return -1;
        }
        if(HoraInicio < 8 || HoraFinal > 23 || HoraInicio >= HoraFinal){
        System.out.println("El horario no es valido");
        return -1;
        }
        if(dia.equalsIgnoreCase("Sabado") || dia.equalsIgnoreCase("Domingo")){
        total = (HoraFinal - HoraInicio) * 250;
        }else{
            for(int hora = HoraInicio; hora < HoraFinal; hora++){
            if(hora >= 8 && hora < 17){
                total = total + 150;
            }else{
                total = total + 225;
            }
        }
    }
    return total;
    }
    public void CalculoTarifas(){
        String dia = JOptionPane.showInputDialog("Ingrese el dia de la semana: ");
        int HoraInicio = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la Hora de Inicio: "));
        int HoraFinal = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la Hora Final: "));
        
        double precio = Tarifas(dia, HoraInicio, HoraFinal);
        if(precio >= 0){
            System.out.println("Dia que lo requiere: " + dia);
            System.out.println("Hora de Inicio: " + HoraInicio);
            System.out.println("Hora Final: " + HoraFinal);
            System.out.println("Costo total: " + precio);
        }
    }
    public boolean TarifaModificada(int posicion, Tarifa tarifanueva){
        if(posicion < 0 || posicion >= cantidad || tarifanueva == null){
            return false;
        }
         for(int i = 0; i < cantidad; i++){
             if(i != posicion && tar[i].getDia().equalsIgnoreCase(tarifanueva.getDia()) && tar[i].getHorario().equalsIgnoreCase(tarifanueva.getHorario())){
                 return false;
             }
         }
        tar[posicion] = tarifanueva;
        ArchivoTarifa();
        return true;
    } 
    public boolean TarifaExistente(String dia, int HoraInicio, int HoraFinal){
        for(int i = 0; i < cantidad; i++){
            Tarifa tarifita = tar[i];
            if(tarifita.getDia().equalsIgnoreCase(dia)){
                if(HoraInicio >= tarifita.getHorainicio() && HoraFinal <= tarifita.getHorafinalizacion() && HoraInicio < HoraFinal){
                    return true;
                }
            }
        }
        return false;
    }
    public void ArchivoTarifa(){
        try{
            FileWriter WF = new FileWriter("tarifas.txt");
            BufferedWriter WB = new BufferedWriter(WF);
            
            WB.write("Dia,Horario,HoraInicio,HoraFinal,PrecioPorHora");
            WB.newLine();
            for(int i = 0; i < cantidad; i++){
                WB.write(tar[i].getDia() + "," + tar[i].getHorario() + "," + tar[i].getHorainicio() + "," + tar[i].getHorafinalizacion() + "," + tar[i].getPrecioporhora());
                WB.newLine();
            }
            WB.close();
            System.out.println("Archivo Tarifa Guardado");
        }catch(IOException e){
            System.out.println("ERROR: " + e.getMessage());
        }
    }
    public void CargarArchivoTarifa(){
        try{
        cantidad = 0;
        FileReader RF = new FileReader("tarifas.txt");
        BufferedReader RB = new BufferedReader(RF);
        String linea = RB.readLine();
        linea = RB.readLine();
        
        while(linea != null){    
            String[] info = linea.split(",", -1);
            if(info.length == 5 && cantidad < tar.length){
            try{
            String dia = info[0]; 
            String horario = info[1];
            int horainicio = Integer.parseInt(info[2]);
            int horafinal = Integer.parseInt(info[3]);
            double precio = Double.parseDouble(info[4]);
            
            Tarifa nuevo = new Tarifa(dia, horario, horainicio, horafinal, precio);
            boolean repeticion = false;
            for(int i = 0; i < cantidad; i++){
                if(tar[i].getDia().equalsIgnoreCase(dia) && tar[i].getHorario().equalsIgnoreCase(horario)){
                repeticion = true;
                break;
                }
            }
            if(!repeticion){
                tar[cantidad] = nuevo;
                cantidad++;
                }
            }catch(NumberFormatException e){
            System.out.println("Hay datos incorrectos en la tarifa");
            }
        }    
        linea = RB.readLine();
        }   
        RB.close();
        System.out.println("Tarifas cargadas al archivo: " + cantidad);
        }catch(IOException e){
            System.out.println("ERROR: " + e.getMessage());
            ArchivoTarifa();
        }
    }
}
