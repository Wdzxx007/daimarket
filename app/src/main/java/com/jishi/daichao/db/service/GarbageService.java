package com.jishi.daichao.db.service;


import com.jishi.daichao.app.App;
import com.jishi.daichao.db.AppDatabase;
import com.jishi.daichao.db.dao.GarbageDao;
import com.jishi.daichao.db.entity.GarbageEntity;

import java.util.List;

public class GarbageService {

    private static GarbageService mService;
    private GarbageDao mDao;

    public static GarbageService getInstance() {
        if (mService == null) {
            synchronized (GarbageService.class) {
                mService = new GarbageService();
            }
        }
        return mService;
    }

    private GarbageService() {
        mDao = AppDatabase.getDatabase(App.getContext()).getGarbageDao();
    }


    public void insertData(GarbageEntity entity) {
        mDao.insertData(entity);
    }

    public void deleteData(GarbageEntity entity) {
        mDao.deleteData(entity);
    }

    public GarbageEntity findBySubmitTime(String submitTime) {
        return mDao.findBySubmitTime(submitTime);
    }

    public GarbageEntity findData() {
        List<GarbageEntity> list = mDao.getAllData();
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public List findAllData() {
        return mDao.getAllData();
    }

    public List getAfterIdData(long id) {
        return mDao.getAfterIdData(id);
    }

    public void deleteAll() {
        mDao.deleteAll();
    }

}
