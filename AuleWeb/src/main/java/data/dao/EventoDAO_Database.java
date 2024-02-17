package data.dao;

import data.domain.Aula;
import data.domain.Corso;
import data.domain.Evento;
import data.domainImpl.Ricorrenza;
import data.domainImpl.TipologiaEvento;
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
            sEventiRicorrenti, sAllEventi, dEvento, sEventoIDMaster, sEventoByNome;

    public EventoDAO_Database(DataLayer d) {
        super(d);
    }

    @Override
    public void init() throws DataException {
        try {
            super.init();

            sEventoByID = connection.prepareStatement("SELECT * FROM Evento WHERE ID=?");
            sEventoIDMaster = connection.prepareStatement("SELECT * FROM Evento WHERE IDMaster = ?");
            sAllEventi = connection.prepareStatement("SELECT * FROM Evento");
            sEventoByAula = connection.prepareStatement("SELECT * FROM Evento WHERE IDAula=?");
            sEventoByNome = connection.prepareStatement("SELECT * FROM Evento WHERE nome = ?");
            sEventiByDay = connection.prepareStatement(
                    " SELECT e.* FROM Evento e JOIN Aula a ON e.IDAula = a.ID JOIN Dipartimento d ON a.IDDipartimento = d.ID WHERE d.ID = ? AND e.dataInizio <= ? AND e.dataFine >= ?");
            sEventiByCorso = connection.prepareStatement(
                    " SELECT e.* FROM Evento e JOIN Aula a ON e.IDAula = a.ID JOIN Corso c ON e.IDCorso = c.ID "
                    + " WHERE c.ID = ? AND a.IDDipartimento = ? "
                    + " AND e.Data BETWEEN ? AND ?");
            sEventiRicorrenti = connection
                    .prepareStatement("SELECT ID AS eventoID FROM evento WHERE nome=? AND responsabileID=? order by giorno");

            iEvento = connection.prepareStatement(
                    "INSERT INTO Evento (IDMaster, nome, oraInizio, oraFine, descrizione, ricorrenza, Data, dataFineRicorrenza, tipologiaEvento, IDResponsabile, IDCorso, IDAula, version) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

            uEvento = connection.prepareStatement(
                    "UPDATE Evento\n"
                    + "SET IDMaster = ?,\n"
                    + "nome = ?,\n"
                    + "    oraInizio = ?,\n"
                    + "    oraFine = ?,\n"
                    + "    descrizione = ?,\n"
                    + "    ricorrenza = ?,\n"
                    + "    Data = ?,\n"
                    + "    dataFineRicorrenza = ?,\n"
                    + "    tipologiaEvento = ?,\n"
                    + "    IDResponsabile = ?,\n"
                    + "    IDCorso = ?,\n"
                    + "    IDAula = ?,\n"
                    + "    version = ?\n"
                    + " WHERE ID = ? AND version = ?");

            dEvento = connection.prepareStatement("DELETE FROM evento WHERE ID=?");
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
            sAllEventi.close();
            dEvento.close();
            sEventoByNome.close();

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
            e.setIDMaster(rs.getInt("IDMaster"));
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
            e.setTipologiaEvento(TipologiaEvento.valueOf(rs.getObject("tipologiaEvento").toString()));
            e.setDataFineRicorrenza(rs.getDate("dataFineRicorrenza"));

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
    public List<Evento> getEventiByNome(String nome) throws DataException {
        List<Evento> result = new ArrayList();

        try {
            sEventiRicorrenti.setString(1, nome);
            ResultSet rs = sEventiRicorrenti.executeQuery();

            while (rs.next()) {
                result.add((Evento) getEventoByID(rs.getInt("ID")));
            }
            return result;
        } catch (SQLException ex) {
            throw new DataException("Unable to load eventi", ex);
        }
    }

    @Override
    public void deleteEvento(Evento evento) throws DataException {
        try {
            dEvento.setInt(1, evento.getKey());
            dEvento.executeUpdate();
        } catch (SQLException e) {
            throw new DataException("Unable to Delete Evento", e);
        }

    }

    @Override
    public List<Evento> getAllEventi() throws DataException {
        List<Evento> result = new ArrayList();

        try (ResultSet rs = sAllEventi.executeQuery()) {
            while (rs.next()) {
                result.add((Evento) getEventoByID(rs.getInt("ID")));
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load aule", ex);
        }
        return result;
    }

    @Override
    public List<Evento> getEventiRicorrenti(String nome, int IDresponsabile) throws DataException {
        List<Evento> result = new ArrayList();

        try {
            sEventiRicorrenti.setString(1, nome);
            sEventiRicorrenti.setInt(2, IDresponsabile);
            ResultSet rs = sEventiRicorrenti.executeQuery();

            while (rs.next()) {
                result.add((Evento) getEventoByID(rs.getInt("eventoID")));
            }
            return result;
        } catch (SQLException ex) {
            throw new DataException("Unable to load eventi", ex);
        }
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

    // TODO DA RIFARE
    @Override
    public void storeEvento(Evento e) throws DataException {
        try {
            if (e.getRicorrenza().toString() != "NESSUNA") {
                // TODO: Gestire ricorrenze
                throw new DataException("GESTIRE RICORRENZE");
            }

            if (e.getKey() != null && e.getKey() > 0) {
                if (e instanceof DataItemProxy && !((DataItemProxy) e).isModified()) {
                    return;
                }
                /**
                 *
                 * "UPDATE Evento\n" + "SET "IDMaster = ?",\n + "nome = ?,\n" +
                 * " oraInizio = ?,\n" + " oraFine = ?,\n" + " descrizione =
                 * ?,\n" + " ricorrenza = ?,\n" + " Data = ?,\n" + "
                 * dataFineRicorrenza = ?,\n" + " tipologiaEvento = ?,\n" + "
                 * IDResponsabile = ?,\n" + " IDCorso = ?,\n" + " IDAula = ?,\n"
                 * + " version = ?\n" + " WHERE ID = ? AND version = ?");
                 *
                 */
                // UPDATE
                // ID MASTER
                uEvento.setNull(1, java.sql.Types.INTEGER);
                uEvento.setString(2, e.getNome());
                uEvento.setTime(3, e.getOraInizio());
                uEvento.setTime(4, e.getOraFine());
                uEvento.setString(5, e.getDescrizione());
                uEvento.setString(6, e.getRicorrenza().toString());
                uEvento.setDate(7, e.getData());
                // DATA FINE RICORRENZA
                uEvento.setNull(8, java.sql.Types.INTEGER);
                uEvento.setString(9, e.getTipologiaEvento().toString());

                if (e.getResponsabile() != null) {
                    uEvento.setInt(10, e.getResponsabile().getKey());
                } else {
                    uEvento.setNull(10, java.sql.Types.INTEGER);
                }

                if (e.getCorso() != null) {
                    uEvento.setInt(11, e.getCorso().getKey());
                } else {
                    uEvento.setNull(11, java.sql.Types.INTEGER);
                }

                if (e.getAula() != null) {
                    uEvento.setInt(12, e.getAula().getKey());
                } else {
                    uEvento.setNull(12, java.sql.Types.INTEGER);
                }

                long current_version = e.getVersion();
                long next_version = current_version + 1;

                uEvento.setLong(13, next_version);
                uEvento.setInt(14, e.getKey());
                uEvento.setLong(15, current_version);

                if (uEvento.executeUpdate() == 0) {
                    throw new OptimisticLockException(e);
                } else {
                    e.setVersion(e.getVersion() + 1);
                }
            } else { // insert
                // ID MASTER
                iEvento.setNull(1, java.sql.Types.INTEGER);
                iEvento.setString(2, e.getNome());
                iEvento.setTime(3, e.getOraInizio());
                iEvento.setTime(4, e.getOraFine());
                iEvento.setString(5, e.getDescrizione());
                iEvento.setString(6, e.getRicorrenza().toString());
                iEvento.setDate(7, e.getData());
                // DATA FINE RICORRENZA
                iEvento.setNull(8, java.sql.Types.INTEGER);
                iEvento.setString(9, e.getTipologiaEvento().toString());

                if (e.getResponsabile() != null) {
                    iEvento.setInt(10, e.getResponsabile().getKey());
                } else {
                    iEvento.setNull(10, java.sql.Types.INTEGER);
                }

                if (e.getCorso() != null) {
                    iEvento.setInt(11, e.getCorso().getKey());
                } else {
                    iEvento.setNull(11, java.sql.Types.INTEGER);
                }

                if (e.getAula() != null) {
                    iEvento.setInt(12, e.getAula().getKey());
                } else {
                    iEvento.setNull(12, java.sql.Types.INTEGER);
                }

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
}
