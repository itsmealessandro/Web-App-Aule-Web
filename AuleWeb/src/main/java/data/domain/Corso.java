/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data.domain;

import data.domainImpl.ResponsabileImpl;
import framework.data.DataItem;

/**
 *
 * @author Administrator
 */
public interface Corso extends DataItem<Integer> {
     
    public String getNome();

    public ResponsabileImpl getResponsabile();

    public void setNome(String nome);

    public void setResponsabile(ResponsabileImpl responsabile);
}
