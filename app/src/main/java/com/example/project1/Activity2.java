package com.example.project1;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

public class Activity2 extends AppCompatActivity {
    private Button button;
    EditText et_phone;
    Button send_otp_btn;
    ProgressBar pb_bar;
    String phoneNumber;

    private EditText editTextMobile;
    Button btnContinue;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        editTextMobile = findViewById(R.id.et_phone);
        btnContinue = findViewById(R.id.send_otp_btn);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        //check whether the user is logged in
        if (currentUser != null) {
            Intent intent = new Intent(Activity2.this, WelcomeActivity.class);
            startActivity(intent);
            finish();
        } else {
            btnContinue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String mobileNo = editTextMobile.getText().toString().trim();
                    Intent intent = new Intent(Activity2.this, VerifyPhoneActivity.class);
                    intent.putExtra("mobile", mobileNo);
                    startActivity(intent);
                }
            });
        }
    }
}
/***
        et_phone = findViewById(R.id.et_phone);
        send_otp_btn = findViewById(R.id.send_otp_btn);
        pb_bar = findViewById(R.id.pb_bar);

        pb_bar.setVisibility(View.GONE);

        send_otp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNumber = et_phone.getText().toString().trim();
                if(phoneNumber.isEmpty()){
                    et_phone.setError("Invalid Phone Number");
                }else {
                    pb_bar.setVisibility(View.VISIBLE);
                    sendVerificationCode(phoneNumber);
                }
            }
        });

 *////

 /*       button = (Button) findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity2();
            }
        });
      */


    /***
    private void sendVerificationCode(String phoneNumber){

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91"+phoneNumber,
                60,
                TimeUnit.SECONDS,
                this,
                mCall
        );
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCall = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
        }


        @Override
        public void onVerificationFailed(FirebaseException e) {

            pb_bar.setVisibility(View.GONE);
        }

        @Override
        public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken token) {

            pb_bar.setVisibility(View.GONE);
            String mVerificationId = verificationId;
            Log.e("MainActivity" , "Verification id : " + verificationId);
            Intent intent = new Intent(Activity2.this , OtpActivity.class);
            intent.putExtra("verificationId" , mVerificationId);
            startActivity(intent);
            finish();
        }

    };
     *///

  /*  public void openActivity2() {
        Intent intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);
    }
    */


