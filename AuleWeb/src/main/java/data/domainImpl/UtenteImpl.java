/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data.domainImpl;

import data.domain.Utente;

/**
 *
 * @author alessandro
 */
public class UtenteImpl implements Utente {
    private int id;
    private String username;
    private String password;
    private RuoloImpl ruolo;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public RuoloImpl getRuolo() {
        return ruolo;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public void setRuolo(RuoloImpl ruolo) {
        this.ruolo = ruolo;
    }
    
}
