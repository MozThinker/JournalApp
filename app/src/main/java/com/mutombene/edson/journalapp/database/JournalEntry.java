package com.mutombene.edson.journalapp.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

/**
 * Created by EMutombene on 6/25/2018.
 */

@Entity(tableName = "journal" )
public class JournalEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title, content;

    private String userId;
    private String userName;
    private String mood;



    @ColumnInfo(name = "updated_at")
    private Date updatedAt;

    @ColumnInfo(name = "created_at")
    private Date createdAt;

    @Ignore
    public JournalEntry(String userId, String userName, String title, String content, Date createdAt, Date updatedAt, String mood){
        this.userId = userId;
        this.userName = userName;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.mood = mood;
    }

    public JournalEntry(int id, String userId, String userName, String title, String content, Date createdAt, Date updatedAt, String mood){
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.mood = mood;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String mUserId) {
        this.userId = mUserId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

}
