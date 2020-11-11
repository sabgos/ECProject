package com.ElderCare.ElderCareFD;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.example.FoodSearchQuery;
import com.example.UpdateProfileQueryMutation;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Profile extends AppCompatActivity {
    private static final String TAG = "Profile";
    //TextView textView;
    FirebaseAuth firebaseAuth;
    String myStringData, myKeyValueData, name, UID, myUser, addrs, phone;
    EditText nameText, phoneText, addrsText, diabText, vegText;
    // CheckBox diab, veg;
    Button updateButton;

    MaterialCardView diabeticChip, vegChip, nonVegChip;
    TextView diabeticChipTV, vegChipTV, nonVegChipTV;
    CheckBox vegCB, nonVegCB, diabeticCB, nonDiabeticCB;

    @SuppressLint("RestrictedApi")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updated_activity_profile);
       // FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
      //  UID = user.getUid();

        nameText = (EditText) findViewById(R.id.editTextPersonName2);
        //  IDText= (TextView) findViewById(R.id.textViewID);
        phoneText = (EditText) findViewById(R.id.editTextPhone);
        addrsText = (EditText) findViewById(R.id.editTextAddress);

        diabeticChip = findViewById(R.id.diabeticChip);
        vegChip = findViewById(R.id.vegChip);
        nonVegChip = findViewById(R.id.nonVegChip);

        diabeticChipTV = findViewById(R.id.diabeticChipTV);
        vegChipTV = findViewById(R.id.vegChipTV);
        nonVegChipTV = findViewById(R.id.nonVegChipTV);


        vegCB = findViewById(R.id.vegCB);
        nonVegCB = findViewById(R.id.nonVegCB);
        diabeticCB = findViewById(R.id.diabeticCB);
        nonDiabeticCB = findViewById(R.id.nonDiabeticCB);


        vegCB.setOnCheckedChangeListener((cb, isChecked) -> {
            if (cb.isPressed()) {
                nonVegCB.setChecked(!isChecked);
            }
        });

        nonVegCB.setOnCheckedChangeListener((cb, isChecked) -> {
            if (cb.isPressed()) {
                vegCB.setChecked(!isChecked);
            }
        });

        diabeticCB.setOnCheckedChangeListener((cb, isChecked) -> {
            if (cb.isPressed()) {
                nonDiabeticCB.setChecked(!isChecked);
            }
        });

        nonDiabeticCB.setOnCheckedChangeListener((cb, isChecked) -> {
            if (cb.isPressed()) {
                diabeticCB.setChecked(!isChecked);
            }
        });


//        vegCB.setOnClickListener(view -> {
//
//            nonVegChip.setVisibility(View.VISIBLE);
//            vegChip.setVisibility(View.GONE);
//        });
//
//
//        vegChip.setOnClickListener(view -> {
//            nonVegChip.setVisibility(View.VISIBLE);
//            vegChip.setVisibility(View.GONE);
//        });
//
//        nonVegChip.setOnClickListener(view -> {
//            vegChip.setVisibility(View.VISIBLE);
//            nonVegChip.setVisibility(View.GONE);
//        });

//        diabeticChip.setOnClickListener(view -> {
//            if (diabeticChipTV.getText().toString().startsWith("Non"))
//                diabeticChipTV.setText("Diabetic");
//            else
//                diabeticChipTV.setText("Non Diabetic");
//        });


        //textView = findViewById(R.id.textback);
        //diabText = (EditText) findViewById(R.id.diabb);
        //vegText = (EditText) findViewById(R.id.vegg);
        // diab = (CheckBox) findViewById(R.id.diabProf);
        //  veg = (CheckBox) findViewById(R.id.vegProf);

