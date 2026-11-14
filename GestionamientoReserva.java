/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.canchapro.clasesdelsistema.gestionamiento;
import com.mycompany.canchapro.clasesdelsistema.Reserva;
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
public class GestionamientoReserva {
    private Reserva[] reserva = new Reserva[30];
    int cantidad = 0;
    
    public GestionamientoReserva(){
        CargandoArchivosReserva();
    }
    
    public boolean GuardarDatosCreadosReserva(Reserva nuevareserva){
        boolean Encontrado = BuscarDatosReserva(nuevareserva.getCodigoreserva()); 
        if(Encontrado == true){
            return false;
        }
        
        boolean Disponibilidad = DisponibilidadReserva(nuevareserva);
        if(Disponibilidad == false){
            System.out.println("La cancha ya esta reservada");
            return false;
        }
        if(cantidad < reserva.length){
            reserva[cantidad] = nuevareserva;
            cantidad ++;
            ArchivoReserva();
            
            System.out.println("Reserva Guardada");
            return true;
        }
        System.out.println("No se guardaron mas reservas");
        return false;
    }
    public boolean BuscarDatosReserva(int BuscarCancha){
        boolean Encontrado = false;
        int num = 0;
        
        while((num < cantidad) && (Encontrado == false)){
            if(reserva[num].getCodigoreserva() == BuscarCancha){
                Encontrado = true;
        }
        num ++;    
        }
        return Encontrado;
    }
    public Reserva ObtenmosReserva(int Codigo){
        int posicion = 0;
        while(posicion < cantidad){
            if(reserva[posicion].getCodigoreserva() == Codigo){
             return reserva[posicion];   
            }
            posicion++;
        } 
        return null;
    }
    public int getCantidad(){
        return cantidad;
    }
    public Reserva ReservaObtenida(int posicion){
       if(posicion >= 0 && posicion < cantidad){
           return reserva[posicion];
       }
       return null;
    }
    
