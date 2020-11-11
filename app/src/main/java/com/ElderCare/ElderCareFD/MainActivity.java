package com.ElderCare.ElderCareFD;


import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.example.SignUpWithContactNumberMutation;
import com.example.UpdateProfileQueryMutation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firestore.v1.StructuredQuery;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {
    private ImageButton login;
    private CardView register;
    FirebaseUser currentUser;
    private EditText editTextMobile;
    private Object OrderMutation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        register = (CardView) findViewById(R.id.buttonReg);
        editTextMobile = findViewById(R.id.et_phone);
        //login = (Button) findViewById(R.id.buttonLogin);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();


        ApolloClient apolloClient = ApolloClient.builder()
                .serverUrl("http://167.71.232.133/graphql")
                .build();


        //check whether the user is logged in
        if (currentUser != null) {
            Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
            startActivity(intent);
            finish();
        } else {
            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String mobileNo = editTextMobile.getText().toString().trim();
                    Intent intent = new Intent(MainActivity.this, VerifyPhoneActivity.class);
                    intent.putExtra("mobile", mobileNo);
                    startActivity(intent);
                }
            });
/*
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            });

 */
        }
    }

}