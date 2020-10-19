package com.ElderCare.ElderCareFD;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.example.FoodSearchQuery;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import okhttp3.OkHttpClient;

public class Menu extends AppCompatActivity implements RecyclerViewAdapter3.ItemClickListener {

    RecyclerViewAdapter3 adapter;
    String TAG = "Hello menu";
    public ArrayList<String> itemsName = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // data to populate the RecyclerView with

        //itemsName.add("AAA");

        //String BASE_URL = "http://167.71.232.133/graphql";
       // OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

      //  ApolloClient apolloClient = ApolloClient.builder().serverUrl(BASE_URL).okHttpClient(okHttpClient).build();

        GetMenu.showMenu();

        Log.i(TAG + "3length=", String.valueOf(itemsName.size()));
        for(int i=0;i<itemsName.size();i++)
         Log.i(TAG + "3item=", String.valueOf(itemsName.get(i)));


// set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.showItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapter3(this, itemsName);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);



    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
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
                startActivity(new Intent(Menu.this, MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}