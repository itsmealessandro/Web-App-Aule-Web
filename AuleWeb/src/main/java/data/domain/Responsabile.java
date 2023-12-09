package data.domain;

import framework.data.DataItem;

public interface Responsabile extends DataItem<Integer>{

    public String getNome();

    public String getCognome();

    public String getEmail();

    public void setNome(String nome);

    public void setCognome(String cognome);

    public void setEmail(String email);

}
