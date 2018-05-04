package xyz.wendyltanpcy.formulaapplication;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.speedystone.greendaodemo.db.DaoMaster;
import com.speedystone.greendaodemo.db.DaoSession;

/**
 * Created by Wendy on 2018/5/3.
 */

public class MyApp extends Application {

    private DaoSession daoSession;
    @Override
    public void onCreate() {
        super.onCreate();
        initGreenDao();
    }

    private void initGreenDao() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this,"formulaApp.db");
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
