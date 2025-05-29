package org.example.view.componentes;

import org.example.controller.TrafegoController;

import javax.swing.table.AbstractTableModel;

public class MalhaViariaTableModel extends AbstractTableModel {
    private TrafegoController tableController;
    private static final long serialVersionUID = 1L;

    public MalhaViariaTableModel(TrafegoController tableController) {
        super();
        this.tableController = tableController;
    }

    @Override
    public int getRowCount() {
        return tableController.getRowCount();
    }

    @Override
    public int getColumnCount() {
        return tableController.getColumnCount();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return tableController.getValueAt(rowIndex, columnIndex);
    }
}
