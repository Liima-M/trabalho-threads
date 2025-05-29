package org.example.abstractfactory;

import org.example.model.celula.AbstractCelula;
import org.example.model.celula.CelulaSemaforo;
import org.example.model.celula.CelulaSemaforoCruzamento;
import org.example.observer.ObserverCelula;

public class SemaforoFactory extends AbstractThreadMediadorFactory {
    @Override
    public AbstractCelula createNode(int x, int y, int type, ObserverCelula observer) {
        return new CelulaSemaforo(x, y, type, observer);
    }

    @Override
    public AbstractCelula createCrossNode(int x, int y, int type, ObserverCelula observer) {
        return new CelulaSemaforoCruzamento(x, y, type, observer);
    }
}
