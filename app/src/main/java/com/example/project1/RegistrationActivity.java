package com.example.project1;

import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    CheckBox diabText, vegText;
    String name,UID,diab,veg,address,mobile;
    EditText nameText,addrText;

    private String verificationid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Intent intent = getIntent();
        mobile = intent.getStringExtra("mobile");
       // editTextMobile = findViewById(R.id.editTextMobile);
        registerNew = findViewById(R.id.buttonContinue);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        UID = user.getUid();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef= database.getReference("users");

        nameText = findViewById(R.id.editTextTextPersonName);
        diabText = findViewById(R.id.checkbox10);
        vegText= findViewById(R.id.checkbox20);
        addrText = findViewById(R.id.editTextAddress1);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

               if(snapshot.child(UID).exists()) {
                   name= snapshot.child(UID).child("username").getValue(String.class);
                   String addrs= snapshot.child(UID).child("address").getValue(String.class);
                   String diabTxt=snapshot.child(UID).child("diabetic").getValue(String.class);
                   String vegTxt=snapshot.child(UID).child("veg").getValue(String.class);

                   nameText.setText(name);

                   if(diabTxt.equalsIgnoreCase("Diabetic"))
                       diabText.setChecked(true);
                   else
                       diabText.setChecked(false);

                   if(vegTxt.equalsIgnoreCase("Vegetarian"))
                       vegText.setChecked(true);
                   else
                       vegText.setChecked(false);

                   addrText.setText(addrs);


               }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        registerNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name=nameText.getText().toString();
                address = addrText.getText().toString();
                //diab=diabText.getText().toString();
                if(diabText.isChecked())
                   diab="Diabetic";
                else
                    diab="Non Diabetic";

               // veg=vegText.getText().toString();
                if(vegText.isChecked())
                    veg="Vegetarian";
                else
                    veg="Non Vegetarian";

                myRef.push().setValue(UID);
                myRef.child(UID).child("username").setValue(name);
                myRef.child(UID).child("diabetic").setValue(diab);
                myRef.child(UID).child("veg").setValue(veg);
                myRef.child(UID).child("address").setValue(address);
                myRef.child(UID).child("phone").setValue(mobile);
                startActivity(new Intent(RegistrationActivity.this, WelcomeActivity.class));
            }
        });
    }
}