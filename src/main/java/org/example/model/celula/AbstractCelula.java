package org.example.model.celula;

import org.example.model.Carro;
import org.example.observer.ObserverCelula;

public abstract class AbstractCelula {
    private AbstractCelula celulaAcima;
    private AbstractCelula celulaAbaixo;
    private AbstractCelula celulaDireita;
    private AbstractCelula celulaEsquerda;
    private int line;
    private int column;
    private int tipo;
    private boolean cruzamento;
    private ObserverCelula observer;

    public AbstractCelula(int x, int y, int tipo, ObserverCelula observer, boolean cruzamento) {
        this.celulaAcima = null;
        this.celulaAbaixo = null;
        this.celulaDireita = null;
        this.celulaEsquerda = null;
        this.line = x;
        this.column = y;
        this.tipo = tipo;
        this.observer = observer;
        this.cruzamento = cruzamento;
    }

    public void setCelulaAcima(AbstractCelula celulaAcima) {
        this.celulaAcima = celulaAcima;
    }

    public void setCelulaAbaixo(AbstractCelula celulaAbaixo) {
        this.celulaAbaixo = celulaAbaixo;
    }

    public void setCelulaDireita(AbstractCelula celulaDireita) {
        this.celulaDireita = celulaDireita;
    }

    public void setCelulaEsquerda(AbstractCelula celulaEsquerda) {
        this.celulaEsquerda = celulaEsquerda;
    }

    public AbstractCelula getCelulaAcima() {
        return celulaAcima;
    }

    public AbstractCelula getCelulaAbaixo() {
        return celulaAbaixo;
    }

    public AbstractCelula getCelulaDireita() {
        return celulaDireita;
    }

    public AbstractCelula getCelulaEsquerda() {
        return celulaEsquerda;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    public int getTipo() {
        return tipo;
    }

    public boolean isCruzamento() {
        return cruzamento;
    }

    public ObserverCelula getObserver() {
        return this.observer;
    }

    public abstract void moveCar(Carro car) throws InterruptedException;
    public abstract AbstractCelula getNextNode(Carro car);
    public abstract boolean tryNext() throws InterruptedException;
    public abstract void block() throws InterruptedException;
    public abstract void release();
}
