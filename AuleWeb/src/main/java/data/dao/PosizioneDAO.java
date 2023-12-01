/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package data.dao;

import data.domain.Aula;
import data.domain.Posizione;
import java.util.List;

/**
 *
 * @author emanu
 */
public interface PosizioneDAO {
    
    // Crea e restituisce una nuova posizione
    Posizione creaNuovaPosizione();

    // Restituisce la posizione corrente
    Posizione ottieniPosizioneCorrente();

    // Restituisce la posizione associata a un'aula specifica
    Posizione ottieniPosizionePerAula(Aula aula);

    // Restituisce tutte le posizioni disponibili
    List<Posizione> ottieniTutteLePosizioni();

    // Salva la posizione corrente
    void salvaPosizioneCorrente();  
}
