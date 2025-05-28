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
    private ObserverCelula observerNode;
    private int[][] roadMesh;
    private boolean interruptClick;
    private AbstractCelula[][] nodeMesh;
    public Estrada[][] pieces;
    private List<Carro> cars;
    private int numThreads = 0;

    public TrafegoController() {
        super();
        interruptClick = false;
        this.roadMesh = MalhaController.getInstance().getMalhaViaria();
        pieces = MalhaController.getInstance().getPieces();
    }

    public int getRowCount() {
        return roadMesh.length;
    }

    public int getColumnCount() {
        return roadMesh[0].length;
    }

    public Estrada getValueAt(int rowIndex, int columnIndex) {
        return pieces[rowIndex][columnIndex];
    }

    public boolean hasElementAt(int linha, int coluna, int[][] state) {
        if (state[linha][coluna] == 0) {
            return false;
        } else {
            return true;
        }
    }

    public List<Carro> getCars() {
        return cars;
    }

    public void onIniciar(String s) {
        interruptClick = false;
        nodeMesh = MalhaController.getInstance().criaMatriz(this);
        mapeaEntrada();
        cars = new ArrayList<>();
        if (s.matches("^\\d+$")) {
            int numThreads = Integer.parseInt(s);
            this.numThreads = numThreads;
            CarroGenerator generator = new CarroGenerator(numThreads, cars, numThreads);
            generator.start();
        }
    }

    public void geraCarro() {
        CarroGenerator generator = new CarroGenerator(1, cars, numThreads);
        generator.start();
    }

    public void onEncerrarCarros() {
        interruptClick = true;
        for (Carro carro : cars) {
            carro.setBlockedTrue();
        }
        cars.clear();
    }

    private void mapeaEntrada() {
        for (int coluna = 0; coluna < roadMesh[0].length; coluna++) {
            Direcoes colunaDirecoesBaixo = Direcoes.fromValor(roadMesh[0][coluna]);
            if (colunaDirecoesBaixo == Direcoes.BAIXO) {
                MalhaController.getInstance().addInitNode(nodeMesh[0][coluna]);
            }
            Direcoes colunaDirecoesCima = Direcoes.fromValor(roadMesh[roadMesh.length - 1][coluna]);
            if (colunaDirecoesCima == Direcoes.CIMA) {
                MalhaController.getInstance().addInitNode(nodeMesh[roadMesh.length - 1][coluna]);
            }
        }
        for (int linha = 0; linha < roadMesh.length - 1; linha++) {
            Direcoes linhaDirecoesDireita = Direcoes.fromValor(roadMesh[linha][0]);
            if (linhaDirecoesDireita == Direcoes.DIREITA) {
                MalhaController.getInstance().addInitNode(nodeMesh[linha][0]);
            }
            Direcoes linhaDirecoesEsquerda = Direcoes.fromValor(roadMesh[linha][roadMesh[0].length - 1]);
            if (linhaDirecoesEsquerda == Direcoes.ESQUERDA) {
                MalhaController.getInstance().addInitNode(nodeMesh[linha][roadMesh[0].length - 1]);
            }
        }
    }

    public void addObserver(ObserverCelula observer) {
        observerNode = observer;
    }


    @Override
    public void notifyStartCar(int line, int column) {
        observerNode.notifyStartCar(line, column);
    }

    @Override
    public void notifyMoveCar(int pastLine, int pastColumn, int newLine, int newColumn) {
        observerNode.notifyMoveCar(pastLine, pastColumn, newLine, newColumn);
    }

    @Override
    public void notifyEndCar(int line, int column, Carro car) {
        observerNode.notifyEndCar(line, column, car);
        if (!interruptClick) {
            cars.remove(car);
            geraCarro();
        }
    }
}
