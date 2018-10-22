package com.mystra.controller;

import com.mystra.model.ActivityDay;
import com.mystra.model.ActivityItem;
import com.mystra.util.HibernateUtil;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.time.LocalDate;

public class DialogController {
    @FXML
    private TextField shortDescriptionField;
    @FXML
    private TextArea detailsArea;
    @FXML
    private Slider hourOfDaySlider;

    public ActivityItem processResults() {
        String shordDescription = shortDescriptionField.getText().trim();
        String details = detailsArea.getText().trim();
        int hourOfDayValue = (int) hourOfDaySlider.getValue();

        // TODO: Learn where this logic should sit
        Session session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();
        String hql = "SELECT ad FROM ActivityDay ad WHERE ad.date = :date";
        Query query = session.createQuery(hql);
        query.setParameter("date", LocalDate.now());
        ActivityDay activityDay = (ActivityDay) query.getSingleResult();
        ActivityItem item = new ActivityItem(shordDescription, details, hourOfDayValue, activityDay);
        session.save(item);
        session.getTransaction().commit();
        session.close();
        return item;
    }
}
