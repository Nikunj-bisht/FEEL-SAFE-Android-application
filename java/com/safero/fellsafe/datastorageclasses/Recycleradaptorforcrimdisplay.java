package com.safero.fellsafe.datastorageclasses;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.safero.fellsafe.R;

import java.util.ArrayList;

public class Recycleradaptorforcrimdisplay extends RecyclerView.Adapter<Viewholderforcrim> {


    Context context;

    ArrayList<Crimstorageclass> arrayList ;


    public Recycleradaptorforcrimdisplay(Context context , ArrayList<Crimstorageclass> arrayList1){
        this.context = context;
        this.arrayList = arrayList1;
    }

    @NonNull
    @Override
    public Viewholderforcrim onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(R.layout.crimcustomview,parent,false);
        Viewholderforcrim viewholderforcrim = new Viewholderforcrim(view);


        return viewholderforcrim;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholderforcrim holder, int position) {

        holder.getTextView().setText(arrayList.get(position).getName());
        holder.getGetTextView().setText(arrayList.get(position).getCrimlocation());
        holder.getGetGetTextView().setText(arrayList.get(position).getDisciption());
        Fetchimageofcriminal fetchimageofcriminal = new Fetchimageofcriminal(holder.getImageView(),arrayList.get(position).getImageurl());
        fetchimageofcriminal.start();

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class Fetchimageofcriminal extends  Thread{

        ImageView imageView;
        String im;

        public Fetchimageofcriminal(ImageView imageView , String image){

            this.imageView = imageView;
            this.im  = image;


        }

        @Override
        public void run() {

            RequestQueue requestQueue = Volley.newRequestQueue(context);


            ImageRequest imageRequest = new ImageRequest("https://safetyapiforw.herokuapp.com/crimimg/" + im, new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap response) {
                    imageView.setImageBitmap(response);
                }
            }, 0, 0, null, null, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "cant fetch"+im, Toast.LENGTH_SHORT).show();
                }
            });
           requestQueue.add(imageRequest) ;

        }
    }


}