/*
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final DatabaseReference myRef = database.getReference();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                name = snapshot.child("users").child(UID).child("username").getValue(String.class);
                addrs = snapshot.child("users").child(UID).child("address").getValue(String.class);
                phone = snapshot.child("users").child(UID).child("phone").getValue(String.class);
                // IDText.setText(UID);
                nameText.setText(name);
                addrsText.setText(addrs);
                phoneText.setText(phone);

                String diabTxt = snapshot.child("users").child(UID).child("diabetic").getValue(String.class);
                String vegTxt = snapshot.child("users").child(UID).child("veg").getValue(String.class);

//                diabeticChip.setVisibility(View.VISIBLE);
//                diabeticChipTV.setText(diabTxt);
//
//                if (vegTxt.startsWith("Non")) {
//                    nonVegChip.setVisibility(View.VISIBLE);
//                    vegChip.setVisibility(View.GONE);
//                    nonVegChipTV.setText(vegTxt);
//                } else {
//                    vegChip.setVisibility(View.VISIBLE);
//                    nonVegChip.setVisibility(View.GONE);
//                    vegChipTV.setText(vegTxt);
//                }

                if (vegTxt != null && vegTxt.startsWith("Non")) {
                    vegCB.setChecked(false);
                    nonVegCB.setChecked(true);
                } else {
                    nonVegCB.setChecked(false);
                    vegCB.setChecked(true);
                }

                if (diabTxt != null && diabTxt.startsWith("Non")) {
                    diabeticCB.setChecked(false);
                    nonDiabeticCB.setChecked(true);
                } else {
                    nonDiabeticCB.setChecked(false);
                    diabeticCB.setChecked(true);
                }


                // diabText.setText(diabTxt);
                //vegText.setText(vegTxt);

                //Log.e(TAG, diabTxt);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

*/
        updateButton = (Button) findViewById(R.id.btnUpdate);
        updateButton.setOnClickListener(v -> {
            String newName = nameText.getText().toString();
            String newAddr = addrsText.getText().toString();
/*
            myRef.child("users").child(UID).child("username").setValue(newName);
            myRef.child("users").child(UID).child("address").setValue(newAddr);
            //myRef.child("users").child(UID).child("diabetic").setValue(diabeticChipTV.getText());


            if(vegCB.isChecked())
                myRef.child("users").child(UID).child("veg").setValue("Vegetarian");
            else
                myRef.child("users").child(UID).child("veg").setValue("Non Vegetarian");


            if(diabeticCB.isChecked())
                myRef.child("users").child(UID).child("diabetic").setValue("Diabetic");
            else
                myRef.child("users").child(UID).child("diabetic").setValue("Non Diabetic");
//            if (vegChip.getVisibility() == View.VISIBLE)
//                myRef.child("users").child(UID).child("veg").setValue("Vegetarian");
//            else
//                myRef.child("users").child(UID).child("veg").setValue("Non Vegetarian");
*/

            //This is a dummy Call to test GraphQL Mutation in pgSQL.
            updatePgSQLDB(43L, newName,"");

            Context context = getApplicationContext();
            CharSequence text = "Data Updated!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            //db.ref("-Users/-KUanJA9egwmPsJCxXpv").update({ username: newName });
        });
    }

    void updatePgSQLDB(Long id, String name, String email){
        UpdateProfileQueryMutation profileQueryMutation = UpdateProfileQueryMutation.builder().id(id)
                .name(name).email(email).build();
        ApolloConnector.setupApollo().mutate(profileQueryMutation).enqueue(new ApolloCall.Callback<UpdateProfileQueryMutation.Data>() {
            @Override
            public void onResponse(@NotNull Response<UpdateProfileQueryMutation.Data> response) {
                Log.e(TAG, "onResponse: " + response.toString() );
                if(response.getData() == null){
                    Log.e(TAG, "onResponse: " + "response.getData() => NULL" );
                    return;
                }
                Log.e(TAG, "onResponse: " + response.getData().updateUserProfile() );
                Log.e(TAG, "onResponse: pgSQLDB updated Successfully " );
            }
            @Override
            public void onFailure(@NotNull ApolloException e) {
                Log.e(TAG, "onResponse ERROR: " + e);
                Log.e(TAG, "onResponse ERROR: " + e.getMessage() );
            }
        });
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
        Toast.makeText(this, "Selected Item: " + item.getTitle(), Toast.LENGTH_SHORT).show();
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
                startActivity(new Intent(this, About1.class));
                return true;
            case R.id.about2:
                startActivity(new Intent(this, About2.class));
                return true;
            case R.id.logout:
                firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.signOut();
                startActivity(new Intent(Profile.this, MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}