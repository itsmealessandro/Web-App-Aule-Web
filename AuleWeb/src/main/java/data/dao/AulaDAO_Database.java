package data.dao;

import data.domain.Aula;
import data.domain.Dipartimento;
import data.domain.Evento;
import data.proxy.AulaProxy;
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

public class AulaDAO_Database extends DAO implements AulaDAO {

  private PreparedStatement sAulaPerID, sAulaByName;
  private PreparedStatement sAule, sAulePerDipartimento;
  private PreparedStatement iAula, uAula, dAula;

  public AulaDAO_Database(DataLayer d) {
    super(d);
  }

  @Override
  public void init() throws DataException {
    try {
      super.init();

      sAulaPerID = connection.prepareStatement("SELECT * FROM Aula WHERE ID=?");
      sAulaByName = connection.prepareStatement("SELECT * FROM Aula WHERE Nome=?");
      sAule = connection.prepareStatement("SELECT ID FROM Aula");
      sAulePerDipartimento = connection.prepareStatement("SELECT ID FROM Aula WHERE IDdipartimento=?");

      iAula = connection.prepareStatement(
          "INSERT INTO Aula (nome, luogo, edificio, piano, capienza, preseElettriche, preseRete, note, "
              + "IDAttrezzatura, IDDipartimento, IDResponsabile) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);",
          Statement.RETURN_GENERATED_KEYS);
      uAula = connection.prepareStatement(
          "UPDATE Aula SET nome = ?, luogo = ?, edificio = ?, piano = ?, capienza = ?, preseElettriche = ?, preseRete = ?, note = ?, "
              + "IDAttrezzatura = ?, IDDipartimento = ?, IDResponsabile = ?, version = ? WHERE ID = ? and version=?");
      dAula = connection.prepareStatement("DELETE FROM Aula WHERE ID=?");
    } catch (SQLException ex) {
      throw new DataException("Error initializing auleweb data layer", ex);
    }
  }

  @Override
  public void destroy() throws DataException {
    try {

      sAulaPerID.close();
      sAulaByName.close();
      sAule.close();
      sAulePerDipartimento.close();
      iAula.close();
      uAula.close();
      dAula.close();

    } catch (SQLException ex) {
      throw new DataException("Error in destroy", ex);
    }
    super.destroy();
  }

  @Override
  public Aula createAula() {
    return new AulaProxy(getDataLayer());
  }

  public Aula creaNuovaAula(ResultSet rs) throws DataException {
    AulaProxy a = (AulaProxy) createAula();
    try {
      a.setKey(rs.getInt("ID"));
      a.setNome(rs.getString("nome"));
      a.setLuogo(rs.getString("luogo"));
      a.setEdificio(rs.getString("edificio"));
      a.setPiano(rs.getString("piano"));
      a.setCapienza(rs.getInt("capienza"));
      a.setPreseElettriche(rs.getInt("preseElettriche"));
      a.setPreseRete(rs.getInt("preseRete"));
      a.setNote(rs.getString("note"));

      // riferimenti ad oggetti
      a.setDipartimentoKey(rs.getInt("IDDipartimento"));
      a.setAttrezzaturaKey(rs.getInt("IDAttrezzatura"));
      a.setResponsabileKey(rs.getInt("IDResponsabile"));

      a.setVersion(rs.getInt("version"));
    } catch (SQLException ex) {
      throw new DataException("Unable to create Aula object from ResultSet", ex);
    }
    return a;
  }

  @Override
  public Aula getAulaByID(int aula_key) throws DataException {
    Aula a = null;
    // prima vediamo se l'oggetto è già stato caricato
    // first look for this object in the cache
    if (dataLayer.getCache().has(Aula.class, aula_key)) {
      a = dataLayer.getCache().get(Aula.class, aula_key);
    } else {
      // altrimenti lo carichiamo dal database
      // otherwise load it from the database
      try {
        sAulaPerID.setInt(1, aula_key);
        try (ResultSet rs = sAulaPerID.executeQuery()) {
          if (rs.next()) {
            // Creiamo un'istanza di AulaProxy invece di Aula
            a = creaNuovaAula(rs);
            // e lo mettiamo anche nella cache
            // and put it also in the cache
            dataLayer.getCache().add(Aula.class, a);
          }
        }
      } catch (SQLException ex) {
        throw new DataException("Unable to load aula by ID", ex);
      }
    }
    return a;
  }

  @Override
  public Aula getAulaByName(String aula_name) throws DataException {
    Aula a = null;

    try {
      sAulaByName.setString(1, aula_name);
      try (ResultSet rs = sAulaByName.executeQuery()) {
        if (rs.next()) {
          a = getAulaByID(rs.getInt("ID"));
        }
      }
      return a;
    } catch (SQLException ex) {
      throw new DataException("Unable to load aula by Name", ex);
    }
  }

  @Override
  public List<Aula> getAllAule() throws DataException {
    List<Aula> result = new ArrayList<>();

    try (ResultSet rs = sAule.executeQuery()) {
      while (rs.next()) {
        result.add((Aula) getAulaByID(rs.getInt("ID")));
      }
    } catch (SQLException ex) {
      throw new DataException("Unable to load aule", ex);
    }
    return result;
  }

