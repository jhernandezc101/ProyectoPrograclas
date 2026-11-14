/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.canchapro.clasesdelsistema.gestionamiento;
import com.mycompany.canchapro.clasesdelsistema.Cliente;
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
public class GestionamientoCliente {
    
    private Cliente[] Clientes = new Cliente[25];
    private int cantidad = 0;
    
    public GestionamientoCliente(){
        CargarArchivoCliente();
    }
    public boolean RegistrarCliente(Cliente clientes){
        
        if(BuscarCliente(clientes.getCodigo())){
            return false;
        }
        if(cantidad < Clientes.length){
            Clientes[cantidad] = clientes;
            cantidad ++;
            ArchivoCliente();
            return true;
        }
        return false;
    }
    
    public boolean BuscarCliente(int buscacliente){
        boolean Encontrado = false;
        int num = 0;
        
        while((num < cantidad) && (Encontrado == false)){
            if(Clientes[num].getCodigo() == buscacliente){
                Encontrado = true;
        }
        num ++;    
        }
        return Encontrado;
    }
    
    public void ModificarCliente(){
        int posicion = 0;
        int codigo = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el codigo a modidicar: "));
        while(posicion < cantidad && Clientes[posicion].getCodigo() != codigo){ 
            posicion++;
        } 
        if(posicion  >= cantidad){
            JOptionPane.showMessageDialog(null, "Esa posicion no existe");
        }else{
            String NuevoNombre = JOptionPane.showInputDialog("Nuevo Nombre: ");
            int NuevoCodigo = Integer.parseInt(JOptionPane.showInputDialog("Nuevo Codigo: "));
            String Nuevotelefono = JOptionPane.showInputDialog("Nuevo Telefono: ");
            String NuevoCorreo = JOptionPane.showInputDialog("Nuevo Correo: ");
            
            Clientes[posicion].setNombre(NuevoNombre);
            Clientes[posicion].setCodigo(NuevoCodigo);
            Clientes[posicion].setTelefono(Nuevotelefono);
            Clientes[posicion].setCorreo(NuevoCorreo);
            
             JOptionPane.showMessageDialog(null, "Cliente modificado con exito");
            ArchivoCliente();
        }
    }
    public boolean ModificarCliente(Cliente clientes){
        for(int i = 0; i < cantidad; i++){
            if(Clientes[i].getCodigo() == clientes.getCodigo()){
                Clientes[i].setNombre(clientes.getNombre());
                Clientes[i].setTelefono(clientes.getTelefono());
                Clientes[i].setCorreo(clientes.getCorreo());
                Clientes[i].setFechaderegistro(clientes.getFechaderegistro());
                ArchivoCliente();
                return true;
            }
        }
        return false;
    }
    public boolean EliminarCliente(int codigo){
        int posicion = 0;
        while(posicion < cantidad && Clientes[posicion].getCodigo() != codigo){ 
            posicion++;
        }       
        if( posicion  >= cantidad){
            return false;
        }else{
           for(int i = posicion; i < cantidad - 1; i++){
           Clientes[i] = Clientes[i + 1]; 
        }
            Clientes[cantidad - 1] = null;
            cantidad--; 
            ArchivoCliente();
            return true;
        }
    }
    public Cliente ClienteObtenido(int codigo){
        for(int i = 0; i < cantidad; i++){
            if(Clientes[i].getCodigo() == codigo){
                return Clientes[i];
            }
        }
        return null;
    }
    public int getCantidad(){
        return cantidad;
    }
    public Cliente ObteniendoCliente(int posicion){
        return Clientes[posicion];
    }
    public void CargarArchivoCliente(){
        try{
            cantidad = 0;
            FileReader RF = new FileReader("cliente.txt");
            BufferedReader RB = new BufferedReader(RF);
            String linea = RB.readLine();
        
        while(linea != null){    
            String[] info = linea.split(",", -1);
            if(info.length == 5 && cantidad < Clientes.length){
            try{
            int codigo = Integer.parseInt(info[0]); 
            String nombre = info[1];
            String telefono = info[2];
            String correo = info[3];
            String fecharegistro = info[4];
                        
            Cliente nuevo = new Cliente(codigo, nombre, telefono, correo, fecharegistro);
            if(!BuscarCliente(codigo)){
            Clientes[cantidad] = nuevo;
            cantidad++;
            }
            
            }catch(NumberFormatException e){
                    System.out.println("Hay datos incorrectos en clientes");
                    }
            }
            linea = RB.readLine();
        }
        RB.close();
        }catch(IOException e){
            System.out.println("ERROR: " + e.getMessage());
        }
    }
    public void ArchivoCliente(){
        try{
            FileWriter WF = new FileWriter("cliente.txt");
            BufferedWriter WB = new BufferedWriter(WF);
            
            WB.write("Codigo,  Nombre,  Telefono,  Correo,  FechaRegistro");
            WB.newLine();
            for(int i = 0; i < cantidad; i++){
                WB.write(Clientes[i].getCodigo() + "," + Clientes[i].getNombre() + "," + Clientes[i].getTelefono() + "," + Clientes[i].getCorreo() + "," + Clientes[i].getFechaderegistro());
                WB.newLine();
            }
            WB.close();
            System.out.println("No te preocupes cliente guardado");
        }catch(IOException e){
            System.out.println("ERROR: " + e.getMessage());
        }
    }

}
