package com.ElderCare.ElderCareFD.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ElderCare.ElderCareFD.R;
import com.ElderCare.ElderCareFD.utility.Utils;

public class AboutBornToHelp extends AppCompatActivity {
    Button button;
    TextView textView;
    @SuppressLint("RestrictedApi")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_borntohelp);

        textView = findViewById(R.id.textback);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
                startActivity(new Intent(this, AboutBornToHelp.class));
                return true;
            case R.id.about2:
                startActivity(new Intent(this, AboutServicePlace.class));
                return true;
            case R.id.logout:
                //startActivity(new Intent(this, MainActivity.class));
                Utils.logout(this);
                return true;
            case R.id.help:
                startActivity(new Intent(this, Help.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}