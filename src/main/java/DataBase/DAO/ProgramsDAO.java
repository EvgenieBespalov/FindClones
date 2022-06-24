package DataBase.DAO;

import DataBase.HibernateSessionFactoryUtil;
import DataBase.Interfaces.DAOIntr;
import DataBase.Models.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class ProgramsDAO implements DAOIntr<Programs> {
    @Override
    public Programs findByObject(Programs programs){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();

        Query query = session.createQuery(
                "select pro from Programs as pro where pro.program = '" + programs.getProgram() + "'");

        Programs program = (Programs) query.list().get(0);
        tx1.commit();
        session.close();

        return program;
    }

    @Override
    public Programs findById(long id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();

        Query query = session.createQuery(
                "select pro from Programs as pro where pro.id = '" + id + "'");

        Programs program = (Programs) query.list().get(0);
        tx1.commit();
        session.close();

        return program;
    }

    @Override
    public List<Programs> findAll(){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();

        Query query = session.createQuery(
                "From Programs order by id");

        List<Programs> program = query.list();
        tx1.commit();
        session.close();

        return program;
    }
    @Override
    public void delete(Programs pro){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();


        session.delete(pro);

        tx1.commit();
        session.close();
    }
    @Override
    public void update(Programs pro){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();

        session.update(pro);

        tx1.commit();
        session.close();
    }
    @Override
    public void save(Programs pro){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();

        session.merge(pro);

        tx1.commit();
        session.close();
    }

    public List<Programs> findPrograms(Programs program){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();

        Query query = session.createQuery(
                "select pr from Programs as pr " +
                        "join pr.variant var " +
                        "join pr.subject sub " +
                        "join pr.labWork lab " +
                        "where var.id='" + program.getVariant().getId() + "' and " +
                        "lab.id='" + program.getLabwork().getId() + "' and " +
                        "sub.id='" + program.getSubject().getId() + "'");

        List<Programs> programs = query.list();
        tx1.commit();
        session.close();

        return programs;
    }
}
