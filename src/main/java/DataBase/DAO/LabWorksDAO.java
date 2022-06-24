package DataBase.DAO;

import DataBase.HibernateSessionFactoryUtil;
import DataBase.Interfaces.DAOIntr;
import DataBase.Models.LabWorks;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class LabWorksDAO implements DAOIntr<LabWorks> {
    @Override
    public LabWorks findByObject(LabWorks labWork){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();

        Query query = session.createQuery(
                "select lw from LabWorks as lw where lw.labWork = '" + labWork.getLabwork() + "'");

        LabWorks labWorks = (LabWorks) query.list().get(0);
        tx1.commit();
        session.close();

        return labWorks;
    }
    @Override
    public LabWorks findById(long id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();

        Query query = session.createQuery(
                "select lw from LabWorks as lw where lw.id = '" + id + "'");

        LabWorks labWorks = (LabWorks) query.list().get(0);
        tx1.commit();
        session.close();

        return labWorks;
    }
    @Override
    public List<LabWorks> findAll(){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();

        Query query = session.createQuery(
                "From LabWorks order by labWork");

        List<LabWorks> labWorks = query.list();
        tx1.commit();
        session.close();

        return labWorks;
    }
    @Override
    public void delete(LabWorks labWorks){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();

        session.delete(labWorks);

        tx1.commit();
        session.close();
    }
    @Override
    public void update(LabWorks object){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();

        session.update(object);

        tx1.commit();
        session.close();
    }
    @Override
    public void save(LabWorks labWorks){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();

        session.saveOrUpdate(labWorks);

        tx1.commit();
        session.close();
    }
}