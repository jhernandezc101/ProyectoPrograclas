/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.canchapro.clasesdelsistema.gestionamiento;
import com.mycompany.canchapro.clasesdelsistema.Pago;
import com.mycompany.canchapro.clasesdelsistema.Reserva;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;

/**
 *
 * @author josue
 */
public class GestionamientoPago {
    private Pago[] pagos = new Pago[1000];
    private int cantidad = 0;
    
    public GestionamientoPago(){
        CargarArchivoPago();
    }
        public int BuscarPago(String codigopago){
            for(int i = 0; i < cantidad; i++){
            if(pagos[i].getCodigopago().equals(codigopago)){
                return i;    
        }    
    }
    System.out.println("No se encontro ningun pago");
    return -1;
}    
        public boolean RegistroPago(Pago pagonuevo, Reserva reserva){
        if(pagonuevo == null || reserva == null){
                return false;
        }
        if(cantidad >= pagos.length){
                return false;
        }
        if(BuscarPago(pagonuevo.getCodigopago()) != -1){
                return false;
        }
        boolean CodigosRepetidos = pagonuevo.getCodigoreserva().equals(String.valueOf(reserva.getCodigoreserva()));
        if(CodigosRepetidos == false){
            return false;
        }
        double SaldoActual = reserva.getSaldoPendiente();
        if(pagonuevo.getMonto() <= 0 || pagonuevo.getMonto() > SaldoActual){
            return false;
        }
        double SaldoNuevo = SaldoActual - pagonuevo.getMonto();
        reserva.setSaldoPendiente(SaldoNuevo);
        pagonuevo.setSaldopendiente(SaldoNuevo);
        if(SaldoNuevo == 0){
            pagonuevo.setEstado("PAGADO");
        }else{
            pagonuevo.setEstado("PENDIENTE");
        }
        pagos[cantidad] = pagonuevo;
        cantidad++;
        ArchivoPago();
        return true;
    }
    public void ArchivoPago(){
        try{
            FileWriter WF = new FileWriter("pagos.txt");
            BufferedWriter WB = new BufferedWriter(WF);
            
            WB.write("CodigoPago,CodigoReserva,Cliente,MetodoPago,TipoPago,Monto,Estado,SaldoPendiente,CostoTotal,FechaPago,Observacion,HoraPago");
            WB.newLine();
            for(int i = 0; i < cantidad; i++){
                Pago paguito = pagos[i];
                String observacion = paguito.getObservacion().replace(",", "");     
                WB.write(pagos[i].getCodigopago() + "," + pagos[i].getCodigoreserva() + "," + pagos[i].getCliente() + "," +pagos[i].getMetodopago() + "," + pagos[i].getTipopago() + "," +pagos[i].getMonto() + "," + pagos[i].getEstado() + "," + pagos[i].getSaldopendiente() + "," + pagos[i].getCostototal() + "," +  pagos[i].getFechapago() + "," + observacion + "," + pagos[i].getHorapago());
                WB.newLine();
            }
            WB.close();
            System.out.println("No te preocupes pagos guardados");
        }catch(IOException e){
            System.out.println("ERROR: " + e.getMessage());
            }
        }
    
    public void CargarArchivoPago(){
        try{
            
        cantidad = 0;
        FileReader RF = new FileReader("pagos.txt");
        BufferedReader RB = new BufferedReader(RF);
        String linea = RB.readLine();
        linea = RB.readLine();
        
        while(linea != null){    
            String[] info = linea.split(",", -1);
            if(info.length == 12 && cantidad < pagos.length){
            try{
                String codigo = info[0]; 
                String codigoreserva = info[1];
                String cliente = info[2];
                String metodo = info[3];
                String tipo = info[4];
                double monto = Double.parseDouble(info[5]);
                String estado = info[6];
                double saldo = Double.parseDouble(info[7]);
                double costo = Double.parseDouble(info[8]);
                String fecha = info[9];
                String observacion = info[10];
                int hora = Integer.parseInt(info[11]);
       
                if(BuscarPago(codigo) == -1){
                Pago nueva = new Pago(codigo, codigoreserva, cliente, metodo, tipo, monto, estado, saldo, costo, fecha, observacion, hora);
                pagos[cantidad] = nueva;
                cantidad++;
                }
            }catch(NumberFormatException e){
            System.out.println("Hay datos incorrectos en un pago");
            }
        }        
        linea = RB.readLine();
        }   
        RB.close();
        System.out.println("Pagos recuperados: " + cantidad);
        }catch(IOException e){
        System.out.println("ERROR: " + e.getMessage());
        }
    }
    public Pago ObtenemosPago(int posicion){
        if(posicion >= 0 && posicion < cantidad){
            return pagos[posicion];
        }
        return null;
    }
    public void MostrarDatosPagos(){
        for(int i = 0; i < cantidad; i++){
        System.out.println("Codigo de Pago: " + pagos[i].getCodigopago());
        System.out.println("Codigo de Reserva: " + pagos[i].getCodigoreserva());
        System.out.println("Cliente: " + pagos[i].getCliente());
        System.out.println("Fecha de Pago: " + pagos[i].getFechapago());
        System.out.println("Hora de pago: " + pagos[i].getHorapago());
        System.out.println("Metodo de Pago: " + pagos[i].getMetodopago());
        System.out.println("Tipo de Pago: " + pagos[i].getTipopago());
        System.out.println("Monto: " + pagos[i].getMonto());
        System.out.println("Estado: " + pagos[i].getEstado());
        System.out.println("Saldo Pendiente: " + pagos[i].getSaldopendiente());
        System.out.println("--------------------------------------------------");
        }
    }
    public int getCantidad(){
        return cantidad;
    }
    public int PagosPendientes(){
        int pend = 0;
        for(int i = 0; i < cantidad; i++){
            if(pagos[i].getEstado().equalsIgnoreCase("PENDIENTE")){
                pend++;
            }
        }
        return pend;
    }
}    
