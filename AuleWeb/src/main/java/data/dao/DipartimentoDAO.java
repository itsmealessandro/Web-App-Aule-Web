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
    Dipartimento creaNuovoGruppo();

    // Restituisce il gruppo corrente
    Dipartimento ottieniGruppoCorrente();

    // Restituisce tutti i gruppi disponibili
    List<Dipartimento> ottieniTuttiIGruppi();

    // Salva il gruppo corrente
    void salvaGruppoCorrente();

    // Elimina il gruppo corrente
    void eliminaGruppoCorrente();    
}
