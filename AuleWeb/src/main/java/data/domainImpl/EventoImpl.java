package data.domainImpl;

import data.domain.Aula;
import data.domain.Corso;
import data.domain.Evento;
import data.domain.Responsabile;
import framework.data.DataItemImpl;
import java.sql.Time;
import java.sql.Date;

public class EventoImpl extends DataItemImpl<Integer> implements Evento {

    private int IDMaster;
    private String nome;
    private Time oraInizio;
    private Time oraFine;
    private String descrizione;
    private Aula aula;
    private Ricorrenza ricorrenza;
    private Responsabile responsabile;
    private Corso corso;
    private TipologiaEvento tipologiaEvento;
    private Date data;

    public int getIDMaster() {
        return IDMaster;
    }

    public void setIDMaster(int iDMaster) {
        IDMaster = iDMaster;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public Time getOraInizio() {
        return oraInizio;
    }

    @Override
    public void setOraInizio(Time oraInizio) {
        this.oraInizio = oraInizio;
    }

    @Override
    public Time getOraFine() {
        return oraFine;
    }

    @Override
    public void setOraFine(Time oraFine) {
        this.oraFine = oraFine;
    }

    @Override
    public String getDescrizione() {
        return descrizione;
    }

    @Override
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    @Override
    public Aula getAula() {
        return aula;
    }

    @Override
    public void setAula(Aula aula) {
        this.aula = aula;
    }

    @Override
    public Ricorrenza getRicorrenza() {
        return ricorrenza;
    }

    @Override
    public void setRicorrenza(Ricorrenza ricorrenza) {
        this.ricorrenza = ricorrenza;
    }

    @Override
    public Date getData() {
        return data;
    }

    @Override
    public void setData(Date data) {
        this.data = data;
    }

    @Override
    public Responsabile getResponsabile() {
        return responsabile;
    }

    @Override
    public void setResponsabile(Responsabile responsabile) {
        this.responsabile = responsabile;
    }

    @Override
    public Corso getCorso() {
        return corso;
    }

    @Override
    public void setCorso(Corso corso) {
        this.corso = corso;
    }

    @Override
    public TipologiaEvento getTipologiaEvento() {
        return tipologiaEvento;
    }

    @Override
    public void setTipologiaEvento(TipologiaEvento tipologiaEvento) {
        this.tipologiaEvento = tipologiaEvento;
    }

}
