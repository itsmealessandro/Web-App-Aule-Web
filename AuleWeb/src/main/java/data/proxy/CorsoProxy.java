/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data.proxy;

import data.domainImpl.CorsoImpl;
import framework.data.DataItemProxy;
import framework.data.DataLayer;

/**
 *
 * @author Administrator
 */
public class CorsoProxy extends CorsoImpl implements DataItemProxy {
   
    protected boolean modified;
     protected DataLayer dataLayer;
     protected int responsabile_key = 0;
     
     public CorsoProxy(DataLayer d) {
        super();
        this.dataLayer = d;
        this.modified = false;
        this.responsabile_key = 0;
    }    

     @Override
    public void setKey(Integer key) {
        super.setKey(key);
        this.modified = true;
    }
     
    @Override
    public void setNome(String nome) {
        super.setNome(nome);
        this.modified = true;
    }

    
    public void setResponsabileKey( int responsabile_key) {
        this.responsabile_key = responsabile_key;
        super.setResponsabile(null);
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
