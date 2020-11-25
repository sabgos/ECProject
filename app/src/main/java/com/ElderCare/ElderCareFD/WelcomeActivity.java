package com.ElderCare.ElderCareFD;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.example.RegisterQuery;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.chip.Chip;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class WelcomeActivity extends AppCompatActivity {

    private Button button;
    FirebaseAuth firebaseAuth;

    Button myApplyBt;
    EditText myEditText, myKeyValue;
    TextView nameText;//,IDText,prefText;
    Chip hasDiabetic, mealPreference;
    MaterialCardView cvMenu, cvNewOrder, cvPreOrder, cvWhatsApp;
    String myStringData, myKeyValueData, name, UID, myUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

//        button = (Button) findViewById(R.id.button8);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openActivity2();
//            }
//        });

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
        nameText = (TextView) findViewById(R.id.userName);
        hasDiabetic = findViewById(R.id.diabeticChip);
        mealPreference = findViewById(R.id.vegetarianChip);

        cvMenu = findViewById(R.id.cvMenu);
        cvNewOrder = findViewById(R.id.cvNewOrder);
        cvPreOrder = findViewById(R.id.cvPreOrder);
        cvWhatsApp = findViewById(R.id.cvWhatsApp);

        ApolloConnector.setupApollo().query(RegisterQuery.builder().build()).enqueue(new ApolloCall.Callback<RegisterQuery.Data>() {
            private static final String TAG = "Welcome";

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


        cvWhatsApp.setOnClickListener(v -> {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,"Text sent");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        });
        cvMenu.setOnClickListener(v -> {
            Intent intent = new Intent(this, Menus.class);
            startActivity(intent);
        });

        cvNewOrder.setOnClickListener(v -> {
            Intent intent = new Intent(this, SelectPersons.class);
            startActivity(intent);
        });
        cvPreOrder.setOnClickListener(v -> {
            Intent intent = new Intent(this, PreorderDates.class);
            startActivity(intent);
        });


        //  IDText= (TextView) findViewById(R.id.textViewID);
        //prefText=(TextView) findViewById(R.id.textView21);
        // Firebase mRef=new Firebase("https://absolute-accord-283510.firebaseio.com/");

        //Getting Firebase Instance

        //Getting Reference to Root Node
        /*
        DatabaseReference myRef = database.getReference();
        myRef.child("users").child(UID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name = snapshot.child("username").getValue(String.class);
                //IDText.setText(UID);
                nameText.setText(name);
                String hasDiab = snapshot.child("diabetic").getValue(String.class);
                String isVeg = snapshot.child("veg").getValue(String.class);
                if(isVeg != null && isVeg.startsWith("Non"))
                    mealPreference.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(WelcomeActivity.this, R.color.nonVegetarianColor)));
                else
                    mealPreference.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(WelcomeActivity.this, R.color.vegetarianColor)));

                hasDiabetic.setText(hasDiab);
                mealPreference.setText(isVeg);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

         */


//        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                name = snapshot.child("users").child(UID).child("username").getValue(String.class);
//                //IDText.setText(UID);
//                nameText.setText(name);
//                String hasDiab = snapshot.child("users").child(UID).child("diabetic").getValue(String.class);
//                String isVeg = snapshot.child("users").child(UID).child("veg").getValue(String.class);
//
//                hasDiabetic.setText(hasDiab);
//                mealPreference.setText(isVeg);
//
//
//                //  prefText.setText(diab+"/"+veg);
//               /*
//                if(diab=="no")
//                    diab="Non Diabetic";
//                else
//                    diab="Diabetic";
//
//                if(veg=="no")
//                    veg="Non Vegetarian";
//                else
//                    veg="Vegetarian";
//                */
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
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


//    public void openActivity2() {
//        Intent intent = new Intent(this, MainButtons.class);
//        startActivity(intent);
//    }

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
            case R.id.help:
                startActivity(new Intent(this, Help.class));
                return true;
            case R.id.custSupport:
                startActivity(new Intent(this, CustomerSupport.class));
                return true;
            case R.id.logout:
                Utils.logout(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}