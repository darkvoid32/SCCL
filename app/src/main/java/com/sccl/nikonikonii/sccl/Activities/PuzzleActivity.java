package com.sccl.nikonikonii.sccl.Activities;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.sccl.nikonikonii.sccl.R;
import com.sccl.nikonikonii.sccl.Utils.ImagePiece;
import com.sccl.nikonikonii.sccl.Utils.ImageUtil;

import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class PuzzleActivity extends AppCompatActivity {

    private ImageView mascotIV, puzzleInit;
    private RelativeLayout speechRL;
    private LinearLayout rootLL;
    private Thread t;
    private boolean gameRunning = true;
    private int mascotPosition = 0;
    private TextView speechText;

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
        speechRL = findViewById(R.id.speech_bubble);
        puzzleInit = findViewById(R.id.puzzleInit);
        puzzleInit.setVisibility(View.INVISIBLE);
        speechText = findViewById(R.id.speech_bubble_textView);

        setMascot();

        setPuzzlePiece();

        createMascotThread();
    }

    private void createMascotThread() {
        t = new Thread(new Runnable() {
            public void run() {
                try {
                    while (gameRunning) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                changeMascotPosition();
                            }
                        });
                        Thread.sleep(2000);
                    }
                } catch (Exception ex) {
                    Log.i("Game crashed", ex.toString());
                }
            }
        });

        t.start();
    }

    private void changeMascotPosition() {
        String text = "Thread running";
            switch(mascotPosition){
                case 0:
                    mascotIV.setImageResource(R.drawable.panda1);
                    text += ".";
                    break;
                case 1:
                    mascotIV.setImageResource(R.drawable.panda2);
                    text += "..";
                    break;
                case 2:
                    mascotIV.setImageResource(R.drawable.panda3);
                    text += "...";
                    break;
                case 3:
                    mascotIV.setImageResource(R.drawable.panda4);
                    text += "....";
                    break;
                case 4:
                    mascotIV.setImageResource(R.drawable.panda0);
                    text += "";
                    break;
                default:
                    break;
            }

            if(mascotPosition == 4){
                mascotPosition = 0;
            } else {
                mascotPosition ++;
            }
            speechText.setText(text);
    }

    private void setPuzzlePiece() {
        ImageUtil imgUtil = new ImageUtil();

        ArrayList<Bitmap> bitmapPieces = imgUtil.splitImage(puzzleInit);
        ArrayList<ImagePiece> imagePieces = new ArrayList<>(12);

        //TODO Figure out how to move IV around
        for (int i = 0; i < bitmapPieces.size(); i++) {
            final ImagePiece piece = new ImagePiece(getApplicationContext(), bitmapPieces.get(i), i);
            imagePieces.add(piece);
        }

        LinearLayout row1 = findViewById(R.id.firstRow);
        LinearLayout row2 = findViewById(R.id.secondRow);
        LinearLayout row3 = findViewById(R.id.thirdRow);

        for (int i = 0; i < imagePieces.size(); i++) { //Adding bitmaps to the rows 1/2/3
            if (i < imagePieces.size() / 3) {
                row1.addView(imagePieces.get(i));
            } else if (i < (imagePieces.size() / 3) * 2) {
                row2.addView(imagePieces.get(i));
            } else {
                row3.addView(imagePieces.get(i));
            }
        }

    }

    private void setMascot() {
        DisplayMetrics displayMetrics = new DisplayMetrics(); //Getting Screen height + width
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int userHeight = displayMetrics.heightPixels;
        int userWidth = displayMetrics.widthPixels;

        //Layout params for mascot ImageView
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(userHeight/2, userWidth/2);
        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lp.setMargins(12, 0, 0, 1);
        mascotIV.setLayoutParams(lp);
        mascotIV.requestLayout();

        //Layout params for speech_bubble
        RelativeLayout.LayoutParams lp3 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        //speechIV.getLayoutParams().height = (userHeight / 2);
        //speechIV.getLayoutParams().width = (userwWidth / 2);
        //lp3.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        lp3.height = userHeight / 2;
        lp3.width = userWidth / 2;
        speechRL.setLayoutParams(lp3);
        speechRL.requestLayout();

        //Layout params for Puzzle Image
        //TODO Remove hardcoding
        rootLL = findViewById(R.id.rootLL);
        RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp2.height = (userHeight / 7) * 4; // Dynamically set height
        lp2.width = (userWidth / 4) * 3;
        lp2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lp2.addRule(RelativeLayout.CENTER_VERTICAL);
        lp2.rightMargin = -125;
        rootLL.setLayoutParams(lp2);
        rootLL.requestLayout();
    }
}
