package data.dao;

import data.domain.Amministratore;
import data.proxy.AmministratoreProxy;
import framework.data.DAO;
import framework.data.DataException;
import framework.data.DataItemProxy;
import framework.data.DataLayer;
import framework.data.OptimisticLockException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AmministratoreDAO_Database extends DAO implements AmministratoreDAO {

  private PreparedStatement iAmministratore, uAmministratore, sAmministratoreByID, sAmmministratoreByUsername;

  public AmministratoreDAO_Database(DataLayer d) {
    super(d);
  }

  @Override
  public void init() throws DataException {
    try {
      super.init();

      sAmministratoreByID = connection.prepareStatement("SELECT * FROM Amministratore WHERE ID=?");
      sAmmministratoreByUsername = connection.prepareStatement("SELECT ID FROM Amministratore WHERE username=?");
      iAmministratore = connection.prepareStatement("INSERT INTO Amministratore (username,password) VALUES(?,?)",
          Statement.RETURN_GENERATED_KEYS);
      uAmministratore = connection
          .prepareStatement("UPDATE Amministratore SET username=?,password=?,version=? WHERE ID=? and version=?");
    } catch (SQLException ex) {
      throw new DataException("Error initializing AuleWeb data layer", ex);
    }
  }

  @Override
  public void destroy() throws DataException {
    try {
      sAmministratoreByID.close();
      sAmmministratoreByUsername.close();
      iAmministratore.close();
      uAmministratore.close();

    } catch (SQLException ex) {
      throw new DataException("Error In Destroy", ex);
    }
    super.destroy();
  }

  @Override
  public Amministratore createAmministratore() {
    return new AmministratoreProxy(getDataLayer());
  }

  private AmministratoreProxy createAmministratore(ResultSet rs) throws DataException {
    AmministratoreProxy a = (AmministratoreProxy) createAmministratore();
    try {
      a.setKey(rs.getInt("ID"));
      a.setUsername(rs.getString("username"));
      a.setPassword(rs.getString("password"));
      a.setVersion(rs.getLong("version"));
      return a;
    } catch (SQLException ex) {
      throw new DataException("Unable to create Amministratore object form ResultSet", ex);
    }
  }

  @Override
  public Amministratore getAmministratore(int admin_key) throws DataException {
    Amministratore a = null;

    if (dataLayer.getCache().has(Amministratore.class, admin_key)) {

      a = dataLayer.getCache().get(Amministratore.class, admin_key);
    } else {
      try {
        sAmministratoreByID.setInt(1, admin_key);
        try (ResultSet rs = sAmministratoreByID.executeQuery()) {
          if (rs.next()) {

            a = createAmministratore(rs);

            dataLayer.getCache().add(Amministratore.class, a);
          }
        }
      } catch (SQLException ex) {
        throw new DataException("Unable to load Amministratore by ID", ex);
      }
    }
    return a;
  }

  @Override
  public void storeAmministratore(Amministratore a) throws DataException {

    try {
      if (a.getKey() != null && a.getKey() > 0) { // update

        if (a instanceof DataItemProxy && !((DataItemProxy) a).isModified()) {
          return;
        }
        uAmministratore.setString(1, a.getUsername());
        uAmministratore.setString(2, a.getPassword());

        long current_version = a.getVersion();
        long next_version = current_version + 1;

        uAmministratore.setLong(3, next_version);
        uAmministratore.setInt(4, a.getKey());
        uAmministratore.setLong(5, current_version);

        if (uAmministratore.executeUpdate() == 0) {
          throw new OptimisticLockException(a);
        } else {
          a.setVersion(next_version);
        }
      } else { // insert
        iAmministratore.setString(1, a.getUsername());
        iAmministratore.setString(2, a.getPassword());

        if (iAmministratore.executeUpdate() == 1) {

          try (ResultSet keys = iAmministratore.getGeneratedKeys()) {

            if (keys.next()) {

              int key = keys.getInt(1);

              a.setKey(key);

              dataLayer.getCache().add(Amministratore.class, a);
            }
          }
        }
      }

      if (a instanceof DataItemProxy) {
        ((DataItemProxy) a).setModified(false);
      }
    } catch (SQLException | OptimisticLockException ex) {
      throw new DataException("Unable to store Amministratore", ex);
    }
  }

  @Override
  public Amministratore getAmministratoreByUsername(String username) throws DataException {

    try {
      sAmmministratoreByUsername.setString(1, username);
      try (ResultSet rs = sAmmministratoreByUsername.executeQuery()) {
        if (rs.next()) {
          return getAmministratore(rs.getInt("ID"));
        }
      }
    } catch (SQLException ex) {
      throw new DataException("Unable to find user", ex);
    }
    return null;
  }

}
