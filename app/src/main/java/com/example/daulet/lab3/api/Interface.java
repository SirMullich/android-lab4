package com.example.daulet.lab3.api;

import com.example.daulet.lab3.models.Hero;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PUT;

/**
 * Created by daulet on 10/16/17.
 */

public interface Interface {
    @GET("api/jsonBlob/66d7f293-b222-11e7-96e9-3b4c3404a5bb")
    Call<List<Hero>> getHerosAPIList();

    @PUT("api/jsonBlob/66d7f293-b222-11e7-96e9-3b4c3404a5bb")
    Call<List<Hero>> createHeroesAPIBlob();

    @DELETE("/api/jsonBlob/66d7f293-b222-11e7-96e9-3b4c3404a5bb")
    Call deletePost();
}
