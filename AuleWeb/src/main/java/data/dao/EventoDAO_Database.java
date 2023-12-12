package data.dao;


import data.domain.Evento;
import data.proxy.EventoProxy;

import framework.data.DAO;
import framework.data.DataException;
import framework.data.DataItemProxy;
import framework.data.DataLayer;
import framework.data.OptimisticLockException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EventoDAO_Database extends DAO implements EventoDAO {

    private PreparedStatement iEvento, uEvento, sEventoByID;

    public EventoDAO_Database(DataLayer d) {
        super(d);
    }

    @Override
    public void init() throws DataException {
        try {
            super.init();

            sEventoByID = connection.prepareStatement("SELECT * FROM Evento WHERE ID=?");
            iEvento = connection.prepareStatement("INSERT INTO Evento (nome, oraInizio, oraFine, descrizione, aula, ricorrenza, dataInizio, dataFine, responsabile, corso, tipologiaEvento) VALUES(?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            uEvento = connection.prepareStatement("UPDATE Evento SET nome=?, oraInizio=?, oraFine=?, descrizione=?, aula=?, ricorrenza=?, dataInizio=?, dataFine=?, responsabile=?, corso=?, tipologiaEvento=?, version=? WHERE ID=? and version=?");
        } catch (SQLException ex) {
            throw new DataException("Error initializing data layer", ex);
        }
    }

    @Override
    public void destroy() throws DataException {
        try {
            sEventoByID.close();
            iEvento.close();
            uEvento.close();

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
        e.setDataInizio(rs.getDate("dataInizio"));
        e.setDataFine(rs.getDate("dataFine"));
        /*
        e.setRicorrenza( Imposta l'oggetto Ricorrenza in base ai dati del ResultSet );
        CAPIRE COME GESTIRLO
        */
        // TODO da impostare le chiavi nei proxy e tutto blablabla
        
        e.setAula(/* Imposta l'oggetto AulaImpl in base ai dati del ResultSet );
        e.setResponsabile(/* Imposta l'oggetto ResponsabileImpl in base ai dati del ResultSet );
        e.setCorso(/* Imposta l'oggetto CorsoImpl in base ai dati del ResultSet );
        e.setTipologiaEvento(/* Imposta l'oggetto TipologiaEventoImpl in base ai dati del ResultSet );
        
        */
        
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
    public void storeEvento(Evento e) throws DataException {

        try {
            if (e.getKey() != null && e.getKey() > 0) { //update

                if (e instanceof DataItemProxy && !((DataItemProxy) e).isModified()) {
                    return;
                }
                uEvento.setString(1, e.getNome());
                uEvento.setTime(2, e.getOraInizio());
                uEvento.setTime(3, e.getOraFine());
                uEvento.setString(4, e.getDescrizione());
                uEvento.setDate(7, e.getDataInizio());
                uEvento.setDate(8, e.getDataFine());
                
                /* Questi vanno gestiti con le chiavi e blabla
                uEvento.setObject(9, e.getResponsabile());  // Assumi che l'oggetto ResponsabileImpl possa essere convertito in un formato adatto per il database
                uEvento.setObject(10, e.getCorso());  // Assumi che l'oggetto CorsoImpl possa essere convertito in un formato adatto per il database
                uEvento.setObject(11, e.getTipologiaEvento());  // Assumi che l'oggetto TipologiaEventoImpl possa essere convertito in un formato adatto per il database
                uEvento.setLong(12, e.getVersion());
                uEvento.setObject(5, e.getAula());  // Assumi che l'oggetto AulaImpl possa essere convertito in un formato adatto per il database
                uEvento.setObject(6, e.getRicorrenza());  // Assumi che l'oggetto Ricorrenza possa essere convertito in un formato adatto per il database
                */
                uEvento.setInt(13, e.getKey());
                uEvento.setLong(14, e.getVersion());

                if (uEvento.executeUpdate() == 0) {
                    throw new OptimisticLockException(e);
                } else {
                    e.setVersion(e.getVersion() + 1);
                }
            } else { //insert
                iEvento.setString(1, e.getNome());
                iEvento.setTime(2, e.getOraInizio());
                iEvento.setTime(3, e.getOraFine());
                iEvento.setString(4, e.getDescrizione());
                iEvento.setDate(7, e.getDataInizio());
                iEvento.setDate(8, e.getDataFine());
                
                /*
                iEvento.setObject(5, e.getAula());  // Assumi che l'oggetto AulaImpl possa essere convertito in un formato adatto per il database
                iEvento.setObject(6, e.getRicorrenza());  // Assumi che l'oggetto Ricorrenza possa essere convertito in un formato adatto per il database
                iEvento.setObject(9, e.getResponsabile());  // Assumi che l'oggetto ResponsabileImpl possa essere convertito in un formato adatto per il database
                iEvento.setObject(10, e.getCorso());  // Assumi che l'oggetto CorsoImpl possa essere convertito in un formato adatto per il database
                iEvento.setObject(11, e.getTipologiaEvento());  // Assumi che l'oggetto TipologiaEventoImpl possa essere convertito in un formato adatto per il database
                */
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

