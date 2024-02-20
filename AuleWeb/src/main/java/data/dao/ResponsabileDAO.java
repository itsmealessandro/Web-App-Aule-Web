package data.dao;

import java.util.List;

import data.domain.Responsabile;
import framework.data.DataException;

public interface ResponsabileDAO {

  // Crea e restituisce un nuovo responsabile
  Responsabile createResponsabile();

  // Restituisce il responsabile corrente
  Responsabile getResponsabile(int resp_key) throws DataException;

  // Restituice il responsabile con quella email
  Responsabile getResponsabileByEmail(String email) throws DataException;

  // Restituisce tutti i responsabili disponibili
  List<Responsabile> getAllResponsabili() throws DataException;

  // Salva il responsabile corrente
  void storeResponsabile(Responsabile r) throws DataException;
}
