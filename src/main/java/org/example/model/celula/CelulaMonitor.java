package org.example.model.celula;

import org.example.model.Carro;
import org.example.observer.ObserverCelula;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class CelulaMonitor extends AbstractCelula {
    private ReentrantLock recurse = new ReentrantLock();

    public CelulaMonitor(int x, int y, int type, ObserverCelula observer) {
        super(x, y, type, observer, false);
    }

    @Override
    public synchronized void moveCar(Carro car) throws InterruptedException {
        AbstractCelula nextNode = null;
        AbstractCelula currentNode = car.getNodeAtual();
        AbstractCelula firstNode = currentNode;
        List<AbstractCelula> nodesCross = new ArrayList<>();

        try {
            nextNode = getNextNode(car);
            if (nextNode == null) {
                car.setBlocked(true);
                getObserver().notifyEndCar(getLine(), getColumn(), car);
                this.release();
            } else if (!nextNode.isCruzamento()) {
                moveOne(car, nextNode);
            } else {
                boolean find = false;
                boolean isOK = true;

                if (nextNode.tryNext()) {
                    nodesCross.add(nextNode);
                    currentNode = nextNode;
                    while (!find) {
                        nextNode = getNextNodeSimple(currentNode);
                        if (nextNode.isCruzamento()) {
                            if (nextNode.tryNext()) {
                                nodesCross.add(nextNode);
                                currentNode = nextNode;
                            } else {
                                isOK = false;
                            }
                        } else {
                            if (nextNode.tryNext()) {
                                nodesCross.add(nextNode);
                            } else {
                                isOK = false;
                            }
                            find = true;
                        }
                    }
                } else {
                    isOK = false;
                }

                if (isOK) {
                    moveCross(car, firstNode, nodesCross);
                } else {
                    for (AbstractCelula node2 : nodesCross) {
                        node2.release();
                    }
                }
            }

        } catch (InterruptedException e) {
            this.release();
            throw new InterruptedException();
        }
    }

    private void moveCross(Carro car, AbstractCelula firstNode, List<AbstractCelula> nodesCross) throws InterruptedException {
        for (AbstractCelula node : nodesCross) {
            car.setNodeAtual(node);
            firstNode.getObserver().notifyMoveCar(firstNode.getLine(), firstNode.getColumn(), node.getLine(), node.getColumn());
            firstNode.release();
            firstNode = node;
            car.sleep();
        }
    }

    private void moveOne(Carro car, AbstractCelula nextNode) throws InterruptedException {
        if (nextNode.tryNext()) {
            car.setNodeAtual(nextNode);
            this.getObserver().notifyMoveCar(this.getLine(), this.getColumn(), nextNode.getLine(), nextNode.getColumn());
            this.release();
            car.sleep();
        }
    }

    @Override
    public AbstractCelula getNextNode(Carro car) {
        AbstractCelula currentNode = car.getNodeAtual();
        AbstractCelula nextNode = null;

        AbstractCelula[] directions = {
                currentNode.getCelulaEsquerda(),
                currentNode.getCelulaAbaixo(),
                currentNode.getCelulaDireita(),
                currentNode.getCelulaAcima()
        };

        for (AbstractCelula direction : directions) {
            if (direction != null) {
                nextNode = direction;
                break;
            }
        }
        return nextNode;
    }

    public AbstractCelula getNextNodeSimple(AbstractCelula initialNode) {
        AbstractCelula currentNode = initialNode;
        AbstractCelula nextNode = null;

        AbstractCelula[] directions = {
                currentNode.getCelulaEsquerda(),
                currentNode.getCelulaAbaixo(),
                currentNode.getCelulaDireita(),
                currentNode.getCelulaAcima()
        };

        Random random = new Random();

        while (nextNode == null) {
            int randomIndex = random.nextInt(directions.length);
            nextNode = directions[randomIndex];
        }

        return nextNode;
    }

    @Override
    public boolean tryNext() throws InterruptedException {
        return recurse.tryLock(new Random().nextInt(2001 - 500) + 500, TimeUnit.MILLISECONDS);
    }

    @Override
    public void block() throws InterruptedException {
        recurse.lock();
    }

    @Override
    public void release() {
        if (recurse.isHeldByCurrentThread()) {
            recurse.unlock();
        }
    }
}
