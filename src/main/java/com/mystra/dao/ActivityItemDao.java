package com.mystra.dao;

import com.mystra.model.ActivityItem;
import com.mystra.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ActivityItemDao {
    private Session currentSession;

    private Transaction currentTransaction;

    public ActivityItemDao(){
    }

    public Session openCurrentSession() {
        currentSession = HibernateUtil.getSessionFactory().openSession();
        return currentSession;
    }

    public Session openCurrentSessionwithTransaction() {
        currentSession = HibernateUtil.getSessionFactory().openSession();
        currentTransaction = currentSession.beginTransaction();
        return currentSession;
    }

    public void closeCurrentSession() {
        currentSession.close();
    }

    public void closeCurrentSessionwithTransaction() {
        currentTransaction.commit();
        currentSession.close();
    }

    public Session getCurrentSession() {
        return currentSession;
    }

    public void setCurrentSession(Session currentSession) {
        this.currentSession = currentSession;
    }

    public Transaction getCurrentTransaction() {
        return currentTransaction;
    }

    public void setCurrentTransaction(Transaction currentTransaction) {
        this.currentTransaction = currentTransaction;
    }

    public void persist(ActivityItem entity) {
        getCurrentSession().save(entity);
    }

    public void update(ActivityItem entity) {
        getCurrentSession().update(entity);
    }

    public ActivityItem findById(String id) {
        return getCurrentSession().get(ActivityItem.class, id);

    }

    public void delete(ActivityItem entity) {
        getCurrentSession().delete(entity);
    }
}
