package com.mutombene.edson.journalapp;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;

import com.mutombene.edson.journalapp.database.AppDatabase;

/**
 * Created by EMutombene on 6/25/2018.
 */

public class AddJournalViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final AppDatabase mDb;
    private final int mJournalId;

    public AddJournalViewModelFactory(AppDatabase mDb, int mJournalId) {
        this.mDb = mDb;
        this.mJournalId = mJournalId;
    }

    @Override
    public <J extends ViewModel>J create(Class<J> modelClass){
        return (J) new AddJournalViewModel(mDb,mJournalId);
    }
}
