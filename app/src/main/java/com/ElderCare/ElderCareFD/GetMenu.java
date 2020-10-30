package com.ElderCare.ElderCareFD;

import android.os.Build;
import android.util.Log;
import android.view.View;

import androidx.annotation.RequiresApi;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.example.FoodSearchQuery;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import static android.content.ContentValues.TAG;

public interface GetMenu{
    public static void showMenu() {
    }
}