package org.example.view;

import org.example.controller.TrafegoController;
import org.example.model.Carro;
import org.example.model.Estrada;
import org.example.observer.ObserverCelula;
import org.example.view.componentes.MalhaViariaJTable;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;

public class MalhaViariaView extends JFrame implements ObserverCelula {
    public static final int ALTURA_TELA = (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 0.8);
    public static final int LARGURA_TELA = (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.8);
    public static final int MARGEM_BOTOES = (int) (LARGURA_TELA * 0.00916);
    public static final int LARGURA_COLUNA_GRID = 35;
    private TrafegoController controller;
    private JLabel lblNumeroThreadAtual;
    private MalhaViariaJTable board;

    public MalhaViariaView() {
        super();
        controller = new TrafegoController();
        controller.addObserver(this);
        init();
    }

    private void init() {
        setProperties();
        addComponents();
    }

    private void setProperties() {
        setTitle("Simulador de Tráfico");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(LARGURA_TELA, ALTURA_TELA));
        setLayout(new BorderLayout());
        pack();
    }

    private void addComponents() {
        board = new MalhaViariaJTable(controller);

        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();

        // Labels
        JLabel lblTituloNumeroThread = new JLabel("Número de Threads");
        lblTituloNumeroThread.setPreferredSize(new Dimension(LARGURA_TELA / 6, LARGURA_COLUNA_GRID));

        JLabel lblTituloThreadAtual = new JLabel("Threads em funcionamento");
        lblTituloThreadAtual.setPreferredSize(new Dimension(LARGURA_TELA / 6, LARGURA_COLUNA_GRID));

        // Buttons
        JButton btnIniciar = new JButton("INICIAR");
        btnIniciar.setPreferredSize(new Dimension(LARGURA_TELA / 6, LARGURA_COLUNA_GRID));

        JButton btnEncerrar = new JButton("ENCERRAR");
        btnEncerrar.setPreferredSize(new Dimension(LARGURA_TELA / 6, LARGURA_COLUNA_GRID));
        btnEncerrar.setEnabled(false);

        // TextField
        JTextField txtNumeroThreads = new JTextField();
        txtNumeroThreads.setPreferredSize(new Dimension(LARGURA_TELA / 6, LARGURA_COLUNA_GRID));

        // Labels
        lblNumeroThreadAtual = new JLabel();
        lblNumeroThreadAtual.setText("0");
        lblNumeroThreadAtual.setPreferredSize(new Dimension(LARGURA_TELA / 6, LARGURA_COLUNA_GRID));

        // JPanel panLinhasBotoes
        JPanel panLinhasBotoes = new JPanel();
        panLinhasBotoes.setLayout(layout);

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(0, 10, 0, 10);
        panLinhasBotoes.add(lblTituloNumeroThread, constraints);

        constraints.gridx = 1;
        panLinhasBotoes.add(lblTituloThreadAtual, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.insets = new Insets(10, 10, 0, 10);
        panLinhasBotoes.add(txtNumeroThreads, constraints);

        constraints.gridx = 3;
        constraints.gridx = 1;
        panLinhasBotoes.add(lblNumeroThreadAtual, constraints);

        constraints.insets = new Insets(0, 0, MARGEM_BOTOES, 0);
        constraints.gridx = 0;
        constraints.gridy = 1;
        panLinhasBotoes.add(btnIniciar, constraints);
        constraints.insets = new Insets(0, MARGEM_BOTOES, MARGEM_BOTOES, 0);
        constraints.gridx = 1;
        panLinhasBotoes.add(btnEncerrar, constraints);
        constraints.gridx = 2;

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.insets = new Insets(0, 10, 10, 10);
        panLinhasBotoes.add(btnIniciar, constraints);

        constraints.gridx = 1;
        panLinhasBotoes.add(btnEncerrar, constraints);

        // JPanel jpTraffic
        JPanel jpTraffic = new JPanel();
        jpTraffic.setLayout(layout);
        jpTraffic.add(board, constraints);

        // JPanel panLayout
        JPanel panLayout = new JPanel();
        panLayout.setLayout(layout);
        panLayout.setSize(LARGURA_TELA, ALTURA_TELA);

        constraints.gridx = 0;
        constraints.gridy = 0;
        panLayout.add(panLinhasBotoes, constraints);

        constraints.gridy = 1;
        panLayout.add(jpTraffic, constraints);
        // JScrollPane scpScroll
        JScrollPane scpScroll = new JScrollPane(panLayout);
        setTitle("Malha rodoviaria");
        setVisible(true);
        setSize(LARGURA_TELA, ALTURA_TELA);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(scpScroll);

        btnIniciar.addActionListener(e -> {
            controller.onIniciar(txtNumeroThreads.getText().toString());
            btnEncerrar.setEnabled(true);
            btnIniciar.setEnabled(false);
        });
        btnEncerrar.addActionListener(e -> {
            controller.onEncerrarCarros();
            btnIniciar.setEnabled(true);
            btnEncerrar.setEnabled(false);
        });
    }

    public synchronized void atualizarThread() {
        lblNumeroThreadAtual.setText(String.valueOf(controller.getCarros().size()));
        lblNumeroThreadAtual.repaint();
    }


    @Override
    public void notifyStartCar(int line, int column) {
        TableModel model = board.getModel();
        Estrada pieceAtual = (Estrada) model.getValueAt(line, column);
        pieceAtual.setPossuiCarro(true);
        model.setValueAt(pieceAtual, line, column);
        controller.estradas[line][column] = pieceAtual;
        board.repaint();
        atualizarThread();
    }

    @Override
    public void notifyMoveCar(int pastLine, int pastColumn, int newLine, int newColumn) {
        TableModel model = board.getModel();
        Estrada pieceAtual = (Estrada) model.getValueAt(pastLine, pastColumn);
        Estrada pieceNext = (Estrada) model.getValueAt(newLine, newColumn);
        pieceAtual.setPossuiCarro(false);
        pieceNext.setPossuiCarro(true);
        model.setValueAt(pieceAtual, pastLine, pastColumn);
        model.setValueAt(pieceNext, newLine, newColumn);
        controller.estradas[pastLine][pastColumn] = pieceAtual;
        controller.estradas[newLine][newColumn] = pieceNext;
        board.repaint();
    }

    @Override
    public void notifyEndCar(int line, int column, Carro car) {
        TableModel model = board.getModel();
        Estrada pieceAtual = (Estrada) model.getValueAt(line, column);
        pieceAtual.setPossuiCarro(false);
        model.setValueAt(pieceAtual, line, column);
        controller.estradas[line][column] = pieceAtual;
        board.repaint();
        atualizarThread();
    }
}
