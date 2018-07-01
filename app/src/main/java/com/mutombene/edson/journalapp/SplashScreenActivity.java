package com.mutombene.edson.journalapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mutombene.edson.journalapp.activities.LoginActivity;

import java.util.Arrays;
import java.util.List;

public class SplashScreenActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 123;
    private SharedPreferences sharedPreferences;
    private Boolean loggedIn = false;
    private TextView tvSplash;
    private String mSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        tvSplash = findViewById(R.id.tv_splash);



        readFromSharedPreference();

        tvSplash.setText(mSplash);


        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                showLogin();
            }
        }, 2000);



    }



    private void showLogin(){

        if(loggedIn){
            Intent intent = new Intent(SplashScreenActivity.this,
                    MainActivity.class);
            startActivity(intent);
            finish();
        }

        else{
            Intent intent = new Intent(SplashScreenActivity.this,
                    LoginActivity.class);
            startActivity(intent);
            finish();
        }

    }



    private void readFromSharedPreference(){

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        loggedIn = sharedPreferences.getBoolean("logged_in", false);
        mSplash = sharedPreferences.getString("splash_text", "Your Journal Starts Now");


    }

}
