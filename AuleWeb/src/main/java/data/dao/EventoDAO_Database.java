package data.dao;

import data.domain.Aula;
import data.domain.Corso;
import data.domain.Dipartimento;
import data.domain.Evento;
import data.domainImpl.Ricorrenza;
import data.proxy.EventoProxy;
import framework.data.DAO;
import framework.data.DataException;
import framework.data.DataItemProxy;
import framework.data.DataLayer;
import framework.data.OptimisticLockException;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

public class EventoDAO_Database extends DAO implements EventoDAO {

  private PreparedStatement iEvento, uEvento, sEventoByID, sEventoByAula, sEventiByDay, sEventiByCorso,
      sEventiRicorrenti, sEventiByTreOre;

  public EventoDAO_Database(DataLayer d) {
    super(d);
  }

  @Override
  public void init() throws DataException {
    try {
      super.init();

      sEventoByID = connection.prepareStatement("SELECT * FROM Evento WHERE ID=?");
      sEventoByAula = connection.prepareStatement("SELECT * FROM Evento WHERE IDAula=?");
      sEventiByDay = connection.prepareStatement(
          " SELECT e.* FROM Evento e JOIN Aula a ON e.IDAula = a.ID JOIN Dipartimento d ON a.IDDipartimento = d.ID WHERE d.ID = ? AND e.dataInizio <= ? AND e.dataFine >= ?");
      sEventiByCorso = connection.prepareStatement(
          " SELECT e.* FROM Evento e JOIN Aula a ON e.IDAula = a.ID JOIN Corso c ON e.IDCorso = c.ID " +
              " WHERE c.ID = ? AND a.IDDipartimento = ? " +
              " AND e.Data BETWEEN ? AND ?");
      sEventiRicorrenti = connection
          .prepareStatement("SELECT ID AS eventoID FROM evento WHERE nome=? AND responsabileID=? order by giorno");

      iEvento = connection.prepareStatement(
          "INSERT INTO `Evento` (`nome`, `oraInizio`, `oraFine`, `descrizione`, `IDAula`, `ricorrenza`," +
              " `IDResponsabile`, `IDCorso`, `tipologiaEvento`, `version`, `IDMaster`, `Data`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);",
          Statement.RETURN_GENERATED_KEYS);

      uEvento = connection.prepareStatement(
          "UPDATE `Evento` SET `nome` = ?,`oraInizio` = ?, `oraFine` = ?, `descrizione` = ?,`IDAula` = ?, " +
              "`ricorrenza` = ?, `IDResponsabile` = ?, `IDCorso` = ?, `tipologiaEvento` = ?, `version` = ?,"
              + "`IDMaster` = ?, `Data` = ? WHERE `ID` = ?;");
       sEventiByTreOre = connection.prepareStatement("SELECT e.*\n" +
            "FROM Evento e\n" +
                " JOIN Aula a ON e.IDAula = a.ID\n" +
                " WHERE e.oraInizio BETWEEN CURRENT_TIME() AND ADDTIME(CURRENT_TIME(), '03:00:00')\n" +
                " AND e.Data = CURDATE()\n" +
                " AND a.IDDipartimento = ?;" );
    } catch (SQLException ex) {
      throw new DataException("Error initializing data layer", ex);
    }
  }

  @Override
  public void destroy() throws DataException {
    try {
      sEventoByID.close();
      sEventoByAula.close();
      sEventiByDay.close();
      sEventiByCorso.close();
      sEventiRicorrenti.close();
      iEvento.close();
      uEvento.close();
      sEventiByTreOre.close();

    } catch (SQLException ex) {
      // TODO gestire l'eccezione
    }
    super.destroy();
  }

  @Override
  public Evento createEvento() {
    return new EventoProxy(getDataLayer());
  }

