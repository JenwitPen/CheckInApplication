package com.checkin.maceducation.checkinapplication.QueryDatabase;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import checkin.maceducation.daogenerator.DaoMaster;
import checkin.maceducation.daogenerator.DaoSession;

/**
 * Created by jenwit on 26/9/2558.
 */
public class GreenDaoApplication extends Application {

    DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        setupDatabase();
    }

    private void setupDatabase() {
        DaoMaster.DevOpenHelper helper =
                new DaoMaster.DevOpenHelper(this, "MyGreenDao.db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        mDaoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }
}