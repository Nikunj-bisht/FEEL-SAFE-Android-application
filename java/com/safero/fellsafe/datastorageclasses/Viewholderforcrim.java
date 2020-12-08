package com.safero.fellsafe.datastorageclasses;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.safero.fellsafe.R;

public class Viewholderforcrim extends RecyclerView.ViewHolder {


  private   ImageView imageView;
   private TextView textView;
   private TextView getTextView;
  private   TextView getGetTextView;


    public Viewholderforcrim(@NonNull View itemView) {
        super(itemView);

          imageView = itemView.findViewById(R.id.imageView3);
          textView = itemView.findViewById(R.id.textView5);
          getTextView = itemView.findViewById(R.id.textView17);
          getGetTextView = itemView.findViewById(R.id.textView18);


    }

    public ImageView getImageView() {
        return imageView;
    }

    public TextView getTextView() {
        return textView;
    }

    public TextView getGetTextView() {
        return getTextView;
    }

    public TextView getGetGetTextView() {
        return getGetTextView;
    }
}
