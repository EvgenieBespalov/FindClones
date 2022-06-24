package DataBase.DAO;

import DataBase.HibernateSessionFactoryUtil;
import DataBase.Interfaces.DAOIntr;
import DataBase.Models.Students;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class StudentsDAO implements DAOIntr<Students> {
    private SessionFactory sessionFactory;

    @Override
    public Students findByObject(Students students){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();

        Query query = session.createQuery(
                "select st from Students as st where st.student = '" + students.getStudent() + "'");

        Students student = (Students) query.list().get(0);
        tx1.commit();
        session.close();

        return student;
    }
    @Override
    public Students findById(long id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();

        Query query = session.createQuery(
                "select st from Students as st where st.id = '" + id + "'");

        Students student = (Students) query.list().get(0);
        tx1.commit();
        session.close();

        return student;
    }
    @Override
    public List<Students> findAll(){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();

        Query query = session.createQuery(
                "From Students order by student");

        List<Students> students = query.list();
        tx1.commit();
        session.close();

        return students;
    }
    @Override
    public void delete(Students student){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();

        session.delete(student);

        tx1.commit();
        session.close();
    }
    @Override
    public void update(Students student){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();

        session.update(student);

        tx1.commit();
        session.close();
    }
    @Override
    public void save(Students student){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();

        session.saveOrUpdate(student);

        tx1.commit();
        session.close();
    }
}