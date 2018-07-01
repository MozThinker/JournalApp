package com.mutombene.edson.journalapp;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.mutombene.edson.journalapp.database.AppDatabase;
import com.mutombene.edson.journalapp.database.JournalEntry;

import java.util.List;

/**
 * Created by EMutombene on 6/25/2018.
 */

public class MainViewModel extends AndroidViewModel {

    private  static final String TAG = MainViewModel.class.getSimpleName();
    private LiveData<List<JournalEntry>> journals;

    public MainViewModel(@NonNull Application application) {
        super(application);
        AppDatabase database = AppDatabase.getsInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving the journals from the DataBase");
        journals = database.journalDao().loadAllJournals();

    }

    public LiveData<List<JournalEntry>> getJournals(){
        return journals;
    }
}
