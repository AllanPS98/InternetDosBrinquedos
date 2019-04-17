/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import java.awt.Color;
import java.io.Serializable;

/**
 *
 * @author allan
 */
public class Carro implements Serializable{
    private int numero;
    private Color cor;
    private String equipe;

    public Carro(int numero, Color cor) {
        this.numero = numero;
        this.cor = cor;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Color getCor() {
        return cor;
    }

    public void setCor(Color cor) {
        this.cor = cor;
    }

    public String getEquipe() {
        return equipe;
    }

    public void setEquipe(String equipe) {
        this.equipe = equipe;
    }

    @Override
    public String toString() {
        return "Carro{" + "numero=" + numero + ", cor=" + cor + ", equipe=" + equipe + '}';
    }

    
    
    
            
}
