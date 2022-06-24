package DataBase.DAO;

import DataBase.HibernateSessionFactoryUtil;
import DataBase.Interfaces.DAOIntr;
import DataBase.Models.Subjects;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class SubjectsDAO implements DAOIntr<Subjects> {
    @Override
    public Subjects findByObject(Subjects subjects){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();

        Query query = session.createQuery(
                "select sub from Subjects as sub where sub.subject = '" + subjects.getSubject() + "'");

        Subjects subject = (Subjects) query.list().get(0);
        tx1.commit();
        session.close();

        return subject;
    }

    @Override
    public Subjects findById(long id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();

        Query query = session.createQuery(
                "select sub from Subjects as sub where sub.id = '" + id + "'");

        Subjects subject = (Subjects) query.list().get(0);
        tx1.commit();
        session.close();

        return subject;
    }

    @Override
    public List<Subjects> findAll(){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();

        Query query = session.createQuery(
                "From Subjects order by id");

        List<Subjects> subject = query.list();
        tx1.commit();
        session.close();

        return subject;
    }
    @Override
    public void delete(Subjects subject){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();

        session.delete(subject);

        tx1.commit();
        session.close();
    }
    @Override
    public void update(Subjects object){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();

        session.update(object);

        tx1.commit();
        session.close();
    }

    @Override
    public void save(Subjects subject){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();

        session.saveOrUpdate(subject);

        tx1.commit();
        session.close();
    }
}