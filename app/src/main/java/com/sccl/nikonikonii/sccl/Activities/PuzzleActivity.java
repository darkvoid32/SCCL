package com.sccl.nikonikonii.sccl.Activities;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.sccl.nikonikonii.sccl.R;

public class PuzzleActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);

        ImageButton pauseButton = findViewById(R.id.pauseButton);

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PauseDialog asd = new PauseDialog(PuzzleActivity.this);
                asd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                asd.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
                asd.show();
                asd.getWindow().getDecorView().setSystemUiVisibility(getWindow().getDecorView().getSystemUiVisibility());
                asd.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
            }
        });
    }
}
