package com.example.cw.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.cw.Models.Hike;

import java.util.List;

@Dao
public interface HikeDao {
    @Insert
    long insertHike(Hike hike);

    @Query("SELECT * FROM Hike ORDER BY name")
    List<Hike> getAllHikes();
    @Query("SELECT * FROM Hike WHERE hike_id = :hikeId")
    Hike getHikeById(long hikeId);

    @Query("SELECT * FROM Hike WHERE name LIKE '%' || :name || '%'")
    List<Hike> getHikesByName(String name);
    @Query("SELECT * FROM Hike WHERE name = :name")
    Hike getHikeByName(String name);
    @Delete
    void deleteHike(Hike hike);
    @Update
    int updateHike(Hike hike);
}