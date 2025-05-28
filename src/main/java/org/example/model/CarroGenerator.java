package org.example.model;

import org.example.controller.MalhaController;
import org.example.model.celula.AbstractCelula;

import java.util.List;
import java.util.Random;

public class CarroGenerator extends Thread {
    private List<Carro> cars;
    private int quantidade;
    private int maxThread;

    public CarroGenerator(int quantidade, List<Carro> cars, int maxThread) {
        this.quantidade = quantidade;
        this.cars = cars;
        this.maxThread = maxThread;
    }

    private void geraCarro(){
        int posicao = new Random().nextInt(MalhaController.getInstance().getNodeInit().size());
        AbstractCelula choose = MalhaController.getInstance().getNodeInit().get(posicao);
        Carro car = new Carro(choose);
        cars.add(car);
        car.start();
    }

    @Override
    public void run() {
        if(cars.size() < maxThread) {
            for(int i= 0; i < quantidade; i++) {
                geraCarro();
            }
            Thread.currentThread().interrupt();
        }
    }
}
