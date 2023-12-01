/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package data.dao;

import data.domain.Dipartimento;
import java.util.List;

/**
 *
 * @author emanu
 */
public interface DipartimentoDAO {
    
    // Crea e restituisce un nuovo gruppo
    Dipartimento creaNuovoDipartimento();

    // Restituisce il gruppo corrente
    Dipartimento ottieniDipartimentoCorrente();

    // Restituisce tutti i gruppi disponibili
    List<Dipartimento> ottieniTuttiIDipartimenti();

    // Salva il gruppo corrente
    void salvaDipartimentoCorrente();

    // Elimina il gruppo corrente
    void eliminaDipartimentoCorrente();    
}
