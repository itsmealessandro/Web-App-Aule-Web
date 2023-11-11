/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import framework.result.TemplateManagerException;
import framework.result.TemplateResult;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author alessandro
 */
public class Welcome extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private String getDBInfo() {

        String info = "";
        
        System.out.print("connessione stabilita pre cose");

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            try (
                    Connection c = DriverManager.getConnection("jdbc:mysql://localhost/auleDB?connectionTimeZone=LOCAL","root","SQLpass");
                    Statement s = c.createStatement();
                    ResultSet r = s.executeQuery("SELECT * FROM Aule");) {
                
                    
                    r.next(); 
                    
                
                info = r.getString("Nome");

            } catch (SQLException ex) {
                Logger.getLogger(Welcome.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Welcome.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        System.out.print("connessione stabilita");
        return info;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String info = this.getDBInfo();

        // Inoltre, specifica il nome del template Freemarker da utilizzare per la pagina di benvenuto.
        String templateName = "welcome.ftl.html";
        
        // Qui puoi utilizzare la classe TemplateResult per attivare il template e generare la risposta.
        TemplateResult templateResult = new TemplateResult(getServletContext());
        try {
            templateResult.activate(templateName, request, response);
        } catch (TemplateManagerException ex) {
            Logger.getLogger(Welcome.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
