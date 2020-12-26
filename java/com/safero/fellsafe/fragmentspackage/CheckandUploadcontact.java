package com.safero.fellsafe.fragmentspackage;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.safero.fellsafe.R;
import com.safero.fellsafe.Savednumbers;

import org.json.JSONException;
import org.json.JSONObject;

public class CheckandUploadcontact extends Fragment implements View.OnClickListener {

    public Context context;

public CheckandUploadcontact(Context context){
    this.context = context;
}
    EditText editText;
    ProgressBar progressBar;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

View view = inflater.inflate(R.layout.addcontactfragment,container,false);


         editText = view.findViewById(R.id.editTextPhone2);
        Button button = view.findViewById(R.id.button6);
        progressBar = view.findViewById(R.id.progressBar3);

button.setOnClickListener(this);




        return view;
    }

    @Override
    public void onClick(View view){

        switch (view.getId()){

            case R.id.button6:
progressBar.setVisibility(View.VISIBLE);
                String url = "https://safetyapiforw.herokuapp.com/apifor/users/checkforclosecontact";

                RequestQueue requestQueue = Volley.newRequestQueue(context);

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("num",editText.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.POST,url,jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressBar.setVisibility(View.INVISIBLE);

                        Toast.makeText(context,response.toString(),Toast.LENGTH_LONG).show();


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(context,error.toString(),Toast.LENGTH_LONG).show();

                    }
                });


                requestQueue.add(jsonArrayRequest);

                break;


        }


    }
}
