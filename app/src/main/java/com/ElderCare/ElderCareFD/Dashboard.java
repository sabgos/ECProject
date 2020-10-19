package com.ElderCare.ElderCareFD;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;

public class Dashboard extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    CardView cv1, cv2, cv3, cv4,btnWsap;

    @SuppressLint("RestrictedApi")
    @RequiresApi(api = Build.VERSION_CODES.N)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
/*
        cv1 = (CardView) findViewById(R.id.cardView);
        cv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity2();
            }
        });


        cv2 = (CardView) findViewById(R.id.cardView2);
        cv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity5();
            }
        });

        cv3 = (CardView) findViewById(R.id.cardView3);
        cv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity6();
            }
        });

        btnWsap = (CardView) findViewById(R.id.cardView4);
        btnWsap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,"Text sent");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });
    }
    public void openActivity2() {
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
    }

    public void openActivity5() {
        Intent intent = new Intent(this, SelectPersons.class);
        startActivity(intent);
    }
    public void openActivity6() {
        Intent intent = new Intent(this, PreorderDates.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        Toast.makeText(this, "Selected Item: " +item.getTitle(), Toast.LENGTH_SHORT).show();
        switch (item.getItemId()) {
            case R.id.homeRet:
                startActivity(new Intent(this, WelcomeActivity.class));
                return true;
            case R.id.profile:
                startActivity(new Intent(this, Profile.class));
                return true;
            case R.id.orders:
                startActivity(new Intent(this, Orders.class));
                return true;
            case R.id.about1:
                startActivity(new Intent(this, About1.class));
                return true;
            case R.id.about2:
                startActivity(new Intent(this, About2.class));
                return true;
            case R.id.logout:
                firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.signOut();
                startActivity(new Intent(Dashboard.this, MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

 */
    }

}
