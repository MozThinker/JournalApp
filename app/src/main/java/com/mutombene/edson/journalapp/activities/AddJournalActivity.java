package com.mutombene.edson.journalapp.activities;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mutombene.edson.journalapp.AddJournalViewModel;
import com.mutombene.edson.journalapp.AddJournalViewModelFactory;
import com.mutombene.edson.journalapp.AppExecutors;
import com.mutombene.edson.journalapp.MainActivity;
import com.mutombene.edson.journalapp.R;
import com.mutombene.edson.journalapp.database.AppDatabase;
import com.mutombene.edson.journalapp.database.JournalEntry;

import java.util.Date;

/**
 * Created by EMutombene on 6/25/2018.
 */

public class AddJournalActivity extends AppCompatActivity {

    public static final String EXTRA_JOURNAL_ID = "extraJournalId";
    public static final String INSTANCE_JOURNAL_ID = "instanceJournalId";
    private static final int DEFAULT_JOURNAL_ID = -1;
    private static final String TAG = AddJournalActivity.class.getSimpleName();

    EditText mAddTitle;
    EditText mAddContent;

    Button mSave;
    String rbMood = "happy";

    private int mJOURNALId = DEFAULT_JOURNAL_ID;

    private AppDatabase mDb;

    SharedPreferences sharedPreferences ;
    String userName = "";
    String userId = "";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_journal);
        initViews();
        readFromSharedPreference();
        mDb = AppDatabase.getsInstance(getApplicationContext());

        if (savedInstanceState != null && savedInstanceState.containsKey(INSTANCE_JOURNAL_ID)) {
            mJOURNALId = savedInstanceState.getInt(INSTANCE_JOURNAL_ID, DEFAULT_JOURNAL_ID);
        }

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_JOURNAL_ID)) {
            mSave.setText(R.string.update_button);
            setTitle(R.string.edit_entry);
            if (mJOURNALId == DEFAULT_JOURNAL_ID) {
                // populate the UI
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


    private void populateUI(JournalEntry journal) {

        if(journal == null){
            return;
        }
        mAddTitle.setText(journal.getTitle());
        mAddContent.setText(journal.getContent());



    }

    public void onSaveButtonClicked(){



            if(emptyField()){
                Snackbar.make (findViewById(R.id.ll_add_journal), getString(R.string.empty_field), Snackbar.LENGTH_LONG).show();
            }else {
                Date date = new Date();


                // COMPLETED (8) Create taskEntry variable using the variables defined above
                final JournalEntry journalEntry = new JournalEntry(userId,userName, mAddTitle.getText().toString(),mAddContent.getText().toString(), date,date, rbMood);
                // COMPLETED (9) Use the taskDao in the AppDatabase variable to insert the taskEntry

                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (mJOURNALId == DEFAULT_JOURNAL_ID){
                            mDb.journalDao().insertJournal(journalEntry);
                        } else {
                            journalEntry.setId(mJOURNALId);
                            mDb.journalDao().updateJournal(journalEntry);
                        }

                        // COMPLETED (10) call finish() to come back to MainActivity
                        finish();
                        //Toast.makeText(this,mDb.journalDao().loadAllTasks().toString(), Toast.LENGTH_LONG).show();
                    }
                });

                 Toast.makeText(this,getString(R.string.submited),Toast.LENGTH_LONG).show();
            }









    }

    private void initViews(){
        mSave = findViewById(R.id.saveButton);
        mAddTitle = findViewById(R.id.tv_add_title);
        mAddContent = findViewById(R.id.tv_add_content);
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSaveButtonClicked();
            }
        });
    }

    private void readFromSharedPreference(){

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        userName = sharedPreferences.getString("user_name", "vazio");
        userId = sharedPreferences.getString("user_id", "vazio");

    }


    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();


        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.rb_mod_happy:
                if (checked)
                    rbMood = getString(R.string.mood_happy);
                    break;
            case R.id.rb_mod_ungry:
                if (checked)
                    rbMood  = getString(R.string.mood_ungry);
                    break;
            case R.id.rb_mod_calm:
                if (checked)
                    rbMood = getString(R.string.mood_calm);
                    break;
            case R.id.rb_mod_relaxed:
                if (checked)
                    rbMood = getString(R.string.mood_relaxed);
                    break;
            case R.id.rb_mod_down:
                if (checked)
                    rbMood = getString(R.string.mood_down);
                    break;
            case R.id.rb_mod_sad:
                if (checked)
                    rbMood = getString(R.string.mood_sad);
                    break;
        }
    }



    public boolean emptyField(){
        if(mAddTitle.getText().toString().equals("") || mAddContent.getText().toString().equals("")){
            return true;
        }return false;
    }



}
