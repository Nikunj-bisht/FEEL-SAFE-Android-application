package com.safero.fellsafe.datastorageclasses;

import android.content.Context;
import android.content.DialogInterface;
import android.hardware.biometrics.BiometricPrompt;
import android.os.CancellationSignal;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.safero.fellsafe.R;
import com.safero.fellsafe.requestclasses.Anothertokensender;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Recycleradapter extends RecyclerView.Adapter<Customview> {

    ArrayList<Usersdata> arrayList ;
    Context context;
foretell formethod;
String loc;

  public   interface foretell {

        void callstart();

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

        holder.getTextView().setText(arrayList.get(position).getName());
        holder.getTextView1().setText(arrayList.get(position).getNumber());
        holder.getTextView2().setText(arrayList.get(position).getProfession());

           holder.getButton().setOnClickListener(new View.OnClickListener() {
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


            Anothertokensender.sendreq(context,receivertoken,formethod,loc);



        }
    }

}
