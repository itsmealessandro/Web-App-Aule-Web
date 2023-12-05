/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data.proxy;

import data.domainImpl.AttrezzaturaImpl;
import framework.data.DataItemProxy;
import framework.data.DataLayer;

/**
 *
 * @author Administrator
 */
public class AttrezzaturaProxy extends AttrezzaturaImpl implements DataItemProxy{
    
     protected boolean modified;
     protected DataLayer dataLayer;
     
     public AttrezzaturaProxy(DataLayer d) {
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
