package com.safero.fellsafe.ui.login;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.safero.fellsafe.Loadingact;
import com.safero.fellsafe.R;
import com.safero.fellsafe.Startactivity;
import com.safero.fellsafe.ui.login.LoginViewModel;
import com.safero.fellsafe.ui.login.LoginViewModelFactory;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingProgressBar.setVisibility(View.VISIBLE);

                String url = "https://safetyapiforw.herokuapp.com/check/loggedin";
                RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Toast.makeText(Startactivity.this,"Sent",Toast.LENGTH_LONG).show();

                        if(response.equals("success")) {
                            new Thread() {

                                public void run() {

                                    try {
                                        Thread.sleep(3000);
                                        Intent intent = new Intent(LoginActivity.this, Loadingact.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }

                            }.start();
                        }else{

                            Toast.makeText(LoginActivity.this,"Try again",Toast.LENGTH_LONG).show();
                            loadingProgressBar.setVisibility(View.GONE);

                        }

                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this,"Server error try again",Toast.LENGTH_LONG).show();
                        error.printStackTrace();
                    }
                }){
                    @Override
                    protected Map<String,String> getParams(){
                        HashMap<String,String> map = new HashMap<>();
                        map.put("userid",usernameEditText.getText().toString());
                        map.put("pass",passwordEditText.getText().toString());
                        return map;
                    }
                    @Override
                    public Map<String,String> getHeaders() throws AuthFailureError {
                        HashMap<String,String> map = new HashMap<>();
                        map.put("Content-Type","application/x-www-form-urlencoded");
                        return map;
                    }
                };


                requestQueue.add(stringRequest);




            }
        });


    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}