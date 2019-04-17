/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author allan
 */
public class ThreadCorrida implements Runnable {
    
    private final Socket cliente;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;
    private static Corrida qualify;
    private static Corrida corrida;
    
    public ThreadCorrida(Socket cliente, ObjectOutputStream out, ObjectInputStream in) {
        this.cliente = cliente;
        this.out = out;
        this.in = in;
    }
    
    @Override
    public void run() {
        Random rand = new Random();
        try {
            
            int protocoloAtual;
            while(true){
                protocoloAtual = (int) input();
                if(protocoloAtual == Protocolo.INICIAR_QUALIFY){
                    qualify = (Corrida) input();
                    Recorde recordeQualify = (Recorde) input();
                    //substituir esse trecho de código pelo recebimento de dados do raspberry
                    float tempoInicial = 0;
                    float maiorTempo;
                    while(tempoInicial < qualify.getTempoDeQualificacao()){
                        qualify.getJogadores().get(0).setTempoVolta(rand.nextFloat()*10+8);
                        qualify.getJogadores().get(1).setTempoVolta(rand.nextFloat()*10+8);
                        qualify.getJogadores().get(2).setTempoVolta(rand.nextFloat()*10+8);
                        qualify.getJogadores().get(3).setTempoVolta(rand.nextFloat()*10+8);
                        if(tempoInicial == 0){
                            qualify.getJogadores().get(0).setVoltaRapida(qualify.getJogadores().get(0).getTempoVolta());
                            qualify.getJogadores().get(1).setVoltaRapida(qualify.getJogadores().get(1).getTempoVolta());
                            qualify.getJogadores().get(2).setVoltaRapida(qualify.getJogadores().get(2).getTempoVolta());
                            qualify.getJogadores().get(3).setVoltaRapida(qualify.getJogadores().get(3).getTempoVolta());
                        }
                        else{
                            if(qualify.getJogadores().get(0).getVoltaRapida()>qualify.getJogadores().get(0).getTempoVolta()){
                                qualify.getJogadores().get(0).setVoltaRapida(qualify.getJogadores().get(0).getTempoVolta());
                            }
                            if(qualify.getJogadores().get(1).getVoltaRapida()>qualify.getJogadores().get(1).getTempoVolta()){
                                qualify.getJogadores().get(1).setVoltaRapida(qualify.getJogadores().get(1).getTempoVolta());
                            }
                            if(qualify.getJogadores().get(2).getVoltaRapida()>qualify.getJogadores().get(2).getTempoVolta()){
                                qualify.getJogadores().get(2).setVoltaRapida(qualify.getJogadores().get(2).getTempoVolta());
                            }
                            if(qualify.getJogadores().get(3).getVoltaRapida()>qualify.getJogadores().get(3).getTempoVolta()){
                                qualify.getJogadores().get(3).setVoltaRapida(qualify.getJogadores().get(3).getTempoVolta());
                            }
                        }
                        maiorTempo = qualify.getJogadores().get(0).getTempoVolta();
                        for(int i = 0; i < qualify.getJogadores().size(); i++){
                            if(qualify.getJogadores().get(i).getTempoVolta()>maiorTempo){
                                maiorTempo = qualify.getJogadores().get(i).getTempoVolta();
                            }
                        }
                        tempoInicial+=maiorTempo;
                        System.out.println("-------------------------------------");
                        for(Jogador j: qualify.getJogadores()){
                            System.out.println(j.toStringComCarro());
                        }
                        output(qualify);
                    }
                    Collections.sort(qualify.getJogadores());
                    output(qualify);
                    System.out.println("---------------Final Da Qualificação-----------------");
                    System.out.println("-----------------------------------------------------");
                    for(Jogador j: qualify.getJogadores()){
                        System.out.println(j.toStringComCarro());
                        if(j.getVoltaRapida()<recordeQualify.getRecorde()){
                            recordeQualify.setRecorde(j.getVoltaRapida());
                        }
                    }
                    output(recordeQualify);
                }else if(protocoloAtual == Protocolo.INICIAR_CORRIDA){
                    //substituir esse trecho de código pelo recebimento de dados do raspberry
                    corrida = (Corrida) input();
                    
                    System.out.println("------------------Grid de Largada--------------------");
                    System.out.println("-----------------------------------------------------");
                    for(Jogador j: corrida.getJogadores()){
                        System.out.println(j.toStringComCarro());
                    }
                    Thread.sleep(1000);
                    System.out.println("3...");
                    Thread.sleep(1000);
                    System.out.println("2...");
                    Thread.sleep(1000);
                    System.out.println("1...");
                    Thread.sleep(1000);
                    System.out.println("GO!!!");
                    List <Integer> rando = new ArrayList<>();
                    for(int j = 1; j<5; j++){
                        rando.add(j);
                    }
                    int voltasInicial = 0;
                    Corrida corrida2 = null;
                    while(voltasInicial < corrida.getQtdVoltas()){
                        corrida2 = corrida; //corrida ordenada por posição
                        for(int j = 0; j < corrida.getJogadores().size(); j++){
                            corrida2.getJogadores().remove(j);
                        }
                        Collections.shuffle(rando);
                        corrida.getJogadores().get(0).setPosicao(rando.get(0));
                        corrida.getJogadores().get(1).setPosicao(rando.get(1));
                        corrida.getJogadores().get(2).setPosicao(rando.get(2));
                        corrida.getJogadores().get(3).setPosicao(rando.get(3));
                        //ordenar por posição
                        for(int i = 0; i < corrida.getJogadores().size(); i++){
                            corrida2.getJogadores().add(corrida.getJogadores().get(i).getPosicao()-1, corrida.getJogadores().get(i));
                        }
                        System.out.println((voltasInicial+1)+" - ################################################################");
                        for(Jogador j: corrida2.getJogadores()){
                            System.out.println(j.toStringComCarro());
                        }
                        output(corrida2);
                        voltasInicial++;
                    }
                    System.out.println("---------------Final Da Corrida-----------------");
                    System.out.println("------------------------------------------------");
                    for(Jogador j: corrida2.getJogadores()){
                        System.out.println(j.toStringComCarro());
                    }
                    output(corrida2);
                }
            }
        } catch (IOException | ClassNotFoundException | InterruptedException ex) {
            Logger.getLogger(ThreadCorrida.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void output(Object msg) throws IOException{
        out.flush();
        out.writeObject(msg);
        out.reset();
    }
    
    public Object input() throws IOException, ClassNotFoundException {
        return in.readObject();
    }
    
    
}
