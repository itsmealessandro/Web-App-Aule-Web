package data.dao;

import data.domain.Amministratore;
import data.domain.Aula;
import data.domain.Corso;
import data.domain.Dipartimento;
import data.domain.Evento;
import data.domain.Responsabile;
import framework.data.DataException;
import framework.data.DataLayer;

import java.sql.SQLException;
import javax.sql.DataSource;

public class AuleWebDataLayer extends DataLayer {

    public AuleWebDataLayer(DataSource datasource) throws SQLException {
        super(datasource);
    }

    @Override
    public void init() throws DataException {
        //registriamo i nostri dao
        //register our daos
        registerDAO(AmministratoreDAO.class, new AmministratoreDAO_Database(this));
        registerDAO(AttrezzaturaDAO.class, new AttrezzaturaDAO_Database(this));
        registerDAO(AulaDAO.class, new AulaDAO_Database(this));
        registerDAO(CorsoDAO.class, new CorsoDAO_Database(this));
        registerDAO(DipartimentoDAO.class, new DipartimentoDAO_Database(this));
        registerDAO(EventoDAO.class, new EventoDAO_Database(this));
        registerDAO(ResponsabileDAO.class, new ResponsabileDAO_Database(this));
    }

    //helpers 
    public AttrezzaturaDAO getAmministratoreDAO() {
        return (AttrezzaturaDAO) getDAO(Amministratore.class);
    }

    public AulaDAO getAulaDAO() {
        return (AulaDAO) getDAO(Aula.class);
    }

    public CorsoDAO getCorsoDAO() {
        return (CorsoDAO) getDAO(Corso.class);
    }

    public DipartimentoDAO getDipartimentoDAO() {
        return (DipartimentoDAO) getDAO(Dipartimento.class);
    }

    public EventoDAO getEventoDAO() {
        return (EventoDAO) getDAO(Evento.class);
    }

    public ResponsabileDAO getResponsabileDAO() {
        return (ResponsabileDAO) getDAO(Responsabile.class);

    }
    
}
