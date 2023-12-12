/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data.domainImpl;

import data.domain.Corso;
import framework.data.DataItemImpl;

/**
 *
 * @author user
 */
public class CorsoImpl extends DataItemImpl<Integer> implements Corso {
    
    private String nome;
    private ResponsabileImpl responsabile;

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public ResponsabileImpl getResponsabile() {
        return responsabile;
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
