package GUI;

import DataBase.Logic.LogicStudents;
import DataBase.Models.Programs;
import DataBase.Models.Students;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class StudentsPanel extends JPanel{
    LogicStudents logicStudents = new LogicStudents();
    private int numberStudent = -1;
    private JList listStudents = new JList();
    private JList listPrograms = new JList();
    private JTextField studentText = new JTextField();
    private ArrayList<Students> students = new ArrayList();
    private List<Programs> programs;
    private int newStudent;


    public StudentsPanel(){
        setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        JPanel leftPanel = new JPanel(new BorderLayout());
        JPanel rightPanel = new JPanel(new GridBagLayout());

        JPanel buttonAndTextFieldPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        JPanel textFieldPanel = new JPanel(new GridLayout(1, 1, 10, 10));
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        JPanel listProgramsPanel = new JPanel(new GridLayout(1, 1, 10, 10));

        JButton add = new JButton("Добавить");
        JButton delete = new JButton("Удалить");
        JButton edit = new JButton("Редактировать");
        ////////////////////////////////////НАСТРОЙКА ШРИФТОВ///////////////////////////////////////////////////////////
        Font myFont = new Font("Dialog", Font.PLAIN, 14);
        add.setFont(myFont);
        delete.setFont(myFont);
        edit.setFont(myFont);
        listStudents.setFont(myFont);
        listPrograms.setFont(myFont);
        studentText.setFont(myFont);
        ///////////////////////////////////////НАСТРОЙКА КОМПОНОВОК/////////////////////////////////////////////////////
        constraints.fill = GridBagConstraints.BOTH;

        buttonPanel.add(add);
        buttonPanel.add(delete);
        buttonPanel.add(edit);
        textFieldPanel.add(studentText);
        buttonAndTextFieldPanel.add(textFieldPanel);
        buttonAndTextFieldPanel.add(buttonPanel);
        listProgramsPanel.add(new JScrollPane(listPrograms));

        constraints.weightx = 1.0f;
        constraints.weighty = 0.1f;
        constraints.gridx = 0;
        constraints.gridy = 0;
        rightPanel.add(buttonAndTextFieldPanel, constraints);
        constraints.weighty = 0.9f;
        constraints.gridx = 0;
        constraints.gridy = 1;
        rightPanel.add(listProgramsPanel, constraints);
        leftPanel.add(new JScrollPane(listStudents));

        constraints.weightx = 0.35f;
        constraints.weighty = 1.0f;
        constraints.gridx = 0;
        constraints.gridy = 0;
        add(leftPanel, constraints);
        constraints.gridx = 1;
        constraints.weightx = 0.65f;
        add(rightPanel, constraints);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        listStudents.setModel(new StudentsListModel());
        listPrograms.setModel(new ProgramsListModel());
        edit.addActionListener(new EditButtonActionListener());
        delete.addActionListener(new DeleteButtonActionListener());
        add.addActionListener(new AddButtonActionListener());
        listStudents.addListSelectionListener(new StudentsListSelectionListener());
        listStudents.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    class StudentsListSelectionListener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent event) {
            numberStudent = ((JList<?>)event.getSource()).getSelectedIndex();
            ((ProgramsListModel) listPrograms.getModel()).update();
        }
    }

    public class DeleteButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if(numberStudent < 0 || numberStudent >= students.size())
                new JOptionPane().showMessageDialog(StudentsPanel.this, "Не выбрана удаляемая запись");
            else {
                ((StudentsListModel) listStudents.getModel()).delete();
                ProgramsPanel.updateStudents();
            }
        }
    }

    public class AddButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            try {
                newStudent = Integer.parseInt(studentText.getText());
                ((StudentsListModel) listStudents.getModel()).add();
                ProgramsPanel.updateStudents();
            }
            catch(NumberFormatException e){
                new JOptionPane().showMessageDialog(StudentsPanel.this, "Неверный формат, введите число");
            }
        }
    }

    public class EditButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if(numberStudent < 0 || numberStudent >= students.size())
                new JOptionPane().showMessageDialog(StudentsPanel.this, "Не выбрана удаляемая запись");
            else {
                try {
                    newStudent = Integer.parseInt(studentText.getText());
                    ((StudentsListModel) listStudents.getModel()).edit();
                    ProgramsPanel.updateStudents();
                }
                catch(NumberFormatException e){
                    new JOptionPane().showMessageDialog(StudentsPanel.this, "Неверный формат, введите число");
                }
            }
        }
    }

    public class StudentsListModel extends AbstractListModel<Students> {
        public StudentsListModel() {
            try {
                synchronized ( students ) {
                    students = (ArrayList) logicStudents.findAll(); // Добавление данных в коллекцию
                }
                fireIntervalAdded(this, 0, students.size());
                fireContentsChanged(this, 0, students.size());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
        // Функция размера массива данных в списке
        public int getSize() {
            synchronized ( students ) {
                return students.size();
            }
        }
        // Функция извлечения элемента
        public Students getElementAt(int idx) {
            synchronized ( students ) {
                return students.get(idx);
            }
        }

        public void edit() {
            synchronized ( students ) {
                students.get(numberStudent).setStudent(newStudent);
                logicStudents.update(getElementAt(numberStudent));
                students = (ArrayList) logicStudents.findAll();
            }
            fireContentsChanged(this, 0, 0);
        }

        public void add() {
            synchronized ( students ) {
                logicStudents.save(new Students(newStudent));
                students = (ArrayList) logicStudents.findAll();
            }
            fireContentsChanged(this, 0, 0);
        }

        public void delete() {
            synchronized ( students ) {
                logicStudents.delete(students.get(numberStudent));
                students = (ArrayList) logicStudents.findAll();
            }
            fireContentsChanged(this, 0, 0);
        }
    }

    public class ProgramsListModel extends AbstractListModel<Programs> {
        public ProgramsListModel()
        {
        }
        // Функция размера массива данных в списке
        public int getSize() {
            try{
                return programs.size();
            }
            catch(Exception e){
                return 0;
            }
        }
        // Функция извлечения элемента
        public Programs getElementAt(int idx) {
                return programs.get(idx);
        }
        public void update() {
            programs = students.get(numberStudent).getPrograms();
            fireContentsChanged(this, 0, 0);
        }
    }
}