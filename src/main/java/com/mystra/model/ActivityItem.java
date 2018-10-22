package com.mystra.model;

import javax.persistence.*;

@Entity
@Table(name = "activity_item")
public class ActivityItem {
    private int id;
    private String shortDescription;
    private String details;
    private int hourOfDay;

    private ActivityDay activityDay;

    public ActivityItem() {
        // Used by Hibernate
    }

    public ActivityItem(String shortDescription, String details, int hourOfDay, ActivityDay activityDay) {
        this.shortDescription = shortDescription;
        this.details = details;
        this.hourOfDay = hourOfDay;
        this.activityDay = activityDay;
    }

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "short_description")
    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    @Column(name = "details")
    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Column(name = "hour_of_day")
    public int getHourOfDay() {
        return hourOfDay;
    }

    public void setHourOfDay(int hourOfDay) {
        this.hourOfDay = hourOfDay;
    }

    @ManyToOne
    @JoinColumn(name="activityDay", nullable=false)
    public ActivityDay getActivityDay() {
        return activityDay;
    }

    public void setActivityDay(ActivityDay activityDay) {
        this.activityDay = activityDay;
    }
}
