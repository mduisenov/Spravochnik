package kz.test.spravochnik.data.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import kz.test.spravochnik.data.model.cinema.Cinema;
import kz.test.spravochnik.data.model.hospital.Hospital;
import timber.log.Timber;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String DATABASE_NAME = "spravochnik.db";
    private static final int DATABASE_VERSION = 1;
    private Dao<Cinema, Integer> cinemaDao;
    private Dao<Hospital, Integer> hospitalDao;

    public interface Tables {
        String CINEMAS = "cinemas";
        String HOSPITALS = "hospitals";
    }

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Timber.d("DatabaseHelper(context)");
    }

    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        Timber.d("onCreate");
        try {
            Timber.d("createTable Cinema");
            TableUtils.createTable(connectionSource, Cinema.class);
            Timber.d("createTable Hospital");
            TableUtils.createTable(connectionSource, Hospital.class);
            hospitalDao = getHospitalDao();
            cinemaDao = getCinemaDao();
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
        }
    }

    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        Timber.d("onUpgrade");
        try {
            Log.i(DatabaseHelper.class.getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource, Cinema.class, true);
            TableUtils.dropTable(connectionSource, Hospital.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }

    public void close() {
        super.close();
        hospitalDao = null;
        cinemaDao = null;
    }


    public Dao<Hospital, Integer> getHospitalDao() throws SQLException {
        Timber.d("getHospitalDao");
        if (hospitalDao == null) {
            hospitalDao = getDao(Hospital.class);
        }
        return hospitalDao;
    }

    public Dao<Cinema, Integer> getCinemaDao() throws SQLException {
        Timber.d("getCinemaDao");
        if (cinemaDao == null) {
            cinemaDao = getDao(Cinema.class);
        }
        return cinemaDao;
    }
}
