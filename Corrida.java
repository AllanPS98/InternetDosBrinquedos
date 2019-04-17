/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author allan
 */
public class Corrida implements Serializable{
    private int qtdVoltas;
    private final List<Jogador> jogadores;
    private float tempoDeQualificacao;
    private float recorde;
    private String recordista;

    public Corrida(List<Jogador> jogadores) {
        this.jogadores = jogadores;
    }

    public int getQtdVoltas() {
        return qtdVoltas;
    }

    public void setQtdVoltas(int qtdVoltas) {
        this.qtdVoltas = qtdVoltas;
    }

    public List<Jogador> getJogadores() {
        return jogadores;
    }

    
    public float getRecorde() {
        return recorde;
    }

    public void setRecorde(float recorde) {
        this.recorde = recorde;
    }

    public String getRecordista() {
        return recordista;
    }

    public void setRecordista(String recordista) {
        this.recordista = recordista;
    }

    public float getTempoDeQualificacao() {
        return tempoDeQualificacao;
    }

    public void setTempoDeQualificacao(float tempoDeQualificacao) {
        this.tempoDeQualificacao = tempoDeQualificacao;
    }
    
    public boolean mostrarQualify(){
        if(jogadores!=null){
            System.out.println("Tempo de qualificação -> "+tempoDeQualificacao+" seg");
            for(Jogador jog: jogadores){
                System.out.println(jog.toStringComCarro());
            }
            return true;
        }
        System.out.println("Não existe corrida");
        return false;
    }
    
    
    
}