  private EventoProxy createEvento(ResultSet rs) throws DataException {
    try {
      EventoProxy e = (EventoProxy) createEvento();
      e.setKey(rs.getInt("ID"));
      e.setNome(rs.getString("nome"));
      e.setOraInizio(rs.getTime("oraInizio"));
      e.setOraFine(rs.getTime("oraFine"));
      e.setDescrizione(rs.getString("descrizione"));
      e.setData(rs.getDate("data"));
      e.setAulaKey(rs.getInt("IDAula"));
      e.setResponsabileKey(rs.getInt("IDResponsabile"));
      e.setCorsoKey(rs.getInt("IDCorso"));
      e.setRicorrenza(Ricorrenza.valueOf(rs.getObject("ricorrenza").toString()));
      e.setVersion(rs.getLong("version"));

      return e;
    } catch (SQLException ex) {
      throw new DataException("Unable to create Evento object from ResultSet", ex);
    }

  }

  @Override
  public Evento getEventoByID(int event_key) throws DataException {
    Evento e = null;

    if (dataLayer.getCache().has(Evento.class, event_key)) {

      e = dataLayer.getCache().get(Evento.class, event_key);
    } else {
      try {
        sEventoByID.setInt(1, event_key);
        try (ResultSet rs = sEventoByID.executeQuery()) {
          if (rs.next()) {

            e = createEvento(rs);

            dataLayer.getCache().add(Evento.class, e);
          }
        }
      } catch (SQLException ex) {
        throw new DataException("Unable to load Evento by ID", ex);
      }
    }
    return e;
  }

  @Override
  public List<Evento> getEventiByAula(Aula aula) throws DataException {
    List<Evento> listaEventi = new ArrayList<>();

    try {
      sEventoByAula.setInt(1, aula.getKey());
      try (ResultSet resultSet = sEventoByAula.executeQuery()) {
        while (resultSet.next()) {
          listaEventi.add((Evento) getEventoByID(resultSet.getInt("ID")));
        }
      }
    } catch (SQLException sqlException) {
      throw new DataException("Unable to load Eventi from Aula");
    }

    return listaEventi;

  }

  @Override
  public List<Evento> getEventiByDay(Date data, int dip_key) throws DataException {

    List<Evento> listaEventi = new ArrayList<>();
    try {
      sEventiByDay.setInt(1, dip_key);
      sEventiByDay.setDate(2, data);
      sEventiByDay.setDate(3, data);

      try (ResultSet rSet = sEventiByDay.executeQuery()) {
        while (rSet.next()) {
          listaEventi.add((Evento) getEventoByID(rSet.getInt("ID")));
        }
      }
    } catch (SQLException ex) {
      throw new DataException("Unable to load Eventi by Date", ex);
    }
    return listaEventi;
  }

