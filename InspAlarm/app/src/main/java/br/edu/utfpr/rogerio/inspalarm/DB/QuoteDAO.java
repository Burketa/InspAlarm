package br.edu.utfpr.rogerio.inspalarm.DB;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import br.edu.utfpr.rogerio.inspalarm.model.Quote;
import br.edu.utfpr.rogerio.inspalarm.model.Schedule;

@Dao
public interface QuoteDAO {

    @Insert
    long insert(Quote quote);

    @Delete
    void delete(Quote quote);

    @Update
    void update(Quote quote);

    @Query("SELECT * FROM quotes WHERE id = :id")
    Schedule queryForId(long id);

    @Query("SELECT * FROM quotes ORDER BY id ASC")
    List<Schedule> queryAll();

    @Query("SELECT * FROM quotes WHERE tagId = :id ORDER BY id ASC")
    List<Schedule> queryForTipoId(long id);
}