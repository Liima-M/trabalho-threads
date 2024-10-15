package org.example.view;

import org.example.model.MalhaViaria;

import javax.swing.table.AbstractTableModel;
import java.nio.file.Path;

public class MalhaTable extends AbstractTableModel {
    private static String files_path = Path.of("").toAbsolutePath() + "/src/main/java/org.example/file/";
    private int linhas;
    private int colunas;

    private MalhaViaria malha[][];

    public int getLinhas() {
        return linhas;
    }

    public void setLinhas(int linhas) {
        this.linhas = linhas;
    }

    public int getColunas() {
        return colunas;
    }

    public void setColunas(int colunas) {
        this.colunas = colunas;
    }

    public MalhaViaria[][] getMalha() {
        return malha;
    }

    public void setMalha(MalhaViaria[][] malha) {
        this.malha = malha;
    }

    @Override
    public int getRowCount() {
        return 0;
    }

    @Override
    public int getColumnCount() {
        return 0;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return null;
    }
}
