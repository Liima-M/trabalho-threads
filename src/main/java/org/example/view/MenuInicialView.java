package org.example.view;

import org.example.controller.MalhaController;
import org.example.controller.Controller;
import org.example.abstractfactory.MonitorFactory;
import org.example.abstractfactory.SemaforoFactory;
import org.example.observer.ObserverView;

import javax.swing.*;
import java.awt.*;

public class MenuInicialView extends JFrame implements ObserverView {

    private Controller controller;
    private JFileChooser jfcArquivo;
    private JTextField txtCaminho;
    private JButton btnProcurar;
    private JComboBox<String> cmbOpcao;
    private JButton btnIniciar;

    public MenuInicialView() {
        controller = new Controller(this);
        criaCampos();
        criaActionListeners();
    }

    private void criaCampos() {
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        JLabel lblTitulo = new JLabel("Escolha o arquivo da malha rodoviaria");

        txtCaminho = new JTextField("Selecione um arquivo");
        txtCaminho.setPreferredSize(new Dimension(310, 26));
        txtCaminho.setEnabled(false);

        btnProcurar = new JButton("Procurar");
        btnProcurar.setPreferredSize(new Dimension(120, 26));

        JPanel panLinhaBusca = new JPanel();
        panLinhaBusca.setLayout(layout);
        panLinhaBusca.setSize(400, 50);

        constraints.gridx = 0;
        constraints.gridy = 0;
        panLinhaBusca.add(txtCaminho, constraints);

        constraints.gridx = 1;
        constraints.insets = new Insets(0, 10, 0, 0);
        panLinhaBusca.add(btnProcurar, constraints);

        cmbOpcao = new JComboBox<>(new String[]{"Semáforo", "Monitor"});
        cmbOpcao.setPreferredSize(new Dimension(120, 26));
        cmbOpcao.setEnabled(false);

        btnIniciar = new JButton("Iniciar");
        btnIniciar.setPreferredSize(new Dimension(120, 26));
        btnIniciar.setEnabled(false);

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
        constraints.gridy = 0;
        constraints.insets = new Insets(0, 0, 0, 0);
        panLayout.add(lblTitulo, constraints);

        constraints.gridy = 1;
        constraints.insets = new Insets(10, 0, 0, 0);
        panLayout.add(panLinhaBusca, constraints);

        constraints.gridy = 2;
        constraints.insets = new Insets(10, 0, 0, 0);
        panLayout.add(cmbOpcao, constraints);

        constraints.gridy = 3;
        constraints.insets = new Insets(10, 0, 0, 0);
        panLayout.add(btnIniciar, constraints);

        return panLayout;
    }

    private void criaActionListeners() {
        btnProcurar.addActionListener(click -> {
            int i = jfcArquivo.showSaveDialog(null);
            if (i == JFileChooser.APPROVE_OPTION) {
                controller.updateRoadMesh(jfcArquivo.getSelectedFile());
            } else {
                txtCaminho.setText("Selecione um arquivo de malha rodoviaria");
                cmbOpcao.setEnabled(false);
                btnIniciar.setEnabled(false);
            }
        });

        btnIniciar.addActionListener(click -> {
            String opcao = (String) cmbOpcao.getSelectedItem();
            if ("Semáforo".equals(opcao)) {
                MalhaController.getInstance().setThreadController(new SemaforoFactory());
            } else if ("Monitor".equals(opcao)) {
                MalhaController.getInstance().setThreadController(new MonitorFactory());
            }
            controller.navigateNextView();
        });
    }

    @Override
    public void ativedInitialButton() {
        cmbOpcao.setEnabled(true);
        btnIniciar.setEnabled(true);
    }

    @Override
    public void notifyErrorFile() {
        JOptionPane.showMessageDialog(null, "O arquivo selecionado não possui o padrão adequado de uma malha rodoviaria",
                "Erro no arquivo", JOptionPane.ERROR_MESSAGE);
        cmbOpcao.setEnabled(false);
        btnIniciar.setEnabled(false);
    }

    @Override
    public void navigateNextView() {
        MalhaViariaView view = new MalhaViariaView();
        view.setVisible(true);
        this.dispose();
    }
}
