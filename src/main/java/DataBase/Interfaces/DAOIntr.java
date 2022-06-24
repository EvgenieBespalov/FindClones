package DataBase.Interfaces;

import java.util.List;

public interface DAOIntr<Type> {
    Type findByObject(Type object);
    Type findById(long id);
    List<Type> findAll();
    void delete(Type object);
    void update(Type object);
    void save(Type object);
}
