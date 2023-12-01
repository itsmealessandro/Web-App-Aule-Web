/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package data.dao;

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
    Corso getCorsoCorrente();

    // Restituisce il corso associato a un evento specifico
    Corso getCorsoPerEvento(Evento evento);

    // Restituisce il corso associato a un nome specifico
    Corso getCorsoPerNome(String nomeCorso);

    // Restituisce tutti i corsi disponibili
    List<Corso> getTuttiICorsi();

    // Salva il corso corrente
    void salvaCorsoCorrente();
}
