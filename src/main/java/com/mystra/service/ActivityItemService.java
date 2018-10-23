package com.mystra.service;

import com.mystra.dao.ActivityItemDao;
import com.mystra.model.ActivityItem;

public class ActivityItemService {
    private static ActivityItemDao ActivityItemDao;

    public ActivityItemService() {
        ActivityItemDao = new ActivityItemDao();
    }

    public void persist(ActivityItem entity) {
        ActivityItemDao.openCurrentSessionwithTransaction();
        ActivityItemDao.persist(entity);
        ActivityItemDao.closeCurrentSessionwithTransaction();
    }

    public void update(ActivityItem entity) {
        ActivityItemDao.openCurrentSessionwithTransaction();
        ActivityItemDao.update(entity);
        ActivityItemDao.closeCurrentSessionwithTransaction();
    }

    public ActivityItem findById(String id) {
        ActivityItemDao.openCurrentSession();
        ActivityItem ActivityItem = ActivityItemDao.findById(id);
        ActivityItemDao.closeCurrentSession();
        return ActivityItem;
    }
    public void delete(ActivityItem entity) {
        ActivityItemDao.openCurrentSessionwithTransaction();
        ActivityItemDao.delete(entity);
        ActivityItemDao.closeCurrentSessionwithTransaction();
    }
}
