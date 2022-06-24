package DataBase.Logic;

import DataBase.DAO.VariantsDAO;
import DataBase.Interfaces.LogicAbstr;
import DataBase.Models.Variants;
import java.util.List;

public class LogicVariants extends LogicAbstr<Variants> {
    VariantsDAO variantsDAO = new VariantsDAO();

    public void save(Variants variant){
        try{
            variantsDAO.save(variant);
        }
        catch(Exception e){
            System.out.println("Ошибка: " + e);
        }
    }
    public void delete(Variants variants){
        variantsDAO.delete(variants);
    }
    public void update(Variants variants){
        variantsDAO.update(variants);
    }
    public List<Variants> findAll(){
        return variantsDAO.findAll();
    }
    public Variants findById(int id){
        return variantsDAO.findById(id);
    }
}