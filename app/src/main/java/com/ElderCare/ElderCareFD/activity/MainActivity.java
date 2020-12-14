package com.ElderCare.ElderCareFD.activity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.ElderCare.ElderCareFD.R;
import com.ElderCare.ElderCareFD.utility.SaveSharedPreference;

public class MainActivity extends AppCompatActivity {
    private ImageButton login;
    private CardView register;
    //FirebaseUser currentUser;
    private EditText editTextMobile;
    //private Object OrderMutation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        register = (CardView) findViewById(R.id.buttonReg);
        editTextMobile = findViewById(R.id.et_phone);
        //login = (Button) findViewById(R.id.buttonLogin);
        //currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if(checkIfUserAlreadyLoggedIn()){
            Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
            startActivity(intent);
            finish();
        }

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobileNo = editTextMobile.getText().toString().trim();
                Intent intent = new Intent(MainActivity.this, VerifyPhoneActivity.class);
                intent.putExtra("mobile", mobileNo);
                startActivity(intent);
            }
        });

    }

    private boolean checkIfUserAlreadyLoggedIn() {
        return SaveSharedPreference.getLoggedStatus(getApplicationContext());
    }

}