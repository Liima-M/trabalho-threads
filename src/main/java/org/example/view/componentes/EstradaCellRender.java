package org.example.view.componentes;

import org.example.model.Estrada;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;

public class EstradaCellRender extends DefaultTableCellRenderer {
    private final Integer SQUARE_SIZE;

    public EstradaCellRender(Integer squareSize) {
        this.SQUARE_SIZE = squareSize;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        this.setOpaque(false);
        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setVerticalAlignment(SwingConstants.CENTER);
        this.defineIcon(value);
        this.defineSize(table, column);
        return this;
    }

    private void defineIcon(Object value) {
        if (value instanceof Estrada) {
            Estrada newPiece = (Estrada) value;
            EstradaIcon iconePersonalizado = new EstradaIcon(newPiece);
            this.setIcon(iconePersonalizado);
        } else {
            this.setIcon(null);
        }
    }

    protected void defineSize(JTable table, int column) {
        if (this.SQUARE_SIZE != null) {
            int columnSize = this.SQUARE_SIZE;
            TableColumnModel tableColumnModel = table.getColumnModel();
            TableColumn columnModel = tableColumnModel.getColumn(column);
            columnModel.setWidth(columnSize);
            columnModel.setMinWidth(columnSize);
            columnModel.setMaxWidth(columnSize);
            columnModel.setPreferredWidth(columnSize);
        }
    }
}
