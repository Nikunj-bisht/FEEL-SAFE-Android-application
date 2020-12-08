package com.safero.fellsafe.datastorageclasses;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.safero.fellsafe.R;
import com.safero.fellsafe.requestclasses.Anothertokensender;
import com.safero.fellsafe.requestclasses.Requestclass;

import java.util.ArrayList;

public class Recycleradapter extends RecyclerView.Adapter<Customview> {

    ArrayList<Usersdata> arrayList ;
    Context context;

    public Recycleradapter(ArrayList<Usersdata> arrayL , Context context){

        this.context = context;
        this.arrayList = arrayL;

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

        holder.getTextView().setText(arrayList.get(position).getName());
        holder.getTextView1().setText(arrayList.get(position).getNumber());
        holder.getTextView2().setText(arrayList.get(position).getProfession());

           holder.getButton().setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {

                   Sendmessae sendmessae = new Sendmessae(arrayList.get(position).getToken());
sendmessae.start();
//                   Intent intent = new Intent(Intent.ACTION_CALL);
//                   intent.setData(Uri.parse("tel:"+arrayList.get(position).getNumber()));
//                   context.startActivity(intent);

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

            Anothertokensender.sendreq(context,receivertoken);



        }
    }

}
