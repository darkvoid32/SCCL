package com.sccl.nikonikonii.sccl.Activities;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableRow;

import com.sccl.nikonikonii.sccl.R;
import com.sccl.nikonikonii.sccl.Utils.ImagePiece;
import com.sccl.nikonikonii.sccl.Utils.ImageUtil;

import java.util.ArrayList;

public class PuzzleActivity extends AppCompatActivity{

    private ImageView mascotIV, puzzleInit;
    private LinearLayout rootLL;

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

        mascotIV = findViewById(R.id.mascotIV);

        puzzleInit = findViewById(R.id.puzzleInit);
        puzzleInit.setVisibility(View.INVISIBLE);

        setLayoutParams();

        ImageUtil imgUtil = new ImageUtil();

        ArrayList<Bitmap> bitmapPieces = imgUtil.splitImage(puzzleInit);
        ArrayList<ImagePiece> imagePieces = new ArrayList<ImagePiece>(12);

        for(int i = 0; i < bitmapPieces.size();i++) {
            ImagePiece piece = new ImagePiece(getApplicationContext(), bitmapPieces.get(i), i);
            Log.i("Pieces size", String.valueOf(bitmapPieces.get(i).getWidth()));
            imagePieces.add(piece);
        }

        LinearLayout row1 = findViewById(R.id.firstRow);
        LinearLayout row2 = findViewById(R.id.secondRow);
        LinearLayout row3 = findViewById(R.id.thirdRow);

        for(int i = 0;i < imagePieces.size();i++){ //Adding bitmaps to the rows 1/2/3
            if(i < imagePieces.size()/3){
                row1.addView(imagePieces.get(i));
            }else if(i < (imagePieces.size()/3)*2){
                row2.addView(imagePieces.get(i));
            }else{
                row3.addView(imagePieces.get(i));
            }
        }

    }

    private void setLayoutParams(){
        DisplayMetrics displayMetrics = new DisplayMetrics(); //Getting Screen height + width
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int userHeight = displayMetrics.heightPixels;
        int userwWidth = displayMetrics.widthPixels;
        Log.i("Parent Size", String.valueOf(userwWidth));

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        mascotIV.getLayoutParams().height = (userHeight / 2);
        mascotIV.getLayoutParams().width = (userwWidth / 2);
        lp.setMargins(0, userHeight/2, 0, 0);
        mascotIV.setLayoutParams(lp);
        mascotIV.requestLayout();

        //TODO Remove hardcoding
        rootLL = findViewById(R.id.rootLL);
        RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp2.height = (userHeight / 7) * 4; // Dynamically set height
        lp2.width = (userwWidth / 4) * 3;
        lp2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lp2.addRule(RelativeLayout.CENTER_VERTICAL);
        lp2.rightMargin = -125;
        rootLL.setLayoutParams(lp2);
        rootLL.requestLayout();
    }
}
