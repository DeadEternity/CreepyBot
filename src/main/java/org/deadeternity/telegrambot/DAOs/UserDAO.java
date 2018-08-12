package org.deadeternity.telegrambot.DAOs;

import org.deadeternity.telegrambot.Entities.User;
import org.deadeternity.telegrambot.Utils.HibernateSessionFactory;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UserDAO {

    public static void saveUser(User user) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            //session.merge(user);
            session.saveOrUpdate(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        transaction.commit();
        session.close();
    }

    public static User getUserByID(String id) {
        User user = null;
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        try {
            user = session.get(User.class, id);
        } catch (ObjectNotFoundException e) {
            session.close();
            return null;
        } catch (Exception e) {
            session.close();
            e.printStackTrace();
        }
        session.close();
        return user;
    }

}
