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

public class AttrezzaturaDAO_Database extends DAO implements AttrezzaturaDAO {

  private PreparedStatement sAttrezzaturaByID;
  private PreparedStatement sAttrezzature, sAttrezzatureByAula, sAttrezzaturaByName;
  private PreparedStatement iAttrezzatura, uAttrezzatura, dAttrezzatura;

  public AttrezzaturaDAO_Database(DataLayer d) {
    super(d);
  }

  @Override
  public void init() throws DataException {
    try {
      super.init();

      sAttrezzaturaByID = connection.prepareStatement("SELECT * FROM AttrezzaturaDisponibile WHERE ID=?");
      sAttrezzature = connection.prepareStatement("SELECT ID AS AttrezzaturaID FROM AttrezzaturaDisponibile");
      sAttrezzaturaByName = connection.prepareStatement("SELECT * FROM AttrezzaturaDisponibile WHERE Nome=?");
      iAttrezzatura = connection.prepareStatement("INSERT INTO AttrezzaturaDisponibile (nome) VALUES(?)",
          Statement.RETURN_GENERATED_KEYS);
      uAttrezzatura = connection
          .prepareStatement("UPDATE AttrezzaturaDisponibile SET nome=?, version=? WHERE ID=? and version=?");
      dAttrezzatura = connection.prepareStatement("DELETE FROM AttrezzaturaDisponibile WHERE ID=?");
    } catch (SQLException ex) {
      throw new DataException("Error initializing AuleWeb data layer", ex);
    }
  }

  @Override
  public void destroy() throws DataException {
    try {

      sAttrezzaturaByID.close();
      sAttrezzaturaByName.close();
      sAttrezzature.close();
      iAttrezzatura.close();
      uAttrezzatura.close();
      dAttrezzatura.close();

    } catch (SQLException ex) {
      throw new DataException("errore nella Destroy");
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
  public Attrezzatura getAttrezzatura(int attrezzatura_key) throws DataException {
    Attrezzatura a = null;
    if (dataLayer.getCache().has(Attrezzatura.class, attrezzatura_key)) {
      a = dataLayer.getCache().get(Attrezzatura.class, attrezzatura_key);
    } else {
      try {
        sAttrezzaturaByID.setInt(1, attrezzatura_key);
        try (ResultSet rs = sAttrezzaturaByID.executeQuery()) {
          if (rs.next()) {
            a = creaNuovaAttrezzatura(rs);
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
    List<Attrezzatura> result = new ArrayList<>();

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
  public Attrezzatura getAttrezzaturaByName(String nomeAttrezzatura) throws DataException {
    Attrezzatura attrezzatura = null;

    try {
      sAttrezzaturaByName.setString(1, nomeAttrezzatura);
      try (ResultSet rs = sAttrezzaturaByName.executeQuery()) {
        while (rs.next()) {
          attrezzatura = getAttrezzatura(rs.getInt("ID"));
        }
      }
    } catch (SQLException e) {
      throw new DataException("Unable to load attrezzature by Name", e);
    }
    return attrezzatura;
  }

  @Override
  public void storeAttrezzatura(Attrezzatura attrezzatura) throws DataException {
    try {
      if (attrezzatura.getKey() != null && attrezzatura.getKey() > 0) { // update
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
      } else { // insert
        iAttrezzatura.setString(1, attrezzatura.getNome());

        if (iAttrezzatura.executeUpdate() == 1) {
          try (ResultSet keys = iAttrezzatura.getGeneratedKeys()) {
            if (keys.next()) {
              int key = keys.getInt(1);
              attrezzatura.setKey(key);
              dataLayer.getCache().add(Attrezzatura.class, attrezzatura);
            }
          }
        }
      }
      if (attrezzatura instanceof DataItemProxy) {
        ((DataItemProxy) attrezzatura).setModified(false);
      }
    } catch (SQLException | OptimisticLockException ex) {
      throw new DataException("Unable to store article", ex);
    }
  }

}