  @Override
  public List<Evento> getEventiSettimanaliByCorso(Corso corso, LocalDate date, int dip_key) throws DataException {

    List<Evento> listaEventi = new ArrayList<>();
    LocalDate inizioSettimana = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
    LocalDate fineSettimana = date.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

    Date inizioSettimanaSql = Date.valueOf(inizioSettimana);
    Date fineSettimanaSql = Date.valueOf(fineSettimana);

    try {
      sEventiByCorso.setInt(1, corso.getKey());
      sEventiByCorso.setInt(2, dip_key);
      sEventiByCorso.setDate(3, inizioSettimanaSql);
      sEventiByCorso.setDate(4, fineSettimanaSql);

      try (ResultSet rs = sEventiByCorso.executeQuery()) {
        while (rs.next()) {
          listaEventi.add(getEventoByID(rs.getInt("ID")));
        }
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
      throw new DataException("Unable to load Eventi by Day");
    }

    return listaEventi;
  }
  
  @Override
    public List<Evento> getEventiByTreOre(Dipartimento dipartimento) throws DataException {
        List<Evento> listaEventi = new ArrayList<>();
        try {
            sEventiByTreOre.setInt(1, dipartimento.getKey());
            try (ResultSet resultSet = sEventiByTreOre.executeQuery()) {
                while (resultSet.next()) {
                    listaEventi.add((Evento) getEventoByID(resultSet.getInt("ID")));
                }
            }
        } catch (SQLException sqlException) {
            throw new DataException("Unable to load Eventi from Aula");
        }
        return listaEventi;
        }

  // TODO DA RIFARE
  @Override
  public void storeEvento(Evento e) throws DataException {

    try {
      if (e.getKey() != null && e.getKey() > 0) { // update

        if (e instanceof DataItemProxy && !((DataItemProxy) e).isModified()) {
          return;
        }
        uEvento.setString(1, e.getNome());
        uEvento.setTime(2, e.getOraInizio());
        uEvento.setTime(3, e.getOraFine());
        uEvento.setString(4, e.getDescrizione());
        uEvento.setInt(5, e.getAula().getKey()); // Assumi che l'oggetto AulaImpl possa essere convertito in un formato
                                                 // adatto per il database
        uEvento.setObject(6, e.getRicorrenza().toString());
        uEvento.setInt(9, e.getResponsabile().getKey()); // Assumi che l'oggetto ResponsabileImpl possa essere
                                                         // convertito in un formato adatto per il database
        uEvento.setInt(10, e.getCorso().getKey()); // Assumi che l'oggetto CorsoImpl possa essere convertito in un
                                                   // formato adatto per il database
        uEvento.setString(11, e.getTipologiaEvento().toString());
        // TipologiaEventoImpl possa essere convertito in un formato adatto per il
        // database
        uEvento.setLong(12, e.getVersion());
        uEvento.setInt(13, e.getKey());
        uEvento.setLong(14, e.getVersion());

        if (uEvento.executeUpdate() == 0) {
          throw new OptimisticLockException(e);
        } else {
          e.setVersion(e.getVersion() + 1);
        }
      } else { // insert
        iEvento.setString(1, e.getNome());
        iEvento.setTime(2, e.getOraInizio());
        iEvento.setTime(3, e.getOraFine());
        iEvento.setString(4, e.getDescrizione());
        iEvento.setInt(5, e.getAula().getKey()); // Assumi che l'oggetto AulaImpl possa essere convertito in un formato
                                                 // adatto per il database
        iEvento.setObject(6, e.getRicorrenza().toString()); // Assumi che l'oggetto
        // Ricorrenza possa essere convertito in un formato adatto per il database
        iEvento.setInt(9, e.getResponsabile().getKey()); // Assumi che l'oggetto ResponsabileImpl possa essere
                                                         // convertito in un formato adatto per il database
        iEvento.setInt(10, e.getCorso().getKey()); // Assumi che l'oggetto CorsoImpl possa essere convertito in un
                                                   // formato adatto per il database
        iEvento.setString(11, e.getTipologiaEvento().toString()); // Assumi che l'oggetto
        // TipologiaEventoImpl possa essere convertito in un formato adatto per il
        // database

        if (iEvento.executeUpdate() == 1) {

          try (ResultSet keys = iEvento.getGeneratedKeys()) {

            if (keys.next()) {

              int key = keys.getInt(1);

              e.setKey(key);

              dataLayer.getCache().add(Evento.class, e);
            }
          }
        }
      }

      if (e instanceof DataItemProxy) {
        ((DataItemProxy) e).setModified(false);
      }
    } catch (SQLException | OptimisticLockException ex) {
      throw new DataException("Unable to store Evento", ex);
    }
  }

  @Override
  public List<Evento> getEventiByNome(String nome) throws DataException {
    List<Evento> result = new ArrayList();

    try {
      sEventiRicorrenti.setString(1, nome);
      ResultSet rs = sEventiRicorrenti.executeQuery();

      while (rs.next()) {
        result.add((Evento) getEventoByID(rs.getInt("eventoID")));
      }
      return result;
    } catch (SQLException ex) {
      throw new DataException("Unable to load eventi", ex);
    }
  }

}
