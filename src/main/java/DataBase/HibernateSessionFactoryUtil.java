package DataBase;

import DataBase.Models.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.logging.Level;


public class HibernateSessionFactoryUtil {
    private static SessionFactory sessionFactory;

    private HibernateSessionFactoryUtil() {}

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration().configure();
                configuration.addAnnotatedClass(Variants.class);
                configuration.addAnnotatedClass(Programs.class);
                configuration.addAnnotatedClass(Subjects.class);
                configuration.addAnnotatedClass(LabWorks.class);
                configuration.addAnnotatedClass(Students.class);

                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
                sessionFactory = configuration.buildSessionFactory(builder.build());

            } catch (Exception e) {
                System.out.println("Что-то не так! " + e);
            }
        }
        return sessionFactory;
    }
}