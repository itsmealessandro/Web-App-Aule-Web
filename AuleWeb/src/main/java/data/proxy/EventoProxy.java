/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data.proxy;

import data.domainImpl.AulaImpl;
import data.domainImpl.CorsoImpl;
import data.domainImpl.EventoImpl;
import data.domainImpl.ResponsabileImpl;
import data.domainImpl.RicorrenzaImpl;
import data.domainImpl.TipologiaEventoImpl;
import framework.data.DataItemProxy;
import framework.data.DataLayer;
import java.sql.Date;
import java.sql.Time;

/**
 *
 * @author Administrator
 */
public class EventoProxy extends EventoImpl implements DataItemProxy {
    protected boolean modified;
     protected DataLayer dataLayer;
     
     public EventoProxy(DataLayer d) {
        super();
        this.dataLayer = d;
        this.modified = false;
    }    
@Override
    public void setId(int id) {
        super.setId(id);
        this.modified = true;
    }

    @Override
    public void setNome(String nome) {
        super.setNome(nome);
        this.modified = true;
    }

    @Override
    public void setOraInizio(Time oraInizio) {
        super.setOraInizio(oraInizio);
        this.modified = true;
    }

    @Override
    public void setOraFine(Time oraFine) {
        super.setOraFine(oraFine);
        this.modified = true;
    }

    @Override
    public void setDescrizione(String descrizione) {
        super.setDescrizione(descrizione);
        this.modified = true;
    }

    @Override
    public void setAula(AulaImpl aula) {
        super.setAula(aula);
        this.modified = true;
    }

    @Override
    public void setRicorrenza(RicorrenzaImpl ricorrenza) {
        super.setRicorrenza(ricorrenza);
        this.modified = true;
    }

    @Override
    public void setDataInizio(Date dataInizio) {
        super.setDataInizio(dataInizio);
        this.modified = true;
    }
    
    @Override
    public void setDataFine(Date dataFine) {
        super.setDataFine(dataFine);
        this.modified = true;
    }

    @Override
    public void setResponsabile(ResponsabileImpl responsabile) {
        super.setResponsabile(responsabile);
        this.modified = true;
    }

    @Override
    public void setCorso(CorsoImpl corso) {
        super.setCorso(corso);
        this.modified = true;
    }

    @Override
    public void setTipologiaEvento(TipologiaEventoImpl tipologiaEvento) {
        super.setTipologiaEvento(tipologiaEvento);
        this.modified = true;
    }

//METODI DEL PROXY
    //PROXY-ONLY METHODS
    @Override
    public void setModified(boolean dirty) {
        this.modified = dirty;
    }

    @Override
    public boolean isModified() {
        return modified;
    }
}
