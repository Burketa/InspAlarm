package br.edu.utfpr.rogerio.inspalarm.DB;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import br.edu.utfpr.rogerio.inspalarm.model.Schedule;

@Dao
public interface ScheduleDAO {

    @Insert
    long insert(Schedule schedule);

    @Delete
    void delete(Schedule schedule);

    @Update
    void update(Schedule schedule);

    @Query("SELECT * FROM schedules WHERE id = :id")
    Schedule queryForId(long id);

    @Query("SELECT * FROM schedules ORDER BY id ASC")
    List<Schedule> queryAll();

    @Query("SELECT * FROM schedules WHERE tagId = :id ORDER BY id ASC")
    List<Schedule> queryForTipoId(long id);
}