package com.ElderCare.ElderCareFD.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import android.content.Intent;
import android.view.Menu;
import android.widget.Toast;

import com.ElderCare.ElderCareFD.DB.ApolloConnector;
import com.ElderCare.ElderCareFD.R;
import com.ElderCare.ElderCareFD.adapter.RecyclerViewAdapterOneDates;
import com.ElderCare.ElderCareFD.utility.Utils;
import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.example.SelectDatesMutation;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class PreorderDates extends AppCompatActivity implements RecyclerViewAdapterOneDates.ItemClickListener {

    FirebaseAuth firebaseAuth;

    TextView textView;
    Button btnDatePicker;
    EditText txtDate;
    private Button button;
    private int mYear, mMonth, mDay;
    RecyclerViewAdapterOneDates adapter;
    Button addButton;
    ArrayList<String> dates;
    private static final String TAG = "Date Selected";

    @SuppressLint("RestrictedApi")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preorder_dates);
        textView = findViewById(R.id.textback);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        button = (Button) findViewById(R.id.button9);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dates.get(0).equals(""))
                    Toast.makeText(getApplicationContext(), "Please select a date!", Toast.LENGTH_SHORT).show();
                else
                    openActivity7();
            }
        });

        dates = new ArrayList<>();
        dates.add("");


        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.selectDates);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        adapter = new RecyclerViewAdapterOneDates(this, dates);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        addButton= (Button) findViewById(R.id.buttonAddMore1);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRow();
            }
        });

    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
       // adapter.notifyItemInserted(position);
        //adapter.notifyDataSetChanged();

    }

    private void addRow() {
        if(dates.get(dates.size() -1).equals(""))
            Toast.makeText(getApplicationContext(), "Please select a date!", Toast.LENGTH_SHORT).show();
        else {
            dates.add("");
            adapter.notifyItemInserted(dates.size());
            //adapter.notifyDataSetChanged();
        }

    }
    public void openActivity7() {

        ApolloConnector.setupApollo().mutate(SelectDatesMutation.builder().build()).enqueue(new ApolloCall.Callback<SelectDatesMutation.Data>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(@NotNull Response<SelectDatesMutation.Data> response) {
                    Log.e(TAG, "onResponse: " + response.toString() );
             //   runOnUiThread(() -> adapter.notifyDataSetChanged());
            }
            @Override
            public void onFailure(@NotNull ApolloException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        });

        Intent intent = new Intent(this, SelectPersons.class);
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
//                startActivity(new Intent(PreorderDates.this, MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}