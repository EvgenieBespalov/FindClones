package GUI;

import DataBase.Logic.LogicSubjects;
import DataBase.Models.Programs;
import DataBase.Models.Subjects;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class SubjectsPanel extends JPanel {
    LogicSubjects logicSubjects = new LogicSubjects();
    private JList listSubjects = new JList();
    private JList listPrograms = new JList();
    private JTextField subjectsText = new JTextField();
    private ArrayList<Subjects> subjects = new ArrayList();
    private List<Programs> programs;
    private String newSubjects;
    private int numberSubjects = -1;

    public SubjectsPanel(){
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
        listSubjects.setFont(myFont);
        listPrograms.setFont(myFont);
        subjectsText.setFont(myFont);
        ///////////////////////////////////////НАСТРОЙКА КОМПОНОВОК/////////////////////////////////////////////////////
        constraints.fill = GridBagConstraints.BOTH;

        buttonPanel.add(add);
        buttonPanel.add(delete);
        buttonPanel.add(edit);
        textFieldPanel.add(subjectsText);
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
        leftPanel.add(new JScrollPane(listSubjects));

        constraints.weightx = 0.35f;
        constraints.weighty = 1.0f;
        constraints.gridx = 0;
        constraints.gridy = 0;
        add(leftPanel, constraints);
        constraints.gridx = 1;
        constraints.weightx = 0.65f;
        add(rightPanel, constraints);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        listSubjects.setModel(new SubjectsListModel());
        listPrograms.setModel(new ProgramsListModel());
        edit.addActionListener(new EditButtonActionListener());
        delete.addActionListener(new DeleteButtonActionListener());
        add.addActionListener(new AddButtonActionListener());
        listSubjects.addListSelectionListener(new SubjectsListSelectionListener());
        listSubjects.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    class SubjectsListSelectionListener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent event) {
            numberSubjects = ((JList<?>)event.getSource()).getSelectedIndex();
            ((ProgramsListModel) listPrograms.getModel()).update();
        }
    }

    public class DeleteButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if(numberSubjects < 0 || numberSubjects >= subjects.size())
                new JOptionPane().showMessageDialog(SubjectsPanel.this, "Не выбрана удаляемая запись");
            else {
                ((SubjectsListModel) listSubjects.getModel()).delete();
                ProgramsPanel.updateSubjectss();
                FindClonesPanel.updateSubjectss();
            }
        }
    }

    public class AddButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            try {
                newSubjects = subjectsText.getText();
                ((SubjectsListModel) listSubjects.getModel()).add();
                ProgramsPanel.updateSubjectss();
                FindClonesPanel.updateSubjectss();
            }
            catch(NumberFormatException e){
                new JOptionPane().showMessageDialog(SubjectsPanel.this, "Неверный формат, введите строку");
            }
        }
    }

    public class EditButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if(numberSubjects < 0 || numberSubjects >= subjects.size())
                new JOptionPane().showMessageDialog(SubjectsPanel.this, "Не выбрана удаляемая запись");
            else {
                try {
                    newSubjects = subjectsText.getText();
                    ((SubjectsListModel) listSubjects.getModel()).edit();
                    ProgramsPanel.updateSubjectss();
                    FindClonesPanel.updateSubjectss();
                }
                catch(NumberFormatException e){
                    new JOptionPane().showMessageDialog(SubjectsPanel.this, "Неверный формат, введите строку");
                }
            }
        }
    }

    public class SubjectsListModel extends AbstractListModel<Subjects> {
        public SubjectsListModel()
        {
            try {
                synchronized (subjects) {
                    subjects = (ArrayList) logicSubjects.findAll(); // Добавление данных в коллекцию
                }
                fireIntervalAdded(this, 0, subjects.size());
                fireContentsChanged(this, 0, subjects.size());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
        // Функция размера массива данных в списке
        public int getSize() {
            synchronized (subjects) {
                return subjects.size();
            }
        }
        // Функция извлечения элемента
        public Subjects getElementAt(int idx) {
            synchronized (subjects) {
                return subjects.get(idx);
            }
        }

        public void edit() {
            synchronized (subjects) {
                subjects.get(numberSubjects).setSubject(newSubjects);
                logicSubjects.update(getElementAt(numberSubjects));
                subjects = (ArrayList) logicSubjects.findAll();
            }
            fireContentsChanged(this, 0, 0);
        }

        public void add() {
            synchronized (subjects) {
                logicSubjects.save(new Subjects(newSubjects));
                subjects = (ArrayList) logicSubjects.findAll();
            }
            fireContentsChanged(this, 0, 0);
        }

        public void delete() {
            synchronized (subjects) {
                logicSubjects.delete(subjects.get(numberSubjects));
                subjects = (ArrayList) logicSubjects.findAll();
            }
            fireContentsChanged(this, 0, 0);
        }
    }

    public class ProgramsListModel extends AbstractListModel<Programs> {
        public ProgramsListModel()
        { }
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
            programs = subjects.get(numberSubjects).getPrograms();
            fireContentsChanged(this, 0, 0);
        }
    }
}