    public boolean DisponibilidadReserva(Reserva nuevareserva, int posicionExcluir){
        for(int i = 0; i < cantidad; i++){
            if(i == posicionExcluir){
                continue;
            }
            if(reserva[i].getCancha().equals(nuevareserva.getCancha()) && reserva[i].getFecha().equals(nuevareserva.getFecha()) && !reserva[i].getEstadonuevareserva().equalsIgnoreCase("CANCELADA") && !reserva[i].getEstadonuevareserva().equalsIgnoreCase("CANCELADO") && nuevareserva.getHoraInicio() < reserva[i].getHoraFinal() && nuevareserva.getHoraFinal()  > reserva[i].getHoraInicio()){
                System.out.println("Cliente registrado: " + reserva[i].getCliente());
                System.out.println("Fecha registrada: " + reserva[i].getFecha());
                System.out.println("Hora de inicio registrada: " + reserva[i].getHoraInicio());
                System.out.println("Hora Final registrada: " + reserva[i].getHoraFinal());

                return false;
            }
        }
        return true;
    }
    public boolean DisponibilidadReserva(Reserva nuevareserva){
        return DisponibilidadReserva(nuevareserva, -1);
    }
    public boolean DisponibilidadDisponible(String fecha, String cancha, int horainicio, int horafinal){
        for(int i = 0; i < cantidad; i++){
            if(reserva[i].getEstadonuevareserva().equalsIgnoreCase("CANCELADA") || reserva[i].getEstadonuevareserva().equalsIgnoreCase("CANCELADO")){
                continue;
            }
            if(reserva[i].getFecha().equals(fecha) && String.valueOf(reserva[i].getCancha()).equals(cancha)){
            if(horainicio < reserva[i].getHoraFinal() && horafinal > reserva[i].getHoraInicio()){
                return false;
                }
            }
        }  
        return true;
    }    
    public void ModificarDatosReserva(){
        int posicion = 0;
        int codigo = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el codigo a modidicar: "));
        while(posicion < cantidad && reserva[posicion].getCodigoreserva() != codigo){ 
            posicion++;
        } 
        if(posicion >= cantidad){
            JOptionPane.showMessageDialog(null, "La reserva no esta registrada, por lo tanto no existe");
            return;
        }else{
            if(reserva[posicion].getEstadonuevareserva().equalsIgnoreCase("CANCELADA")){
            JOptionPane.showMessageDialog(null, "La reserva esta cancelada por lo tanto no se puede modificar ");
            return;
            }
            
            String NuevoCliente = JOptionPane.showInputDialog("Nuevo Cliente: ");
            String NuevoCancha = JOptionPane.showInputDialog("Nueva cancha: ");
            String NuevoFecha = JOptionPane.showInputDialog("Nueva Fecha: ");
            String NuevoEstadoReserva = JOptionPane.showInputDialog("Nueva Estado de Reserva: ");
            int NuevoCodigoreserva = Integer.parseInt(JOptionPane.showInputDialog("Nuevo Codigo de Reserva: "));
            int NuevoHoraInicio = Integer.parseInt(JOptionPane.showInputDialog("Nueva Hora de Inicio: "));
            int NuevoHoraFinal = Integer.parseInt(JOptionPane.showInputDialog("Nueva Hora Final: "));
            double NuevoSaldoPendiente = Double.parseDouble(JOptionPane.showInputDialog("Nuevo Saldo: "));
            double NuevoCosto = Double.parseDouble(JOptionPane.showInputDialog("Nuevo Costo: "));
            
            Reserva nuevareserva = new Reserva(NuevoCliente, NuevoCancha, NuevoFecha, NuevoCosto, NuevoEstadoReserva, NuevoCodigoreserva, NuevoHoraInicio, NuevoHoraFinal, NuevoSaldoPendiente);
            if(nuevareserva.ValidezHorario() == false){
                JOptionPane.showMessageDialog(null, "La Hora es incorrecta");
                return;
            }
            for(int i = 0; i < cantidad; i++){
                if(i != posicion && reserva[i].getCodigoreserva() == NuevoCodigoreserva){
                JOptionPane.showMessageDialog(null, "Codigo existente");
                return;
            }
         }
        if(!DisponibilidadReserva(nuevareserva, posicion)){
            JOptionPane.showMessageDialog(null, "Cancha reservada en esa hora y fecha");
            return;
        }
            
            reserva[posicion].setCliente(NuevoCliente);
            reserva[posicion].setCancha(NuevoCancha);
            reserva[posicion].setFecha(NuevoFecha);
            reserva[posicion].setEstadonuevareserva(NuevoEstadoReserva);
            reserva[posicion].setCodigoreserva(NuevoCodigoreserva);
            reserva[posicion].setHoraInicio(NuevoHoraInicio);
            reserva[posicion].setHoraFinal(NuevoHoraFinal);
            reserva[posicion].setSaldoPendiente(NuevoSaldoPendiente);
            reserva[posicion].setCosto(NuevoCosto);
            ArchivoReserva();
            
             JOptionPane.showMessageDialog(null, "Reserva modificada con exito");
        }
    }
    public boolean ModificarDatosReserva(Reserva nuevareserva, int CodigoExistente){
        int posicion = 0;
        while(posicion < cantidad && reserva[posicion].getCodigoreserva() != CodigoExistente){ 
            posicion++;
        }
        if(posicion >= cantidad){
            return false;
        }
        if(reserva[posicion].getEstadonuevareserva().equalsIgnoreCase("CANCELADA")){
            return false;
        }
        if(nuevareserva.ValidezHorario() == false){
            return false;
        }
        for(int i = 0; i < cantidad; i++){
                if(i != posicion && reserva[i].getCodigoreserva() == nuevareserva.getCodigoreserva()){
                    return false;
                }
            }
        if(!DisponibilidadReserva(nuevareserva, posicion)){
            return false;
        }
        reserva[posicion] = nuevareserva;
        ArchivoReserva();
        return true;
    }
    public boolean CancelarReserva(int codigo){
        int posicion = 0;
        while(posicion < cantidad && reserva[posicion].getCodigoreserva() != codigo){ 
            posicion++;
        }       
        if( posicion  >= cantidad){
            JOptionPane.showMessageDialog(null, "No se encontro la reserva");
            return false;
        }if(reserva[posicion].getEstadonuevareserva().equalsIgnoreCase("CANCELADA")){
            JOptionPane.showMessageDialog(null, "La Reserva esta cancelada ");
            return false;
        }
        reserva[posicion].setEstadonuevareserva("CANCELADA");
        JOptionPane.showMessageDialog(null, "La reserva ha sido cancelada con exito");
        ArchivoReserva();
        return true;
    }
    public void MostrarDatosReserva(){
        for(int i = 0; i < cantidad; i++){
        System.out.println("Cliente: " + reserva[i].getCliente());
        System.out.println("Cancha: " + reserva[i].getCancha());
        System.out.println("Fecha: " + reserva[i].getFecha());
        System.out.println("Estado nuevo de la Reserva " + reserva[i].getEstadonuevareserva());
        System.out.println("Codigoreserva: " + reserva[i].getCodigoreserva());
        System.out.println("Hora de Inicio: " + reserva[i].getHoraInicio());
        System.out.println("Hora Final: " + reserva[i].getHoraFinal());
        System.out.println("Saldo Pendiente: " + reserva[i].getSaldoPendiente());
        System.out.println("Costo: " + reserva[i].getCosto());
        }
    }
    public void CargandoArchivosReserva(){
        try{
        
        FileReader RF = new FileReader("reservas.txt");
        BufferedReader RB = new BufferedReader(RF);
        String linea = RB.readLine();
        
        while((linea = RB.readLine()) != null){    
            String[] info = linea.split(",", -1);
            try{
            if(cantidad < reserva.length){
            Reserva Res = null;
            if(info.length == 9 ){
            String cliente = info[0]; 
            String cancha = info[1];
            String fecha = info[2];
            double costo = Double.parseDouble(info[3]);
            String estado = info[4];
            int codigo = Integer.parseInt(info[5]);
            int HoraInicio = Integer.parseInt(info[6]);
            int HoraFinal = Integer.parseInt(info[7]);
            double SaldoPendiente = Double.parseDouble(info[8]);
            
            Res = new Reserva(cliente, cancha, fecha, costo, estado, codigo, HoraInicio, HoraFinal, SaldoPendiente);
            }else if(info.length == 11 || info.length == 12){
                int codigo = Integer.parseInt(info[0]);
                String cliente = info[1]; 
                String nombrecliente = info[2];
                String cancha = info[3];
                String nombrecancha = info[4];
                String fecha = info[5];
                double costo = Double.parseDouble(info[6]);
                String estado = info[7];
                int HoraInicio = Integer.parseInt(info[8]);
                int HoraFinal = Integer.parseInt(info[9]);
                double SaldoPendiente = Double.parseDouble(info[10]);
                
                Res = new Reserva(cliente, cancha, fecha, costo, estado, codigo, HoraInicio, HoraFinal, SaldoPendiente);
                Res.setNombrecliente(nombrecliente);
                Res.setNombrecancha(nombrecancha);
                if(info.length == 12){
                    Res.setServicioadicional(info[11]);
                }
            }else{
                System.out.println("Linea ignorada" + linea);
            }
            if(Res != null){
                reserva[cantidad] = Res;
                cantidad++;
                }
            } 
        }catch(NumberFormatException e){
        System.out.println("Hay datos incorrectos en la reserva");
            }
        }   
        RB.close();
        RF.close();
        System.out.println("Reserva cargadas al archivo: " + cantidad);
        }catch(IOException e){
            System.out.println("ERROR: " + e.getMessage());
        }
    }
        
