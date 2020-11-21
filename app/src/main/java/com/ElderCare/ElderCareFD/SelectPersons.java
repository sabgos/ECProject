package com.ElderCare.ElderCareFD;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.example.SelectDatesMutation;
import com.example.SelectPersonsMutation;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class SelectPersons extends AppCompatActivity implements RecyclerViewAdapter2.ItemClickListener {

    RecyclerViewAdapter2 adapter2;
    Button addButton;
    ArrayList<String> personNames;
    int num=1;
    FirebaseAuth firebaseAuth;


    private Button button1,button2;
    TextView textView;
    private static final String TAG = "Persons Selected";

    @SuppressLint("RestrictedApi")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_persons);
        textView = findViewById(R.id.textback);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        personNames = new ArrayList<>();
        personNames.add("1");

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.selectPerson);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));


        adapter2 = new RecyclerViewAdapter2(this, personNames);
        adapter2.setClickListener(this);
        recyclerView.setAdapter(adapter2);

        addButton= (Button) findViewById(R.id.buttonAddMore2);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRow();
            }
        });

        button2 = (Button) findViewById(R.id.button12);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity9();
            }
        });
    }
    @Override
    public void onItemClick(View view, int position) {
        //Toast.makeText(this, "You clicked " + adapter2.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
        adapter2.notifyDataSetChanged();

    }

    private void addRow() {
        num=num+1;
        personNames.add(Integer.toString(num));
        adapter2.notifyDataSetChanged();

    }

    public void openActivity9() {
        ApolloConnector.setupApollo().mutate(SelectPersonsMutation.builder().build()).enqueue(new ApolloCall.Callback<SelectPersonsMutation.Data>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(@NotNull Response<SelectPersonsMutation.Data> response) {
                Log.e(TAG, "onResponse: " + response.toString() );
                //   runOnUiThread(() -> adapter.notifyDataSetChanged());
            }
            @Override
            public void onFailure(@NotNull ApolloException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        });
        Intent intent = new Intent(this, OrderSummary.class);
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
                startActivity(new Intent(this, About1.class));
                return true;
            case R.id.about2:
                startActivity(new Intent(this, About2.class));
                return true;
            case R.id.help:
                startActivity(new Intent(this, Help.class));
            case R.id.logout:
                firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.signOut();
                startActivity(new Intent(SelectPersons.this, MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}