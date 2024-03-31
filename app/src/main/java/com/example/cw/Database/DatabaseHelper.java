package com.example.cw.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.cw.Dao.HikeDao;
import com.example.cw.Dao.ObservationDao;
import com.example.cw.Models.Hike;
import com.example.cw.Models.Observation;

@Database(entities = {Hike.class,Observation.class}, version = 1)
public abstract class DatabaseHelper extends RoomDatabase {
    public abstract HikeDao hikeDao();
    public abstract ObservationDao observationDao();
}





