package com.example.meds;

//x
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
//import android.support.design.widget.TabLayout;
//import android.support.v4.view.ViewPager;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

//import org.json.JSONException;
//import org.json.JSONObject;

//import java.io.IOException;


//x
public class MainActivity<SectionPagerAdapter> extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Toolbar mainToolBar;
    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        bottomNavigation = findViewById(R.id.bottom_Nav);

        // Authentication
        mAuth = FirebaseAuth.getInstance();

        /*mainToolBar = findViewById(R.id.main_tool_bar);

        setSupportActionBar(mainToolBar);
        mainToolBar.setLogo(R.drawable.sports_small_icon);
        // Tabs
        ViewPager viewPager = findViewById(R.id.main_tabs);
        SectionPagerAdapter sectionPagerAdapter = new SectionPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(sectionPagerAdapter);
        TabLayout tabLayout = findViewById(R.id.main_pager_bar);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.pager_message);
        tabLayout.getTabAt(1).setIcon(R.drawable.pager_game);
        tabLayout.getTabAt(2).setIcon(R.drawable.pager_team);
        tabLayout.getTabAt(3).setIcon(R.drawable.pager_friend);*/

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!isNetworkConnected()) {
            Toast.makeText(this, "No Internet connection!", Toast.LENGTH_SHORT).show();
        }
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {  // user is NOT signed in, go to StartActivity
            toStartActivity();
        }
        else{
            toHealthSummaryActivity();
        }
        /*else {
            setMainToolBarTitleAsUsername();
            try {
                JSONObject general = getWeatherForecast();
                String weather = general.get("forecast").toString();
                JSONObject temperature = new JSONObject(general.get("temperature").toString());
                int highTemperature=temperature.getInt("high");
                int lowTemperature=temperature.getInt("low");
                int goodWeather = isGoodWeather(weather);

                //display in long period of time
                Toast.makeText(getApplicationContext(), weather+"\nhighest temperature is "+highTemperature+"°C\nlowest temperature is "+lowTemperature+"°C", Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(), giveRecommendation(highTemperature,lowTemperature,goodWeather), Toast.LENGTH_LONG).show();

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/

    }


    private void toStartActivity(){
        Intent startIntent = new Intent(MainActivity.this, StartActivity.class);
        startActivity(startIntent);
        finish();
    }

    private void toHealthSummaryActivity(){
        Intent startIntent = new Intent(MainActivity.this, HealthSummaryActivity.class);
        startActivity(startIntent);
        finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void setMainToolBarTitleAsUsername() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users").child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).child("name");

        myRef.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                String username = Objects.requireNonNull(dataSnapshot.getValue()).toString();
                mainToolBar.setTitle(username);
                Log.i("Username", username);
            }

            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {

            }
        });
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        assert cm != null;
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null;
    }
}

