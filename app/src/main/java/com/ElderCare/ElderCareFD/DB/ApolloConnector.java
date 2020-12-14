package com.ElderCare.ElderCareFD.DB;

import com.apollographql.apollo.ApolloClient;

import okhttp3.OkHttpClient;

public class ApolloConnector {

    //private static final String BASE_URL = "http://167.71.232.133/graphql";
    private static final String BASE_URL = "http://192.168.111.112:8080/graphql";

    public static ApolloClient setupApollo(){

        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

        return ApolloClient.builder()
                .serverUrl(BASE_URL)
                .okHttpClient(okHttpClient)
                .build();
    }

}
