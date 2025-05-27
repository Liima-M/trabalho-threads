package org.example.model.celula;

import org.example.model.Carro;
import org.example.observer.ObserverCelula;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class CelulaMonitorCruzamento extends AbstractCelula {
    private ReentrantLock lock = new ReentrantLock();

    public CelulaMonitorCruzamento(int x, int y, int type, ObserverCelula observer) {
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
        return lock.tryLock(new Random().nextInt(2001 - 500) + 500, TimeUnit.MILLISECONDS);
    }

    @Override
    public void block() throws InterruptedException {
        lock.lock();
    }

    @Override
    public void release() {
        if (lock.isHeldByCurrentThread()) {
            lock.unlock();
        }
    }
}
