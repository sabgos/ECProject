package com.ElderCare.ElderCareFD.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ElderCare.ElderCareFD.R;
import com.ElderCare.ElderCareFD.utility.Utils;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.android.gms.maps.GoogleMap;

import java.util.Arrays;
import java.util.List;


public class SelectAddress extends AppCompatActivity{
    TextView textView;
    Button button;
    FirebaseAuth firebaseAuth;
    String UID,addrs;
    EditText addrsText;
    private GoogleMap mMap;
    private static int AUTOCOMPLETE_REQUEST_CODE = 1;
    private static final String TAG = "ADDress";
    EditText places;
    TextView placesText1,placesText2;
    @SuppressLint("RestrictedApi")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_address);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        UID = user.getUid();

places=findViewById(R.id.places);
        placesText1=findViewById(R.id.placesText1);
        placesText2=findViewById(R.id.placesText2);

        String apiKey = getString(R.string.api_key);

        /**
         * Initialize Places. For simplicity, the API key is hard-coded. In a production
         * environment we recommend using a secure mechanism to manage API keys.
         */
      //  if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), apiKey);
        //}
        places.setFocusable(false);
    places.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS,Place.Field.LAT_LNG,Place.Field.NAME);
            Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fieldList).build(SelectAddress.this);
            startActivityForResult(intent,100);
        }
    });


// Create a new Places client instance.
    //    PlacesClient placesClient = Places.createClient(this);

/*
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {

            @Override
            public void onPlaceSelected(@NotNull Place place) {
                // TODO: Get info about the selected place.
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
            }


            @Override
            public void onError(@NotNull Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });


*/
        addrsText=(EditText) findViewById(R.id.addressText);

        textView = findViewById(R.id.textback);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        button = (Button) findViewById(R.id.button10);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity11();
            }
        });

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            private static final String TAG = "Hi";

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                addrs= snapshot.child("users").child(UID).child("address").getValue(String.class);
                addrsText.setText(addrs);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Button updateButton = (Button) findViewById(R.id.addressUpdate);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newAddr = addrsText.getText().toString();

                myRef.child("users").child(UID).child("address").setValue(newAddr);

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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
               // Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
            places.setText(place.getAddress());
            placesText1.setText(String.format("Locality: %s",place.getName()));
            placesText2.setText(String.valueOf(place.getLatLng()));
            }

            else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Toast.makeText(getApplicationContext(),status.getStatusMessage(),Toast.LENGTH_SHORT).show();
                //Log.i(TAG, status.getStatusMessage());
            }
    }

    public void openActivity11() {
        Intent intent = new Intent(this, FinalSummary.class);
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
                startActivity(new Intent(this, AboutBornToHelp.class));
                return true;
            case R.id.about2:
                startActivity(new Intent(this, AboutServicePlace.class));
                return true;
            case R.id.help:
                startActivity(new Intent(this, Help.class));
            case R.id.logout:
                Utils.logout(this);
//                firebaseAuth = FirebaseAuth.getInstance();
//                firebaseAuth.signOut();
//                startActivity(new Intent(SelectAddress.this, MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
