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
    Button bu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startactivity);

        button = findViewById(R.id.button);
        editText = findViewById(R.id.num);
        editText1 = findViewById(R.id.email);
        editText2 = findViewById(R.id.pass);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
button.setVisibility(View.INVISIBLE);
findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
                final String val = editText.getText().toString();
                if(!val.equals("")){


String url = "https://safetyapiforw.herokuapp.com/auth/login";
                    RequestQueue requestQueue = Volley.newRequestQueue(Startactivity.this);

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                           // Toast.makeText(Startactivity.this,"Sent",Toast.LENGTH_LONG).show();
                            findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);

findViewById(R.id.donn).setVisibility(View.VISIBLE);
if(response.equals("success")) {
    new Thread() {

        public void run() {

            try {

                Savednumbers.getInstance().savenumbers(Startactivity.this,val);


                Thread.sleep(3000);

                Intent intent = new Intent(Startactivity.this, Loadingact.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }.start();
}else{
    Toast.makeText(Startactivity.this,"Try again",Toast.LENGTH_LONG).show();
    button.setVisibility(View.VISIBLE);

}

                            }

                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);

                            Toast.makeText(Startactivity.this,"Server error try again",Toast.LENGTH_LONG).show();
                            button.setVisibility(View.VISIBLE);

                            //error.printStackTrace();
                        }
                    }){
                        @Override
                        protected Map<String,String> getParams(){
                            HashMap<String,String> map = new HashMap<>();
                            map.put("userid",editText1.getText().toString());
                            map.put("pass",editText2.getText().toString());
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