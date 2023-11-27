/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data.domain;

import data.domainImpl.AulaImpl;
import data.domainImpl.CorsoImpl;
import data.domainImpl.ResponsabileImpl;
import data.domainImpl.RicorrenzaImpl;
import data.domainImpl.TipologiaEventoImpl;
import java.sql.Date;
import java.sql.Time;

/**
 *
 * @author Administrator
 */
public interface Evento {
    public int getId();

    public void setId(int id);

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

    public RicorrenzaImpl getRicorrenza();

    public void setRicorrenza(RicorrenzaImpl ricorrenza);

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
