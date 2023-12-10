package data.domain;

import data.domainImpl.AulaImpl;
import data.domainImpl.CorsoImpl;
import data.domainImpl.ResponsabileImpl;
import data.domainImpl.Ricorrenza;
import data.domainImpl.TipologiaEventoImpl;
import framework.data.DataItem;
import java.sql.Date;
import java.sql.Time;

public interface Evento extends DataItem<Integer>{

    public String getNome();

    public void setNome(String nome);

    public Time getOraInizio();

    public void setOraInizio(Time oraInizio);

    public Time getOraFine();

    public void setOraFine(Time oraFine);

    public String getDescrizione();

    public void setDescrizione(String descrizione);

    public AulaImpl getAula();

    public void setAula(AulaImpl aula);

    public Ricorrenza getRicorrenza();

    public void setRicorrenza(Ricorrenza ricorrenza);

    public Date getDataInizio();

    public void setDataInizio(Date dataInizio);

    public Date getDataFine() ;

    public void setDataFine(Date dataFine);

    public ResponsabileImpl getResponsabile();

    public void setResponsabile(ResponsabileImpl responsabile);

    public CorsoImpl getCorso();

    public void setCorso(CorsoImpl corso);

    public TipologiaEventoImpl getTipologiaEvento() ;

    public void setTipologiaEvento(TipologiaEventoImpl tipologiaEvento);
    }
