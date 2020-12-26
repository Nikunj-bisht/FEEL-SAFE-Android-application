package com.safero.fellsafe.videospackage;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.safero.fellsafe.R;

public class Vidoviewholder extends RecyclerView.ViewHolder {

    VideoView videoView;
    ProgressBar progressBar;

    public Vidoviewholder(@NonNull View itemView) {
        super(itemView);

        videoView = itemView.findViewById(R.id.videov);
        progressBar = itemView.findViewById(R.id.prog);


    }

    public VideoView getVideoView() {
        return videoView;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }
}
