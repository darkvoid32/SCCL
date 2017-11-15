package com.sccl.nikonikonii.sccl.Activities;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableRow;

import com.sccl.nikonikonii.sccl.R;
import com.sccl.nikonikonii.sccl.Utils.ImagePiece;
import com.sccl.nikonikonii.sccl.Utils.ImageUtil;

import java.util.ArrayList;

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

        ImageView puzzleInit = findViewById(R.id.puzzleInit);
        puzzleInit.setVisibility(View.INVISIBLE);

        ImageUtil imgUtil = new ImageUtil();

        ArrayList<Bitmap> bitmapPieces = imgUtil.splitImage(puzzleInit);
        ArrayList<ImagePiece> imagePieces = new ArrayList<ImagePiece>(12);

        for(int i = 0; i < bitmapPieces.size();i++) {
            ImagePiece piece = new ImagePiece(getApplicationContext(), bitmapPieces.get(i), i);
            imagePieces.add(piece);
        }

        TableRow row1 = findViewById(R.id.firstRow);
        TableRow row2 = findViewById(R.id.secondRow);
        TableRow row3 = findViewById(R.id.thirdRow);

        for(int i = 0;i < imagePieces.size();i++){
            if(i <= imagePieces.size()/3){
                row1.addView(imagePieces.get(i));
            }else if(i <= (imagePieces.size()/3)*2){
                row2.addView(imagePieces.get(i));
            }else{
                row3.addView(imagePieces.get(i));
            }
        }
    }
}
