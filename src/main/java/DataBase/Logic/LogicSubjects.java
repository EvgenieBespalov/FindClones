package DataBase.Logic;

import DataBase.DAO.SubjectsDAO;
import DataBase.Interfaces.LogicAbstr;
import DataBase.Models.Subjects;
import java.util.List;

public class LogicSubjects extends LogicAbstr<Subjects> {
    private SubjectsDAO subjectsDAO = new SubjectsDAO();

    public void save(Subjects subject){
        try{
            subjectsDAO.save(subject);
        }
        catch(Exception e){
            System.out.println("Ошибка: " + e);
        }
    }
    public void delete(Subjects subject){
        subjectsDAO.delete(subject);
    }
    public void update(Subjects subject){
        subjectsDAO.update(subject);
    }
    public List<Subjects> findAll(){
        return subjectsDAO.findAll();
    }
    public Subjects findById(int id){
        try{
            return subjectsDAO.findById(id);
        }
        catch(Exception e){
            System.out.println("Ошибка: " + e);
            return null;
        }
    }
}
