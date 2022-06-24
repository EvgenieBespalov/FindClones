package DataBase.DAO;

import DataBase.HibernateSessionFactoryUtil;
import DataBase.Interfaces.DAOIntr;
import DataBase.Models.Variants;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class VariantsDAO  implements DAOIntr<Variants> {
    @Override
    public Variants findByObject(Variants variants){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();

        Query query = session.createQuery(
                "select var from Variants as var where var.variant = '" + variants.getVariant() + "'");

        Variants variant = (Variants) query.list().get(0);
        tx1.commit();
        session.close();

        return variant;
    }

    @Override
    public Variants findById(long id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();

        Query query = session.createQuery(
                "select var from Variants as var where var.id = '" + id + "'");

        Variants variant = (Variants) query.list().get(0);
        tx1.commit();
        session.close();

        return variant;
    }

    @Override
    public List<Variants> findAll(){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();

        Query query = session.createQuery(
                "From Variants order by variant");

        List<Variants> variant = query.list();
        tx1.commit();
        session.close();

        return variant;
    }
    @Override
    public void delete(Variants variants){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();

        session.delete(variants);

        tx1.commit();
        session.close();
    }
    @Override
    public void update(Variants object){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();

        session.update(object);

        tx1.commit();
        session.close();
    }

    @Override
    public void save(Variants pro){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();

        session.saveOrUpdate(pro);

        tx1.commit();
        session.close();
    }
}
