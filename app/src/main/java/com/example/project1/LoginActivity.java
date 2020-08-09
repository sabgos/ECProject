package com.example.project1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

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