/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data.domain;

import framework.data.DataItem;

/**
 *
 * @author Administrator
 */
public interface Attrezzatura extends DataItem<Integer>{
    public int getId();

    public void setId(int id);

    public String getNome();

    public void setNome(String nome);
}
