package com.example.project1;

import androidx.annotation.NonNull;
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
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.example.FoodSearchQuery;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;

public class Activity4 extends AppCompatActivity {
    TextView textView;
    FirebaseAuth firebaseAuth;
    final String TAG = "Hello menu";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_4);
        //getFoodmenuSearch();


        String BASE_URL = "http://167.71.232.133/graphiql";
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        ApolloClient apolloClient = ApolloClient.builder().serverUrl(BASE_URL).okHttpClient(okHttpClient).build();

        apolloClient.query(FoodSearchQuery.builder().build()).enqueue(new ApolloCall.Callback<FoodSearchQuery.Data>() {
            @Override
            public void onResponse(@NotNull Response<FoodSearchQuery.Data> response) {
                Log.i(TAG, response.toString());
            }

            @Override
            public void onFailure(@NotNull ApolloException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        });
        /*
        apolloClient.query(FoodSearchQuery.builder().build())
                .enqueue(new ApolloCall.Callback<FoodSearchQuery.Data>()
                         {
                             @RequiresApi(api = Build.VERSION_CODES.N)
                             @Override
                             public  void  onResponse(@NotNull Response<FoodSearchQuery.Data> response) {
                                 response.data().foodmenuSearch().stream().forEach(item -> {
                                     Log.i("id", item.id().toString());
                                     Log.i("description", item.description());
                                 });
                             }
                             @Override
                             public  void  onFailure(@NotNull ApolloException e) {
                                 Log.e(TAG, "failed:"+e.getLocalizedMessage());
                             }
                         });
*/

/*
        // First, create an `ApolloClient`
// Replace the serverUrl with your GraphQL endpoint
        ApolloClient apolloClient = ApolloClient.builder()
                .serverUrl("http://167.71.232.133/graphiql")
                .build();

// Then enqueue your query
        apolloClient.query(new FoodSearchQuery())
                .enqueue(new ApolloCall.Callback<FoodSearchQuery.Data>() {
                    @Override
                    public void onResponse(@NotNull Response<FoodSearchQuery.Data> response) {
                        Log.e("Apollo", "Launch site: " + response.getData().toString());
                    }

                    @Override
                    public void onFailure(@NotNull ApolloException e) {
                        Log.e("Apollo", "Error", e);
                    }
                });
*/

        /*
// Access a Cloud Firestore instance from your Activity
        FirebaseFirestore db = FirebaseFirestore.getInstance();

     DocumentReference mDocRef = FirebaseFirestore.getInstance().document("sampleData/inspiration");
        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("first", "Ada");
        user.put("last", "Lovelace");
        user.put("born", 1815);
        mDocRef.set(user);

// Add a new document with a generated ID
        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });


        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

*/
                textView = findViewById(R.id.textback);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

/*
    private void getFoodmenuSearch(){
        ApolloConnector.setupApollo().query(
                FoodSearchQuery
                        .builder()
                        .build())
                .enqueue(new ApolloCall.Callback<FoodSearchQuery.Data>() {
                    @Override
                    public void onResponse(@NotNull Response<FoodSearchQuery.Data> response) {

                        Log.d(TAG, "Response: " + response.data().foodmenuSearch());
                    }

                    @Override
                    public void onFailure(@NotNull ApolloException e) {

                        Log.d(TAG, "Exception: " + e.getMessage(), e);
                    }
                });
    }

 */


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
            case R.id.logout:
                firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.signOut();
                startActivity(new Intent(Activity4.this, MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}