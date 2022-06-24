package DataBase.Logic;

import DataBase.DAO.*;
import DataBase.Interfaces.LogicAbstr;
import DataBase.Models.Programs;
import Lexers.C.CTokenType;
import TokenBased.TokenBased;

import java.util.*;

public class LogicPrograms extends LogicAbstr<Programs> {
    private ProgramsDAO programsDAO = new ProgramsDAO();

    public void save(Programs program){
        SubjectsDAO subjectsDAO = new SubjectsDAO();
        try{ program.setSubject(subjectsDAO.findByObject(program.getSubject())); }
        catch(Exception e){System.out.println("subjectsDAO");
        }

        LabWorksDAO labWorksDAO = new LabWorksDAO();
        try{ program.setLabwork(labWorksDAO.findByObject(program.getLabwork())); }
        catch(Exception e){System.out.println("labWorksDAO");
        }

        VariantsDAO variantsDAO = new VariantsDAO();
        try{ program.setVariant(variantsDAO.findByObject(program.getVariant())); }
        catch(Exception e){System.out.println("variantsDAO");
        }

        StudentsDAO studentsDAO = new StudentsDAO();
        try{ program.setStudent(studentsDAO.findByObject(program.getStudent())); }
        catch(Exception e){System.out.println("studentsDAO");
        }

        addClones(program);
    }

    public void delete(Programs program){
        programsDAO.delete(program);
    }

    public void update(Programs program){
        programsDAO.update(program);
    }

    public List<Programs> findAll(){
        return programsDAO.findAll();
    }

    public List<Programs> findProgramsWithClones(){
        List<Programs> allPrograms = programsDAO.findAll(), programsWithClones = new ArrayList<>();
        for(int i = 0; i < allPrograms.size(); i++){
            if(allPrograms.get(i).getProgramClones().size() > 0){
                programsWithClones.add(allPrograms.get(i));
            }
        }
        return programsWithClones;
    }

    public Programs findById(int id){
        return programsDAO.findById(id);
    }

    private void addClones(Programs program){
        List<Programs> allPrograms = programsDAO.findPrograms(program), clones = new ArrayList<>();
        StringBuffer listOfNumberClones = new StringBuffer();
        TokenBased tokenBased = new TokenBased(CTokenType.values());

        tokenBased.setNewProgram(program.getProgram());
        for(int i =0; i< allPrograms.size(); i++){
            tokenBased.setExpectedClone(allPrograms.get(i).getProgram());
            if (tokenBased.searchClones() == true){
                program.addClone(allPrograms.get(i));
                clones.add(allPrograms.get(i));

                //listOfNumberClones.append(allPrograms.get(i).getId() + " ");
            }
        }
        programsDAO.save(program);

    }
}
