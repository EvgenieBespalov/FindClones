package GUI;

import DataBase.Logic.LogicLabWorks;
import DataBase.Logic.LogicPrograms;
import DataBase.Logic.LogicSubjects;
import DataBase.Logic.LogicVariants;
import DataBase.Models.LabWorks;
import DataBase.Models.Programs;
import DataBase.Models.Subjects;
import DataBase.Models.Variants;
import GUI.PrintClonesToGUI.PrintClones;
import GUI.PrintClonesToGUI.RedirectingOutputStream;
import Lexers.C.CTokenType;
import TokenBased.TokenBased;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.ConsoleHandler;

public class FindClonesPanel extends JPanel {
    static private JComboBox programsComboBox = new JComboBox();
    static private JComboBox subjectsComboBox = new JComboBox();
    static private JComboBox labWorksComboBox = new JComboBox();
    static private JComboBox variantsComboBox = new JComboBox();

    private JComboBox clonesComboBox = new JComboBox();
    private PrintClones  textProgram = new PrintClones();
    private PrintClones textClone = new PrintClones();
    private JTextArea student1 = new JTextArea();
    private JTextArea student2 = new JTextArea();

    private Set<Programs> clones = new HashSet();
    private ArrayList<Programs> programs = new ArrayList();

    public FindClonesPanel(){
        programs = (ArrayList) new LogicPrograms().findProgramsWithClones();

        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;

        JPanel comboBoxSelectedDataOfProgramsPanel = new JPanel();
        JPanel textAreaStudentsPanel = new JPanel();
        JPanel comboBoxPanel = new JPanel();
        JPanel textAreaPanel = new JPanel();
        JButton showAllClones = new JButton("Показать всё");
        JButton showSelectedClones = new JButton("Показать выбранные");

        programsComboBox.setFont(new Font("Dialog", Font.PLAIN, 15));
        clonesComboBox.setFont(new Font("Dialog", Font.PLAIN, 15));
        subjectsComboBox.setFont(new Font("Dialog", Font.PLAIN, 15));
        labWorksComboBox.setFont(new Font("Dialog", Font.PLAIN, 15));
        variantsComboBox.setFont(new Font("Dialog", Font.PLAIN, 15));
        showAllClones.setFont(new Font("Dialog", Font.PLAIN, 15));
        showSelectedClones.setFont(new Font("Dialog", Font.PLAIN, 15));
        student1.setFont(new Font("Dialog", Font.PLAIN, 15));
        student2.setFont(new Font("Dialog", Font.PLAIN, 15));
        textProgram.setFont(new Font("Dialog", Font.PLAIN, 14));
        textClone.setFont(new Font("Dialog", Font.PLAIN, 14));


        comboBoxSelectedDataOfProgramsPanel.setLayout(new GridLayout(1, 5, 10, 10));
        textAreaStudentsPanel.setLayout(new GridLayout(1, 2, 10, 10));
        comboBoxPanel.setLayout(new GridLayout(1, 2, 10, 10));
        textAreaPanel.setLayout(new GridLayout(1, 2, 10, 10));


        comboBoxSelectedDataOfProgramsPanel.add(subjectsComboBox);
        comboBoxSelectedDataOfProgramsPanel.add(labWorksComboBox);
        comboBoxSelectedDataOfProgramsPanel.add(variantsComboBox);
        comboBoxSelectedDataOfProgramsPanel.add(showAllClones);
        comboBoxSelectedDataOfProgramsPanel.add(showSelectedClones);
        textAreaStudentsPanel.add(student1);
        textAreaStudentsPanel.add(student2);
        comboBoxPanel.add(programsComboBox);
        comboBoxPanel.add(clonesComboBox);
        textAreaPanel.add(new JScrollPane(textProgram));
        textAreaPanel.add(new JScrollPane(textClone));

        constraints.weightx = 0.50f;
        constraints.weighty = 0.01f;
        constraints.gridy = 0;
        constraints.gridx = 0;
        add(comboBoxSelectedDataOfProgramsPanel, constraints);
        constraints.weighty = 0.01f;
        constraints.gridy = 1;
        constraints.gridx = 0;
        add(textAreaStudentsPanel, constraints);
        constraints.weighty = 0.01f;
        constraints.gridy = 2;
        constraints.gridx = 0;
        add(comboBoxPanel, constraints);
        constraints.weighty = 0.97f;
        constraints.gridy = 3;
        constraints.gridx = 0;
        add(textAreaPanel, constraints);

        programsComboBox.setModel(new ProgramsBoxModels());
        clonesComboBox.setModel(new ClonesBoxModels());
        subjectsComboBox.setModel(new SubjectsBoxModels());
        labWorksComboBox.setModel(new LabWorksBoxModels());
        variantsComboBox.setModel(new VariantsBoxModels());
        programsComboBox.addActionListener(new ProgramsBoxListener());
        clonesComboBox.addActionListener(new ClonesBoxActionListener());

        showAllClones.addActionListener(new ShowAllClonesButtonActionListener());
        showSelectedClones.addActionListener(new ShowSelectedClonesButtonActionListener());
    }

