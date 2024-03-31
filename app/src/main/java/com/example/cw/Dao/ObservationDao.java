package com.example.cw.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.cw.Models.Observation;

import java.util.List;

@Dao
public interface ObservationDao {
    @Insert
    long insertObservation(Observation observation);

    @Query("SELECT * FROM Observation ORDER BY observation")
    List<Observation> getAllObservations();
    @Delete
    void deleteObservation(Observation observation);
}