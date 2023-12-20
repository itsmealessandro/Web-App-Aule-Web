package data.dao;

import data.domain.Attrezzatura;
import data.domain.Aula;
import data.proxy.AttrezzaturaProxy;
import framework.data.DAO;
import framework.data.DataException;
import framework.data.DataItemProxy;
import framework.data.DataLayer;
import framework.data.OptimisticLockException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author emanu
 */
public class AttrezzaturaDAO_Database extends DAO implements AttrezzaturaDAO {

    private PreparedStatement sAttrezzaturaByID;
    private PreparedStatement sAttrezzature, sAttrezzatureByAula;
    private PreparedStatement iAttrezzatura, uAttrezzatura, dAttrezzatura;

    public AttrezzaturaDAO_Database(DataLayer d) {
        super(d);
    }

    @Override
    public void init() throws DataException {
        try {
            super.init();

            sAttrezzaturaByID = connection.prepareStatement("SELECT * FROM AttrezzaturaDisponibile WHERE ID=?");
            sAttrezzature = connection.prepareStatement("SELECT ID AS attrezzaturaID FROM AttrezzaturaDisponibile");
            sAttrezzatureByAula = connection.prepareStatement("SELECT attrezzaturaID AS attrezzaturaID FROM Fornito WHERE aulaID=?");
            iAttrezzatura = connection.prepareStatement("INSERT INTO AttrezzaturaDisponibile (nome) VALUES(?)", Statement.RETURN_GENERATED_KEYS);
            uAttrezzatura = connection.prepareStatement("UPDATE AttrezzaturaDisponibile SET nome=?, version=? WHERE ID=? and version=?");
            dAttrezzatura = connection.prepareStatement("DELETE FROM AttrezzaturaDisponibile WHERE ID=?");
        } catch (SQLException ex) {
            throw new DataException("Error initializing AuleWeb data layer", ex);
        }
    }
    
      @Override
    public void destroy() throws DataException {
        try {

            sAttrezzaturaByID.close();

            sAttrezzatureByAula.close();
            sAttrezzature.close();

            iAttrezzatura.close();
            uAttrezzatura.close();
            dAttrezzatura.close();

        } catch (SQLException ex) {
            //
        }
        super.destroy();
    }
    
    @Override
    public Attrezzatura createAttrezzatura() {
        return new AttrezzaturaProxy(getDataLayer());
    }

    public Attrezzatura creaNuovaAttrezzatura(ResultSet rs) throws DataException {
        AttrezzaturaProxy a = (AttrezzaturaProxy) createAttrezzatura();
        try {
            a.setKey(rs.getInt("ID"));
            a.setNome(rs.getString("nome"));
            a.setVersion(rs.getLong("version"));
        } catch (SQLException ex) {
            throw new DataException("Unable to create attrezzatura object from ResultSet", ex);
        }
        return a;
    }

    @Override
    public Attrezzatura getAttrezzatura(int attrezzatura_key) throws DataException  {
          Attrezzatura a = null;
        //prima vediamo se l'oggetto è già stato caricato
        //first look for this object in the cache
        if (dataLayer.getCache().has(Attrezzatura.class, attrezzatura_key)) {
            a = dataLayer.getCache().get(Attrezzatura.class, attrezzatura_key);
        } else {
            //altrimenti lo carichiamo dal database
            //otherwise load it from database
            try {
                sAttrezzaturaByID.setInt(1, attrezzatura_key);
                try (ResultSet rs = sAttrezzaturaByID.executeQuery()) {
                    if (rs.next()) {
                        a = creaNuovaAttrezzatura(rs);
                        //e lo mettiamo anche nella cache
                        //and put it also in the cache
                        dataLayer.getCache().add(Attrezzatura.class, a);
                    }
                }
            } catch (SQLException ex) {
                throw new DataException("Unable to load attrezzatura by ID", ex);
            }
        }
        return a;
    }

