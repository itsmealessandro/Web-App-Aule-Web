/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data.dao;

import data.domain.Corso;
import data.proxy.CorsoProxy;
import framework.data.DAO;
import framework.data.DataException;
import framework.data.DataItemProxy;
import framework.data.DataLayer;
import framework.data.OptimisticLockException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author emanu
 */
public class CorsoDAO_Database extends DAO implements CorsoDAO {

  private PreparedStatement iCorso, uCorso, sCorsoByID, sCorsoByNome;

  public CorsoDAO_Database(DataLayer d) {
    super(d);
  }

  @Override
  public void init() throws DataException {
    try {
      super.init();

      sCorsoByID = connection.prepareStatement("SELECT * FROM Corso WHERE ID=?");
      iCorso = connection.prepareStatement("INSERT INTO Corso (nome,responsabileID) VALUES(?,?)",
          Statement.RETURN_GENERATED_KEYS);
      uCorso = connection
          .prepareStatement("UPDATE Corso SET nome=?,responsabileID=?,version=? WHERE ID=? and version=?");
      sCorsoByNome = connection.prepareStatement("SELECT * FROM Corso WHERE Nome=?");
    } catch (SQLException ex) {
      throw new DataException("Error initializing AuleWeb data layer", ex);
    }
  }

  @Override
  public void destroy() throws DataException {
    try {
      sCorsoByID.close();
      iCorso.close();
      uCorso.close();

    } catch (SQLException ex) {
      throw new DataException("Error In Destroy", ex);
    }
    super.destroy();
  }

  @Override
  public Corso createCorso() {
    return new CorsoProxy(getDataLayer());
  }

  private CorsoProxy createCorso(ResultSet rs) throws DataException {
    try {
      CorsoProxy corsoProxy = (CorsoProxy) createCorso();
      corsoProxy.setKey(rs.getInt("ID"));
      corsoProxy.setNome(rs.getString("nome"));
      corsoProxy.setResponsabileKey(rs.getInt("IDResponsabile"));
      corsoProxy.setVersion(rs.getLong("version"));
      return corsoProxy;
    } catch (SQLException ex) {
      throw new DataException("Unable to create Corso object form ResultSet", ex);
    }
  }

  @Override
  public Corso getCorso(int corso_key) throws DataException {
    Corso corso = null;

    if (dataLayer.getCache().has(Corso.class, corso_key)) {

      corso = dataLayer.getCache().get(Corso.class, corso_key);
    } else {
      try {
        sCorsoByID.setInt(1, corso_key);
        try (ResultSet rs = sCorsoByID.executeQuery()) {
          if (rs.next()) {

            corso = createCorso(rs);

            dataLayer.getCache().add(Corso.class, corso);
          }
        }
      } catch (SQLException ex) {
        throw new DataException("Unable to load Corso by ID", ex);
      }
    }
    return corso;
  }

  @Override
  public void storeCorso(Corso corso) throws DataException {

    try {
      if (corso.getKey() != null && corso.getKey() > 0) { // update

        if (corso instanceof DataItemProxy && !((DataItemProxy) corso).isModified()) {
          return;
        }
        uCorso.setString(1, corso.getNome());
        uCorso.setInt(2, corso.getResponsabile().getKey());

        long current_version = corso.getVersion();
        long next_version = current_version + 1;

        uCorso.setLong(3, next_version);
        uCorso.setInt(4, corso.getKey());
        uCorso.setLong(5, current_version);

        if (uCorso.executeUpdate() == 0) {
          throw new OptimisticLockException(corso);
        } else {
          corso.setVersion(next_version);
        }
      } else { // insert
        iCorso.setString(1, corso.getNome());
        iCorso.setInt(2, corso.getResponsabile().getKey());

        if (iCorso.executeUpdate() == 1) {

          try (ResultSet keys = iCorso.getGeneratedKeys()) {

            if (keys.next()) {

              int key = keys.getInt(1);

              corso.setKey(key);

              dataLayer.getCache().add(Corso.class, corso);
            }
          }
        }
      }

      if (corso instanceof DataItemProxy) {
        ((DataItemProxy) corso).setModified(false);
      }
    } catch (SQLException | OptimisticLockException ex) {
      throw new DataException("Unable to store Corso", ex);
    }
  }

  @Override
  public Corso getCorsoByNome(String nome) throws DataException {
    try {
      sCorsoByNome.setString(1, nome);
      try (ResultSet rs = sCorsoByNome.executeQuery()) {
        if (rs.next()) {
          return getCorso(rs.getInt("ID"));
        }
      }
    } catch (SQLException ex) {
      throw new DataException("Unable to find corso", ex);
    }
    return null;
  }
}
