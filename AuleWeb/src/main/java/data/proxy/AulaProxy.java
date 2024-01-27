package data.proxy;

import data.dao.AttrezzaturaDAO;
import data.dao.DipartimentoDAO;
import data.dao.ResponsabileDAO;
import data.domain.Attrezzatura;
import data.domain.Dipartimento;
import data.domain.Responsabile;
import data.domainImpl.AulaImpl;
import framework.data.DataException;
import framework.data.DataItemProxy;
import framework.data.DataLayer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AulaProxy extends AulaImpl implements DataItemProxy {

  protected boolean modified;
  protected DataLayer dataLayer;

  protected int dipartimento_key = 0;
  protected int attrezzatura_key = 0;
  protected int responsabile_key = 0;

  public AulaProxy(DataLayer d) {
    super();
    this.dataLayer = d;
    this.modified = false;
    this.attrezzatura_key = 0;
    this.dipartimento_key = 0;
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
  public void setPreseElettriche(int preseElettriche) {
    super.setPreseElettriche(preseElettriche);
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
  public Attrezzatura getAttrezzatura() {
    if (super.getAttrezzatura() == null && attrezzatura_key > 0) {
      try {
        super.setAttrezzatura(
            ((AttrezzaturaDAO) dataLayer.getDAO(Attrezzatura.class)).getAttrezzatura(attrezzatura_key));
      } catch (DataException ex) {
        Logger.getLogger(AulaProxy.class.getName()).log(Level.SEVERE, null, ex);
      }

    }
    return super.getAttrezzatura();
  }

  @Override
  public void setAttrezzatura(Attrezzatura attrezzatura) {
    super.setAttrezzatura(attrezzatura);
    this.attrezzatura_key = attrezzatura.getKey();
    this.modified = true;
  }

  @Override
  public Dipartimento getDipartimento() {

    if (super.getDipartimento() == null && dipartimento_key > 0) {
      try {
        super.setDipartimento(
            ((DipartimentoDAO) dataLayer.getDAO(Dipartimento.class)).getDipartimento(dipartimento_key));
      } catch (DataException ex) {
        Logger.getLogger(AulaProxy.class.getName()).log(Level.SEVERE, null, ex);
      }

    }
    return super.getDipartimento();
  }

  @Override
  public void setDipartimento(Dipartimento dipartimento) {
    super.setDipartimento(dipartimento);
    this.dipartimento_key = dipartimento.getKey();
    this.modified = true;
  }

  @Override
  public Responsabile getResponsabile() {

    if (super.getResponsabile() == null && responsabile_key > 0) {
      try {
        super.setResponsabile(
            ((ResponsabileDAO) dataLayer.getDAO(Responsabile.class)).getResponsabile(responsabile_key));
      } catch (DataException ex) {
        Logger.getLogger(AulaProxy.class.getName()).log(Level.SEVERE, null, ex);
      }

    }
    return super.getResponsabile();
  }

  @Override
  public void setResponsabile(Responsabile responsabile) {
    super.setResponsabile(responsabile);
    this.responsabile_key = responsabile.getKey();
    this.modified = true;
  }

  // METODI DEL PROXY
  // PROXY-ONLY METHODS
  @Override
  public void setModified(boolean dirty) {
    this.modified = dirty;
  }

  @Override
  public boolean isModified() {
    return modified;
  }

  public void setDipartimentoKey(int dipartimento_key) {
    this.dipartimento_key = dipartimento_key;
    super.setDipartimento(null);
  }

  public void setAttrezzaturaKey(int attrezzatura_key) {
    this.attrezzatura_key = attrezzatura_key;
    super.setAttrezzatura(null);
  }

  public void setResponsabileKey(int responsabile_key) {
    this.responsabile_key = responsabile_key;
    super.setResponsabile(null);
  }
}
