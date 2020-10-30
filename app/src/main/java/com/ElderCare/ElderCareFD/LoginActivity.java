package com.ElderCare.ElderCareFD;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private EditText textMobile, textOtp;
    private Button btnLogin,btnOTP;
    private FirebaseAuth mAuth;
    private String mVerificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        textMobile = findViewById(R.id.phoneLogin);
        //textOtp = findViewById(R.id.phoneOtp);
       // btnOTP = findViewById(R.id.buttonOTP);
        btnLogin = findViewById(R.id.buttonLogin2);
        mAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mobileNo = textMobile.getText().toString().trim();
                Intent intent = new Intent(LoginActivity.this, VerifyPhoneActivity.class);
                intent.putExtra("mobile", mobileNo);
                startActivity(intent);
            }
        });

    }


}