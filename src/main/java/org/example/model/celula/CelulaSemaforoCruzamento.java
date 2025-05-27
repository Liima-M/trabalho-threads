package org.example.model.celula;

import org.example.model.Carro;
import org.example.observer.ObserverCelula;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class CelulaSemaforoCruzamento extends AbstractCelula {
    private Semaphore semaphore = new Semaphore(1);

    public CelulaSemaforoCruzamento(int x, int y, int type, ObserverCelula observer) {
        super(x, y, type, observer, true);
    }

    @Override
    public synchronized void moveCar(Carro car) throws InterruptedException {
        throw new InterruptedException();
    }

    @Override
    public AbstractCelula getNextNode(Carro car) {
        return null;
    }

    @Override
    public boolean tryNext() throws InterruptedException {
        return semaphore.tryAcquire(new Random().nextInt(2001 - 500) + 500, TimeUnit.MILLISECONDS);
    }

    @Override
    public void block() throws InterruptedException {
        semaphore.acquire();
    }

    @Override
    public void release() {
        semaphore.release();
    }
}
