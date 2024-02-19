package data.dao;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import data.domain.Aula;
import data.domain.Corso;
import data.domain.Dipartimento;
import data.domain.Evento;
import framework.data.DataException;
import java.sql.Time;

public interface EventoDAO {

  // Crea e restituisce un nuovo evento
  Evento createEvento() throws DataException;

  // Restituisce l'evento corrente
  Evento getEventoByID(int evento_key) throws DataException;

  List<Evento> getAllEventi() throws DataException;

  List<Evento> getEventiByAula(Aula aula) throws DataException;

  // Restituisce tutti gli eventi di un aula in una settimana specifica
  List<Evento> getEventiSettimanaliByAula(Aula aula, LocalDate date, int dip_key) throws DataException;

  List<Evento> getEventiByDay(Date data, int dip_key) throws DataException;

  // Restituisce tutti gli eventi associati a un corso specifico
  List<Evento> getEventiSettimanaliByCorso(Corso corso, LocalDate data, int dip_key) throws DataException;

  // Restituisce tutti gli eventi delle prossime 3 ore
  List<Evento> getEventiByTreOre(Dipartimento dipartimento) throws DataException;

  // Restituisce tutte le istanze di un evento ricorrente dato un ID master
  List<Evento> getEventiRicorrentiByIDMaster(Integer IDMaster) throws DataException;
  
  List<Evento> getEventiByOrario(Time oraInizio, Time oraFine) throws DataException;

  // Salva l'evento corrente
  void storeEvento(Evento evento) throws DataException;

  void deleteEvento(Evento evento) throws DataException;

  void deleteEventiRicorrenti(Evento evento) throws DataException;

}
