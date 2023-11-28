/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

import data.domain.Corso;
import data.domain.Evento;
import data.domain.Responsabile;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author emanu
 */
public interface EventoDAO {
    
    // Crea e restituisce un nuovo evento
    Evento creaNuovoEvento();

    // Restituisce l'evento corrente
    Evento ottieniEventoCorrente();

    // Restituisce tutti gli eventi associati a un corso specifico
    List<Evento> ottieniEventiPerCorso(Corso corso);

    // Restituisce tutti gli eventi gestiti da un responsabile specifico
    List<Evento> ottieniEventiPerResponsabile(Responsabile responsabile);

    // Restituisce tutti gli eventi disponibili
    List<Evento> ottieniTuttiGliEventi();

    // Restituisce tutti gli eventi in una settimana specifica
    List<Evento> ottieniEventiPerSettimana(int numeroSettimana);

    // Restituisce tutti gli eventi ricorrenti
    List<Evento> ottieniEventiRicorrenti();

    // Restituisce tutti gli eventi in una specifica data
    List<Evento> ottieniEventiPerGiorno(LocalDate data);

    // Restituisce tutti gli eventi in una settimana specifica associati a un corso specifico
    List<Evento> ottieniEventiPerSettimanaECorso(int numeroSettimana, Corso corso);

    // Salva l'evento corrente
    void salvaEventoCorrente();

    // Elimina l'evento corrente
    void eliminaEventoCorrente();
}
