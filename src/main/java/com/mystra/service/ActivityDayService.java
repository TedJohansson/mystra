package com.mystra.service;

import com.mystra.dao.ActivityDayDao;
import com.mystra.model.ActivityDay;

import javax.persistence.NoResultException;
import java.time.LocalDate;

public class ActivityDayService {
    private static ActivityDayDao activityDayDao;

    public ActivityDayService() {
        activityDayDao = new ActivityDayDao();
    }

    public void persist(ActivityDay entity) {
        activityDayDao.openCurrentSessionwithTransaction();
        activityDayDao.persist(entity);
        activityDayDao.closeCurrentSessionwithTransaction();
    }

    public void update(ActivityDay entity) {
        activityDayDao.openCurrentSessionwithTransaction();
        activityDayDao.update(entity);
        activityDayDao.closeCurrentSessionwithTransaction();
    }

    public ActivityDay findById(String id) {
        activityDayDao.openCurrentSession();
        ActivityDay activityDay = activityDayDao.findById(id);
        activityDayDao.closeCurrentSession();
        return activityDay;
    }
    public void delete(ActivityDay entity) {
        activityDayDao.openCurrentSessionwithTransaction();
        activityDayDao.delete(entity);
        activityDayDao.closeCurrentSessionwithTransaction();
    }
    public void createTodaysActivityDay(){
        ActivityItemService activityItemService = new ActivityItemService();
        ActivityDay activityDay = new ActivityDay(LocalDate.now());
        persist(activityDay);
        for (int i=0; i<9; i++) {
            activityItemService.createEmptyActivities(activityDay, i+8);
        }
    }
    public ActivityDay findByDate(LocalDate date){
        activityDayDao.openCurrentSession();
        ActivityDay activityDay = activityDayDao.findByDate(date);
        activityDayDao.closeCurrentSession();
        return activityDay;
    }
    public ActivityDay getTodaysActivityDay() {
        ActivityDay activityDay;
        try {
            activityDay = findByDate(LocalDate.now());
        } catch (NoResultException e) {
            activityDayDao.closeCurrentSession();
            createTodaysActivityDay();
            activityDay = findByDate(LocalDate.now());
        }
        return activityDay;
    }
}
