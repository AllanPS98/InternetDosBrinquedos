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
public class Jogador implements Serializable, Comparable{
    private int posicao;
    private int voltas; //voltas que ele já percorreu
    private float tempoVolta;
    private float tempoCorrida;
    private String equipe;
    private float voltaRapida;
    private int pits;
    private String nome;
    private Carro carro;

    public Jogador(String nome, String equipe) {
        
        this.equipe = equipe;
        this.nome = nome;
    }

    public int getPosicao() {
        return posicao;
    }

    public void setPosicao(int posicao) {
        this.posicao = posicao;
    }

    public int getVoltas() {
        return voltas;
    }

    public void setVoltas(int voltas) {
        this.voltas = voltas;
    }

    public float getTempoVolta() {
        return tempoVolta;
    }

    public void setTempoVolta(float tempoVolta) {
        this.tempoVolta = tempoVolta;
    }

    public float getTempoCorrida() {
        return tempoCorrida;
    }

    public void setTempoCorrida(float tempoCorrida) {
        this.tempoCorrida = tempoCorrida;
    }

    public String getEquipe() {
        return equipe;
    }

    public void setEquipe(String equipe) {
        this.equipe = equipe;
    }

    public float getVoltaRapida() {
        return voltaRapida;
    }

    public void setVoltaRapida(float voltaRapida) {
        this.voltaRapida = voltaRapida;
    }

    public int getPits() {
        return pits;
    }

    public void setPits(int pits) {
        this.pits = pits;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public Carro getCarro() {
        return carro;
    }

    public void setCarro(Carro carro) {
        this.carro = carro;
        this.carro.setEquipe(this.equipe);
    }
    @Override
    public String toString() {
        return "Jogador{" + "equipe=" + equipe + ", nome=" + nome + '}';
    }
    
    public String toStringComCarro() {
        return "Jogador{" + "equipe=" + equipe + ", nome=" + nome + ", carro=" + carro.toString() + '}';
    }

    @Override
    public int compareTo(Object t) {
        Jogador jog = (Jogador) t;
        if(this.voltaRapida < jog.getVoltaRapida()){
            return -1;
        }else if(this.voltaRapida > jog.getVoltaRapida()){
            return 1;
        }
        return 0;
    }
}
