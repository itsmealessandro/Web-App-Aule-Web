package data.domain;

import data.domainImpl.Ricorrenza;
import data.domainImpl.TipologiaEvento;
import framework.data.DataItem;
import java.sql.Date;
import java.sql.Time;

public interface Evento extends DataItem<Integer> {

    public Integer getIDMaster();

    public void setIDMaster(Integer IDMaster);

    public String getNome();

    public void setNome(String nome);

    public Time getOraInizio();

    public void setOraInizio(Time oraInizio);

    public Time getOraFine();

    public void setOraFine(Time oraFine);

    public String getDescrizione();

    public void setDescrizione(String descrizione);

    public Aula getAula();

    public void setAula(Aula aula);

    public Ricorrenza getRicorrenza();

    public void setRicorrenza(Ricorrenza ricorrenza);

    public Responsabile getResponsabile();

    public void setResponsabile(Responsabile responsabile);

    public Corso getCorso();

    public void setCorso(Corso corso);

    public TipologiaEvento getTipologiaEvento();

    public void setTipologiaEvento(TipologiaEvento tipologiaEvento);

    public Date getData();

    public void setData(Date data);

    public Date getDataFineRicorrenza();

    public void setDataFineRicorrenza(Date dataFineRicorrenza);

    
    public boolean isRicorrente();

    
    public void setRicorrente(boolean isRicorrente);
}
