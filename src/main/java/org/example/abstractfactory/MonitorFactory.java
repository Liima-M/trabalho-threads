package org.example.abstractfactory;

import org.example.model.celula.AbstractCelula;
import org.example.model.celula.CelulaMonitor;
import org.example.model.celula.CelulaMonitorCruzamento;
import org.example.observer.ObserverCelula;

public class MonitorFactory extends AbstractThreadMediadorFactory {
    @Override
    public AbstractCelula createNode(int x, int y, int type, ObserverCelula observer) {
        return new CelulaMonitor(x, y, type, observer);
    }

    @Override
    public AbstractCelula createCrossNode(int x, int y, int type, ObserverCelula observer) {
        return new CelulaMonitorCruzamento(x, y, type, observer);
    }
}