    @Override
    public List<Attrezzatura> getTutteLeAttrezzature() throws DataException {
             List<Attrezzatura> result = new ArrayList();

        try (ResultSet rs = sAttrezzature.executeQuery()) {
            while (rs.next()) {
                result.add((Attrezzatura) getAttrezzatura(rs.getInt("attrezzaturaID")));
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load attrezzature", ex);
        }
        return result;
    }

    @Override
    public List<Attrezzatura> getAttrezzaturePerAula(Aula aula) throws DataException {
         List<Attrezzatura> result = new ArrayList();

        try {
            sAttrezzatureByAula.setInt(1, aula.getKey());
            try (ResultSet rs = sAttrezzatureByAula.executeQuery()) {
                while (rs.next()) {
                    result.add((Attrezzatura) getAttrezzatura(rs.getInt("attrezzaturaID")));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load attrezzature by aula", ex);
        }
        return result;
    }

    @Override
    public void storeAttrezzatura(Attrezzatura attrezzatura) throws DataException {
         try {
            if (attrezzatura.getKey() != null && attrezzatura.getKey() > 0) { //update
                //non facciamo nulla se l'oggetto è un proxy e indica di non aver subito modifiche
                //do not store the object if it is a proxy and does not indicate any modification
                if (attrezzatura instanceof DataItemProxy && !((DataItemProxy) attrezzatura).isModified()) {
                    return;
                }
                uAttrezzatura.setString(1, attrezzatura.getNome());

                long current_version = attrezzatura.getVersion();
                long next_version = current_version + 1;

                uAttrezzatura.setLong(6, next_version);
                uAttrezzatura.setInt(7, attrezzatura.getKey());
                uAttrezzatura.setLong(8, current_version);

                if (uAttrezzatura.executeUpdate() == 0) {
                    throw new OptimisticLockException(attrezzatura);
                } else {
                    attrezzatura.setVersion(next_version);
                }
            } else { //insert
                iAttrezzatura.setString(1, attrezzatura.getNome());

                if (iAttrezzatura.executeUpdate() == 1) {
                    //per leggere la chiave generata dal database
                    //per il record appena inserito, usiamo il metodo
                    //getGeneratedKeys sullo statement.
                    //to read the generated record key from the database
                    //we use the getGeneratedKeys method on the same statement
                    try (ResultSet keys = iAttrezzatura.getGeneratedKeys()) {
                        //il valore restituito è un ResultSet con un record
                        //per ciascuna chiave generata (uno solo nel nostro caso)
                        //the returned value is a ResultSet with a distinct record for
                        //each generated key (only one in our case)
                        if (keys.next()) {
                            //i campi del record sono le componenti della chiave
                            //(nel nostro caso, un solo intero)
                            //the record fields are the key componenets
                            //(a single integer in our case)
                            int key = keys.getInt(1);
                            //aggiornaimo la chiave in caso di inserimento
                            //after an insert, uopdate the object key
                            attrezzatura.setKey(key);
                            //inseriamo il nuovo oggetto nella cache
                            //add the new object to the cache
                            dataLayer.getCache().add(Attrezzatura.class, attrezzatura);
                        }
                    }
                }
            }

//            //se possibile, restituiamo l'oggetto appena inserito RICARICATO
//            //dal database tramite le API del modello. In tal
//            //modo terremo conto di ogni modifica apportata
//            //durante la fase di inserimento
//            //if possible, we return the just-inserted object RELOADED from the
//            //database through our API. In this way, the resulting
//            //object will ambed any data correction performed by
//            //the DBMS
//            if (key > 0) {
//                article.copyFrom(getArticle(key));
//            }
            //se abbiamo un proxy, resettiamo il suo attributo dirty
            //if we have a proxy, reset its dirty attribute
            if (attrezzatura instanceof DataItemProxy) {
                ((DataItemProxy) attrezzatura).setModified(false);
            }
        } catch (SQLException | OptimisticLockException ex) {
            throw new DataException("Unable to store article", ex);
        }
    }

}
