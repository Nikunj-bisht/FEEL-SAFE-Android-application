package com.safero.fellsafe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.safero.fellsafe.datastorageclasses.Crimstorageclass;
import com.safero.fellsafe.datastorageclasses.Recycleradaptorforcrimdisplay;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Allareacrim extends AppCompatActivity {


     RecyclerView recyclerView;
ArrayList<Crimstorageclass> arrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allareacrim);

        recyclerView = findViewById(R.id.reco);
arrayList = new ArrayList<>();


        try {
            requestforcrim();
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void requestforcrim() throws JSONException {

        String url = "https://safetyapiforw.herokuapp.com/api/criminal/getcriminal";


        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("location","selaqui");


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    displayinrecyclerview(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {



            }
        });

        requestQueue.add(jsonObjectRequest);

    }

    private void displayinrecyclerview(JSONObject response)throws JSONException {

        JSONArray jsonArray = response.getJSONArray("crim");

           for(int i=0;i<jsonArray.length();i++){

JSONObject jsonObject = jsonArray.getJSONObject(i);

String name = jsonObject.getString("crimname");
               String cl = jsonObject.getString("criminallocation");
               String ci = jsonObject.getString("criimage");


Crimstorageclass crimstorageclass = new Crimstorageclass(name,"Did nothing till now xD",cl,ci);
arrayList.add(crimstorageclass);


           }

        recyclerView.setLayoutManager(new LinearLayoutManager(Allareacrim.this));

        recyclerView.setAdapter(new Recycleradaptorforcrimdisplay(Allareacrim.this,arrayList));


    }


}