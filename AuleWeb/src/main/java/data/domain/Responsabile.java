/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data.domain;

/**
 *
 * @author Administrator
 */
public interface Responsabile {
      public int getId();

    public String getNome();

    public String getCognome();

    public String getEmail();

    public void setId(int id);

    public void setNome(String nome);

    public void setCognome(String cognome);

    public void setEmail(String email);

}
