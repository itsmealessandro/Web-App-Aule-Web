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
    
    // Crea e restituisce un nuovo dipartimento
    Dipartimento creaNuovoDipartimento();

    // Restituisce il dipartimento corrente
    Dipartimento getDipartimentoCorrente();

    // Restituisce tutti i dipartimento disponibili
    List<Dipartimento> getTuttiIDipartimenti();

    // Salva il dipartimento corrente
    void salvaDipartimentoCorrente();

    // Elimina il dipartimento corrente
    void eliminaDipartimentoCorrente();    
    
    
}
