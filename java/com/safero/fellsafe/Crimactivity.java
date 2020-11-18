package com.safero.fellsafe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

public class Crimactivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crimactivity);

String[] val = getIntent().getStringExtra("location").split(" ");

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url="https://data.police.uk/api/stops-street?lat="+val[0]+"&lng="+val[1]+"&date=2020-06";
        //
        final String number = Savednumbers.getInstance().getnumber(this);
        final String message = Savednumbers.getInstance().getmessage(this);
        JsonArrayRequest stringRequest = new JsonArrayRequest(Request.Method.GET, url,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Toast.makeText(Crimactivity.this,"Sent",Toast.LENGTH_LONG).show();

                TextView textView=findViewById(R.id.textView5);
                textView.setText(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Crimactivity.this,"Server error",Toast.LENGTH_LONG).show();
//
            }
        });
        requestQueue.add(stringRequest);



    }
}