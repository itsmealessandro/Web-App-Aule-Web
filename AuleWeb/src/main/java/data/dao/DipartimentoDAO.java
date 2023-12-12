/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package data.dao;

import data.domain.Dipartimento;
import framework.data.DataException;

/**
 *
 * @author emanu
 */
public interface DipartimentoDAO {
    
   // Crea e restituisce un nuovo dipartimento
    Dipartimento createDipartimento();

    // Restituisce dipartimento corrente
    Dipartimento getDipartimento(int dipartimento_key) throws DataException;

    // Salva dipartimento corrente
    void storeAmministratore(Dipartimento dipartimento) throws DataException;
    
}
