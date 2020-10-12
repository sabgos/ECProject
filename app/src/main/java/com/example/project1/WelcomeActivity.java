package com.example.project1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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


public class WelcomeActivity extends AppCompatActivity {

    private Button button;
    FirebaseAuth firebaseAuth;

    Button myApplyBt ;
 EditText myEditText,myKeyValue;
 TextView nameText,IDText,prefText;
 String myStringData, myKeyValueData,name,UID,myUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        button = (Button) findViewById(R.id.button8);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity2();
            }
        });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        UID = user.getUid();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
       // DatabaseReference myRef = database.getReference("message");
     //   myRef.setValue("Hello!");
    //    DatabaseReference myRef= database.getReference("users");
       // myRef.child(UID).setValue(user);
       // User myuser = new User(name);

       // myRef.child("users").child(UID).child("username").setValue("SAb");

      //  if (user != null) {
            // Name, email address etc
           // UID = user.getUid();
          //  myRef.child(UID).setValue(user);
        nameText= (TextView) findViewById(R.id.textView2);
      //  IDText= (TextView) findViewById(R.id.textViewID);
        prefText=(TextView) findViewById(R.id.textView21);
       // Firebase mRef=new Firebase("https://absolute-accord-283510.firebaseio.com/");

        //Getting Firebase Instance

        //Getting Reference to Root Node
        DatabaseReference myRef = database.getReference();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                name= snapshot.child("users").child(UID).child("username").getValue(String.class);
                //IDText.setText(UID);
                nameText.setText(name);
                String diab=snapshot.child("users").child(UID).child("diabetic").getValue(String.class);
                String veg=snapshot.child("users").child(UID).child("veg").getValue(String.class);
                prefText.setText(diab+"/"+veg);
               /*
                if(diab=="no")
                    diab="Non Diabetic";
                else
                    diab="Diabetic";

                if(veg=="no")
                    veg="Non Vegetarian";
                else
                    veg="Vegetarian";
                */
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


      /* Firebase.setAndroidContext(this);

        String DeviceID = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        myFirebase= new Firebase("https://absolute-accord-283510.firebaseio.com/Users"+ DeviceID);

        myApplyBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myKeyValueData= myKeyValue.getText().toString();
                myStringData= myEditText.getText().toString();
                Firebase myNewChild= myFirebase.child(myKeyValueData);
                myNewChild.setValue(myStringData);
                Toast.makeText(WelcomeActivity.this,  myStringData + " updated with " + myKeyValueData, Toast.LENGTH_SHORT).show();
            }
        });
*/



    public void openActivity2() {
        Intent intent = new Intent(this, MainButtons.class);
        startActivity(intent);
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
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}