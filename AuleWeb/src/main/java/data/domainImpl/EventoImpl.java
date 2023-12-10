package data.domainImpl;

import data.domain.Evento;
import framework.data.DataItemImpl;
import java.sql.Time;
import java.sql.Date;

public class EventoImpl extends DataItemImpl<Integer> implements Evento {
    
    private String nome;
    private Time oraInizio;
    private Time oraFine;
    private String descrizione; //Text 
    private AulaImpl aula;
    private Ricorrenza ricorrenza; //Enumerazione
    private Date dataInizio;
    private Date dataFine; 
    private ResponsabileImpl responsabile;
    private CorsoImpl corso; 
    private TipologiaEventoImpl tipologiaEvento;

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
    public AulaImpl getAula() {
        return aula;
    }

    @Override
    public void setAula(AulaImpl aula) {
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
    public Date getDataInizio() {
        return dataInizio;
    }

    @Override
    public void setDataInizio(Date dataInizio) {
        this.dataInizio = dataInizio;
    }

    @Override
    public Date getDataFine() {
        return dataFine;
    }

    @Override
    public void setDataFine(Date dataFine) {
        this.dataFine = dataFine;
    }

    @Override
    public ResponsabileImpl getResponsabile() {
        return responsabile;
    }

    @Override
    public void setResponsabile(ResponsabileImpl responsabile) {
        this.responsabile = responsabile;
    }

    @Override
    public CorsoImpl getCorso() {
        return corso;
    }

    @Override
    public void setCorso(CorsoImpl corso) {
        this.corso = corso;
    }

    @Override
    public TipologiaEventoImpl getTipologiaEvento() {
        return tipologiaEvento;
    }

    @Override
    public void setTipologiaEvento(TipologiaEventoImpl tipologiaEvento) {
        this.tipologiaEvento = tipologiaEvento;
    }
    
}
