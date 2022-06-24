package DataBase.Logic;

import DataBase.DAO.LabWorksDAO;
import DataBase.Interfaces.LogicAbstr;
import DataBase.Models.LabWorks;
import java.util.List;

public class LogicLabWorks extends LogicAbstr<LabWorks> {
    LabWorksDAO labWorksDAO = new LabWorksDAO();
    public LogicLabWorks(){}
    public void save(LabWorks labWork){
        try{
            labWorksDAO.save(labWork);
        }
        catch(Exception e){
            System.out.println("Ошибка: " + e);
        }
    }
    public void delete(LabWorks labWorks){
        labWorksDAO.delete(labWorks);
    }
    public void update(LabWorks labWorks){
        labWorksDAO.update(labWorks);
    }
    public List<LabWorks> findAll(){
        return labWorksDAO.findAll();
    }
    public LabWorks findById(int id){
        return labWorksDAO.findById(id);
    }
}