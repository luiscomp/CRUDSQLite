package com.example.luiseduardo.crudsqlite.dao;

import android.content.Context;

/**
 * Created by LuisEduardo on 10/02/2016.
 */
public class BaseDAO {
    protected static final String DATABASE_NAME = "DBLivro";
    protected static final int DATABASE_VERSION = 1;

    protected static DataBaseHelper DBHelper;

    protected static void newInstance(Context context) {
        DBHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}