    public void ArchivoReserva(){
        try{
            FileWriter WF = new FileWriter("reservas.txt");
            BufferedWriter WB = new BufferedWriter(WF);
            
            WB.write("CodigoReserva,CodigoCliente,NombreCliente,CodigoCancha,NombreCancha,Fecha,Costo,Estado,HoraInicio,HoraFinal,SaldoPendiente,ServiciosAdicionales");
            WB.newLine();
            for(int i = 0; i < cantidad; i++){
                WB.write(reserva[i].getCodigoreserva() + "," +
                         reserva[i].getCliente() + "," + 
                         reserva[i].getNombrecliente() + "," +
                         reserva[i].getCancha() + "," + 
                         reserva[i].getNombrecancha() + "," +
                         reserva[i].getFecha() + "," + 
                         reserva[i].getCosto() + "," + 
                         reserva[i].getEstadonuevareserva() + "," + 
                         reserva[i].getHoraInicio() + "," +  
                         reserva[i].getHoraFinal() + "," + 
                         reserva[i].getSaldoPendiente() + "," +
                         reserva[i].getServicioadicional());
                WB.newLine();
            }
            WB.close();
            System.out.println("No te preocupes reserva guardada");
        }catch(IOException e){
            System.out.println("ERROR: " + e.getMessage());
        }
    }
    public boolean ActualizarReservaServicio(int codigo, String servicios){
        for(int i = 0; i < cantidad; i++){
           if(reserva[i].getCodigoreserva() == codigo){
               reserva[i].setServicioadicional(servicios);
                ArchivoReserva();   
                return true;
           } 
        }
        return false;
    }
}

