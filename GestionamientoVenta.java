/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.canchapro.clasesdelsistema.gestionamiento;
import com.mycompany.canchapro.clasesdelsistema.Venta;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;
/**
 *
 * @author josue
 */
public class GestionamientoVenta {
    
    Venta[] ven = new Venta[100];
    int cantidad = 0;
    
    public GestionamientoVenta(){
        CargarArchivoVenta();
    }
    public boolean VentaGuardada(Venta ventanueva){
        if(VentaBuscada(ventanueva.getNumero())){
            return false;
        }
        if(cantidad < ven.length){
            ven[cantidad] = ventanueva;
            cantidad++;
            ArchivoVenta();
            return true;
        }
        return false;
    }
    public boolean VentaBuscada(int num){
        for(int i = 0; i < cantidad; i++){
            if(ven[i].getNumero() == num){
                return true;
            }
        }
        return false;
    }
    public int getCantidad(){
        return cantidad;
    }
    public Venta VentaObtenida(int posicion){
        return ven[posicion];
    }
    public void ArchivoVenta(){
        try{
            FileWriter WF = new FileWriter("venta.txt");
            BufferedWriter WB = new BufferedWriter(WF);
            
            WB.write("Numero,Fecha,CodigoReserva,Total,MetodoPago");
            WB.newLine();
            for(int i = 0; i < cantidad; i++){
                WB.write(ven[i].getNumero() + "," + ven[i].getFecha() + "," + ven[i].getCodigoreserva() + "," + ven[i].getTotal() + "," + ven[i].getMetodopago());
                WB.newLine();
            }
            WB.close();
        }catch(IOException e){
            System.out.println("ERROR: " + e.getMessage());
        }
    }
    public void CargarArchivoVenta(){
        try{
            FileReader RF = new FileReader("venta.txt");
            BufferedReader RB = new BufferedReader(RF);
            
            String linea;
            cantidad = 0;
            RB.readLine();
            while((linea = RB.readLine()) != null){
                String[] dts = linea.split(",");
                int numero = Integer.parseInt(dts[0]);
                String fecha = dts[1];
                String codigoreserva = dts[2];
                double total = Double.parseDouble(dts[3]);
                String metodopago = dts[4];
                if(cantidad < ven.length){
                ven[cantidad] = new Venta(numero, fecha, codigoreserva, total, metodopago);
                cantidad++;
                }
            }
            RB.close();
            System.out.println("Ventas cargados: " + cantidad);
        }catch(IOException e){
            System.out.println("No se encontro el archivo: " + e.getMessage());
        }
    }
}
