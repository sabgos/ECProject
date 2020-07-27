package com.example.project1;

import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project1.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class RegistrationActivity extends AppCompatActivity{


    public static final int RC_SIGN_IN = 001;
    private static final String TAG = RegistrationActivity.class.getSimpleName();

    private EditText editTextMobile;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    private boolean mVerificationInProgress = false;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    private Button registerNew;
    CheckedTextView diabText, vegText;
    String myStringData, myKeyValueData,name,UID,diab,veg;
    EditText nameText;

    private String verificationid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        editTextMobile = findViewById(R.id.editTextMobile);
        registerNew = findViewById(R.id.buttonContinue);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        UID = user.getUid();
        nameText = findViewById(R.id.editTextTextPersonName);
        diabText = findViewById(R.id.checkedTextView);
        vegText= findViewById(R.id.checkedTextView2);

        name=nameText.getText().toString();
        diab=diabText.getText().toString();
        veg=vegText.getText().toString();

        registerNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef= database.getReference("users");

                myRef.child("users").setValue(UID);
                myRef.child("users").child(UID).child("username").setValue(name);
                myRef.child("users").child(UID).child("diabetic").setValue(diab);
                myRef.child("users").child(UID).child("veg").setValue(veg);
                startActivity(new Intent(RegistrationActivity.this, WelcomeActivity.class));
            }
        });
    }
}