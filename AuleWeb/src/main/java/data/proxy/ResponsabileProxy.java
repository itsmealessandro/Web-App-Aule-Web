/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data.proxy;

import data.domainImpl.ResponsabileImpl;
import framework.data.DataItemProxy;
import framework.data.DataLayer;

/**
 *
 * @author Administrator
 */
public class ResponsabileProxy extends ResponsabileImpl implements DataItemProxy {
    protected boolean modified;
     protected DataLayer dataLayer;
     
     public ResponsabileProxy(DataLayer d) {
        super();
        this.dataLayer = d;
        this.modified = false;
    }    
     @Override
    public void setId(int id) {
        super.setId(id);
        this.modified = true;
    }

    @Override
    public void setNome(String nome) {
        super.setNome(nome);
        this.modified = true;
    }

    @Override
    public void setCognome(String cognome) {
        super.setCognome(cognome);
        this.modified = true;
    }

    @Override
    public void setEmail(String email) {
        super.setEmail(email);
        this.modified = true;
    }

//METODI DEL PROXY
    //PROXY-ONLY METHODS
    @Override
    public void setModified(boolean dirty) {
        this.modified = dirty;
    }

    @Override
    public boolean isModified() {
        return modified;
    }
}
