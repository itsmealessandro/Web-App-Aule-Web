package data.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import data.domain.Dipartimento;
import data.proxy.DipartimentoProxy;
import framework.data.DAO;
import framework.data.DataException;
import framework.data.DataItemProxy;
import framework.data.DataLayer;
import framework.data.OptimisticLockException;

public class DipartimentoDAO_Database extends DAO implements DipartimentoDAO {

  private PreparedStatement iDipartimento, uDipartimento, sDipartimentoByID, sAllDipartimenti, sDipartimentoByNome;

  public DipartimentoDAO_Database(DataLayer d) {
    super(d);
  }

  @Override
  public void init() throws DataException {
    try {
      super.init();

      sDipartimentoByID = connection.prepareStatement("SELECT * FROM Dipartimento WHERE ID=?");
      sDipartimentoByNome = connection.prepareStatement("SELECT * FROM Dipartimento WHERE nome=?");
      iDipartimento = connection.prepareStatement("INSERT INTO Dipartimento (nome,descrizione) VALUES(?,?)",
          Statement.RETURN_GENERATED_KEYS);
      uDipartimento = connection
          .prepareStatement("UPDATE Dipartimento SET nome=?,descrizione=?,version=? WHERE ID=? and version=?");
      sAllDipartimenti = connection.prepareStatement("SELECT * FROM Dipartimento");
    } catch (SQLException ex) {
      throw new DataException("Error initializing AuleWeb data layer", ex);
    }
  }

  @Override
  public void destroy() throws DataException {
    try {
      sDipartimentoByID.close();
      sDipartimentoByNome.close();
      sAllDipartimenti.close();
      iDipartimento.close();
      uDipartimento.close();

    } catch (SQLException ex) {
      throw new DataException("Error in Destroy", ex);
    }
    super.destroy();
  }

  @Override
  public Dipartimento createDipartimento() {
    return new DipartimentoProxy(getDataLayer());
  }

  private DipartimentoProxy createDipartimento(ResultSet rs) throws DataException {
    try {
      DipartimentoProxy dipartimentoProxy = (DipartimentoProxy) createDipartimento();
      dipartimentoProxy.setKey(rs.getInt("ID"));
      dipartimentoProxy.setNome(rs.getString("nome"));
      dipartimentoProxy.setDescrizione(rs.getString("descrizione"));
      dipartimentoProxy.setVersion(rs.getLong("version"));
      return dipartimentoProxy;
    } catch (SQLException ex) {
      throw new DataException("Unable to create dipartimento object form ResultSet", ex);
    }
  }

  @Override
  public Dipartimento getDipartimento(int dipartimento_key) throws DataException {
    Dipartimento dipartimento = null;

    if (dataLayer.getCache().has(Dipartimento.class, dipartimento_key)) {

      dipartimento = dataLayer.getCache().get(Dipartimento.class, dipartimento_key);
    } else {
      try {
        sDipartimentoByID.setInt(1, dipartimento_key);
        try (ResultSet rs = sDipartimentoByID.executeQuery()) {
          if (rs.next()) {

            dipartimento = createDipartimento(rs);

            dataLayer.getCache().add(Dipartimento.class, dipartimento);
          }
        }
      } catch (SQLException ex) {
        throw new DataException("Unable to load Dipartimento by ID", ex);
      }
    }
    return dipartimento;
  }

  @Override
  public Dipartimento getDipartimentoByNome(String nomeDip) throws DataException {
    Dipartimento dipartimento = null;
    try {
      sDipartimentoByNome.setString(1, nomeDip);
      try (ResultSet rs = sDipartimentoByNome.executeQuery()) {
        while (rs.next()) {
          dipartimento = getDipartimento(rs.getInt("ID"));
        }
      }
    } catch (SQLException e) {
      throw new DataException("Unable to load Dipartimento by Nome", e);
    }

    return dipartimento;
  }

  @Override
  public List<Dipartimento> getAllDipartimenti() throws DataException {

    List<Dipartimento> listaDipartimenti = new ArrayList<>();

    try (ResultSet resultSet = sAllDipartimenti.executeQuery()) {
      while (resultSet.next()) {
        listaDipartimenti.add((Dipartimento) getDipartimento(resultSet.getInt("ID")));
      }

    } catch (SQLException e) {
      throw new DataException("Unable to load all dipartimenti", e);
    }

    return listaDipartimenti;
  }

  @Override
  public void storeDipartimento(Dipartimento a) throws DataException {

    try {
      if (a.getKey() != null && a.getKey() > 0) { // update

        if (a instanceof DataItemProxy && !((DataItemProxy) a).isModified()) {
          return;
        }
        uDipartimento.setString(1, a.getNome());
        uDipartimento.setString(2, a.getDescrizione());

        long current_version = a.getVersion();
        long next_version = current_version + 1;

        uDipartimento.setLong(3, next_version);
        uDipartimento.setInt(4, a.getKey());
        uDipartimento.setLong(5, current_version);

        if (uDipartimento.executeUpdate() == 0) {
          throw new OptimisticLockException(a);
        } else {
          a.setVersion(next_version);
        }
      } else { // insert
        iDipartimento.setString(1, a.getNome());
        iDipartimento.setString(2, a.getDescrizione());

        if (iDipartimento.executeUpdate() == 1) {

          try (ResultSet keys = iDipartimento.getGeneratedKeys()) {

            if (keys.next()) {

              int key = keys.getInt(1);
              a.setKey(key);

              dataLayer.getCache().add(Dipartimento.class, a);
            }
          }
        }
      }

      if (a instanceof DataItemProxy) {
        ((DataItemProxy) a).setModified(false);
      }
    } catch (SQLException | OptimisticLockException ex) {
      throw new DataException("Unable to store Dipartimento", ex);
    }
  }
}
