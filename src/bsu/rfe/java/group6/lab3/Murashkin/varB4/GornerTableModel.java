package bsu.rfe.java.group6.lab3.Murashkin.varB4;

import javax.swing.table.AbstractTableModel;

public class GornerTableModel extends AbstractTableModel	{

    private Double[] coefficients;
    private Double from;
    private Double to;
    private Double step;
    private double result[] = new double[3];

    public GornerTableModel(Double from, Double to, Double step, Double[] coefficients)	{
        this.coefficients = coefficients;
        this.from = from;
        this.to = to;
        this.step = step;
    }

    public Double getFrom() {
        return from;
    }
    public Double getTo() {
        return to;
    }
    public Double getStep() {
        return step;
    }

    public int getColumnCount() {
        return 3;
    }

    public int getRowCount() {
        return new Double(Math.ceil((to - from)/step)).intValue()+1;
    }

    public Object getValueAt(int row, int col) {
        double x = from + step * row;
        switch (col) {
            case 0:
                return x;
            case 1: {
                result[0] = 0.0;
                for (int i = 0; i < coefficients.length; i++) {
                    result[0] += Math.pow(x, coefficients.length - 1 - i) * coefficients[i];
                }
                return result[0];
            }
            default: {
                boolean isSimple = true;
                int temp = (int) x;
                for (int i = 0; i < coefficients.length; i++)
                    if (temp % coefficients[i] != 0 && isSimple) {
                        isSimple = false;
                        break;
                    }
                return isSimple;
            }
        }
    }
    public Class<?> getColumnClass(int col) {
        if(col == 0 || col == 1)
        return Double.class;
        else return Boolean.class;
    }

    public String getColumnName(int col) {

        switch (col){
            case 0:
                return "Значение X";
            case 1:
                return  "Значение многочлена";
            default:
                return "Взаимно простые?";
        }
    }
}