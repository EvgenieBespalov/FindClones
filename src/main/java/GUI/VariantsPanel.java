package GUI;

import DataBase.Logic.LogicVariants;
import DataBase.Models.Programs;
import DataBase.Models.Variants;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class VariantsPanel extends JPanel {
    LogicVariants logicVariants = new LogicVariants();
    private JList listVariants = new JList();
    private JList listPrograms = new JList();
    private JTextField variantsText = new JTextField();
    private ArrayList<Variants> variants = new ArrayList();
    private List<Programs> programs;
    private int newVariants;
    private int numberVariants = -1;

    public VariantsPanel(){
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
        listVariants.setFont(myFont);
        listPrograms.setFont(myFont);
        variantsText.setFont(myFont);
        ///////////////////////////////////////НАСТРОЙКА КОМПОНОВОК/////////////////////////////////////////////////////
        constraints.fill = GridBagConstraints.BOTH;

        buttonPanel.add(add);
        buttonPanel.add(delete);
        buttonPanel.add(edit);
        textFieldPanel.add(variantsText);
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
        leftPanel.add(new JScrollPane(listVariants));

        constraints.weightx = 0.35f;
        constraints.weighty = 1.0f;
        constraints.gridx = 0;
        constraints.gridy = 0;
        add(leftPanel, constraints);
        constraints.gridx = 1;
        constraints.weightx = 0.65f;
        add(rightPanel, constraints);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        listVariants.setModel(new VarinatsListModel());
        listPrograms.setModel(new ProgramsListModel());
        edit.addActionListener(new EditButtonActionListener());
        delete.addActionListener(new DeleteButtonActionListener());
        add.addActionListener(new AddButtonActionListener());
        listVariants.addListSelectionListener(new VarinatsListSelectionListener());
        listVariants.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    class VarinatsListSelectionListener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent event) {
            numberVariants = ((JList<?>)event.getSource()).getSelectedIndex();
            ((ProgramsListModel) listPrograms.getModel()).update();
        }
    }

    public class DeleteButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if(numberVariants < 0 || numberVariants >= variants.size())
                new JOptionPane().showMessageDialog(VariantsPanel.this, "Не выбрана удаляемая запись");
            else {
                ((VarinatsListModel) listVariants.getModel()).delete();
                ProgramsPanel.updateVariants();
                FindClonesPanel.updateVariants();
            }
        }
    }

    public class AddButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            try {
                newVariants = Integer.parseInt(variantsText.getText());
                ((VarinatsListModel) listVariants.getModel()).add();
                ProgramsPanel.updateVariants();
                FindClonesPanel.updateVariants();
            }
            catch(NumberFormatException e){
                new JOptionPane().showMessageDialog(VariantsPanel.this, "Неверный формат, введите число");
            }
        }
    }

    public class EditButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if(numberVariants < 0 || numberVariants >= variants.size())
                new JOptionPane().showMessageDialog(VariantsPanel.this, "Не выбрана удаляемая запись");
            else {
                try {
                    newVariants = Integer.parseInt(variantsText.getText());
                    ((VarinatsListModel) listVariants.getModel()).edit();
                    ProgramsPanel.updateVariants();
                    FindClonesPanel.updateVariants();
                }
                catch(NumberFormatException e){
                    new JOptionPane().showMessageDialog(VariantsPanel.this, "Неверный формат, введите число");
                }
            }
        }
    }

    public class VarinatsListModel extends AbstractListModel<Variants> {
        public VarinatsListModel()
        {
            try {
                synchronized (variants) {
                    variants = (ArrayList) logicVariants.findAll(); // Добавление данных в коллекцию
                }
                fireIntervalAdded(this, 0, variants.size());
                fireContentsChanged(this, 0, variants.size());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
        // Функция размера массива данных в списке
        public int getSize() {
            synchronized (variants) {
                return variants.size();
            }
        }
        // Функция извлечения элемента
        public Variants getElementAt(int idx) {
            synchronized (variants) {
                return variants.get(idx);
            }
        }

        public void edit() {
            synchronized (variants) {
                variants.get(numberVariants).setVariant(newVariants);
                logicVariants.update(getElementAt(numberVariants));
                variants = (ArrayList) logicVariants.findAll();
            }
            fireContentsChanged(this, 0, 0);
        }

        public void add() {
            synchronized (variants) {
                logicVariants.save(new Variants(newVariants));
                variants = (ArrayList) logicVariants.findAll();
            }
            fireContentsChanged(this, 0, 0);
        }

        public void delete() {
            synchronized (variants) {
                logicVariants.delete(variants.get(numberVariants));
                variants = (ArrayList) logicVariants.findAll();
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
            programs = variants.get(numberVariants).getPrograms();
            fireContentsChanged(this, 0, 0);
        }
    }
}

