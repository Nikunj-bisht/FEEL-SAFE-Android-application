package com.safero.fellsafe.videospackage;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.safero.fellsafe.R;

import java.util.ArrayList;

public class Videoadaptor  extends RecyclerView.Adapter<Vidoviewholder> {

    Context context;
    ArrayList<String> arrayList;


    public Videoadaptor(Context context , ArrayList<String> arrayList1){

        this.context = context;
        this.arrayList = arrayList1;
    }

    @NonNull
    @Override
    public Vidoviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.layoutforvideos,parent,false);

        Vidoviewholder vidoviewholder = new Vidoviewholder(view);


        return  vidoviewholder;


    }

    @Override
    public void onBindViewHolder(@NonNull final Vidoviewholder holder, int position) {

        holder.getVideoView().setVideoURI(Uri.parse(arrayList.get(position)));
        holder.getVideoView().setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
                holder.getVideoView().start();

//                float videoratio = mediaPlayer.getVideoWidth()/ mediaPlayer.getVideoHeight();
//               float screenratio = holder.getVideoView().getWidth() / holder.getVideoView().getHeight();
//               float scale = videoratio/screenratio;
//               if(scale>=1f){
//
//                   holder.getVideoView().setScaleX(scale);
//
//               }else{
//                   holder.getVideoView().setScaleY(1f/scale);
//
//               }

            }
        });
        holder.getVideoView().setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
                holder.getVideoView().start();

            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
