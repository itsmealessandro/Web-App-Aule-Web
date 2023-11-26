/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data.domain;

import java.sql.Time;
import java.sql.Date;

/**
 *
 * @author user
 */
public class Evento {
    
    private int id;
    private String nome;
    private Time oraInizio;
    private Time oraFine;
    private String descrizione; //Text 
    private Aula aula;
    private Ricorrente ricorrente; //Enumerazione
    private Date dataInizio;
    private Date dataFine; 
    private Responsabile responsabile;
    private Corso corso; 
    private TipologiaEvento tipologiaEvento;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Time getOraInizio() {
        return oraInizio;
    }

    public void setOraInizio(Time oraInizio) {
        this.oraInizio = oraInizio;
    }

    public Time getOraFine() {
        return oraFine;
    }

    public void setOraFine(Time oraFine) {
        this.oraFine = oraFine;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Aula getAula() {
        return aula;
    }

    public void setAula(Aula aula) {
        this.aula = aula;
    }

    public Ricorrente getRicorrente() {
        return ricorrente;
    }

    public void setRicorrente(Ricorrente ricorrente) {
        this.ricorrente = ricorrente;
    }

    public Date getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(Date dataInizio) {
        this.dataInizio = dataInizio;
    }

    public Date getDataFine() {
        return dataFine;
    }

    public void setDataFine(Date dataFine) {
        this.dataFine = dataFine;
    }

    public Responsabile getResponsabile() {
        return responsabile;
    }

    public void setResponsabile(Responsabile responsabile) {
        this.responsabile = responsabile;
    }

    public Corso getCorso() {
        return corso;
    }

    public void setCorso(Corso corso) {
        this.corso = corso;
    }

    public TipologiaEvento getTipologiaEvento() {
        return tipologiaEvento;
    }

    public void setTipologiaEvento(TipologiaEvento tipologiaEvento) {
        this.tipologiaEvento = tipologiaEvento;
    }
    
    
}
