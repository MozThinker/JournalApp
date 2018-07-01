package com.mutombene.edson.journalapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.mutombene.edson.journalapp.activities.AddJournalActivity;
import com.mutombene.edson.journalapp.activities.DetailsJournalEntry;
import com.mutombene.edson.journalapp.activities.LoginActivity;
import com.mutombene.edson.journalapp.adapter.JournalAdapter;
import com.mutombene.edson.journalapp.database.AppDatabase;
import com.mutombene.edson.journalapp.database.JournalEntry;

import java.util.List;

import static android.support.v7.widget.DividerItemDecoration.VERTICAL;

public class MainActivity extends AppCompatActivity implements JournalAdapter.ItemClickListener{

    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private JournalAdapter mAdapter;


    // Member variable for the Database
    private AppDatabase mDb;

    FloatingActionButton fab;

    SharedPreferences sharedPreferences ;
    Intent startLoginActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerViewJournals);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new JournalAdapter(this,this);
        mRecyclerView.setAdapter(mAdapter);

        DividerItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), VERTICAL);

        mRecyclerView.addItemDecoration(decoration);


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            // Called when a user swipes left or right on a ViewHolder
            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                // Here is where you'll implement swipe to delete
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        int position = viewHolder.getAdapterPosition();
                        List<JournalEntry> journals = mAdapter.getJournals();
                        mDb.journalDao().deleteJournal(journals.get(position));
                    }
                });
            }
        }).attachToRecyclerView(mRecyclerView);


        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addJournalIntent = new Intent(MainActivity.this, AddJournalActivity.class);
                startActivity(addJournalIntent);

            }
        });

        mDb = AppDatabase.getsInstance(getApplicationContext());

        setupViewModel();




    }



    @Override
    protected void onResume() {
        super.onResume();
    }

    private void setupViewModel() {

        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getJournals().observe(this, new Observer<List<JournalEntry>>() {
            @Override
            public void onChanged(@Nullable List<JournalEntry> journalEntries) {
                Log.d(TAG,"Updating list of journals from LiveData in ViewModel");
                mAdapter.setJournals(journalEntries);
            }
        });

    }

    @Override
    public void onItemClickListener(int itemId){
        /**
        Intent intent = new Intent(MainActivity.this, AddJournalActivity.class);
        intent.putExtra(AddJournalActivity.EXTRA_JOURNAL_ID, itemId);
        startActivity(intent);
         **/

        Intent intent = new Intent(MainActivity.this, DetailsJournalEntry.class);
        intent.putExtra(DetailsJournalEntry.EXTRA_JOURNAL_ID, itemId);
        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_journal,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.action_exit){

            signOut();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




    private void signOut(){
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {

                        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("logged_in",false);
                        editor.apply();
                        startLoginActivity = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(startLoginActivity);
                        finish();

                    }
                });
    }


}
