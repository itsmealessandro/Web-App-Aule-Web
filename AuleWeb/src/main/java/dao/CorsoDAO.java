/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

import data.domain.Corso;
import data.domain.Evento;
import java.util.List;

/**
 *
 * @author emanu
 */
public interface CorsoDAO {
    
    // Crea e restituisce un nuovo corso
    Corso creaNuovoCorso();

    // Restituisce il corso corrente
    Corso ottieniCorsoCorrente();

    // Restituisce il corso associato a un evento specifico
    Corso ottieniCorsoPerEvento(Evento evento);

    // Restituisce il corso associato a un nome specifico
    Corso ottieniCorsoPerNome(String nomeCorso);

    // Restituisce tutti i corsi disponibili
    List<Corso> ottieniTuttiICorsi();

    // Salva il corso corrente
    void salvaCorsoCorrente();
}
