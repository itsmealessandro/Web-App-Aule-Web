package data.dao;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import data.domain.Aula;
import data.domain.Corso;
import data.domain.Dipartimento;
import data.domain.Evento;
import framework.data.DataException;

public interface EventoDAO {

  // Crea e restituisce un nuovo evento
  Evento createEvento() throws DataException;

  // Salva l'evento corrente
  void storeEvento(Evento evento) throws DataException;

  // Restituisce l'evento corrente
  Evento getEventoByID(int evento_key) throws DataException;

  List<Evento> getEventiByAula(Aula aula) throws DataException;

  // Restituisce tutti gli eventi di un aula in una settimana specifica
  List<Evento> getEventiSettimanaliByAula(Aula aula, LocalDate date, int dip_key) throws DataException;

  List<Evento> getEventiByDay(Date data, int dip_key) throws DataException;

  // Restituisce tutti gli eventi associati a un corso specifico
  List<Evento> getEventiSettimanaliByCorso(Corso corso, LocalDate data, int dip_key) throws DataException;

  public List<Evento> getEventiByNome(String nome) throws DataException;
  
  List<Evento> getEventiByTreOre(Dipartimento dipartimento) throws DataException;

}