    class ProgramsBoxListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            clones = (((ProgramsBoxModels) programsComboBox.getModel()).getSelectedItem()).getClonesProgram();
            ((ClonesBoxModels) clonesComboBox.getModel()).update();
            student1.setText(((ProgramsBoxModels) programsComboBox.getModel()).getSelectedItem().getStudent().toString());
            subjectsComboBox.setSelectedItem(((ProgramsBoxModels) programsComboBox.getModel()).getSelectedItem().getSubject());
            labWorksComboBox.setSelectedItem(((ProgramsBoxModels) programsComboBox.getModel()).getSelectedItem().getLabwork());
            variantsComboBox.setSelectedItem(((ProgramsBoxModels) programsComboBox.getModel()).getSelectedItem().getVariant());
        }
    }
    private class ClonesBoxActionListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            try {
                PrintStream originalPrintStream = System.out;
                TokenBased tokenBased = new TokenBased(CTokenType.values());
                textProgram.setText("");
                textClone.setText("");
                tokenBased.setNewProgram((((ProgramsBoxModels) programsComboBox.getModel()).getSelectedItem()).getProgram());
                tokenBased.setExpectedClone((((ClonesBoxModels) clonesComboBox.getModel()).getSelectedItem()).getProgram());
                System.setOut(new PrintStream(new RedirectingOutputStream(textProgram), true));
                tokenBased.printFirstProgram();
                System.setOut(new PrintStream(new RedirectingOutputStream(textClone), true));
                tokenBased.printSecondProgram();
                System.setOut(originalPrintStream);
                student2.setText(((ClonesBoxModels) clonesComboBox.getModel()).getSelectedItem().getStudent().toString());
            }
            catch(Exception e){
               e.printStackTrace();
            }
        }
    }
    private class ShowAllClonesButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            programs = (ArrayList) new LogicPrograms().findProgramsWithClones();
            ((ProgramsBoxModels) programsComboBox.getModel()).update();
        }
    }
    private class ShowSelectedClonesButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            ArrayList<Programs> allPrograms = (ArrayList) new LogicPrograms().findProgramsWithClones();
            programs.clear();
            for(int i = 0; i < allPrograms.size(); i++){
                if(allPrograms.get(i).getSubject() == ((ProgramsBoxModels) programsComboBox.getModel()).getSelectedItem().getSubject() &&
                        allPrograms.get(i).getLabwork() == ((ProgramsBoxModels) programsComboBox.getModel()).getSelectedItem().getLabwork() &&
                        allPrograms.get(i).getVariant() == ((ProgramsBoxModels) programsComboBox.getModel()).getSelectedItem().getVariant()){
                    programs.add(allPrograms.get(i));
                }
            }
            ((ProgramsBoxModels) programsComboBox.getModel()).update();
        }
    }

    private class ProgramsBoxModels extends DefaultComboBoxModel<Programs>{
        ProgramsBoxModels(){
            super(programs.toArray(new Programs[0]));
        }
        @Override
        public Programs getSelectedItem(){
            return (Programs)super.getSelectedItem();
        }
        public void update(){
            programsComboBox.setModel(new ProgramsBoxModels());
        }
    }
    private class ClonesBoxModels extends DefaultComboBoxModel<Programs>{
        ClonesBoxModels(){
            super(clones.toArray(new Programs[0]));
        }
        @Override
        public Programs getSelectedItem(){
            return (Programs)super.getSelectedItem();
        }
        public void update(){
            clonesComboBox.setModel(new ClonesBoxModels());
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
            subjectsComboBox.setModel(new SubjectsBoxModels());
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
        public void update(){
            labWorksComboBox.setModel(new LabWorksBoxModels());
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

    static public void updatePrograms(){
        ((ProgramsBoxModels) programsComboBox.getModel()).update();
    }
    static public void updateVariants(){
        ((VariantsBoxModels) variantsComboBox.getModel()).update();
    }
    static public void updateSubjectss(){
        ((SubjectsBoxModels) subjectsComboBox.getModel()).update();
    }
    static public void updateLabWorks(){
        ((LabWorksBoxModels) labWorksComboBox.getModel()).update();
    }
}
