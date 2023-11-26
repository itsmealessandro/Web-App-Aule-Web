/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data.domain;

/**
 *
 * @author user
 */
public class Corso {
    private int id;
    private String nome;
    private Responsabile responsabile;

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Responsabile getResponsabile() {
        return responsabile;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setResponsabile(Responsabile responsabile) {
        this.responsabile = responsabile;
    }
    
}
