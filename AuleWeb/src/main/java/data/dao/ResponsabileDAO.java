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
    Responsabile ottieniResponsabileCorrente();

    // Restituisce il responsabile associato a un evento specifico
    Responsabile ottieniResponsabilePerEvento(Evento evento);

    // Restituisce tutti i responsabili disponibili
    List<Responsabile> ottieniTuttiIResponsabili();

    // Salva il responsabile corrente
    void salvaResponsabileCorrente(); 
}
