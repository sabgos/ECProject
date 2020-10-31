package com.ElderCare.ElderCareFD;

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

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.example.FoodSearchQuery;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Menus extends AppCompatActivity implements RecyclerViewAdapter3.ItemClickListener {
    private static final String TAG = "Menus";
    RecyclerViewAdapter3 adapter;
    public ArrayList<String> itemsName = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ArrayList<String> itemsName = new ArrayList<>();
        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.showItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapter3(this, itemsName);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        //String BASE_URL = "http://167.71.232.133/graphql";
       // OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

      //  ApolloClient apolloClient = ApolloClient.builder().serverUrl(BASE_URL).okHttpClient(okHttpClient).build();



        ApolloConnector.setupApollo().query(FoodSearchQuery.builder().build()).enqueue(new ApolloCall.Callback<FoodSearchQuery.Data>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(@NotNull Response<FoodSearchQuery.Data> response) {
                List<FoodSearchQuery.FoodmenuSearch> foodmenuSearches = response.getData().foodmenuSearch();
                for (FoodSearchQuery.FoodmenuSearch foodmenuSearch : foodmenuSearches) {
                    Log.e(TAG, "onResponse: " + foodmenuSearch );
                    itemsName.add(foodmenuSearch.name()+"  "+foodmenuSearch.nonVegFlag()+"  "+foodmenuSearch.diabeticFlag());
                }
                runOnUiThread(() -> adapter.notifyDataSetChanged());
            }
            @Override
            public void onFailure(@NotNull ApolloException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        });


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
                startActivity(new Intent(Menus.this, MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}