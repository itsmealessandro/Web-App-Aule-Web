package data.proxy;

import data.domainImpl.ResponsabileImpl;
import framework.data.DataItemProxy;
import framework.data.DataLayer;

public class ResponsabileProxy extends ResponsabileImpl implements DataItemProxy {
    protected boolean modified;
     protected DataLayer dataLayer;
     
     public ResponsabileProxy(DataLayer d) {
        super();
        this.dataLayer = d;
        this.modified = false;
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
