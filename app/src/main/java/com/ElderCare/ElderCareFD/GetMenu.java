package com.ElderCare.ElderCareFD;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.example.FoodSearchQuery;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import static android.content.ContentValues.TAG;

public class GetMenu extends Menu{
    public static void showMenu() {
        ApolloConnector.setupApollo().query(FoodSearchQuery.builder().build()).enqueue(new ApolloCall.Callback<FoodSearchQuery.Data>() {
            public String TAG="Menu";

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(@NotNull Response<FoodSearchQuery.Data> response) {
                Log.i(TAG, response.toString());
                AtomicInteger i = new AtomicInteger(1);
                response.data().foodmenuSearch().stream().forEach(item -> {
                    Log.i(TAG, String.valueOf(i.get()));
                    Log.i(TAG, item.name());

                    //Menu..add(item.name());
                   // Log.i(TAG + "2length=", String.valueOf(itemsName.size()));
                    //Log.i(TAG + "2item=", String.valueOf(itemsName.get(itemsName.size() - 1)));

                    i.set(i.get() + 1);
                });
            }

            @Override
            public void onFailure(@NotNull ApolloException e) {
                Log.e(TAG, e.getMessage(), e);

            }


        });
    }
}