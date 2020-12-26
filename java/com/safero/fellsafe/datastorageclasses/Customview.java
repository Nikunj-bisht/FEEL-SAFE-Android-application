package com.safero.fellsafe.datastorageclasses;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.safero.fellsafe.R;


public class Customview extends RecyclerView.ViewHolder {


TextView textView;
    TextView textView1;

    TextView textView2;
FloatingActionButton button;
    FloatingActionButton butt;
View view;
    public TextView getTextView() {
        return textView;
    }

    public TextView getTextView1() {
        return textView1;
    }

    public TextView getTextView2() {
        return textView2;
    }

    public FloatingActionButton getButton() {
        return button;
    }

    public FloatingActionButton getButt() {
        return butt;
    }

    public View getView() {
        return view;
    }

    public Customview(@NonNull View itemView) {
        super(itemView);

        textView = itemView.findViewById(R.id.textView14);
        textView1 = itemView.findViewById(R.id.textView15);
        textView2 = itemView.findViewById(R.id.textView16);
        button = itemView.findViewById(R.id.button2);
butt = itemView.findViewById(R.id.button8);
view = itemView;
    }
}
