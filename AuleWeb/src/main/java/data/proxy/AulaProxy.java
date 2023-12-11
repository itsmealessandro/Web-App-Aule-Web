/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data.proxy;

import data.domainImpl.AttrezzaturaImpl;
import data.domainImpl.AulaImpl;
import data.domainImpl.DipartimentoImpl;
import framework.data.DataItemProxy;
import framework.data.DataLayer;

/**
 *
 * @author Administrator
 */
public class AulaProxy extends AulaImpl implements DataItemProxy {
        
     protected boolean modified;
     protected DataLayer dataLayer;
     
     public AulaProxy(DataLayer d) {
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
    public void setLuogo(String luogo) {
        super.setLuogo(luogo);
        this.modified = true;

    }

    @Override
    public void setEdificio(String edificio) {
        super.setEdificio(edificio);
        this.modified = true;

    }

    @Override
    public void setPiano(String piano) {
        super.setPiano(piano);
        this.modified = true;
    }

    @Override
    public void setCapienza(int capienza) {
        super.setCapienza(capienza);
        this.modified = true;
    }

    @Override
    public void setPresaElettrica(int presaElettrica) {
        super.setPresaElettrica(presaElettrica);
        this.modified = true;
    }

    @Override
    public void setPreseRete(int preseRete) {
        super.setPreseRete(preseRete);
        this.modified = true;
    }

    @Override
    public void setNote(String note) {
        super.setNote(note);
        this.modified = true;
    }
    @Override
    public void setAttrezzatura(AttrezzaturaImpl attrezzatura) {
        super.setAttrezzatura(attrezzatura);
        this.modified = true;
    }

    @Override
    public void setDipartimento(DipartimentoImpl dipartimento) {
        super.setDipartimento(dipartimento);
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
