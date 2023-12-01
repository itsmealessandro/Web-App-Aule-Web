/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package data.dao;

import data.domain.Evento;
import data.domain.Responsabile;
import java.util.List;

/**
 *
 * @author emanu
 */
public interface ResponsabileDAO {
    
    // Crea e restituisce un nuovo responsabile
    Responsabile creaNuovoResponsabile();

    // Restituisce il responsabile corrente
    Responsabile getResponsabileCorrente();

    // Restituisce il responsabile associato a un evento specifico
    Responsabile getResponsabilePerEvento(Evento evento);

    // Restituisce tutti i responsabili disponibili
    List<Responsabile> getTuttiIResponsabili();

    // Salva il responsabile corrente
    void salvaResponsabileCorrente(); 
}
