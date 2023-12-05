/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data.proxy;

import data.domainImpl.RuoloImpl;
import data.domainImpl.UtenteImpl;
import framework.data.DataItemProxy;
import framework.data.DataLayer;


/**
 *
 * @author Administrator
 */
public class UtenteProxy extends UtenteImpl implements DataItemProxy {
    protected boolean modified;
     protected DataLayer dataLayer;
     
     public UtenteProxy(DataLayer d) {
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
    public void setUsername(String username) {
        super.setUsername(username);
        this.modified = true;
    }

    @Override
    public void setPassword(String password) {
        super.setPassword(password);
        this.modified = true;
    }

    @Override
    public void setRuolo(RuoloImpl ruolo) {
        super.setRuolo(ruolo);
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
