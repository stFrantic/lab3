package bsu.rfe.java.group6.lab3.Murashkin.varB4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;



public class MainFrame extends JFrame {

    private static final int width = 1200;
    private static final int height = 500;

    private Double[] coeff;

    private JMenuItem saveToTextMenuItem;
    private JMenuItem searchValueMenuItem;
    private JMenuItem informationItem;

    private JTextField from_field;
    private JTextField to_field;
    private JTextField step_field;
    private Box BoxResult;

    private GornerTableCell cell = new GornerTableCell();

    private GornerTableModel data;
    private JFileChooser fileChooser = null;

    private DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance();


    public MainFrame(Double[] coeff) {
        super("Табулирование многочлена на отрезке по схеме Горнера");
        this.coeff = coeff;
        setSize(width, height);
        Toolkit kit = Toolkit.getDefaultToolkit();
        setLocation((kit.getScreenSize().width - width) / 2,
                (kit.getScreenSize().height - height) / 2);

        JMenuBar menu = new JMenuBar();
        setJMenuBar(menu);
        JMenu fileMenu = new JMenu("Файл");
        menu.add(fileMenu);
        JMenu tableMenu = new JMenu("Таблица");
        menu.add(tableMenu);
        JMenu spravkaMenu = new JMenu("Справка");
        menu.add(spravkaMenu);

        Action saveToTextAction = new AbstractAction("Сохранить в файл") {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (fileChooser == null) {
                    fileChooser = new JFileChooser();
                    fileChooser.setCurrentDirectory(new File("."));
                }
                if(fileChooser.showSaveDialog(MainFrame.this)==JFileChooser.APPROVE_OPTION)
                    saveToTextFile(fileChooser.getSelectedFile());
            }
        };
        saveToTextMenuItem = fileMenu.add(saveToTextAction);
        saveToTextMenuItem.setEnabled(true);

