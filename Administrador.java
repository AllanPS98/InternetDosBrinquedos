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
public class Administrador implements Serializable{
    private String nome;
    private String senha;
    

    public Administrador(String nome, String senha) {
        this.nome = nome;
        this.senha = senha;   
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public String toString() {
        return "Administrador = " + nome;
    }

    
    
}
