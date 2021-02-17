package com.safero.fellsafe.fragmentspackage;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.safero.fellsafe.Allareacrim;
import com.safero.fellsafe.R;
import com.safero.fellsafe.Savednumbers;
import com.safero.fellsafe.datastorageclasses.Closecontactdetails;
import com.safero.fellsafe.datastorageclasses.Recycleradapter;
import com.safero.fellsafe.datastorageclasses.Usersdata;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Contactfragment extends Fragment implements Recycleradapter.foretell
{

    private Context context;
private String loca;
ArrayList<Usersdata> arrayList;
Recycleradapter.foretell foretell=null;

    public Contactfragment(Context cont,String location){
        this.context = cont;
        arrayList = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

       View view = inflater.inflate(R.layout.showcontactfragment,container,false);
        RecyclerView recyclerView = view.findViewById(R.id.recc);

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String url = "https://safetyapiforw.herokuapp.com/apifor/users/findallclosecontacts";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("usernumber", Savednumbers.getInstance().getnumber(context));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                Toast.makeText(context,response.toString(),Toast.LENGTH_LONG).show();

                try {
                   JSONArray jsonObject1 = response.getJSONArray("contacts");

                   for(int i=0;i<jsonObject1.length();i++){

                       JSONObject jsonObject2 = jsonObject1.getJSONObject(i).getJSONObject("closecontact");

                       String number = jsonObject2.getString("number");
                       String proffes = jsonObject2.getString("profession");
                       String token = jsonObject2.getString("fcmtoken");

                       Closecontactdetails closecontactdetails = new Closecontactdetails(number,token,proffes);

                       arrayList.add(closecontactdetails);

                   }

                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    recyclerView.setAdapter(new Recycleradapter(arrayList,context,foretell,loca));

                } catch (JSONException e) {
                    e.printStackTrace();
                }


//

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"error",Toast.LENGTH_LONG).show();

            }
        });

        requestQueue.add(jsonObjectRequest);





        return view;
    }

    @Override
    public void callstart(int i, String location) {

    }
}
