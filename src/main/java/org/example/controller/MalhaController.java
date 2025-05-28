package org.example.controller;

import org.example.abstractfactory.AbstractThreadMediadorFactory;
import org.example.enums.Direcoes;
import org.example.model.Estrada;
import org.example.model.celula.AbstractCelula;
import org.example.observer.ObserverCelula;

import java.util.ArrayList;
import java.util.List;

public class MalhaController {
    private static MalhaController instance;
    private int[][] malhaViaria;
    private AbstractCelula[][] matriz;
    private List<AbstractCelula> inicioNode;
    private Estrada[][] estradas;
    private AbstractThreadMediadorFactory threadController;

    private MalhaController() {
        inicioNode = new ArrayList<>();
    }

    public synchronized static MalhaController getInstance() {
        if (instance == null) {
            instance = new MalhaController();
        }
        return instance;
    }

    public void setMalhaViaria(int[][] malha) {
        this.malhaViaria = malha;
    }

    public int[][] getMalhaViaria() {
        return this.malhaViaria;
    }

    public List<AbstractCelula> getNodeInit() {
        return this.inicioNode;
    }

    public void addInitNode(AbstractCelula node) {
        inicioNode.add(node);
    }

    public AbstractThreadMediadorFactory getThreadController() {
        return threadController;
    }

    public void setThreadController(AbstractThreadMediadorFactory threadController) {
        this.threadController = threadController;
    }

    public AbstractCelula[][] criaMatriz(ObserverCelula observer) {
        int rows = malhaViaria.length;
        int cols = malhaViaria[0].length;
        AbstractCelula[][] matriz = new AbstractCelula[rows][cols];
        AbstractThreadMediadorFactory factoryNode = this.getThreadController();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int typeRoad = malhaViaria[i][j];
                Direcoes tipoEstrada = Direcoes.fromValor(typeRoad);
                AbstractCelula node = (tipoEstrada.getValor() <= 4)
                        ? factoryNode.createNode(i, j, typeRoad, observer)
                        : factoryNode.createCrossNode(i, j, typeRoad, observer);
                matriz[i][j] = node;
            }
        }

        this.matriz = matriz;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Direcoes tipo = Direcoes.fromValor(malhaViaria[row][col]);
                adicionaCelulasAdjacentes(tipo, row, col);
            }
        }

        return matriz;
    }

    private void adicionaCelulasAdjacentes(Direcoes tipo, int row, int col) {
        switch (tipo) {
            case CIMA, CRUZAMENTO_CIMA:
                setMove(row, col, row - 1, col, Direcoes.CIMA);
                break;
            case DIREITA, CRUZAMENTO_DIREITA:
                setMove(row, col, row, col + 1, Direcoes.DIREITA);
                break;
            case BAIXO, CRUZAMENTO_BAIXO:
                setMove(row, col, row + 1, col, Direcoes.BAIXO);
                break;
            case ESQUERDA, CRUZAMENTO_ESQUERDA:
                setMove(row, col, row, col - 1, Direcoes.ESQUERDA);
                break;
            case CRUZAMENTO_BAIXO_ESQUERDA:
                setMove(row, col, row + 1, col, Direcoes.BAIXO);
                setMove(row, col, row, col - 1, Direcoes.ESQUERDA);
                break;
            case CRUZAMENTO_BAIXO_DIREITA:
                setMove(row, col, row, col + 1, Direcoes.DIREITA);
                setMove(row, col, row + 1, col, Direcoes.BAIXO);
                break;
            case CRUZAMENTO_CIMA_ESQUERDA:
                setMove(row, col, row - 1, col, Direcoes.CIMA);
                setMove(row, col, row, col - 1, Direcoes.ESQUERDA);
                break;
            case CRUZAMENTO_CIMA_DIREITA:
                setMove(row, col, row - 1, col, Direcoes.CIMA);
                setMove(row, col, row, col + 1, Direcoes.DIREITA);
        }
    }

    private void setMove(int row, int col, int nextRow, int nextCol, Direcoes direcao) {
        if (isValidMove(nextRow, nextCol)) {
            AbstractCelula current = matriz[row][col];
            AbstractCelula next = matriz[nextRow][nextCol];
            switch (direcao) {
                case CIMA -> current.setCelulaAcima(next);
                case BAIXO -> current.setCelulaAbaixo(next);
                case ESQUERDA -> current.setCelulaEsquerda(next);
                case DIREITA -> current.setCelulaDireita(next);
            }
        }
    }

    private boolean isValidMove(int row, int col) {
        return (row >= 0) && (row < (malhaViaria.length - 1)) && (col >= 0) && col < (malhaViaria[0].length - 1);
    }

    public Estrada[][] initPiece() {
        int row = malhaViaria.length;
        int column = malhaViaria[0].length;
        this.estradas = new Estrada[row][column];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                estradas[i][j] = new Estrada(malhaViaria[i][j]);
            }
        }
        return estradas;
    }

    public Estrada[][] getPieces() {
        return estradas;
    }
}
