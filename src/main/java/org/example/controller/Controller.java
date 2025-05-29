package org.example.controller;

import org.example.observer.ObserverView;
import org.example.util.FileUtil;

import java.io.File;

public class Controller {
    private ObserverView observer;
    private int[][] malhaViaria;

    public Controller(ObserverView observer) {
        super();
        this.observer = observer;
    }

    public void updateRoadMesh(File arquivo) {
        try {
            malhaViaria = FileUtil.gerarRoadMesh(arquivo);
            MalhaController.getInstance().setMalhaViaria(malhaViaria);
            MalhaController.getInstance().initPiece();
            observer.ativedInitialButton();
        } catch (Exception e) {
            observer.notifyErrorFile();
        }
    }

    public void navigateNextView() {
        MalhaController.getInstance().setMalhaViaria(malhaViaria);
        observer.navigateNextView();
    }
}
