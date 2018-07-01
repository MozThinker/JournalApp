package com.mutombene.edson.journalapp.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by EMutombene on 6/25/2018.
 */

@Dao
public interface JournalDao {


    @Insert
    void insertJournal(JournalEntry journalEntry);

    @Query("SELECT * FROM journal")
   LiveData<List<JournalEntry>> loadAllJournals();

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateJournal(JournalEntry journalEntry);

    @Delete
    void deleteJournal(JournalEntry journalEntry);

    @Query("SELECT * FROM journal WHERE id = :id")
    LiveData<JournalEntry> loadJournalById(int id);
}
