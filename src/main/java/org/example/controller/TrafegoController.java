package org.example.controller;

import org.example.enums.Direcoes;
import org.example.model.Carro;
import org.example.model.CarroGenerator;
import org.example.model.Estrada;
import org.example.model.celula.AbstractCelula;
import org.example.observer.ObserverCelula;

import java.util.ArrayList;
import java.util.List;

public class TrafegoController implements ObserverCelula {
    private ObserverCelula observerCelula;
    private int[][] malhaViaria;
    private boolean interruptClick;
    private AbstractCelula[][] matriz;
    public Estrada[][] estradas;
    private List<Carro> carros;
    private int numThreads = 0;

    public TrafegoController() {
        super();
        interruptClick = false;
        this.malhaViaria = MalhaController.getInstance().getMalhaViaria();
        estradas = MalhaController.getInstance().getPieces();
    }

    public int getRowCount() {
        return malhaViaria.length;
    }

    public int getColumnCount() {
        return malhaViaria[0].length;
    }

    public Estrada getValueAt(int rowIndex, int columnIndex) {
        return estradas[rowIndex][columnIndex];
    }

    public boolean hasElementAt(int linha, int coluna, int[][] state) {
        if (state[linha][coluna] == 0) {
            return false;
        } else {
            return true;
        }
    }

    public List<Carro> getCarros() {
        return carros;
    }

    public void onIniciar(String s) {
        interruptClick = false;
        matriz = MalhaController.getInstance().criaMatriz(this);
        mapeaEntrada();
        carros = new ArrayList<>();
        if (s.matches("^\\d+$")) {
            int numThreads = Integer.parseInt(s);
            this.numThreads = numThreads;
            CarroGenerator generator = new CarroGenerator(numThreads, carros, numThreads);
            generator.start();
        }
    }

    public void geraCarro() {
        CarroGenerator generator = new CarroGenerator(1, carros, numThreads);
        generator.start();
    }

    public void onEncerrarCarros() {
        interruptClick = true;
        for (Carro carro : carros) {
            carro.setBlockedTrue();
        }
        carros.clear();
    }

    private void mapeaEntrada() {
        for (int coluna = 0; coluna < malhaViaria[0].length; coluna++) {
            Direcoes colunaDirecoesBaixo = Direcoes.fromValor(malhaViaria[0][coluna]);
            if (colunaDirecoesBaixo == Direcoes.BAIXO) {
                MalhaController.getInstance().addInitNode(matriz[0][coluna]);
            }
            Direcoes colunaDirecoesCima = Direcoes.fromValor(malhaViaria[malhaViaria.length - 1][coluna]);
            if (colunaDirecoesCima == Direcoes.CIMA) {
                MalhaController.getInstance().addInitNode(matriz[malhaViaria.length - 1][coluna]);
            }
        }
        for (int linha = 0; linha < malhaViaria.length - 1; linha++) {
            Direcoes linhaDirecoesDireita = Direcoes.fromValor(malhaViaria[linha][0]);
            if (linhaDirecoesDireita == Direcoes.DIREITA) {
                MalhaController.getInstance().addInitNode(matriz[linha][0]);
            }
            Direcoes linhaDirecoesEsquerda = Direcoes.fromValor(malhaViaria[linha][malhaViaria[0].length - 1]);
            if (linhaDirecoesEsquerda == Direcoes.ESQUERDA) {
                MalhaController.getInstance().addInitNode(matriz[linha][malhaViaria[0].length - 1]);
            }
        }
    }

    public void addObserver(ObserverCelula observer) {
        observerCelula = observer;
    }


    @Override
    public void notifyStartCar(int line, int column) {
        observerCelula.notifyStartCar(line, column);
    }

    @Override
    public void notifyMoveCar(int pastLine, int pastColumn, int newLine, int newColumn) {
        observerCelula.notifyMoveCar(pastLine, pastColumn, newLine, newColumn);
    }

    @Override
    public void notifyEndCar(int line, int column, Carro car) {
        observerCelula.notifyEndCar(line, column, car);
        if (!interruptClick) {
            carros.remove(car);
            geraCarro();
        }
    }
}
