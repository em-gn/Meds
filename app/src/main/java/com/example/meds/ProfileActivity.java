package com.example.meds;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;

public class ProfileActivity extends AppCompatActivity {

    String phoneNumber;
    TextView mobileNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // get saved phone number
        SharedPreferences prefs =  getApplicationContext().getSharedPreferences("USER_PREF",
                Context.MODE_PRIVATE);
        phoneNumber = prefs.getString("phoneNumber", NULL);

        mobileNumber = findViewById(R.id.mobileNumber);
        mobileNumber.setText(phoneNumber);

        findViewById(R.id.signout_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();

                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.bottom_navigation_menu, menu);
        return true;
    }


    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_search:
                    toSearchActivity();
                    return true;
                case R.id.navigation_diagnosis:
                    toDiagnosisActivity();
                    return true;
                case R.id.navigation_health_summary:
                    toHealthSummaryActivity();
                    return true;
            }
            return false;
        }
    };

    private void toSearchActivity () {
        Intent startIntent = new Intent(ProfileActivity.this, SearchActivity.class);
        startActivity(startIntent);
        finish();
    }

    private void toDiagnosisActivity () {
        Intent startIntent = new Intent(ProfileActivity.this, DiagnosisActivity.class);
        startActivity(startIntent);
        finish();
    }

    private void toHealthSummaryActivity () {
        Intent startIntent = new Intent(ProfileActivity.this, HealthSummaryActivity.class);
        startActivity(startIntent);
        finish();
    }
}