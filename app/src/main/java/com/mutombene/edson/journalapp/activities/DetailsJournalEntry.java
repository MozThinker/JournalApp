package com.mutombene.edson.journalapp.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mutombene.edson.journalapp.AddJournalViewModel;
import com.mutombene.edson.journalapp.AddJournalViewModelFactory;
import com.mutombene.edson.journalapp.MainActivity;
import com.mutombene.edson.journalapp.MainViewModel;
import com.mutombene.edson.journalapp.R;
import com.mutombene.edson.journalapp.database.AppDatabase;
import com.mutombene.edson.journalapp.database.JournalEntry;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by EMutombene on 6/27/2018.
 */

public class DetailsJournalEntry extends AppCompatActivity {
    public static final String EXTRA_JOURNAL_ID = "extraJournalId";
    public static final String INSTANCE_JOURNAL_ID = "instanceJournalId";
    private static final int DEFAULT_JOURNAL_ID = -1;
    private int mJOURNALId = DEFAULT_JOURNAL_ID;

    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());

    private static final String DATE_FORMAT = "MMMM dd, hh:mm:ss a";

    private static final String TAG = AddJournalActivity.class.getSimpleName();

    FloatingActionButton fab;

    TextView tvShowTitle;
    TextView tvShowContent;


    private AppDatabase mDb;



    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_journal_entry);


        Intent intent = getIntent();

        initViews();
        mDb = AppDatabase.getsInstance(getApplicationContext());

        if (savedInstanceState != null && savedInstanceState.containsKey(INSTANCE_JOURNAL_ID)) {
            mJOURNALId = savedInstanceState.getInt(INSTANCE_JOURNAL_ID, DEFAULT_JOURNAL_ID);
        }

        if(intent != null && intent.hasExtra(EXTRA_JOURNAL_ID)){
            if (mJOURNALId == DEFAULT_JOURNAL_ID){
                mJOURNALId = intent.getIntExtra(EXTRA_JOURNAL_ID, DEFAULT_JOURNAL_ID);

                AddJournalViewModelFactory factory = new AddJournalViewModelFactory(mDb, mJOURNALId);
                final AddJournalViewModel viewModel = ViewModelProviders.of(this,factory).get(AddJournalViewModel.class);
                viewModel.getJournal().observe(this, new Observer<JournalEntry>() {
                    @Override
                    public void onChanged(@Nullable JournalEntry journalEntry) {
                        viewModel.getJournal().removeObserver(this);
                        Log.d(TAG, "Receiving database update from LiveData");
                        populateUI(journalEntry);
                    }
                });


            }
        }


    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        outState.putInt(INSTANCE_JOURNAL_ID, mJOURNALId);
        super.onSaveInstanceState(outState);
    }



    private void initViews(){


        tvShowTitle = findViewById(R.id.tv_journal_details_title);
        tvShowContent = findViewById(R.id.tv_journal_details_content);

        fab = findViewById(R.id.fab_journal_details_edit);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailsJournalEntry.this, AddJournalActivity.class);
                intent.putExtra(AddJournalActivity.EXTRA_JOURNAL_ID, mJOURNALId);
                startActivity(intent);
                finish();

            }
        });

    }



    private void populateUI(JournalEntry journal) {

        if(journal == null){
            return;
        }
        tvShowTitle.setText(journal.getTitle());
        tvShowContent.setText(journal.getContent());

        setTitle(dateFormat.format(journal.getCreatedAt()));


        int color = ContextCompat.getColor(this,R.color.colorPrimaryLight);

        switch (journal.getMood()){
            case "happy":
                color = ContextCompat.getColor(this,R.color.colorMoodHappy);
                tvShowTitle.setTextColor(color);
                break;

            case "ungry" :
                color = ContextCompat.getColor(this,R.color.colorMoodUngry);
                tvShowTitle.setTextColor(color);
                break;

            case "down" :
                color = ContextCompat.getColor(this,R.color.colorMoodDown);
                tvShowTitle.setTextColor(color);
                break;

            case "calm" :
                color = ContextCompat.getColor(this,R.color.colorMoodCalm);
                tvShowTitle.setTextColor(color);
                break;

            case "relaxed" :
                color = ContextCompat.getColor(this,R.color.colorMoodRelaxed);
                tvShowTitle.setTextColor(color);
                break;

            case "sad" :
                color = ContextCompat.getColor(this,R.color.colorMoodSad);
                tvShowTitle.setTextColor(color);
                break;

        }


    }


}
