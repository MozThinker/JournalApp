package com.mutombene.edson.journalapp;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.mutombene.edson.journalapp.database.AppDatabase;
import com.mutombene.edson.journalapp.database.JournalEntry;

/**
 * Created by EMutombene on 6/25/2018.
 */

public class AddJournalViewModel extends ViewModel {
    private LiveData<JournalEntry> journal;


    public AddJournalViewModel(AppDatabase database, int journalId){
        journal = database.journalDao().loadJournalById(journalId);
    }


    public LiveData<JournalEntry> getJournal(){
        return journal;
    }
}
