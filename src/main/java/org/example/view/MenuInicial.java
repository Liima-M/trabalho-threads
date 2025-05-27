package org.example.view;

import org.example.controller.MalhaController;
import org.example.controller.Controller;
import org.example.abstractfactory.MonitorFactory;
import org.example.abstractfactory.SemaforoFactory;

import javax.swing.*;
import java.awt.*;

public class MenuInicial extends JFrame {

    private Controller controller;
    private JFileChooser jfcArquivo;
    private JTextField txtCaminho;
    private JButton btnProcurar;
    private JButton btnMutext;
    private JButton btnMonitor;

    public MenuInicial() {
        definePropriedades();
        criaCampos();
        criaActionListeners();
    }

    private void definePropriedades() {

    }

    private void criaCampos() {
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        JLabel lblTitulo = new JLabel("Escolha o arquivo da malha rodoviaria");

        txtCaminho = new JTextField();
        txtCaminho.setText("Selecione um arquivo");
        txtCaminho.setPreferredSize(new Dimension(310, 26));
        txtCaminho.setMinimumSize(new Dimension(310, 26));
        txtCaminho.setEnabled(false);

        btnProcurar = new JButton("Procurar");
        btnProcurar.setPreferredSize(new Dimension(120, 26));
        btnProcurar.setMinimumSize(new Dimension(120, 26));

        JPanel panLinhaBusca = new JPanel();
        panLinhaBusca.setLayout(layout);
        panLinhaBusca.setSize(400, 50);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(0, 0, 0, 0);
        panLinhaBusca.add(txtCaminho, constraints);
        constraints.gridx = 1;
        constraints.insets = new Insets(0, 10, 0, 0);
        panLinhaBusca.add(btnProcurar, constraints);


        btnMutext = new JButton("Iniciar Mutex");
        btnMutext.setPreferredSize(new Dimension(120, 26));
        btnMutext.setMinimumSize(new Dimension(120, 26));
        btnMutext.setEnabled(false);

        btnMonitor = new JButton("Iniciar Monitor");
        btnMonitor.setPreferredSize(new Dimension(120, 26));
        btnMonitor.setMinimumSize(new Dimension(120, 26));
        btnMonitor.setEnabled(false);


        jfcArquivo = new JFileChooser();
        jfcArquivo.setFileSelectionMode(JFileChooser.FILES_ONLY);


        setTitle("Escolha a malha rodoviaria");
        setVisible(true);
        setSize(600, 250);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(criaPainelPrincipal(layout, constraints, lblTitulo, panLinhaBusca));
    }

    private JPanel criaPainelPrincipal(GridBagLayout layout, GridBagConstraints constraints, JLabel lblTitulo, JPanel panLinhaBusca) {
        JPanel panLayout = new JPanel();
        panLayout.setLayout(layout);
        panLayout.setSize(700, 250);
        constraints.gridx = 0;
        panLayout.add(lblTitulo, constraints);
        constraints.gridy = 1;
        constraints.insets = new Insets(10, 0, 0, 0);
        panLayout.add(panLinhaBusca, constraints);
        constraints.gridy = 2;
        constraints.insets = new Insets(10, 320, 0, 0);
        panLayout.add(btnMutext, constraints);
        constraints.gridy = 4;
        constraints.insets = new Insets(10, 320, 0, 0);
        panLayout.add(btnMonitor, constraints);
        return panLayout;
    }

    private void criaActionListeners() {
        btnProcurar.addActionListener(click -> {
            int i= jfcArquivo.showSaveDialog(null);
            if (i==1){
                txtCaminho.setText("Selecione um arquivo de malha rodoviaria");
                btnMutext.setEnabled(false);
                btnMonitor.setEnabled(false);
            } else {
                this.controller.updateRoadMesh(jfcArquivo.getSelectedFile());
            }
        });

        btnMutext.addActionListener(click -> {
            MalhaController.getInstance().setThreadController(new SemaforoFactory());
            this.controller.navigateNextView();
        });

        btnMonitor.addActionListener(click -> {
            MalhaController.getInstance().setThreadController(new MonitorFactory());
            this.controller.navigateNextView();
        });
    }
}
