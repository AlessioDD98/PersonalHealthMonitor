package com.example.myapplication;

import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@androidx.room.Database(version = 1, entities = {Report.class})
@TypeConverters(DateConverter.class)
public abstract class Database extends RoomDatabase {
    public abstract DaoReport daoReport();
}
