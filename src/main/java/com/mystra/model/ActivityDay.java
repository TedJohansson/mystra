package com.mystra.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.mystra.util.HibernateUtil;
import org.hibernate.Session;
import java.util.List;

public class ActivityDay {
    private static ActivityDay instance = new ActivityDay();

    private ObservableList<com.mystra.model.ActivityItem> activityItems;

    public static ActivityDay getInstance() {
        return instance;
    }

    private ActivityDay() {
    }

    public ObservableList<ActivityItem> getActivityItems() {
        return activityItems;
    }

    public void addTodoItem(ActivityItem item) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();
        session.save(item);
        session.getTransaction().commit();
        session.close();
        activityItems.add(item);
    }

    public void setActivityItems(ObservableList<ActivityItem> activityItems) {
        this.activityItems = activityItems;
    }

    public void loadTodoItems() {
        activityItems = FXCollections.observableArrayList();
        Session session = HibernateUtil.getSessionFactory().openSession();
        List results = session.createQuery("from ActivityItem").list();
        session.close();
        activityItems.addAll(results);
    }
    public void deleteTodoItem(ActivityItem item) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();
        session.delete(item);
        session.getTransaction().commit();
        session.close();
        activityItems.remove(item);
    }
}
