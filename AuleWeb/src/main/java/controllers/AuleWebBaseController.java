package controllers;

import data.dao.AuleWebDataLayer;
import framework.controller.AbstractBaseController;
import framework.data.DataLayer;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.sql.DataSource;


public abstract class AuleWebBaseController extends AbstractBaseController {

    @Override
    protected DataLayer createDataLayer(DataSource ds) throws ServletException {
        try {
            return new AuleWebDataLayer(ds);
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

}
