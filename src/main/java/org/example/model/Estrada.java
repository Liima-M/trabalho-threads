package org.example.model;

import org.example.controller.TrafegoController;
import org.example.factory.EstradaFactory;
import org.example.util.FileUtil;

import javax.swing.*;

public class Estrada {

    private int tipo;
    private boolean possuiCarro;
    private int direcao;

    public Estrada(int tipo) {
        this.tipo = tipo;
        this.possuiCarro = false;
        this.direcao = 0;
    }

    public int getTipo() {
        return tipo;
    }

    public void setPossuiCarro(boolean car) {
        possuiCarro = car;
    }

    public boolean getPossuiCarro() {
        return possuiCarro;
    }

    protected void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getDirecao() {
        return direcao;
    }

    public void setDirecao(int direcao) {
        this.direcao = direcao;
    }

    public ImageIcon getImagem(TrafegoController controller) {
        int sizeIcon = (int) (this.iconSize(controller.getRowCount()));
        String path = this.getPathImageIcon();
        ImageIcon icon = FileUtil.create((String) path, sizeIcon, sizeIcon);
        return icon;
    }

    public ImageIcon getImagem(int size) {
        int sizeIcon = (int) (this.iconSize(size));
        String path = this.getPathImageIcon();
        ImageIcon icon = FileUtil.create((String) path, sizeIcon, sizeIcon);
        return icon;
    }

    public ImageIcon getCar(int size) {
        int sizeIcon = (int) (this.iconSize(size));
        String path = this.getPathImageCar();
        ImageIcon icon = FileUtil.create((String) path, sizeIcon, sizeIcon);
        return icon;
    }

    public String getPathImageIcon() {
        return FileUtil.createImagePath((new EstradaFactory()).getImagePath(getTipo()));
    }

    public String getPathImageCar() {
        return FileUtil.createImagePath("carro");
    }


    private int iconSize(int countLinhas) {
        final int maximo = 550;
        return (int) (maximo / countLinhas);
    }
}
