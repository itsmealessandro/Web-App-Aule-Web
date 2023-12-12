/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package data.dao;

import data.domain.Corso;
import data.domain.Evento;
import data.domain.Responsabile;
import framework.data.DataException;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author emanu
 */
public interface EventoDAO {
    
    // Crea e restituisce un nuovo evento
    Evento createEvento() throws DataException;
    
    // Salva l'evento corrente
    void storeEvento(Evento evento) throws DataException;
    
    // Restituisce l'evento corrente
    Evento getEventoByID(int evento_key) throws DataException;
    
    /*   
    // Restituisce tutti gli eventi associati a un corso specifico
    List<Evento> getEventiPerCorso(Corso corso);

    // Restituisce tutti gli eventi gestiti da un responsabile specifico
    List<Evento> getEventiPerResponsabile(Responsabile responsabile);

    // Restituisce tutti gli eventi disponibili
    List<Evento> getTuttiGliEventi();

    // Restituisce tutti gli eventi in una settimana specifica
    List<Evento> getEventiPerSettimana(int numeroSettimana);

    // Restituisce tutti gli eventi ricorrenti
    List<Evento> getEventiRicorrenti();

    // Restituisce tutti gli eventi in una specifica data
    List<Evento> getEventiPerGiorno(LocalDate data);

    // Restituisce tutti gli eventi in una settimana specifica associati a un corso specifico
    List<Evento> getEventiPerSettimanaECorso(int numeroSettimana, Corso corso);
    
    // Elimina l'evento corrente
    void eliminaEventoCorrente() throws DataException;
    */

    
}
