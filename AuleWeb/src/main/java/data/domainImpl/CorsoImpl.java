/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data.domainImpl;

import data.domain.Corso;

/**
 *
 * @author user
 */
public class CorsoImpl implements Corso {
    
    private int id;
    private String nome;
    private ResponsabileImpl responsabile;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public ResponsabileImpl getResponsabile() {
        return responsabile;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public void setResponsabile(ResponsabileImpl responsabile) {
        this.responsabile = responsabile;
    }
    
}
