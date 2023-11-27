/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data.domain;

import data.domainImpl.RuoloImpl;

/**
 *
 * @author Administrator
 */
public interface Utente {
     public int getId();

    public String getUsername();

    public String getPassword();

    public RuoloImpl getRuolo();

    public void setId(int id);

    public void setUsername(String username);

    public void setPassword(String password);

    public void setRuolo(RuoloImpl ruolo);
    
}
