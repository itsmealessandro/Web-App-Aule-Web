/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package template;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 *
 * @author alessandro
 */
public class ModelloFTL {

    private ServletContext servletContext;
    private Configuration configuration;

    public ModelloFTL(ServletContext servletContext) {

        this.servletContext = servletContext;
        this.initialize();

    }

    // metodo che prepara freemaker a gestire le viste
    private void initialize() {
        try {
            // Configura FreeMarker
            configuration = configureFTL();

            // Carica il contesto per trovare i templates
            configuration.setServletContextForTemplateLoading(servletContext, "/templates");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // metodo per configurare elementi fondamentali di freemarker
    public static Configuration configureFTL() {
        Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(ModelloFTL.class, "/templates");
        configuration.setDefaultEncoding("UTF-8");
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        return configuration;
    }

    
    // metodo che renderizza le viste
    public void renderview(String viewName, Map localdatamodel , HttpServletRequest request, HttpServletResponse response, Writer out) {

        try {
            
            localdatamodel.put("layout", "layout.ftl.html");

            // prendo il file html corrispettivo alla stringa
            Template t = configuration.getTemplate("layout.ftl.html");
            
            // inseriamo la vista dentro il layout
            localdatamodel.put("center_view", viewName);
            
            // inseriamo un parametro a caso giusto per vedere che si può fare
            // localdatamodel.put("argomento","sono l'argomento");

            // lancio la vista
            t.process(localdatamodel, out);

        } catch (TemplateException ex) {
            Logger.getLogger(ModelloFTL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ModelloFTL.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
