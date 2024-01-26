package controllers;

import data.dao.AuleWebDataLayer;
import data.domain.Amministratore;
import framework.data.DataException;
import framework.result.TemplateManagerException;
import framework.result.TemplateResult;
import framework.security.SecurityHelpers;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ema
 */
public class Login extends AuleWebBaseController {

    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, TemplateManagerException {
        TemplateResult result = new TemplateResult(getServletContext());
        request.setAttribute("referrer", request.getParameter("referrer"));
        result.activate("login.ftl.html", request, response);
    }

    //nota: usente di default nel database: nome a, password p
    //note: default user in the database: name: a, password p
    private void action_login(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String username = request.getParameter("u");
    String password = request.getParameter("p");
    
    if (!username.isEmpty() && !password.isEmpty()) {
        try {
            AuleWebDataLayer dataLayer = (AuleWebDataLayer) request.getAttribute("datalayer");
            Amministratore a = dataLayer.getAmministratoreDAO().getAmministratoreByUsername(username);

            if (a != null && SecurityHelpers.checkPasswordHashPBKDF2(password, a.getPassword())) {
                // Login riuscito
                SecurityHelpers.createSession(request, username, a.getKey());
                
                // Se è stato trasmesso un URL di origine, torniamo a quell'indirizzo
                if (request.getParameter("referrer") != null) {
                    response.sendRedirect(request.getParameter("referrer"));
                } else {
                    
                    response.sendRedirect("Homepage");
                }
                return;
            }
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | DataException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Se la validazione fallisce...
    handleError("Login failed", request, response);
}


    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws javax.servlet.ServletException
     */
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        try {
            if (request.getParameter("login") != null) {
                action_login(request, response);
            } else {
                String https_redirect_url = SecurityHelpers.checkHttps(request);
                request.setAttribute("https-redirect", https_redirect_url);
                action_default(request, response);
            }
        } catch (IOException | TemplateManagerException ex) {
            handleError(ex, request, response);
        }
    }
}
