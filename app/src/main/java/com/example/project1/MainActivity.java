package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private Button register,login;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        register = (Button) findViewById(R.id.buttonReg);
        login = (Button) findViewById(R.id.buttonLogin);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        //check whether the user is logged in
        if (currentUser != null) {
            Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
            startActivity(intent);
            finish();
        } else {
            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, Activity2.class);
                    startActivity(intent);
                }
            });

            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
}