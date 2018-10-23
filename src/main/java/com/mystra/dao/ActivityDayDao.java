package com.mystra.dao;

import com.mystra.model.ActivityDay;
import com.mystra.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import java.time.LocalDate;

public class ActivityDayDao {
    private Session currentSession;

    private Transaction currentTransaction;

    public ActivityDayDao(){
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

    public void persist(ActivityDay entity) {
        getCurrentSession().save(entity);
    }

    public void update(ActivityDay entity) {
        getCurrentSession().update(entity);
    }

    public ActivityDay findById(String id) {
        return getCurrentSession().get(ActivityDay.class, id);

    }

    public void delete(ActivityDay entity) {
        getCurrentSession().delete(entity);
    }

    public ActivityDay findByDate(LocalDate date) {
        String hql = "SELECT ad FROM ActivityDay ad WHERE ad.date = :date";
        Query query = getCurrentSession().createQuery(hql).setParameter("date", date);
        return (ActivityDay) query.getSingleResult();
    }
}
