package br.edu.utfpr.rogerio.inspalarm.DB;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import br.edu.utfpr.rogerio.inspalarm.model.Tag;

@Dao
public interface TagDAO {

    @Insert
    long insert(Tag tag);

    @Delete
    void delete(Tag tag);

    @Update
    void update(Tag tag);

    @Query("SELECT * FROM tags WHERE id = :id")
    Tag queryForId(long id);

    @Query("SELECT * FROM tags ORDER BY id ASC")
    List<Tag> queryAll();

    @Query("SELECT * FROM tags WHERE tag = :tag ORDER BY tag ASC")
    List<Tag> queryForDescricao(String tag);

    @Query("SELECT count(*) FROM tags")
    int total();
}
