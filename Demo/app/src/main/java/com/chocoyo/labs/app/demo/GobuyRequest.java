package com.chocoyo.labs.app.demo;

import android.util.Log;

import com.chocoyo.labs.app.demo.models.CategoryModel;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Path;

/**
 * Created by Administrador on 10/12/2016.
 */

public class GobuyRequest {

    public final static String SERVER_URL = "http://maurel19.pythonanywhere.com";

    public interface GobuyInterface{

        //Categorias
        @GET("/market/api/categoria/")
        //@Headers("Authorization: Token fc1ea4c8bb4b1b17e210e0164f818cb6110e56e1")
        void loadCategoriaAsync(Callback<ArrayList<CategoryModel>> callback);
    }


    public static void getCategoriaAsync(Callback<ArrayList<CategoryModel>> callback){
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(SERVER_URL).build();
        GobuyInterface gobuyInterface = adapter.create(GobuyInterface.class);
        gobuyInterface.loadCategoriaAsync(callback);
    }

}