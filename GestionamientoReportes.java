/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.canchapro.clasesdelsistema.gestionamiento;
import com.mycompany.canchapro.clasesdelsistema.Reserva;
import com.mycompany.canchapro.clasesdelsistema.Pago;
/**
 *
 * @author josue
 */
public class GestionamientoReportes {
    
    private GestionamientoReserva GestRes;
    private GestionamientoPago GPA;
    
    public GestionamientoReportes(GestionamientoReserva GestRes, GestionamientoPago GPA){
        this.GestRes = GestRes;
        this.GPA = GPA;
    }
}
