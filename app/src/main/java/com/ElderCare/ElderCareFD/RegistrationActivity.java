package com.ElderCare.ElderCareFD;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.ElderCare.ElderCareFD.R;
import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.example.CompleteSignupMutation;
import com.example.FoodSearchQuery;
import com.example.RegisterQuery;
import com.example.SignUpWithContactNumberMutation;
import com.example.type.UserPersonInput;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.List;

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

        ApolloConnector.setupApollo().query(RegisterQuery.builder().build()).enqueue(new ApolloCall.Callback<RegisterQuery.Data>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(@NotNull Response<RegisterQuery.Data> response) {
                List<RegisterQuery.UserSearch> userSearches = response.getData().userSearch();
                for (RegisterQuery.UserSearch userSearch : userSearches) {
                    Log.e(TAG, "onResponse: " + userSearch );
                    //itemsName.add(foodmenuSearch.name()+"  "+foodmenuSearch.nonVegFlag()+"  "+foodmenuSearch.diabeticFlag());
                    nameText.setText(userSearch.name());
                    //addrText.setText(userSearch.addresses());
                }
            }
            @Override
            public void onFailure(@NotNull ApolloException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        });

        /*
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
*/
        registerNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



/*
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

 */
                registerPgSQLDB(UserPersonInput.builder().build());
                startActivity(new Intent(RegistrationActivity.this, WelcomeActivity.class));
            }
        });


    }
    void registerPgSQLDB(UserPersonInput personInput) {
        CompleteSignupMutation completeSignup= CompleteSignupMutation.builder().build();
        ApolloConnector.setupApollo().mutate(completeSignup).enqueue(new ApolloCall.Callback<CompleteSignupMutation.Data>() {
            @Override
            public void onResponse(@org.jetbrains.annotations.NotNull Response<CompleteSignupMutation.Data> response) {
                Log.e(TAG, "onResponse: " + response.toString());
                if (response.getData() == null) {
                    Log.e(TAG, "onResponse: " + "response.getData() => NULL");
                    return;
                }
                Log.e(TAG, "onResponse: " + response.getData().completeSignup());
                Log.e(TAG, "onResponse: pgSQLDB updated Successfully ");
            }

            @Override
            public void onFailure(@NotNull ApolloException e) {
                Log.e(TAG, "onResponse ERROR: " + e);
                Log.e(TAG, "onResponse ERROR: " + e.getMessage());
            }
        });
    }

}