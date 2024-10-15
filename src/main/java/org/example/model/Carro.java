package org.example.model;

import java.util.ArrayList;
import java.util.Random;

public class Carro extends Thread{
    private boolean encerrar;
    private int velocidade;
    private int type;

    private Random rand = new Random();
    private ArrayList<MalhaViaria> caminho;
    private MalhaViaria[][] pista;
    private MalhaViaria malhaViariaAtual;

    public void encerrar(){
        this.encerrar = true;
        this.interrupt();
    }

    public void run(){
        while(!encerrar){
            while (!caminho.isEmpty()){
                int proximoCaminho = 0;
                if (caminho.get(proximoCaminho).cruzamento()){
                    cruzamentoAtual();
                }else{
                    MalhaViaria malhaViaria = caminho.get(proximoCaminho);
                }
            }
            this.getMalhaViariaAtual().deleteCarro();

            this.getMalhaViariaAtual().release();

            //metodos controller abaixo


            this.encerrar();
        }
    }

    public void cruzamentoAtual(){
        
    }

    public MalhaViaria getMalhaViariaAtual() {
        return malhaViariaAtual;
    }

    public void setMalhaViariaAtual(MalhaViaria malhaViariaAtual) {
        this.malhaViariaAtual = malhaViariaAtual;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


}
