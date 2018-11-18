package br.edu.utfpr.rogerio.inspalarm.model;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "schedules",
        foreignKeys = @ForeignKey(entity = Tag.class,
                parentColumns = "id",
                childColumns  = "tagId"))
public class Schedule {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int scheduleHour;
    private int scheduleMinute;

    @ColumnInfo(index = true)
    private int tagId;

    public Schedule (int scheduleHour, int scheduleMinute, int tagId){
        setScheduleHour(scheduleHour);
        setScheduleMinute(scheduleMinute);
        setTagId(tagId);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScheduleHour() {
        return scheduleHour;
    }

    public void setScheduleHour(int scheduleHour) {
        this.scheduleHour = scheduleHour;
    }

    public int getScheduleMinute() {
        return scheduleMinute;
    }

    public void setScheduleMinute(int scheduleMinute) {
        this.scheduleMinute = scheduleMinute;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    @Override
    public String toString(){
        //Isso é nescessario sernão apareceria 19:2 ao invés de 19:02
        if(getScheduleMinute() > 10)
            return Integer.toString(getScheduleHour()) + ":" + Integer.toString(getScheduleMinute()) + " -- " + tagId;
        else
            return Integer.toString(getScheduleHour()) + ":0" + Integer.toString(getScheduleMinute()) + " -- " + tagId;
    }
}
