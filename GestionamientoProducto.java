/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.canchapro.clasesdelsistema.gestionamiento;
import com.mycompany.canchapro.clasesdelsistema.Producto;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;
/**
 *
 * @author josue
 */
public class GestionamientoProducto {
    
    Producto[] prodc = new Producto[100];
    int cantidad = 0;
    
    public GestionamientoProducto(){
        CargarArchivoProducto();
    }
    
    public boolean ProductoGuardado(Producto productonuevo){
        if(ProductoBuscado(productonuevo.getCodigo())){
            return false;
        }
        if(cantidad < prodc.length){
            prodc[cantidad] = productonuevo;
            cantidad++;
            ArchivoProducto();
            return true;        
        }
        return false;
    }
    public boolean ProductoBuscado(int codigo){
        for(int i = 0; i < cantidad; i++){
            if(prodc[i].getCodigo() == codigo){
                return true;
            }   
        }
        return false;
    }
    public int getCantidad(){
        return cantidad;
    }
    public Producto ProductoObtenido(int posicion){
        return prodc[posicion];
    }
    public void ArchivoProducto(){
        try{
            FileWriter WF = new FileWriter("productos.txt");
            BufferedWriter WB = new BufferedWriter(WF);
            
            WB.write("Codigo,Nombre,Categoria,Precio,Existencia,StockMinimo,Estado");
            WB.newLine();
            for(int i = 0; i < cantidad; i++){
                WB.write(prodc[i].getCodigo() + "," + prodc[i].getNombre() + "," + prodc[i].getCategoria() + "," + prodc[i].getPrecio() + "," + prodc[i].getExistencia() + "," + prodc[i].getStockminimo() + "," + prodc[i].getEstado());
                WB.newLine();
            }
            WB.close();
        }catch(IOException e){
            System.out.println("ERROR: " + e.getMessage());
        }
    }
    public void CargarArchivoProducto(){
        try{
            FileReader RF = new FileReader("productos.txt");
            BufferedReader RB = new BufferedReader(RF);
            
            String linea;
            cantidad = 0;
            RB.readLine();
            while((linea = RB.readLine()) != null){
                String[] dts = linea.split(",");
                int codigo = Integer.parseInt(dts[0]);
                String nombre = dts[1];
                String categoria = dts[2];
                double precio = Double.parseDouble(dts[3]);
                int existencia = Integer.parseInt(dts[4]);
                int stockminimo = Integer.parseInt(dts[5]);
                String estado = dts[6];
                prodc[cantidad] = new Producto(codigo, nombre, categoria, precio, existencia, stockminimo, estado);
                cantidad++;
            }
            RB.close();
            System.out.println("Productos cargados: " + cantidad);
        }catch(IOException e){
            System.out.println("No se encontro el archivo: " + e.getMessage());
        }
    }
}
