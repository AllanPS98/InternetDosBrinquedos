/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import java.io.Serializable;

/**
 *
 * @author allan
 */
public class Recorde implements Serializable{
    private String nome;
    private float recorde;

    public Recorde(String nome, float recorde) {
        this.nome = nome;
        this.recorde = recorde;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public float getRecorde() {
        return recorde;
    }

    public void setRecorde(float recorde) {
        this.recorde = recorde;
    }
    
}
