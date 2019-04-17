/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import java.awt.Color;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author allan
 */
public class ThreadClient implements Runnable {
    private final Socket cliente;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;
    private static List<Jogador> participantes = new LinkedList();
   
    ThreadClient(Socket cliente, ObjectOutputStream out, ObjectInputStream in) {
        this.cliente = cliente;
        this.out = out;
        this.in = in;
    }

    @Override
    public void run() {
        int protocoloAtual;
        while(true){
            Scanner scan = new Scanner(System.in);
            System.out.println(" -------------Menu-------------- ");
            System.out.println("| 1 - Sair                      |");
            System.out.println("| 2 - Cadastro de Usuário       |");
            System.out.println("| 3 - Cadastro de Administrador |");
            System.out.println("| 4 - Cadastro de Corrida       |");
            System.out.println("| 5 - Cadastro de Carro         |");
            System.out.println("| 6 - Listar dados              |");
            System.out.println("| 7 - Inicializar corrida       |");
            System.out.println("| 8 - Inicializar qualificação  |");
            System.out.println("| 9 - Finalizar corrida         |");
            System.out.println(" ------------------------------- ");
            System.out.print("---> ");
            protocoloAtual = scan.nextInt();
            if(protocoloAtual == Protocolo.SAIR){
                try {
                    output(Protocolo.SAIR);
                } catch (IOException ex) {
                    Logger.getLogger(ThreadClient.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    out.close();
                } catch (IOException ex) {
                    Logger.getLogger(ThreadClient.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    cliente.close();
                } catch (IOException ex) {
                    Logger.getLogger(ThreadClient.class.getName()).log(Level.SEVERE, null, ex);
                }
                    System.out.println("CLIENTE DESCONECTADO !!!!!");
                    break;
                
            }
            else if(protocoloAtual == Protocolo.CADASTRO_ADM){
                System.out.print("Digite o nome do administrador: ");
                String nome = scan.next();
                System.out.println();
                System.out.print("Digite a senha do administrador: ");
                String senha = scan.next();
                System.out.println();
                Administrador adm = new Administrador(nome, senha);
                try {
                    output(Protocolo.CADASTRO_ADM);
                    output(adm);
                } catch (IOException ex) {
                    Logger.getLogger(ThreadClient.class.getName()).log(Level.SEVERE, null, ex);
                } 
            }else if(protocoloAtual == Protocolo.CADASTRO_USUARIO){
                System.out.print("Digite o seu nome: ");
                String nome = scan.next();
                System.out.println();
                System.out.print("Digite o nome da sua equipe: ");
                String equipe = scan.next();
                System.out.println();
                Jogador jog = new Jogador(nome,equipe);
                try {
                    output(Protocolo.CADASTRO_USUARIO);
                    System.out.println(jog.toString());
                    output(jog);
                    boolean cadastrou = (boolean) input();
                    if(cadastrou){
                        System.out.println("Jogador cadastrado com sucesso");
                    }else{
                        System.out.println("Jogador não cadastrado. Tente um nome diferente.");
                    }
                } catch (IOException | ClassNotFoundException ex) {
                    Logger.getLogger(ThreadClient.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else if(protocoloAtual == Protocolo.CADASTRO_CARRO){
                System.out.print("Digite o número do carro: ");
                int numero = scan.nextInt();
                System.out.println();
                System.out.print("Digite o nome da cor do carro: ");
                String cores = scan.next();
                System.out.println();
                Color cor = identificarCor(cores);
                if(cor!=null){
                    Carro carro = new Carro(numero,cor);
                    try {
                        output(Protocolo.CADASTRO_CARRO);
                        System.out.println(carro.toString());
                        output(carro);
                    } catch (IOException ex) {
                        Logger.getLogger(ThreadClient.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else{
                    System.out.println("Cor inválida, tente novamente");
                }
            }else if(protocoloAtual == Protocolo.LISTAR){
                try {
                    output(protocoloAtual);
                } catch (IOException ex) {
                    Logger.getLogger(ThreadClient.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            // cadastro da corrida e do qualify
            else if(protocoloAtual == Protocolo.CORRIDA){
                try {
                    output(protocoloAtual);
                } catch (IOException ex) {
                    Logger.getLogger(ThreadClient.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("Digite o tempo máximo de qualificação em segundos: ");
                int tempo = scan.nextInt();
                System.out.println("Digite a quantidade de voltas da corrida: ");
                int voltas = scan.nextInt();
                System.out.println("Selecione os jogadores que participarão da partida: ");
                List<Jogador> jogadores = null;
                List<Carro> carros = null;
                try {
                    jogadores = (List<Jogador>) input();
                    carros = (List<Carro>) input();
                    System.out.println("-------Lista de jogadores-----");
                    for(Jogador jog: jogadores){
                        System.out.println(jog.toString());
                    }
                    System.out.println("--------Lista de carros-----");
                    for(Carro car : carros){
                        System.out.println(car.toString());
                    }
                } catch (IOException | ClassNotFoundException ex) {
                    Logger.getLogger(ThreadClient.class.getName()).log(Level.SEVERE, null, ex);
                }
                int sair;
                // loop responsável pela seleção de jogadores e de carros para os jogadores
                do{
                    boolean existeJogador = false;
                    boolean existeCarro = false;
                    boolean podeAddJogador = false;
                    boolean podeAddCarro = false;
                    System.out.println("Digite o nome para adicionar o jogador:");
                    String nome = scan.next().toLowerCase();
                    for(Jogador jog: participantes){
                        if(jog.getNome().equals(nome)){
                            existeJogador = true;
                        }
                    }
                    if(existeJogador==false){
                        for(Jogador jog: jogadores){
                            if(jog.getNome().equals(nome)){
                                podeAddJogador = true;
                            }
                        }
                        System.out.println("Digite o número do carro que deseja dar a esse jogador: ");
                        int numero = scan.nextInt();
                        for(Jogador jog: jogadores){
                            if(jog.getCarro()!=null){
                                if(jog.getCarro().getNumero() == numero){
                                    existeCarro = true;
                                }
                            }
                        }
                        if(existeCarro==false){
                            for(Carro carro: carros){
                                if(carro.getNumero() == numero){
                                    podeAddCarro = true;
                                }
                            }
                        }else{
                            System.out.println("Já existe algum jogador que possui esse carro");
                        }
                        if(podeAddCarro==true && podeAddJogador==true){
                            for(Jogador jog: jogadores){
                                if(jog.getNome().equals(nome)){
                                    for(Carro carro: carros){
                                        if(carro.getNumero()==numero){
                                            jog.setCarro(carro);
                                            participantes.add(jog);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    else{
                        System.out.println("Esse jogador já está na corrida, selecione outro");
                    }
                    
                    System.out.println("Terminou a seleção? 1-Sim / 2-Não");
                    sair = scan.nextInt();
                }while(sair != 1);
                try {
                    Corrida qualify = new Corrida(participantes);
                    qualify.setTempoDeQualificacao(tempo);
                    qualify.setQtdVoltas(voltas);
                    output(qualify);
                } catch (IOException ex) {
                    Logger.getLogger(ThreadClient.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else if(protocoloAtual == Protocolo.FINALIZAR_CORRIDA){
                try {
                    participantes = new LinkedList();
                    output(protocoloAtual);
                } catch (IOException ex) {
                    Logger.getLogger(ThreadClient.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        
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
    
    public Color identificarCor(String cor) {
        cor = cor.toLowerCase();
        switch(cor){
            case "vermelho":
                return Color.RED;
            case "azul":
                return Color.BLUE;
            case "amarelo":
                return Color.YELLOW;
            case "rosa":
                return Color.PINK;
            case "preto":
                return Color.BLACK;
            case "cinza":
                return Color.GRAY;
            case "branco":
                return Color.WHITE;
            case "laranja":
                return Color.ORANGE;
            case "verde":
                return Color.GREEN;
        }
        return null;
    }
}