        Action searchValueAction = new AbstractAction("Найти значение многочлена") {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String value = JOptionPane.showInputDialog(MainFrame.this,
                        "Введите значение для поиска", "Поиск значения",
                        JOptionPane.QUESTION_MESSAGE);
                cell.setSearch(value);
                getContentPane().repaint();
            }
        };
        searchValueMenuItem = tableMenu.add(searchValueAction);
        searchValueMenuItem.setEnabled(true);

        Action aboutProgramAction=new AbstractAction("О программе") {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Box information=Box.createVerticalBox();
                JLabel author = new JLabel("Автор: Мурашкин Георгий");
                JLabel group = new JLabel("студент 6 группы");
                information.add(Box.createVerticalGlue());
                information.add(author);
                information.add(Box.createVerticalStrut(10));
                information.add(group);
                information.add(Box.createVerticalStrut(10));
                information.add(Box.createVerticalStrut(10));
                information.add(Box.createVerticalGlue());


                JOptionPane.showMessageDialog(MainFrame.this,
                        information, "" +
                                "О программе", JOptionPane.INFORMATION_MESSAGE);

            }
        };
        informationItem = spravkaMenu.add(aboutProgramAction);
        informationItem.setEnabled(true);


        JButton calculateButton = new JButton("Вычислить");
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    // Считать значения границ отрезка, шага из полей ввода
                    Double from = Double.parseDouble(from_field.getText());
                    Double to = Double.parseDouble(to_field.getText());
                    Double step = Double.parseDouble(step_field.getText());
                    if((from<to && step>0.0)||(from>to && step<0.0)) {

                        data = new GornerTableModel(from, to, step, MainFrame.this.coeff);
                        JTable table = new JTable(data);
                        table.setDefaultRenderer(Double.class, cell);
                        table.setRowHeight(30);
                        BoxResult.removeAll();
                        BoxResult.add(new JScrollPane(table));
                        getContentPane().validate();
                        //  Пометить ряд элементов меню как доступных
                        saveToTextMenuItem.setEnabled(true);
                        searchValueMenuItem.setEnabled(true);
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(MainFrame.this,
                                "Проверьте введенные данные\nНевозможно провести вычисления", "" +
                                        "Ошибка ввода", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    // В случае ошибки преобразования показать сообщение об ошибке
                    JOptionPane.showMessageDialog(MainFrame.this, "Ошибка в формате записи числа с плавающей точкой",
                            "Ошибочный формат числа", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        JButton resetButton = new JButton("Очистить поля");
        // Задать действие на нажатие "Очистить поля" и привязать к кнопке
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                // Установить в полях ввода значения по умолчанию
                from_field.setText("0.0");
                to_field.setText("1.0");
                step_field.setText("0.1");
                // Удалить все вложенные элементы контейнера BoxResult
                BoxResult.removeAll();
                // Добавить в контейнер пустую панель
                BoxResult.add(new JPanel());
                // Пометить элементы меню как недоступные
                saveToTextMenuItem.setEnabled(false);
                searchValueMenuItem.setEnabled(false);
                // Обновить область содержания главного окна
                getContentPane().validate();
            }
        });

        Box dataBox= Box.createHorizontalBox();
        dataBox.add(Box.createHorizontalGlue());
        JLabel from_label=new JLabel("х изменяется на интервале от:");
        dataBox.add(from_label);
        dataBox.add(Box.createHorizontalStrut(10));
        from_field = new JTextField("0.0",10);
        from_field.setMaximumSize(from_field.getPreferredSize());
        dataBox.add(from_field);
        dataBox.add(Box.createHorizontalStrut(20));
        JLabel to_label=new JLabel("до:");
        dataBox.add(to_label);
        dataBox.add(Box.createHorizontalStrut(10));
        to_field = new JTextField("0.0",10);
        to_field.setMaximumSize(to_field.getPreferredSize());
        dataBox.add(to_field);
        dataBox.add(Box.createHorizontalStrut(20));
        JLabel step_label=new JLabel("c шагом:");
        dataBox.add(step_label);
        dataBox.add(Box.createHorizontalStrut(10));
        step_field = new JTextField("0.0",10);
        step_field.setMaximumSize(step_field.getPreferredSize());
        dataBox.add(step_field);
        dataBox.add(Box.createHorizontalGlue());
        dataBox.setPreferredSize(new
                Dimension(new Double(dataBox.getMaximumSize().getWidth()).intValue(), new
                Double(dataBox.getMinimumSize().getHeight()).intValue()*2));
        getContentPane().add(dataBox, BorderLayout.NORTH);

        Box buttonBox=Box.createHorizontalBox();
        buttonBox.add(Box.createHorizontalGlue());
        buttonBox.add(calculateButton);
        buttonBox.add(Box.createHorizontalStrut(20));
        buttonBox.add(resetButton);
        buttonBox.add(Box.createHorizontalGlue());
        buttonBox.setPreferredSize(new
                Dimension(new Double(buttonBox.getMaximumSize().getWidth()).intValue(), new
                Double(buttonBox.getMinimumSize().getHeight()).intValue()*2));
        getContentPane().add(buttonBox, BorderLayout.SOUTH);

        BoxResult=Box.createHorizontalBox();
        BoxResult.add(new JPanel());
        getContentPane().add(BoxResult, BorderLayout.CENTER);

    }
    protected void saveToTextFile(File selectedFile) {
        try {
            PrintStream out = new PrintStream(selectedFile);
            for (int i = 0; i < data.getRowCount(); i++) {
                out.println( data.getValueAt(i, 0) + " " + data.getValueAt(i, 1));
            }
            out.close();
        } catch (FileNotFoundException e) {
        }
    }
        public static void main(String[] args)
        {

            if (args.length==0) { System.out.println("Невозможно табулировать многочлен, для которого не задано ни одного коэффициента!");
                System.exit(-1);
            }
            // Зарезервировать места в массиве коэффициентов столько, сколько аргументов командной строки
            Double[] coeff = new Double[args.length];
            int i = 0;
            try {
                // Перебрать все аргументы, пытаясь преобразовать их в Double
                for (String arg: args) {
                    coeff[i] = Double.parseDouble(arg);
                    i++;
                }
            } catch (NumberFormatException ex) {
                // Если преобразование невозможно - сообщить об ошибке и завершиться
                System.out.println("Ошибка преобразования строки '" + args[i] + "' в число типа Double");
                System.exit(-2);
            }

            MainFrame frame=new MainFrame(coeff);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);

        }

}