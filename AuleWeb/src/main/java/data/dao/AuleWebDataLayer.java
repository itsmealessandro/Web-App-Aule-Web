package data.dao;

import data.domain.Amministratore;
import data.domain.Attrezzatura;
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
        registerDAO(Amministratore.class, new AmministratoreDAO_Database(this));
        registerDAO(Attrezzatura.class, new AttrezzaturaDAO_Database(this));
        registerDAO(Aula.class, new AulaDAO_Database(this));
        registerDAO(Corso.class, new CorsoDAO_Database(this));
        registerDAO(Dipartimento.class, new DipartimentoDAO_Database(this));
        registerDAO(Evento.class, new EventoDAO_Database(this));
        registerDAO(Responsabile.class, new ResponsabileDAO_Database(this));
    }

    //helpers 
    public AmministratoreDAO getAmministratoreDAO() {
        return (AmministratoreDAO) getDAO(Amministratore.class);
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
    
    public AttrezzaturaDAO getAttrezzaturaDAO() {
        return (AttrezzaturaDAO) getDAO(Attrezzatura.class);
    }
    
}
