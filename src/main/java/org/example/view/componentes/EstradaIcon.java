package org.example.view.componentes;

import org.example.model.Estrada;
import org.example.view.MalhaViariaView;

import javax.swing.*;
import java.awt.*;

public class EstradaIcon implements Icon {
    private Estrada piece;
    private int iconWidth;
    private int iconHeight;
    private ImageIcon pieceIcone;

    public EstradaIcon(Estrada piece) {
        this.piece = piece;
        this.pieceIcone = this.piece.getImagem(40);
        this.iconWidth = this.pieceIcone.getIconWidth();
        this.iconHeight = this.pieceIcone.getIconHeight();
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        ImageIcon pieceImg = this.pieceIcone;
        Image newImagePiece = this.piece.getImagem(4).getImage();
        g.drawImage(newImagePiece, 0, 0, MalhaViariaView.LARGURA_COLUNA_GRID, MalhaViariaJTable.ALTURA_GRID, pieceImg.getImageObserver());
        if (piece.getPossuiCarro()) {
            this.paintCar(g);
        }
    }

    private void paintCar(Graphics g) {
        ImageIcon car = this.piece.getCar(iconHeight);
        this.desenhaImagem(g, car);
    }

    private void desenhaImagem(Graphics g, ImageIcon car) {
        int x = 0;
        int y = 0;
        Image newImageCar = car.getImage();
        g.drawImage(newImageCar, x, y, (car.getIconWidth() - 10), (car.getIconHeight() - 10), car.getImageObserver());
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
