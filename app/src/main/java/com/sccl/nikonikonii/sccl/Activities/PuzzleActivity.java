package com.sccl.nikonikonii.sccl.Activities;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.sccl.nikonikonii.sccl.R;

public class PuzzleActivity extends AppCompatActivity{

    private ImageView mascotIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // Remove title
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // Full Screen

        setContentView(R.layout.activity_puzzle);

        ImageButton pauseButton = findViewById(R.id.pauseButton);

        //Setting pause button
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PauseDialog pauseDialog = new PauseDialog(PuzzleActivity.this);

                pauseDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                pauseDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
                pauseDialog.show();
                pauseDialog.getWindow().getDecorView().setSystemUiVisibility(getWindow().getDecorView().getSystemUiVisibility());
                pauseDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
            }
        });

        mascotIV = findViewById(R.id.mascotIV); // Getting mascot ImageView

        DisplayMetrics displayMetrics = new DisplayMetrics(); //Getting Screen height + width
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int userHeight = displayMetrics.heightPixels;
        int userwWidth = displayMetrics.widthPixels;

        mascotIV.getLayoutParams().height = (userHeight / 2);
        mascotIV.getLayoutParams().width = (userwWidth / 2);
        mascotIV.requestLayout();

    }
}
