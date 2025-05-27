package org.example.abstractfactory;

import org.example.model.celula.AbstractCelula;
import org.example.observer.ObserverCelula;

public abstract class AbstractThreadMediadorFactory {
    public abstract AbstractCelula createNode(int x, int y, int type, ObserverCelula observer);

    public abstract AbstractCelula createCrossNode(int x, int y, int type, ObserverCelula observer);
}
