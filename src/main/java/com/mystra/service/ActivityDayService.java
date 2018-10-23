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
    public ActivityDay createTodaysActivityDay(){
        ActivityDay activityDay = new ActivityDay(LocalDate.now());
        persist(activityDay);
        return activityDay;
    }
    public ActivityDay getTodaysActivityDay() {
        activityDayDao.openCurrentSession();
        try {
            ActivityDay activityDay = activityDayDao.findByDate(LocalDate.now());
            activityDayDao.closeCurrentSession();
            return activityDay;
        } catch (NoResultException e) {
            activityDayDao.closeCurrentSession();
            return createTodaysActivityDay();
        }
    }
}
