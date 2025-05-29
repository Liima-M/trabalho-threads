package org.example.view.componentes;

import org.example.controller.TrafegoController;
import org.example.view.MalhaViariaView;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class MalhaViariaJTable extends JTable {
    public static final int ALTURA_GRID = 35;
    private TrafegoController trafficController;
    private MalhaViariaTableModel model;
    private TableCellRenderer celula;

    public MalhaViariaJTable(TrafegoController controller) {
        trafficController = controller;
        init();
    }

    private void init() {
        this.defineProperties();
        this.iniciaComponentes();
        this.addComponentes();
    }

    private void defineProperties() {
        this.setOpaque(false);
        this.setBackground(new Color(240, 220, 130));
        Color gridColor = new Color(200, 170, 80);
        setRowHeight(ALTURA_GRID);
        this.setBorder(BorderFactory.createLineBorder(gridColor));
        this.setGridColor(gridColor);
    }

    private void iniciaComponentes() {
        this.model = new MalhaViariaTableModel(this.trafficController);
        this.celula = new EstradaCellRender(MalhaViariaView.LARGURA_COLUNA_GRID);
    }

    private void addComponentes() {
        this.setModel(this.model);
        this.setDefaultRenderer(Object.class, this.celula);
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }

    public void refresh() {
        this.init();
    }
}