package GUI;

import DataBase.Logic.*;
import DataBase.Models.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ProgramsPanel extends JPanel {
    LogicPrograms logicPrograms = new LogicPrograms();
    private int numberPrograms = -1;
    private ArrayList<Programs> programs = new ArrayList();
    private String newPrograms;

    private JList listPrograms = new JList();
    static private JComboBox subjectComboBox = new JComboBox();
    static private JComboBox labWorksComboBox = new JComboBox();
    static private JComboBox variantsComboBox = new JComboBox();
    static private JComboBox studentComboBox = new JComboBox();
    private JTextArea textProgram = new JTextArea();

    public ProgramsPanel(){
        ////////////////////////////////ИНИЦИАЛИЗАЦИЯ ПЕРЕМЕННЫХ////////////////////////////////////////////////////////
        JPanel leftPanel = new JPanel();
        JPanel rightPanel = new JPanel();
        JPanel comboAndButtonPanel = new JPanel();
        JPanel comboBoxPanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        JButton addFile = new JButton("Добавить");
        JButton deleteProgram = new JButton("Удалить");
        JButton editProgram = new JButton("Редактировать");
        JButton loadFile = new JButton("Загрузить");
        JButton showAllPrograms = new JButton("Показать все программы");
        JButton showProgramsSelected = new JButton("Показать прикрепленные программы");
        GridBagConstraints constraints = new GridBagConstraints();
        Dimension comboAndButtonDimension = new Dimension(300, 30);
        ////////////////////////////////////НАСТРОЙКА ШРИФТОВ///////////////////////////////////////////////////////////
        Font myFont = new Font("Dialog", Font.PLAIN, 14);
        addFile.setFont(myFont);
        deleteProgram.setFont(myFont);
        editProgram.setFont(myFont);
        loadFile.setFont(myFont);
        showAllPrograms.setFont(myFont);
        showProgramsSelected.setFont(myFont);
        subjectComboBox.setFont(myFont);
        labWorksComboBox.setFont(myFont);
        variantsComboBox.setFont(myFont);
        studentComboBox.setFont(myFont);
        listPrograms.setFont(myFont);
        textProgram.setFont(new Font("Dialog", Font.PLAIN, 12));
        ////////////////////////////////НАСТРОЙКА ЛОЙАУТОВ//////////////////////////////////////////////////////////////
        setLayout(new GridBagLayout());
        leftPanel.setLayout(new BorderLayout());
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        comboAndButtonPanel.setLayout(new GridLayout(1, 2, 10, 10));
        comboBoxPanel.setLayout(new BoxLayout(comboBoxPanel, BoxLayout.Y_AXIS));
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        ///////////////////////////////НАСТРОЙКА МОДЕЛЕЙ////////////////////////////////////////////////////////////////
        listPrograms.setModel(new ProgramsListModel());
        subjectComboBox.setModel(new SubjectsBoxModels());
        labWorksComboBox.setModel(new LabWorksBoxModels());
        variantsComboBox.setModel(new VariantsBoxModels());
        studentComboBox.setModel(new StudentsBoxModels());
        ////////////////////////////////////НАСТРОЙКА РАЗМЕРОВ//////////////////////////////////////////////////////////
        setPreferredSize(new Dimension(50, 50));
        addFile.setMaximumSize(comboAndButtonDimension);
        deleteProgram.setMaximumSize(comboAndButtonDimension);
        editProgram.setMaximumSize(comboAndButtonDimension);
        loadFile.setMaximumSize(comboAndButtonDimension);
        showAllPrograms.setMaximumSize(comboAndButtonDimension);
        showProgramsSelected.setMaximumSize(comboAndButtonDimension);
        subjectComboBox.setMaximumSize(comboAndButtonDimension);
        labWorksComboBox.setMaximumSize(comboAndButtonDimension);
        variantsComboBox.setMaximumSize(comboAndButtonDimension);
        studentComboBox.setMaximumSize(comboAndButtonDimension);
        comboAndButtonPanel.setMaximumSize(new Dimension(1000, 300));
        ////////////////////////////////////НАСТРОЙКА ПАНЕЛЕЙ///////////////////////////////////////////////////////////
        comboBoxPanel.add(subjectComboBox);
        comboBoxPanel.add(labWorksComboBox);
        comboBoxPanel.add(variantsComboBox);
        comboBoxPanel.add(studentComboBox);
        comboAndButtonPanel.add(comboBoxPanel);

        buttonPanel.add(addFile);
        buttonPanel.add(deleteProgram);
        buttonPanel.add(editProgram);
        buttonPanel.add(loadFile);
        buttonPanel.add(showAllPrograms);
        buttonPanel.add(showProgramsSelected);
        comboAndButtonPanel.add(buttonPanel);
        rightPanel.add(comboAndButtonPanel);
        leftPanel.add(new JScrollPane(listPrograms));

        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 0.35f;
        constraints.weighty = 0.10f;
        constraints.gridy = 0;
        constraints.gridx = 0;
        add(leftPanel, constraints);
        constraints.gridy = 0;
        constraints.gridx = 1;
        constraints.weightx = 0.65f;
        add(rightPanel, constraints);
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridy = 1;
        constraints.gridx = 0;
        constraints.gridwidth = 2;
        constraints.weightx = 1.00f;
        constraints.weighty = 0.90f;
        add(new JScrollPane(textProgram), constraints);
        //////////////////////////////////НАСТРОЙКА СЛУШАТЕЛЕЙ//////////////////////////////////////////////////////////
        addFile.addActionListener(new AddProgramButtonActionListener());
        editProgram.addActionListener(new EditButtonActionListener());
        deleteProgram.addActionListener(new DeleteButtonActionListener());
        loadFile.addActionListener(new LoadFileButtonActionListener());
        showAllPrograms.addActionListener(new ShowAllProgramsButtonActionListener());
        showProgramsSelected.addActionListener(new ShowProgramsSelectedButtonActionListener());
        listPrograms.addListSelectionListener(new ProgramsListSelectionListener());
        listPrograms.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private class ProgramsListSelectionListener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent event) {
            numberPrograms = ((JList<?>)event.getSource()).getSelectedIndex();
            textProgram.setText(programs.get(numberPrograms).getProgram());
            subjectComboBox.setSelectedItem(programs.get(numberPrograms).getSubject());
            labWorksComboBox.setSelectedItem(programs.get(numberPrograms).getLabwork());
            variantsComboBox.setSelectedItem(programs.get(numberPrograms).getVariant());
            studentComboBox.setSelectedItem(programs.get(numberPrograms).getStudent());
        }
    }
    private class DeleteButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if(numberPrograms < 0 || numberPrograms >= programs.size())
                new JOptionPane().showMessageDialog(ProgramsPanel.this, "Не выбрана удаляемая запись");
            else {
                ((ProgramsListModel) listPrograms.getModel()).delete();
                FindClonesPanel.updatePrograms();
            }
        }
    }
    private class AddProgramButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if(newPrograms != null) {
                 logicPrograms.save(new Programs(newPrograms, (((SubjectsBoxModels) subjectComboBox.getModel()).getSelectedItem()),
                        (((LabWorksBoxModels) labWorksComboBox.getModel()).getSelectedItem()),
                        (((VariantsBoxModels) variantsComboBox.getModel()).getSelectedItem()),
                        (((StudentsBoxModels) studentComboBox.getModel()).getSelectedItem())));
                ((ProgramsListModel) listPrograms.getModel()).update();
                newPrograms = null;
                FindClonesPanel.updatePrograms();
            }
            else
                JOptionPane.showMessageDialog(ProgramsPanel.this, "Файл не загружен");
        }
    }
    private class LoadFileButtonActionListener implements ActionListener {
        private  JFileChooser fileChooser = new JFileChooser();
        public void actionPerformed(ActionEvent e) {
            fileChooser.setDialogTitle("Выбор директории");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int result = fileChooser.showOpenDialog(ProgramsPanel.this);
            if (result == JFileChooser.APPROVE_OPTION )
            {
                try {
                    JOptionPane.showMessageDialog(ProgramsPanel.this, "Файл " + fileChooser.getSelectedFile() + " сохранен");
                    newPrograms = readUsingBufferedReader(fileChooser.getSelectedFile());
                } catch (IOException ioException) {
                    JOptionPane.showMessageDialog(ProgramsPanel.this, "Ошибка загрузки " + ioException);
                    ioException.printStackTrace();
                }
            }
        }
        public static String readUsingBufferedReader(File fileName) throws IOException {
            BufferedReader reader = new BufferedReader( new FileReader(fileName));
            String line = null;
            StringBuilder stringBuilder = new StringBuilder();
            String ls = System.getProperty("line.separator");
            while( ( line = reader.readLine() ) != null ) {
                stringBuilder.append( line );
                stringBuilder.append( ls );
            }

            stringBuilder.deleteCharAt(stringBuilder.length()-1);
            return stringBuilder.toString();
        }
    }
    private class EditButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if(numberPrograms < 0 || numberPrograms >= programs.size())
                new JOptionPane().showMessageDialog(ProgramsPanel.this, "Не выбрана удаляемая запись");
            else {
                try {
                    newPrograms = textProgram.getText();
                    ((ProgramsListModel) listPrograms.getModel()).edit();
                    FindClonesPanel.updatePrograms();
                }
                catch(NumberFormatException e){
                    new JOptionPane().showMessageDialog(ProgramsPanel.this, "Неверный формат, введите число");
                }
            }
        }
    }
    private class ShowAllProgramsButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            ((ProgramsListModel) listPrograms.getModel()).showEverything();
        }
    }
    private class ShowProgramsSelectedButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            ((ProgramsListModel) listPrograms.getModel()).showSelected();
        }
    }

    private class ProgramsListModel extends AbstractListModel<Programs> {
        public ProgramsListModel() {
            try {
                synchronized (programs) {
                    programs = (ArrayList) logicPrograms.findAll(); // Добавление данных в коллекцию
                }
                fireIntervalAdded(this, 0, programs.size());
                fireContentsChanged(this, 0, programs.size());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
        public int getSize() {
            synchronized (programs) {
                return programs.size();
            }
        }
        public Programs getElementAt(int idx) {
            synchronized (programs) {
                return programs.get(idx);
            }
        }

        public void update(){
            programs = (ArrayList) logicPrograms.findAll();
            /*ArrayList<Programs> allPrograms = (ArrayList) logicPrograms.findAll();
            programs = new ArrayList();
            for(int i = 0; i < allPrograms.size(); i++){
                if(allPrograms.get(i).getSubject().equality((Subjects) subjectComboBox.getModel().getSelectedItem()) &&
                         allPrograms.get(i).getLabwork().equality((LabWorks) labWorksComboBox.getModel().getSelectedItem()) &&
                         allPrograms.get(i).getVariant().equality((Variants) variantsComboBox.getModel().getSelectedItem())){
                    programs.add(allPrograms.get(i));

                }
            }*/
            fireContentsChanged(this, 0, 0);
        }
        public void showEverything(){
            programs = (ArrayList) logicPrograms.findAll();
            fireContentsChanged(this, 0, 0);
        }
        public void showSelected(){
            ArrayList<Programs> allPrograms = (ArrayList) logicPrograms.findAll();
            programs = new ArrayList();
            for(int i = 0; i < allPrograms.size(); i++){
                if(allPrograms.get(i).getSubject().equality((Subjects) subjectComboBox.getModel().getSelectedItem()) &&
                        allPrograms.get(i).getLabwork().equality((LabWorks) labWorksComboBox.getModel().getSelectedItem()) &&
                        allPrograms.get(i).getVariant().equality((Variants) variantsComboBox.getModel().getSelectedItem())){
                    programs.add(allPrograms.get(i));

                }
            }
            fireContentsChanged(this, 0, 0);
        }

        public void edit() {
            synchronized (programs) {
                programs.get(numberPrograms).setProgram(newPrograms);
                programs.get(numberPrograms).setSubject(((SubjectsBoxModels) subjectComboBox.getModel()).getSelectedItem());
                programs.get(numberPrograms).setLabwork((((LabWorksBoxModels) labWorksComboBox.getModel()).getSelectedItem()));
                programs.get(numberPrograms).setVariant((((VariantsBoxModels) variantsComboBox.getModel()).getSelectedItem()));
                programs.get(numberPrograms).setStudent((((StudentsBoxModels) studentComboBox.getModel()).getSelectedItem()));
                newPrograms = null;
                logicPrograms.update(getElementAt(numberPrograms));
                programs = (ArrayList) logicPrograms.findAll();
            }
            fireContentsChanged(this, 0, 0);
        }

        public void delete() {
            synchronized (programs) {
                logicPrograms.delete(programs.get(numberPrograms));
                programs = (ArrayList) logicPrograms.findAll();
            }
            fireContentsChanged(this, 0, 0);
        }
    }

    private class VariantsBoxModels extends DefaultComboBoxModel<Variants>{
        VariantsBoxModels(){
            super(new LogicVariants().findAll().toArray(new Variants[0]));
        }
        @Override
        public Variants getSelectedItem(){
            return (Variants)super.getSelectedItem();
        }
        public void update(){
            variantsComboBox.setModel(new VariantsBoxModels());
        }
    }
    private class LabWorksBoxModels extends DefaultComboBoxModel<LabWorks>{
        LabWorksBoxModels(){
            super(new LogicLabWorks().findAll().toArray(new LabWorks[0]));
        }
        @Override
        public LabWorks getSelectedItem(){
            return (LabWorks)super.getSelectedItem();
        }
        /*@Override
        public void setSelectedItem(Object object){
            (LabWorks)super.setSelectedItem(object);
        }*/
        public void update(){
            labWorksComboBox.setModel(new LabWorksBoxModels());
        }
    }
    private class StudentsBoxModels extends DefaultComboBoxModel<Students>{
        StudentsBoxModels(){
            super(new LogicStudents().findAll().toArray(new Students[0]));
        }
        @Override
        public Students getSelectedItem(){
            return (Students)super.getSelectedItem();
        }
        public void update(){
            studentComboBox.setModel(new StudentsBoxModels());
        }
    }
    private class SubjectsBoxModels extends DefaultComboBoxModel<Subjects>{
        SubjectsBoxModels(){
            super(new LogicSubjects().findAll().toArray(new Subjects[0]));
        }
        @Override
        public Subjects getSelectedItem(){
            return (Subjects)super.getSelectedItem();
        }
        public void update(){
            subjectComboBox.setModel(new SubjectsBoxModels());
        }
    }

    static public void updateVariants(){
        ((VariantsBoxModels) variantsComboBox.getModel()).update();
    }
    static public void updateSubjectss(){
        ((SubjectsBoxModels) subjectComboBox.getModel()).update();
    }
    static public void updateLabWorks(){
        ((LabWorksBoxModels) labWorksComboBox.getModel()).update();
    }
    static public void updateStudents(){
        ((StudentsBoxModels) studentComboBox.getModel()).update();
    }
}