package GUI;

import DataBase.Logic.LogicLabWorks;
import DataBase.Models.LabWorks;
import DataBase.Models.Programs;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class LabWorksPanel extends JPanel {
    LogicLabWorks logicLabWorks = new LogicLabWorks();
    private JList listLabWorks = new JList();
    private JList listPrograms = new JList();
    private JTextField labWorksText = new JTextField();
    private ArrayList<LabWorks> labWorks = new ArrayList();
    private List<Programs> programs;
    private int newLabWorks;
    private int numberLabWorks = -1;

    public LabWorksPanel(){
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
        listLabWorks.setFont(myFont);
        listPrograms.setFont(myFont);
        labWorksText.setFont(myFont);
        ///////////////////////////////////////НАСТРОЙКА КОМПОНОВОК/////////////////////////////////////////////////////
        constraints.fill = GridBagConstraints.BOTH;

        buttonPanel.add(add);
        buttonPanel.add(delete);
        buttonPanel.add(edit);
        textFieldPanel.add(labWorksText);
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
        leftPanel.add(new JScrollPane(listLabWorks));

        constraints.weightx = 0.35f;
        constraints.weighty = 1.0f;
        constraints.gridx = 0;
        constraints.gridy = 0;
        add(leftPanel, constraints);
        constraints.gridx = 1;
        constraints.weightx = 0.65f;
        add(rightPanel, constraints);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        listLabWorks.setModel(new LabWorksListModel());
        listPrograms.setModel(new ProgramsListModel());
        edit.addActionListener(new EditButtonActionListener());
        delete.addActionListener(new DeleteButtonActionListener());
        add.addActionListener(new AddButtonActionListener());
        listLabWorks.addListSelectionListener(new LabWorksListSelectionListener());
        listLabWorks.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    class LabWorksListSelectionListener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent event) {
            numberLabWorks = ((JList<?>)event.getSource()).getSelectedIndex();
            ((ProgramsListModel) listPrograms.getModel()).update();
        }
    }

    public class DeleteButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if(numberLabWorks < 0 || numberLabWorks >= labWorks.size())
                new JOptionPane().showMessageDialog(LabWorksPanel.this, "Не выбрана удаляемая запись");
            else {
                ((LabWorksListModel) listLabWorks.getModel()).delete();
                ProgramsPanel.updateLabWorks();
                FindClonesPanel.updateLabWorks();
            }
        }
    }

    public class AddButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            try {
                newLabWorks = Integer.parseInt(labWorksText.getText());
                ((LabWorksListModel) listLabWorks.getModel()).add();
                ProgramsPanel.updateLabWorks();
                FindClonesPanel.updateLabWorks();
            }
            catch(NumberFormatException e){
                new JOptionPane().showMessageDialog(LabWorksPanel.this, "Неверный формат, введите число");
            }
        }
    }

    public class EditButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if(numberLabWorks < 0 || numberLabWorks >= labWorks.size())
                new JOptionPane().showMessageDialog(LabWorksPanel.this, "Не выбрана удаляемая запись");
            else {
                try {
                    newLabWorks = Integer.parseInt(labWorksText.getText());
                    ((LabWorksListModel) listLabWorks.getModel()).edit();
                    ProgramsPanel.updateLabWorks();
                    FindClonesPanel.updateLabWorks();
                }
                catch(NumberFormatException e){
                    new JOptionPane().showMessageDialog(LabWorksPanel.this, "Неверный формат, введите число");
                }
            }
        }
    }

    public class LabWorksListModel extends AbstractListModel<LabWorks> {
        public LabWorksListModel()
        {
            try {
                synchronized (labWorks) {
                    labWorks = (ArrayList) logicLabWorks.findAll(); // Добавление данных в коллекцию
                }
                fireIntervalAdded(this, 0, labWorks.size());
                fireContentsChanged(this, 0, labWorks.size());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
        // Функция размера массива данных в списке
        public int getSize() {
            synchronized (labWorks) {
                return labWorks.size();
            }
        }
        // Функция извлечения элемента
        public LabWorks getElementAt(int idx) {
            synchronized (labWorks) {
                return labWorks.get(idx);
            }
        }

        public void edit() {
            synchronized (labWorks) {
                labWorks.get(numberLabWorks).setLabwork(newLabWorks);
                logicLabWorks.update(getElementAt(numberLabWorks));
                labWorks = (ArrayList) logicLabWorks.findAll();
            }
            fireContentsChanged(this, 0, 0);
        }

        public void add() {
            synchronized (labWorks) {
                logicLabWorks.save(new LabWorks(newLabWorks));
                labWorks = (ArrayList) logicLabWorks.findAll();
            }
            fireContentsChanged(this, 0, 0);
        }

        public void delete() {
            synchronized (labWorks) {
                logicLabWorks.delete(labWorks.get(numberLabWorks));
                labWorks = (ArrayList) logicLabWorks.findAll();
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
            programs = labWorks.get(numberLabWorks).getPrograms();
            fireContentsChanged(this, 0, 0);
        }
    }
}
