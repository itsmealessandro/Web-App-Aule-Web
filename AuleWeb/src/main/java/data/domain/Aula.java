package data.domain;

import data.domainImpl.AttrezzaturaImpl;
import data.domainImpl.DipartimentoImpl;
import framework.data.DataItem;

public interface Aula extends DataItem<Integer>{

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
    
    public int getPreseElettriche();

    public void setPreseElettriche(int preseElettriche);
    
    public int getPreseRete();

    public void setPreseRete(int preseRete);

    public String getNote();

    public void setNote(String note);

    public AttrezzaturaImpl getAttrezzatura();

    public void setAttrezzatura(AttrezzaturaImpl attrezzatura);

    public DipartimentoImpl getDipartimento();

    public void setDipartimento(DipartimentoImpl dipartimento);
}
