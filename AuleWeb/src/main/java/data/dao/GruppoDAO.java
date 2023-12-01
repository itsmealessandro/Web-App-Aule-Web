/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package data.dao;

import data.domain.Gruppo;
import java.util.List;

/**
 *
 * @author emanu
 */
public interface GruppoDAO {
    
    // Crea e restituisce un nuovo gruppo
    Gruppo creaNuovoGruppo();

    // Restituisce il gruppo corrente
    Gruppo ottieniGruppoCorrente();

    // Restituisce tutti i gruppi disponibili
    List<Gruppo> ottieniTuttiIGruppi();

    // Salva il gruppo corrente
    void salvaGruppoCorrente();

    // Elimina il gruppo corrente
    void eliminaGruppoCorrente();
}
