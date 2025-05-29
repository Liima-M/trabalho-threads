package org.example.abstractfactory;

import org.example.model.celula.AbstractCelula;
import org.example.observer.ObserverCelula;

public class SemaforoFactory extends AbstractThreadMediadorFactory {
    @Override
    public AbstractCelula createNode(int x, int y, int type, ObserverCelula observer) {
        return null;
    }

    @Override
    public AbstractCelula createCrossNode(int x, int y, int type, ObserverCelula observer) {
        return null;
    }
}
