package com.ElderCare.ElderCareFD.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.ElderCare.ElderCareFD.DB.ApolloConnector;
import com.ElderCare.ElderCareFD.R;
import com.ElderCare.ElderCareFD.adapter.RecyclerViewAdapterThreeMenu;
import com.ElderCare.ElderCareFD.utility.Utils;
import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.example.FoodSearchQuery;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Menu extends AppCompatActivity implements RecyclerViewAdapterThreeMenu.ItemClickListener {

    RecyclerViewAdapterThreeMenu adapter;
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


        ArrayList<String> itemsName = new ArrayList<>();
       // ArrayList<String> itemsVeg = new ArrayList<>();
//        ArrayList<String> itemsDiab = new ArrayList<>();

        ApolloConnector.setupApollo().query(FoodSearchQuery.builder().build()).enqueue(new ApolloCall.Callback<FoodSearchQuery.Data>() {
            public String TAG="Hello : GetMenu";

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(@NotNull Response<FoodSearchQuery.Data> response) {
                Log.i(TAG, response.toString());
                AtomicInteger i = new AtomicInteger(1);
                response.data().foodmenuSearch().stream().forEach(item -> {
                    Log.i(TAG, String.valueOf(i.get()));
                    Log.i(TAG, item.name());

                    itemsName.add(item.name()+"  "+item.nonVegFlag()+"  "+item.diabeticFlag());
                    //itemsVeg.add(item.nonVegFlag());
                   // itemsDiab.add(item.diabeticFlag());

                    Log.i(TAG + "2length=", String.valueOf(itemsName.size()));
                     Log.i(TAG + "2item=", String.valueOf(itemsName.get(itemsName.size() - 1)));

                    i.set(i.get() + 1);
                });

            }
            @Override
            public void onFailure(@NotNull ApolloException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        });
        Log.i(TAG + "3length=", String.valueOf(itemsName.size()));
        for(int i=0;i<itemsName.size();i++)
         Log.i(TAG + "3item=", String.valueOf(itemsName.get(i)));


// set up the RecyclerView

        RecyclerView recyclerView = findViewById(R.id.showItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapterThreeMenu(this, itemsName);
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
                startActivity(new Intent(this, AboutBornToHelp.class));
                return true;
            case R.id.about2:
                startActivity(new Intent(this, AboutServicePlace.class));
                return true;
            case R.id.logout:
                Utils.logout(this);
                //startActivity(new Intent(Menu.this, MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}