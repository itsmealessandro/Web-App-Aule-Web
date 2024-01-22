package data.dao;

import data.domain.Evento;
import data.domain.Responsabile;
import framework.data.DataException;
import java.util.List;

public interface ResponsabileDAO {

  // Crea e restituisce un nuovo responsabile
  Responsabile createResponsabile();

  // Restituisce il responsabile corrente
  Responsabile getResponsabile(int resp_key) throws DataException;

  // Restituisce il responsabile associato a un evento specifico
  Responsabile getResponsabileByEvento(Evento evento) throws DataException;

  // Restituice il responsabile con quella email
  Responsabile getResponsabileByEmail(String email) throws DataException;

  // Restituisce tutti i responsabili disponibili
  List<Responsabile> getAllResponsabili() throws DataException;

  // Salva il responsabile corrente
  void storeResponsabile(Responsabile r) throws DataException;
}
