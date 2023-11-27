/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data.domain;

import data.domainImpl.AttrezzaturaImpl;
import data.domainImpl.DipartimentoImpl;

/**
 *
 * @author Administrator
 */
public interface Aula {
    public int getId();

    public void setId(int id);

    public String getNome();

    public void setNome(String nome);

    public String getLuogo();

    public void setLuogo(String luogo);

    public String getEdificio();
    
    public void setEdificio(String edificio);

    public String getPiano();

    public void setPiano(String piano);

    public int getCapienza();

    public void setCapienza(int capienza);
    
    public int getPresaElettrica();

    public void setPresaElettrica(int presaElettrica);
    
    public int getPreseRete();

    public void setPreseRete(int preseRete);

    public String getNote();

    public void setNote(String note);

    public AttrezzaturaImpl getAttrezzatura();

    public void setAttrezzatura(AttrezzaturaImpl attrezzatura);

    public DipartimentoImpl getDipartimento();

    public void setDipartimento(DipartimentoImpl dipartimento);
}
