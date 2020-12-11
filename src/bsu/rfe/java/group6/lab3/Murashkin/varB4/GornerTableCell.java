package bsu.rfe.java.group6.lab3.Murashkin.varB4;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;


public class GornerTableCell  implements TableCellRenderer{

    private String needle = null;
    private String searchFrom = null;
    private String searchTo = null;
    private JPanel panel = new JPanel();
    private JLabel label = new JLabel();
    private DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance();

    public GornerTableCell(){
        formatter.setMaximumFractionDigits(5);
        formatter.setGroupingUsed(false);
        DecimalFormatSymbols dottedDouble = formatter.getDecimalFormatSymbols();
        dottedDouble.setDecimalSeparator('.');
        formatter.setDecimalFormatSymbols(dottedDouble);
        panel.add(label);
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
    }
    @Override
    public Component getTableCellRendererComponent(JTable jTable, Object value, boolean b, boolean b1, int row, int col) {
            String formattedDouble = formatter.format(value);
            label.setText(formattedDouble);
            if (formattedDouble.contains(".")) {
                String check = formattedDouble.substring(formattedDouble.indexOf("."));
                if (col == 1 && (check.length()) <= 4) {
                    panel.setBackground(Color.pink);
                    label.setForeground(Color.BLACK);
                }else {
                    panel.setBackground(Color.white);
                    label.setForeground(Color.BLACK);}
            } else if (col == 1) {
                panel.setBackground(Color.pink);
                label.setForeground(Color.BLACK);
            }else {
                panel.setBackground(Color.white);
                label.setForeground(Color.BLACK);}
            if (col == 1 && needle != null && needle.equals(formattedDouble)) {
                panel.setBackground(Color.RED);
                label.setForeground(Color.BLACK);
            }
            return panel;
    }
    public void setSearch(String needle) {
        this.needle = needle;
    }
    public void setdiap(String searchFrom, String searchTo) {
        this.searchFrom = searchFrom;
        this.searchTo = searchTo;
    }
}