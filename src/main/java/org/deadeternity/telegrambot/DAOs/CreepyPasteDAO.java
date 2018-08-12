package org.deadeternity.telegrambot.DAOs;

import org.deadeternity.telegrambot.Entities.CreepyPaste;
import org.deadeternity.telegrambot.Utils.HibernateSessionFactory;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class CreepyPasteDAO {

    public static void savePaste(CreepyPaste paste) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.saveOrUpdate(paste);
        } catch (Exception e) {
            e.printStackTrace();
        }
        transaction.commit();
        session.close();
    }

    public static CreepyPaste getCreepyPasteByID(String id) {
        CreepyPaste paste = null;
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        try {
            paste = session.get(CreepyPaste.class, id);
            //session.evict(paste);
        } catch (ObjectNotFoundException e) {
            session.close();
            return null;
        } catch (Exception e) {
            session.close();
            e.printStackTrace();
        }
        session.close();
        return paste;
    }
}
