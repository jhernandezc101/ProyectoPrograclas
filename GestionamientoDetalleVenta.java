/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.canchapro.clasesdelsistema.gestionamiento;
import com.mycompany.canchapro.clasesdelsistema.VentaDetalle;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;
/**
 *
 * @author josue
 */
public class GestionamientoDetalleVenta {
    
    VentaDetalle[] vendet = new VentaDetalle[100];
    int cantidad = 0;
    
    public GestionamientoDetalleVenta(){
        CargarArchivoDetalle();
    }
    public boolean GuardarDetalle(VentaDetalle detallenuevo){
        if(cantidad < vendet.length){
            vendet[cantidad] = detallenuevo;
            cantidad++;
            ArchivoDetalleVenta();
            return true;
        }
        return false;
    }
    public int getCantidad(){
        return cantidad;
    }
    public VentaDetalle DetalleObtenido(int posicion){
        return vendet[posicion];        
    }
    public void ArchivoDetalleVenta(){
        try{
            FileWriter WF = new FileWriter("detalle_venta.txt");
            BufferedWriter WB = new BufferedWriter(WF);
            
            WB.write("CodigoVenta,CodigoProducto,Cantidad,Precio,Total");
            WB.newLine();
            for(int i = 0; i < cantidad; i++){
                WB.write(vendet[i].getCodigoventa() + "," + vendet[i].getCodigoproducto() + "," + vendet[i].getCantidad() + "," + vendet[i].getPrecio() + "," + vendet[i].getTotal());
                WB.newLine();
            }
            WB.close();
        }catch(IOException e){
            System.out.println("ERROR: " + e.getMessage());
        }
    }
    public void CargarArchivoDetalle(){
        try{
            FileReader RF = new FileReader("detalle_venta.txt");
            BufferedReader RB = new BufferedReader(RF);
            
            String linea;
            cantidad = 0;
            RB.readLine();
            while((linea = RB.readLine()) != null){
                String[] dts = linea.split(",");
                int codigoventa = Integer.parseInt(dts[0]);
                int codigoproducto = Integer.parseInt(dts[1]);
                int cantidadproducto = Integer.parseInt(dts[2]);
                double precio = Double.parseDouble(dts[3]);
                double total = Double.parseDouble(dts[4]);
                if(cantidad < vendet.length){
                vendet[cantidad] = new VentaDetalle(codigoventa, codigoproducto, cantidadproducto, precio, total);
                cantidad++;
                }
            }
            RB.close();
            System.out.println("Detalles Ventas cargados: " + cantidad);
        }catch(IOException e){
            System.out.println("No se encontro el archivo: " + e.getMessage());
        }
    }
}
