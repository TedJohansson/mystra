package com.mystra.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.mystra.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "activity_day")
public class ActivityDay {
    private int id;
    private LocalDate date;
    private List<ActivityItem> activities;

    public ActivityDay() {
        // Used by Hibernate
    }

    public ActivityDay(LocalDate date) {
        this.date = date;
    }

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "date")
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @OneToMany(targetEntity = ActivityItem.class,
               mappedBy = "activityDay",
               cascade = CascadeType.ALL,
               fetch = FetchType.EAGER)
    public List<ActivityItem> getActivities() {
        return activities;
    }

    public void setActivities(List<ActivityItem> activities) {
        this.activities = activities;
    }
}
