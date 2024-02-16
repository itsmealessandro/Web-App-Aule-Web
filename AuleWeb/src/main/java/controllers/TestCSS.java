
package controllers;

import data.dao.AuleWebDataLayer;
import framework.data.DataException;
import framework.result.TemplateManagerException;
import framework.result.TemplateResult;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TestCSS extends AuleWebBaseController {

     private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
         
            TemplateResult res = new TemplateResult(getServletContext());
            res.activate("testCSS.ftl.html", request, response);
    }

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {

        try {
            action_default(request, response);

        } catch (IOException | TemplateManagerException ex) {
            handleError(ex, request, response);
        }
    }
}
