package br.edu.utfpr.rogerio.inspalarm;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "tags",
        indices = @Index(value = {"tag"}, unique = true))
public class Tag {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String tag;

    public Tag(String tag){
        setTag(tag);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getTag() {
        return tag;
    }

    public void setTag(@NonNull String tag) {
        this.tag = tag;
    }
}
