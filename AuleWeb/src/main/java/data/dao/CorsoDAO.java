/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package data.dao;

import data.domain.Corso;
import framework.data.DataException;

/**
 *
 * @author emanu
 */
public interface CorsoDAO {
    
     Corso createCorso();

    // Restituisce il corso corrente
    Corso getCorso(int corso_key) throws DataException;

    // Salva il corso corrente
    void storeCorso(Corso corso) throws DataException;
    
    Corso getCorsoByNome(String nome) throws DataException;
}
