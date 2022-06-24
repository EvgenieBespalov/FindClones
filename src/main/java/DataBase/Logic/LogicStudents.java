package DataBase.Logic;

import DataBase.DAO.StudentsDAO;
import DataBase.Interfaces.LogicAbstr;
import DataBase.Models.Students;
import java.util.List;

public class LogicStudents extends LogicAbstr<Students> {
    StudentsDAO studentsDAO = new StudentsDAO();

    public void save(Students student){
        try{
            studentsDAO.save(student);
        }
        catch(Exception e){
            System.out.println("Ошибка: " + e);
        }
    }
    public void delete(Students student){
        studentsDAO.delete(student);
    }
    public void update(Students student){
        studentsDAO.update(student);
    }
    public List<Students> findAll(){
        return studentsDAO.findAll();
    }
    public Students findById(int id){
        return studentsDAO.findById(id);
    }
}
