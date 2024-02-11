package data.proxy;

import data.dao.AulaDAO;
import data.dao.CorsoDAO;
import data.dao.ResponsabileDAO;
import data.domain.Aula;
import data.domain.Corso;
import data.domain.Responsabile;
import data.domainImpl.EventoImpl;
import data.domainImpl.Ricorrenza;
import data.domainImpl.TipologiaEvento;
import framework.data.DataException;
import framework.data.DataItemProxy;
import framework.data.DataLayer;
import java.sql.Date;
import java.sql.Time;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EventoProxy extends EventoImpl implements DataItemProxy {

    protected boolean modified;
    protected int aula_key = 0;
    protected int responsabile_key = 0;
    protected int corso_key = 0;
    protected boolean isRicorrente;

    protected DataLayer dataLayer;

    public EventoProxy(DataLayer d) {
        super();
        this.dataLayer = d;
        this.modified = false;
        this.aula_key = 0;
        this.responsabile_key = 0;
        this.corso_key = 0;
        this.isRicorrente = false;
    }

    @Override
    public void setKey(Integer key) {
        super.setKey(key);
        this.modified = true;
    }

    @Override
    public void setIDMaster(Integer IDMaster) {
        super.setIDMaster(IDMaster);
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
    public void setRicorrenza(Ricorrenza ricorrenza) {
        super.setRicorrenza(ricorrenza);
        this.modified = true;
    }

    @Override
    public void setTipologiaEvento(TipologiaEvento tipologiaEvento) {
        super.setTipologiaEvento(tipologiaEvento);
        this.modified = true;
    }

    public void setData(Date giorno) {
        super.setData(giorno);
        this.modified = true;
    }

    public void setDataFineRicorrenza(Date dataFineRicorrenza) {
        super.setDataFineRicorrenza(dataFineRicorrenza);
        this.modified = true;
    }

    // Aggiunto getter e setter per il campo isRicorrente
    public boolean isRicorrente() {
        return isRicorrente;
    }

    public void setRicorrente(boolean isRicorrente) {
        this.isRicorrente = isRicorrente;
        this.modified = true;
    }

    @Override
    public Aula getAula() {
        if (super.getAula() == null && aula_key > 0) {
            try {
                super.setAula(
                        ((AulaDAO) dataLayer.getDAO(Aula.class)).getAulaByID(aula_key));
            } catch (DataException ex) {
                Logger.getLogger(EventoProxy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return super.getAula();
    }

    @Override
    public void setAula(Aula aula) {
        super.setAula(aula);
        this.aula_key = aula.getKey();
        this.modified = true;
    }

    @Override
    public Corso getCorso() {
        if (super.getCorso() == null && corso_key > 0) {
            try {
                super.setCorso(
                        ((CorsoDAO) dataLayer.getDAO(Corso.class)).getCorso(corso_key));
            } catch (DataException ex) {
                Logger.getLogger(EventoProxy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return super.getCorso();
    }

    @Override
    public void setCorso(Corso corso) {
        super.setCorso(corso);
        this.corso_key = corso.getKey();
        this.modified = true;
    }

    @Override
    public Responsabile getResponsabile() {
        if (super.getResponsabile() == null && responsabile_key > 0) {
            try {
                super.setResponsabile(
                        ((ResponsabileDAO) dataLayer.getDAO(Responsabile.class)).getResponsabile(responsabile_key));
            } catch (DataException ex) {
                Logger.getLogger(EventoProxy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return super.getResponsabile();
    }

    @Override
    public void setResponsabile(Responsabile responsabile) {
        super.setResponsabile(responsabile);
        this.responsabile_key = responsabile.getKey();
        this.modified = true;
    }

    public void setResponsabileKey(int responsabile_key) {
        this.responsabile_key = responsabile_key;
        super.setResponsabile(null);
    }

    public void setAulaKey(int aula_key) {
        this.aula_key = aula_key;
        super.setAula(null);
    }

    public void setCorsoKey(int corso_key) {
        this.corso_key = corso_key;
        super.setCorso(null);
    }

    // METODI DEL PROXY
    // PROXY-ONLY METHODS
    @Override
    public void setModified(boolean dirty) {
        this.modified = dirty;
    }

    @Override
    public boolean isModified() {
        return modified;
    }

}
