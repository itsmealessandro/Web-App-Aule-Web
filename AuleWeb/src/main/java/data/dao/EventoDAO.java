package data.dao;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import data.domain.Aula;
import data.domain.Corso;
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

  List<Evento> getEventiByDay(Date data, int dip_key) throws DataException;

  // Restituisce tutti gli eventi associati a un corso specifico
  void deleteEvento(Evento evento) throws DataException;

  List<Evento> getEventiSettimanaliByCorso(Corso corso, LocalDate data, int dip_key) throws DataException;

}