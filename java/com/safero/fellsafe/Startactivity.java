package com.safero.fellsafe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.safero.fellsafe.ui.login.LoginActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Startactivity extends AppCompatActivity {


    Button button;
    EditText editText;
    EditText editText1;
    EditText editText2;

    EditText editText3;
    EditText editText4;
    EditText editText5;
    EditText editText6;
    Button bu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startactivity);




        button = findViewById(R.id.button);
        editText = findViewById(R.id.editTextTextPersonName);
        editText1 = findViewById(R.id.editTextTextPassword);
        editText2 = findViewById(R.id.editTextPhone);

        editText3 = findViewById(R.id.editTextTextPersonName2);
        editText4 = findViewById(R.id.editTextTextPersonName3);
        editText5 = findViewById(R.id.editTextTextEmailAddress);
        editText6 = findViewById(R.id.editTextTextPersonName4);



final String token = Savednumbers.getInstance().returntoken(Startactivity.this);



        button.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
button.setVisibility(View.INVISIBLE);

//builder.setView(R.layout.alertdialog);


                final String name = editText.getText().toString();

                final String password = editText1.getText().toString();
                final String number = editText2.getText().toString();
                final String loca1 = editText3.getText().toString();
                final String loca2 = editText4.getText().toString();
                final String email = editText5.getText().toString();
                final String prof = editText6.getText().toString();
                if(!name.equals("") && !password.equals("")
                        && !number.equals("")
                        && !loca1.equals("")
                        && !loca2.equals("")
                        && !email.equals("") && !prof.equals("")  ){



String url = "https://safetyapiforw.herokuapp.com/apifor/users";
                    RequestQueue requestQueue = Volley.newRequestQueue(Startactivity.this);

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                           // Toast.makeText(Startactivity.this,"Sent",Toast.LENGTH_LONG).show();

                            AlertDialog.Builder builder = new AlertDialog.Builder(Startactivity.this);

if(response.equals("success")) {

            try {
                builder.setTitle("Account created successfully");
                builder.setView(R.layout.alertdialog);


                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Startactivity.this, Loadingact.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

                Savednumbers.getInstance().savenumbers(Startactivity.this,number);

            } catch (Exception e) {
                e.printStackTrace();
            }



}else{

    builder.setTitle("Error please try again!");
    builder.setView(R.layout.alertdialog);

    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {

            dialogInterface.cancel();
        }
    });
    AlertDialog dialog = builder.create();
    dialog.show();

    Toast.makeText(Startactivity.this,"Try again",Toast.LENGTH_LONG).show();
    button.setVisibility(View.VISIBLE);

}

                            }

                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toast.makeText(Startactivity.this,"Server error try again",Toast.LENGTH_LONG).show();
                            button.setVisibility(View.VISIBLE);

                            //error.printStackTrace();
                        }
                    }){
                        @Override
                        protected Map<String,String> getParams(){
                            HashMap<String,String> map = new HashMap<>();
                            map.put("nam",name);
                            map.put("pass",password);

                            map.put("num",number);
                            map.put("loc1",loca1);
                            map.put("loc2",loca2);
                            map.put("email",email);
                            map.put("prof",prof);
                            map.put("fcm",token);
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

                }else{
                    button.setVisibility(View.VISIBLE);

                }
            }
        });
    }
//


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.options,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.messag){

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Message");
            builder.setMessage("The message you will enter here will be sent along with location to your added contact you can enter it only once so type it carefully");

            final EditText editText = new EditText(this);
            editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
            builder.setView(editText);

            builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String messageval = editText.getText().toString();

                      if(!messageval.equals("")) {

                          Savednumbers.getInstance().savemessage(Startactivity.this, messageval);
                          Toast.makeText(Startactivity.this,"Message saved successfully",Toast.LENGTH_LONG).show();
dialogInterface.dismiss();
                      }else{
                          Toast.makeText(Startactivity.this,"You entered empty string so only location will send by default!",Toast.LENGTH_LONG).show();
dialogInterface.dismiss();
                      }
                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });

            AlertDialog build = builder.create();


            build.show();

        }else if(item.getItemId()==R.id.login){

Intent intent = new Intent(Startactivity.this, LoginActivity.class);
startActivity(intent);



        }


        return super.onOptionsItemSelected(item);
    }
}