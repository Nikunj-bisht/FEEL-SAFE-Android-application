package com.safero.fellsafe.datastorageclasses;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.biometrics.BiometricPrompt;
import android.net.Uri;
import android.os.Build;
import android.os.CancellationSignal;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.safero.fellsafe.R;
import com.safero.fellsafe.Savednumbers;
import com.safero.fellsafe.requestclasses.Anothertokensender;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Recycleradapter extends RecyclerView.Adapter<Customview> {

    ArrayList<Usersdata> arrayList ;
    Context context;
foretell formethod;
String loc;

  public   interface foretell {

        void callstart(int i,String location);

    }


    public Recycleradapter(ArrayList<Usersdata> arrayL , Context context , foretell formt , String location){

        this.context = context;
        this.arrayList = arrayL;
this.formethod = formt;

  this.loc = location;
  }

    @NonNull
    @Override
    public Customview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(R.layout.singleuserview,parent,false);

        Customview customview = new Customview(view);

        return customview;
    }

    @Override
    public void onBindViewHolder(@NonNull Customview holder, final int position) {

      holder.getView().setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
if(formethod!=null){

    formethod.callstart(position,loc);

}
          }
      });

        holder.getTextView().setText(arrayList.get(position).getName());
        holder.getTextView1().setText(arrayList.get(position).getNumber());
        holder.getTextView2().setText(arrayList.get(position).getProfession());

           holder.getButton().setOnClickListener(new View.OnClickListener() {
               @RequiresApi(api = Build.VERSION_CODES.P)
               @Override
               public void onClick(View view) {
                   Executor executor = Executors.newSingleThreadExecutor();

                   BiometricPrompt biometricPrompt = new BiometricPrompt.Builder(context)    // Biometric just for safety purposes
                           .setTitle("Please verify first")
                           .setSubtitle("Go fast")
                           .setNegativeButton("NO", executor, new DialogInterface.OnClickListener() {
                               @Override
                               public void onClick(DialogInterface dialogInterface, int i) {



                               }
                           }).build();

                   biometricPrompt.authenticate(new CancellationSignal(), executor, new BiometricPrompt.AuthenticationCallback() {
                       @Override
                       public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                           super.onAuthenticationSucceeded(result);
                           Sendmessae sendmessae = new Sendmessae(arrayList.get(position).getToken());
                           sendmessae.start();

                       }
                   });



               }
           });
holder.getButt().setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:"+arrayList.get(position).getNumber()));
        context.startActivity(intent);

    }
});

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class Sendmessae extends Thread{

        String receivertoken;

        public Sendmessae(String token){
            this.receivertoken = token;
        }

        @Override
        public void run() {



            final String url = "https://fcm.googleapis.com/fcm/send";



            RequestQueue requestQueue = Volley.newRequestQueue(context);

            JSONObject notificationdata = new JSONObject();

            JSONObject map  = new JSONObject();
            try {

                notificationdata.put("title","Its an emergency help me i am here");
                notificationdata.put("message",loc);
                notificationdata.put("number", Savednumbers.getInstance().getnumber(context));
                notificationdata.put("type","single");

                map.put("to",receivertoken);
                map.put("data",notificationdata);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, map, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    Toast.makeText(context,"Sent success" , Toast.LENGTH_SHORT).show();
//fort.callstart();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(context,"Problem",Toast.LENGTH_LONG).show();

                }

            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {

                    Map<String,String> maps = new HashMap<>();
                    maps.put("Authorization","key=AAAAWzNQBq4:APA91bFKMlMzzeEpn9HnEbiAjEylt3MPxi-fwmzB15-nbP0ROf3IZI8pFoxfeb7CLmnuLjRlvM6SAR11eKwnLkABEKjWbxz70upCvLEo9XKF7GbPJtDk9Dcl4csF7mycJyNpPvi5IxtB");
                    maps.put("Content-Type","application/json");

                    return maps;
                }
            };





            requestQueue.add(jsonObjectRequest);




        }
    }

}
