package data.proxy;

import data.domainImpl.AulaImpl;
import data.domainImpl.CorsoImpl;
import data.domainImpl.EventoImpl;
import data.domainImpl.ResponsabileImpl;
import data.domainImpl.Ricorrenza;
import data.domainImpl.TipologiaEvento;
import framework.data.DataItemProxy;
import framework.data.DataLayer;
import java.sql.Date;
import java.sql.Time;

public class EventoProxy extends EventoImpl implements DataItemProxy {

    protected boolean modified;
    protected int aula_key = 0;
    
    // TODO da inserire tutte le chiavi degli oggetti conessi a evento
    
    
    protected DataLayer dataLayer;

    public EventoProxy(DataLayer d) {
        super();
        this.dataLayer = d;
        this.modified = false;
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
    public void setRicorrenza(Ricorrenza ricorrenza) {
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
    public void setTipologiaEvento(TipologiaEvento tipologiaEvento) {
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
