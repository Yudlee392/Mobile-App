package com.example.cw.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Observation")
public class Observation {
    @PrimaryKey(autoGenerate = true)
    public long observation_id;
    public String observation;
    public String too;
    public String comments;

}