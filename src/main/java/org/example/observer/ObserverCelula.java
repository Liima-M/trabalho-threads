package org.example.observer;

import org.example.model.Carro;

public interface ObserverCelula {
    void notifyStartCar(int line, int column);
    void notifyMoveCar(int pastLine, int pastColumn, int newLine, int newColumn);
    void notifyEndCar(int line, int column, Carro car);
}
