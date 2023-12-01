package data.dao;

import framework.data.DataException;
import framework.data.DataLayer;

import java.sql.SQLException;
import javax.sql.DataSource;

/**
 *
 * @author Giuseppe Della Penna
 */
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

    //helpers // TODO da modificare
    /**
    public ArticleDAO getArticleDAO() {
        return (ArticleDAO) getDAO(Article.class);
    }

    public AuthorDAO getAuthorDAO() {
        return (AuthorDAO) getDAO(Author.class);
    }

    public IssueDAO getIssueDAO() {
        return (IssueDAO) getDAO(Issue.class);
    }

    public ImageDAO getImageDAO() {
        return (ImageDAO) getDAO(Image.class);
    }
    
     public UserDAO getUserDAO() {
        return (UserDAO) getDAO(User.class);
    }
     **/

}
