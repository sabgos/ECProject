package com.ElderCare.ElderCareFD;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {
    TextView textView;
    FirebaseAuth firebaseAuth;
    String myStringData, myKeyValueData,name,UID,myUser,addrs,phone;
    EditText nameText, phoneText, addrsText, diabText, vegText;
    CheckBox diab, veg;
    Button updateButton;
    @SuppressLint("RestrictedApi")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        UID = user.getUid();

        nameText= (EditText) findViewById(R.id.editTextPersonName2);
      //  IDText= (TextView) findViewById(R.id.textViewID);
        phoneText = (EditText) findViewById(R.id.editTextPhone);
        addrsText=(EditText) findViewById(R.id.editTextAddress);
        //diabText = (EditText) findViewById(R.id.diabb);
        //vegText = (EditText) findViewById(R.id.vegg);
       // diab = (CheckBox) findViewById(R.id.diabProf);
      //  veg = (CheckBox) findViewById(R.id.vegProf);


        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        textView = findViewById(R.id.textback);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final DatabaseReference myRef = database.getReference();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            private static final String TAG = "Hi";

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                name= snapshot.child("users").child(UID).child("username").getValue(String.class);
                addrs= snapshot.child("users").child(UID).child("address").getValue(String.class);
                phone = snapshot.child("users").child(UID).child("phone").getValue(String.class);
               // IDText.setText(UID);
                nameText.setText(name);
                addrsText.setText(addrs);
                phoneText.setText(phone);
        //        String diabTxt=snapshot.child("users").child(UID).child("diabetic").getValue(String.class);
          //      String vegTxt=snapshot.child("users").child(UID).child("veg").getValue(String.class);
               // diabText.setText(diabTxt);
                //vegText.setText(vegTxt);

    //Log.e(TAG, diabTxt);
/*
                if(diabTxt.equalsIgnoreCase("Diabetic"))
                    diab.setChecked(true);
                else
                    diab.setChecked(false);

                if(vegTxt.equalsIgnoreCase("Vegetarian"))
                    veg.setChecked(true);
                else
                    veg.setChecked(false);
*/
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        updateButton = (Button) findViewById(R.id.btnUpdate);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName = nameText.getText().toString();
                String newAddr = addrsText.getText().toString();
                String newDiab;

                String newVeg;

                if(diab.isChecked())
                    newDiab= "Diabetic";
                else
                    newDiab="Non Diabetic";

                if(veg.isChecked())
                    newVeg="Vegetarian";
                else
                    newVeg="Non Vegetarian";


                myRef.child("users").child(UID).child("username").setValue(newName);
                myRef.child("users").child(UID).child("address").setValue(newAddr);
               // myRef.child("users").child(UID).child("username").setValue(newName);
                myRef.child("users").child(UID).child("diabetic").setValue(newDiab);
                myRef.child("users").child(UID).child("veg").setValue(newVeg);

                Context context = getApplicationContext();
                CharSequence text = "Data Updated!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                //db.ref("-Users/-KUanJA9egwmPsJCxXpv").update({ username: newName });
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
        Toast.makeText(this, "Selected Item: " +item.getTitle(), Toast.LENGTH_SHORT).show();
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