package org.example.view.componentes;

import org.example.model.Estrada;
import org.example.view.MalhaViariaView;

import javax.swing.*;
import java.awt.*;

public class EstradaIcon implements Icon {
    private Estrada estrada;
    private int iconWidth;
    private int iconHeight;
    private ImageIcon pieceIcone;

    public EstradaIcon(Estrada estrada) {
        this.estrada = estrada;
        this.pieceIcone = this.estrada.getImagem(40);
        this.iconWidth = this.pieceIcone.getIconWidth();
        this.iconHeight = this.pieceIcone.getIconHeight();
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        ImageIcon pieceImg = this.pieceIcone;
        Image newImagePiece = this.estrada.getImagem(4).getImage();
        g.drawImage(newImagePiece, 0, 0, MalhaViariaView.LARGURA_COLUNA_GRID, MalhaViariaJTable.ALTURA_GRID, pieceImg.getImageObserver());
        if (estrada.getPossuiCarro()) {
            this.paintCar(g);
        }
    }

    private void paintCar(Graphics g) {
        ImageIcon carro = this.estrada.getCarro(iconHeight);
        this.desenhaImagem(g, carro);
    }

    private void desenhaImagem(Graphics g, ImageIcon carro) {
        int x = 0;
        int y = 0;
        Image newImageCar = carro.getImage();
        g.drawImage(newImageCar, x, y, (carro.getIconWidth() - 10), (carro.getIconHeight() - 10), carro.getImageObserver());
    }

    @Override
    public int getIconWidth() {
        return this.iconWidth;
    }

    @Override
    public int getIconHeight() {
        return this.iconHeight;
    }
}