  @Override
  public List<Aula> getAulePerEvento(Evento evento) throws DataException {
    List<Aula> result = new ArrayList<>();

    try {
      sAulePerDipartimento.setInt(1, evento.getKey());
      try (ResultSet rs = sAulePerDipartimento.executeQuery()) {
        while (rs.next()) {
          // la query estrae solo gli ID degli articoli selezionati
          // poi sarà getArticle che, con le relative query, popolerà
          // gli oggetti corrispondenti. Meno efficiente, ma così la
          // logica di creazione degli articoli è meglio incapsulata
          // the query extracts only the IDs of the selected articles
          // then getArticle, with its queries, will populate the
          // corresponding objects. Less efficient, but in this way
          // article creation logic is better encapsulated
          result.add((Aula) getAulaByID(rs.getInt("ID")));
        }
      }
    } catch (SQLException ex) {
      throw new DataException("Unable to load aule by evento", ex);
    }
    return result;
  }

  @Override
  public List<Aula> getAulaPerDipartimento(Dipartimento dipartimento) throws DataException {

    List<Aula> listaAule = new ArrayList<>();
    try {
      sAulePerDipartimento.setInt(1, dipartimento.getKey());
      try (ResultSet resultSet = sAulePerDipartimento.executeQuery()) {
        while (resultSet.next()) {
          listaAule.add((Aula) getAulaByID(resultSet.getInt("ID")));
        }
      }
    } catch (SQLException e) {
      throw new DataException("Unable to load aule by dipartimento", e);
    }
    return listaAule;
  }

  @Override
  public void storeAula(Aula a) throws DataException {
    /*
     * "UPDATE Aula SET nome = ?, luogo = ?, edificio = ?, piano = ?, capienza = ?,
     * preseElettriche = ?, preseRete = ?, note = ?,
     * IDAttrezzatura = ?, IDDipartimento = ?, IDResponsabile =? ,version = ? WHERE
     * ID = ? and version=?");
     */
    try {
      if (a.getKey() != null && a.getKey() > 0) { // update

        if (a instanceof DataItemProxy && !((DataItemProxy) a).isModified()) {
          return;
        }

        uAula.setString(1, a.getNome());
        uAula.setString(2, a.getLuogo());
        uAula.setString(3, a.getEdificio());
        uAula.setString(4, a.getPiano());
        uAula.setInt(5, a.getCapienza());
        uAula.setInt(6, a.getPreseElettriche());
        uAula.setInt(7, a.getPreseRete());
        uAula.setString(8, a.getNote());

        if (a.getAttrezzatura() != null) {
          uAula.setInt(9, a.getAttrezzatura().getKey());
        } else {
          uAula.setNull(9, java.sql.Types.INTEGER);
        }

        if (a.getDipartimento() != null) {
          uAula.setInt(10, a.getDipartimento().getKey());
        } else {
          uAula.setNull(10, java.sql.Types.INTEGER);
        }

        if (a.getResponsabile() != null) {
          uAula.setInt(11, a.getResponsabile().getKey());
        } else {
          uAula.setNull(11, java.sql.Types.INTEGER);
        }

        long current_version = a.getVersion();
        long next_version = current_version + 1;

        uAula.setLong(12, next_version);
        uAula.setInt(13, a.getKey());
        uAula.setLong(14, current_version);

        if (uAula.executeUpdate() == 0) {
          throw new OptimisticLockException(a);
        } else {
          a.setVersion(next_version);
        }
      } else { // insert

        iAula.setString(1, a.getNome());
        iAula.setString(2, a.getLuogo());
        iAula.setString(3, a.getEdificio());
        iAula.setString(4, a.getPiano());
        iAula.setInt(5, a.getCapienza());
        iAula.setInt(6, a.getPreseElettriche());
        iAula.setInt(7, a.getPreseRete());
        iAula.setString(8, a.getNote());

        if (a.getAttrezzatura() != null) {
          iAula.setInt(9, a.getAttrezzatura().getKey());
        } else {
          iAula.setNull(9, java.sql.Types.INTEGER);
        }

        if (a.getDipartimento() != null) {
          iAula.setInt(10, a.getDipartimento().getKey());
        } else {
          iAula.setNull(10, java.sql.Types.INTEGER);
        }

        if (a.getResponsabile() != null) {
          iAula.setInt(11, a.getResponsabile().getKey());
        } else {
          iAula.setNull(11, java.sql.Types.INTEGER);
        }

        if (iAula.executeUpdate() == 1) {

          try (ResultSet keys = iAula.getGeneratedKeys()) {

            if (keys.next()) {

              int key = keys.getInt(1);
              a.setKey(key);

              dataLayer.getCache().add(Aula.class, a);
            }
          }
        }
      }

      if (a instanceof DataItemProxy) {
        ((DataItemProxy) a).setModified(false);
      }
    } catch (SQLException | OptimisticLockException ex) {
      throw new DataException("Unable to store Aula", ex);
    }
  }

  @Override
  public void deleteAula(Aula aula) throws DataException {
    try {
      dAula.setInt(1, aula.getKey());
      dAula.executeUpdate();
    } catch (SQLException e) {
      throw new DataException("Richiede Eliminare prima gli Eventi Associati");
    }
  }

}
