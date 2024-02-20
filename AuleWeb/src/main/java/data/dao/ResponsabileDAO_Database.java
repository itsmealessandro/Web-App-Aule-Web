package data.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import data.domain.Responsabile;
import data.proxy.ResponsabileProxy;
import framework.data.DAO;
import framework.data.DataException;
import framework.data.DataItemProxy;
import framework.data.DataLayer;
import framework.data.OptimisticLockException;

public class ResponsabileDAO_Database extends DAO implements ResponsabileDAO {

  private PreparedStatement iResponsabile, uResponsabile, sResponsabileByID, sResponsabileByEmail, sAllResponsabili;

  public ResponsabileDAO_Database(DataLayer d) {
    super(d);
  }

  @Override
  public void init() throws DataException {
    try {
      super.init();

      sResponsabileByID = connection.prepareStatement("SELECT * FROM Responsabile WHERE ID=?");
      sAllResponsabili = connection.prepareStatement("SELECT * FROM Responsabile");
      sResponsabileByEmail = connection.prepareStatement("SELECT * FROM Responsabile WHERE Email=?");
      iResponsabile = connection.prepareStatement("INSERT INTO Responsabile (nome,cognome,email) VALUES(?,?,?)",
          Statement.RETURN_GENERATED_KEYS);
      uResponsabile = connection
          .prepareStatement("UPDATE Responsabile SET nome=?,cognome=?,email=?,version=? WHERE ID=? and version=?");
    } catch (SQLException ex) {
      throw new DataException("Error initializing AuleWeb data layer", ex);
    }
  }

  @Override
  public void destroy() throws DataException {
    try {
      sResponsabileByID.close();
      sAllResponsabili.close();
      sResponsabileByEmail.close();
      iResponsabile.close();
      uResponsabile.close();

    } catch (SQLException ex) {
      throw new DataException("Errore nella Destroy", ex);
    }
    super.destroy();
  }

  @Override
  public Responsabile createResponsabile() {
    return new ResponsabileProxy(getDataLayer());
  }

  private ResponsabileProxy createResponsabile(ResultSet rs) throws DataException {
    try {
      ResponsabileProxy r = (ResponsabileProxy) createResponsabile();
      r.setKey(rs.getInt("ID"));
      r.setNome(rs.getString("nome"));
      r.setCognome(rs.getString("cognome"));
      r.setEmail(rs.getString("email"));
      r.setVersion(rs.getLong("version"));
      return r;
    } catch (SQLException ex) {
      throw new DataException("Unable to create Responsabile object form ResultSet", ex);
    }
  }

  @Override
  public Responsabile getResponsabile(int resp_key) throws DataException {

    Responsabile a = null;

    if (dataLayer.getCache().has(Responsabile.class, resp_key)) {

      a = dataLayer.getCache().get(Responsabile.class, resp_key);
    } else {
      try {
        sResponsabileByID.setInt(1, resp_key);
        try (ResultSet rs = sResponsabileByID.executeQuery()) {
          if (rs.next()) {

            a = createResponsabile(rs);

            dataLayer.getCache().add(Responsabile.class, a);
          }
        }
      } catch (SQLException ex) {
        throw new DataException("Unable to load Responsabile by ID", ex);
      }
    }
    return a;
  }

  @Override
  public Responsabile getResponsabileByEmail(String email) throws DataException {
    Responsabile responsabile = null;
    try {
      sResponsabileByEmail.setString(1, email);
      try (ResultSet rs = sResponsabileByEmail.executeQuery()) {
        while (rs.next()) {
          responsabile = getResponsabile(rs.getInt("ID"));
        }

      }
    } catch (SQLException e) {
      throw new DataException("Unable to load Responsabile by Email", e);
    }

    return responsabile;

  }

  @Override
  public List<Responsabile> getAllResponsabili() throws DataException {

    List<Responsabile> result = new ArrayList<>();

    try (ResultSet rs = sAllResponsabili.executeQuery()) {
      while (rs.next()) {
        result.add((Responsabile) getResponsabile(rs.getInt("ID")));
      }
    } catch (SQLException ex) {
      throw new DataException("Unable to load responsabile", ex);
    }
    return result;
  }

  @Override
  public void storeResponsabile(Responsabile r) throws DataException {
    try {
      if (r.getKey() != null && r.getKey() > 0) { // update

        if (r instanceof DataItemProxy && !((DataItemProxy) r).isModified()) {
          return;
        }
        uResponsabile.setString(1, r.getNome());
        uResponsabile.setString(2, r.getCognome());
        uResponsabile.setString(3, r.getEmail());

        long current_version = r.getVersion();
        long next_version = current_version + 1;

        uResponsabile.setLong(4, next_version);
        uResponsabile.setInt(5, r.getKey());
        uResponsabile.setLong(6, current_version);

        if (uResponsabile.executeUpdate() == 0) {
          throw new OptimisticLockException(r);
        } else {
          r.setVersion(next_version);
        }
      } else { // insert
        iResponsabile.setString(1, r.getNome());
        iResponsabile.setString(2, r.getCognome());
        iResponsabile.setString(3, r.getEmail());

        if (iResponsabile.executeUpdate() == 1) {

          try (ResultSet keys = iResponsabile.getGeneratedKeys()) {

            if (keys.next()) {

              int key = keys.getInt(1);

              r.setKey(key);

              dataLayer.getCache().add(Responsabile.class, r);
            }
          }
        }
      }

      if (r instanceof DataItemProxy) {
        ((DataItemProxy) r).setModified(false);
      }
    } catch (SQLException | OptimisticLockException ex) {
      throw new DataException("Unable to store Responsabile", ex);
    }
  }

}
