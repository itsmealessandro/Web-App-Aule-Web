/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package data.dao;

import data.domain.Attrezzatura;
import data.domain.Aula;
import data.domain.Dipartimento;
import data.domain.Evento;
import framework.data.DataException;
import java.util.List;

/**
 *
 * @author emanu
 */
public interface AulaDAO {

    // Crea e restituisce una nuova aula
    Aula creaNuovaAula() throws DataException;

    // Restituisce l'aula corrente
    Aula getAulaCorrente();

    // Restituisce tutte le aule disponibili
    List<Aula> getTutteLeAule();

    // Restituisce tutte le aule che contengono una specifica attrezzatura
    List<Aula> getAulePerAttrezzatura(Attrezzatura attrezzatura);

    // Restituisce tutte le aule associate a un evento specifico
    List<Aula> getAulePerEvento(Evento evento);

    // Crea e restituisce un nuovo dipartimento
    Dipartimento creaNuovoDipartimento() throws DataException;

    // Restituisce il dipartimento corrente
    Dipartimento getDipartimentoCorrente();

    // Restituisce tutti i dipartimenti disponibili
    List<Dipartimento> getTuttiIDipartimenti();

    // Restituisce l'aula associata a un dipartimento specifico
    Aula getAulaPerDipartimento(Dipartimento dipartimento);

    // Salva il dipartimento corrente
    void salvaDipartimentoCorrente();

    // Elimina il dipartimento corrente
    void eliminaDipartimentoCorrente();
}

