package com.example.imenikapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.imenikapp.db.model.BrojTelefona;
import com.example.imenikapp.db.model.Kontakt;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME    = "imenik.db";

    private static final int DATABASE_VERSION = 1;


    private Dao<BrojTelefona, Integer> mBrojDao = null;
    private Dao<Kontakt, Integer> mKontaktDao = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, BrojTelefona.class);
            TableUtils.createTable(connectionSource, Kontakt.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, BrojTelefona.class, true);
            TableUtils.dropTable(connectionSource, Kontakt.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Dao<BrojTelefona, Integer> getBrojDao() throws SQLException {
        if (mBrojDao == null) {
            mBrojDao = getDao(BrojTelefona.class);
        }

        return mBrojDao;
    }

    public Dao<Kontakt, Integer> getKontaktDao() throws SQLException {
        if (mKontaktDao == null) {
            mKontaktDao = getDao(Kontakt.class);
        }

        return mKontaktDao;
    }

    @Override
    public void close() {
        mBrojDao = null;
        mKontaktDao = null;

        super.close();
    }

}
