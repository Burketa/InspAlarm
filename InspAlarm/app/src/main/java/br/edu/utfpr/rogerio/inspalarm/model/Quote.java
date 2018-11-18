package br.edu.utfpr.rogerio.inspalarm.model;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "quotes",
        foreignKeys = @ForeignKey(entity = Tag.class,
                parentColumns = "id",
                childColumns  = "tagId"))
public class Quote {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String quote;

    @ColumnInfo(index = true)
    private int tagId;

    public Quote (String quote, int tagId){
        setQuote(quote);
        setTagId(tagId);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    @Override
    public String toString(){
        return getQuote();
    }
}
