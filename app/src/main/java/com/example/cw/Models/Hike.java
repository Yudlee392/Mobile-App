package com.example.cw.Models;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "Hike")
public class Hike {
    @PrimaryKey(autoGenerate = true)
    public long hike_id;
    public String name;
    public String location;
    public String doh;
    public String parking;
    public int length;
    public String difficult;
    public String description;
}
