package org.example.model;

import org.example.view.MalhaTable;

import java.nio.file.Path;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class MalhaViaria {
    private static String icons_path = Path.of("").toAbsolutePath() + "/src/main/java/org.example/model/icon/";

    private Semaphore semaphore;
    private boolean entrada;
    private boolean saida;
    private int linha;
    private int coluna;
    private int type;

    private Carro carro;

    public MalhaViaria(int linha, int coluna, int type) {
        this.linha = linha;
        this.coluna = coluna;
        this.type = type;
        semaphore = new Semaphore(1);

    }
    public boolean tentaPegar(){
        boolean pegar = false;
        try{
            pegar = this.semaphore.tryAcquire(200, TimeUnit.MILLISECONDS);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return pegar;
    }

    public void release(){
        this.semaphore.release();
    }

    public void addCarro(Carro carro){
        this.carro = carro;

    }

    public void deleteCarro(){
        this.carro = null;
    }

    public boolean isEntrada() {
        return entrada;
    }

    public void setEntrada(boolean entrada) {
        this.entrada = entrada;
    }

    public boolean isSaida() {
        return saida;
    }

    public void setSaida(boolean saida) {
        this.saida = saida;
    }

    public Carro getCarro() {
        return carro;
    }

    public int getLinha() {
        return linha;
    }

    public int getColuna() {
        return coluna;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean entradaSuperior(){
        return this.getColuna() - 1 < 0 && this.getType() ==3;
    }
    public boolean saidaSuperior(){
        return this.getColuna() - 1 < 0 && this.getType() ==1;
    }

    public boolean entradaInferior(MalhaTable malhaTable){
        return this.getColuna() + 1 >= malhaTable.getLinhas() && this.getType() == 1;
    }

    public boolean saidaInferior(MalhaTable malhaTable){
        return this.getColuna() + 1 >= malhaTable.getLinhas() && this.getType() == 3;
    }

    public boolean entradaEsquerda(){
        return this.getColuna() - 1 < 0 && this.getType() == 2;
    }

    public boolean saidaEsquerda(){
        return this.getColuna() - 1 < 0 && this.getType() == 4;
    }

    public boolean entradaDireita(MalhaTable malhaTable){
        return this.getColuna() + 1 >= malhaTable.getColunas() && this.getType() == 4;
    }
    public boolean saidaDireita(MalhaTable malhaTable){
        return this.getColuna() + 1 >= malhaTable.getColunas() && this.getType() == 2;
    }

    public boolean cruzamento(){
        return this.type > 4;
    }

    public boolean eVazio(){
        return this.carro == null;
    }

    public boolean entrada(){
        return this.getType() > 0;
    }

    public void definirEntradaESaida(MalhaTable malhaTable){
        this.setEntrada((this.entradaSuperior() || this.entradaInferior(malhaTable) || this.entradaEsquerda() || this.entradaDireita(malhaTable)));
        this.setSaida((this.saidaSuperior() || this.saidaInferior(malhaTable) || this.saidaEsquerda() ||this.saidaDireita(malhaTable)));
    }
}
