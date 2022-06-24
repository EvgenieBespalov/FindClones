package GUI;

import javax.swing.*;
import java.awt.*;

public class GUI  extends JFrame {
    public GUI() {
        super("Поиск клонов");
        final int DEFAULT_X = 1000;  //размеры фрейма при запуске
        final int DEFAULT_Y = 1000;
        this.setSize(DEFAULT_X, DEFAULT_Y);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // завершение программы при закрытии фрейма
        this.setMinimumSize(new Dimension(400,500));         //минимальный размер фрейма
        this.setLocationRelativeTo(null); //фрейм в центре экрана
        this.setFocusable(true);          //фокус на фрейме при запуске
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        // глобальный контейнер
        JPanel mainPanel = new JPanel();
        add(mainPanel);
        mainPanel.setLayout(new BorderLayout());

        Font font = new Font("Dialog", Font.PLAIN, 14);;
        final JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(font);
        tabbedPane.addTab("Студенты", new StudentsPanel());
        tabbedPane.addTab("Лабораторные работы", new LabWorksPanel());
        tabbedPane.addTab("Учебные предметы", new SubjectsPanel());
        tabbedPane.addTab("Варианты ЛР", new VariantsPanel());
        tabbedPane.addTab("Программы", new ProgramsPanel());
        tabbedPane.addTab("Найденные клоны", new FindClonesPanel());

        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        this.setVisible(true);
    }
}
