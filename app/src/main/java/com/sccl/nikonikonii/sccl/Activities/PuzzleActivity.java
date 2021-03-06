package com.sccl.nikonikonii.sccl.Activities;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sccl.nikonikonii.sccl.Helper.PauseDialog;
import com.sccl.nikonikonii.sccl.R;
import com.sccl.nikonikonii.sccl.Utils.ImagePiece;
import com.sccl.nikonikonii.sccl.Utils.ImageUtil;
import com.sccl.nikonikonii.sccl.imageClass;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class PuzzleActivity extends AppCompatActivity {

    private ImageView mascotIV, puzzleInit;
    private RelativeLayout speechRL;
    private LinearLayout rootLL;
    private Thread t;
    private boolean gameRunning = true, puzzleComplete = false;
    private int mascotPosition = 0;
    private TextView speechText;

    private LinearLayout row1;
    private LinearLayout row2;
    private LinearLayout row3;

    private ArrayList<ImagePiece> imagePieces;
    private ArrayList<imageClass> usedDrawables;
    private ArrayList<imageClass> totalDrawables;
    private ImagePiece firstClicked;
    private imageClass currentImageShown;

    private Button nextPuzzleButton;

    private TextView puzzleCompeleteTextView;

    private Animation slide_down;
    private ImageView pauseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // Remove title
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // Full Screen

        setContentView(R.layout.activity_puzzle);

        initVariables(); //Initializing all Views
        setMascot(); //Setting layout params for mascot
        setPuzzlePiece(); //Setting first puzzle pieces
        createMascotThread(); //Making mascot move and shit
    }

    private void initVariables() {
        //Initializing all views
        pauseButton = findViewById(R.id.pauseButton);
        mascotIV = findViewById(R.id.mascotIV);
        speechRL = findViewById(R.id.speech_bubble);
        puzzleInit = findViewById(R.id.puzzleInit);
        speechText = findViewById(R.id.speech_bubble_textView);
        nextPuzzleButton = findViewById(R.id.nextPuzzleButton);
        puzzleCompeleteTextView = findViewById(R.id.puzzleCompleteTextView);

        puzzleInit.setVisibility(View.INVISIBLE);
        nextPuzzleButton.setVisibility(View.GONE);

        //Initializing all ArrayLists

        totalDrawables = new ArrayList<>();
        usedDrawables = new ArrayList<>(totalDrawables.size());

        totalDrawables.add(new imageClass(R.drawable.chicken_rice1, "鸡饭"));
        totalDrawables.add(new imageClass(R.drawable.chicken_rice2, "鸡饭"));
        totalDrawables.add(new imageClass(R.drawable.laksa1, "叻沙"));
        totalDrawables.add(new imageClass(R.drawable.laksa2, "叻沙"));
        totalDrawables.add(new imageClass(R.drawable.satay1, "沙爹"));
        totalDrawables.add(new imageClass(R.drawable.satay2, "沙爹"));

        slide_down = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
    }


    private void setPuzzlePiece() {
        if (row1 != null || row2 != null || row3 != null) {
            row1.removeAllViews();
            row2.removeAllViews();
            row3.removeAllViews();
        }

        ArrayList<Integer> ranNum = new ArrayList<Integer>(12);
        ImageUtil imgUtil = new ImageUtil();

        Random r = new Random();
        int ranDrawableResource = totalDrawables.get(r.nextInt(totalDrawables.size())).getResource();
        puzzleInit.setImageResource(ranDrawableResource);
        int u = totalDrawables.size();


        if (u != 1) {
            for (int i = 0; i < u; i++) {
                if (totalDrawables.get(i).getResource() != ranDrawableResource) {
                    usedDrawables.add(totalDrawables.get(i));
                } else {
                    currentImageShown = totalDrawables.get(i);
                }
            }
        }

        totalDrawables = usedDrawables; //Used Drawables actually UnusedDrawables, soz

        ArrayList<Bitmap> bitmapPieces = imgUtil.splitImage(puzzleInit);
        imagePieces = new ArrayList<>(12);

        for (int i = 0; i < 12; i++) {
            ranNum.add(i);
            Log.i("ranNum", String.valueOf(i));
        }

        Collections.shuffle(ranNum);

        for (int i = 0; i < bitmapPieces.size(); i++) {
            final ImagePiece piece = new ImagePiece(getApplicationContext(), this, bitmapPieces.get(i), i, ranNum.get(i));
            imagePieces.add(piece);
        }

        row1 = findViewById(R.id.firstRow);
        row2 = findViewById(R.id.secondRow);
        row3 = findViewById(R.id.thirdRow);

        addBitmapsToLL();
    }

    private void addBitmapsToLL() {
        for (int i = 0; i < imagePieces.size(); i++) { //Adding bitmaps to the rows 1/2/3
            for (int u = 0; u < imagePieces.size(); u++) {
                if (imagePieces.get(u).getCurrentIndex() == i) {
                    if (i < imagePieces.size() / 3) {
                        row1.addView(imagePieces.get(u));
                    } else if (i < (imagePieces.size() / 3) * 2) {
                        row2.addView(imagePieces.get(u));
                    } else {
                        row3.addView(imagePieces.get(u));
                    }
                }
            }
            imagePieces.get(i).unScale();
        }
    }

    public void switchPiece(ImagePiece ip) {
        if (firstClicked == null) {
            firstClicked = ip; //firstClicked = First ImagePiece clicked
        } else {
            int temIndex = firstClicked.getCurrentIndex();
            firstClicked.setCurrentIndex(ip.getCurrentIndex());
            ip.setCurrentIndex(temIndex);
            firstClicked = null;
            MovePieces();
        }
    }

    private void MovePieces() {
        Collections.sort(imagePieces, new Comparator<ImagePiece>() {
            public int compare(ImagePiece p1, ImagePiece p2) {
                int a = p1.getCurrentIndex();
                int b = p1.getCurrentIndex();
                return Integer.compare(a, b);
            }
        });

        row1.removeAllViews();
        row2.removeAllViews();
        row3.removeAllViews();

        addBitmapsToLL();

        row1.invalidate();
        row2.invalidate();
        row3.invalidate();

        checkCompleteness();
    }

    private void checkCompleteness() {
        for (ImagePiece ip : imagePieces) {
            if (ip.getCurrentIndex() == ip.getOriginalIndex()) {
                puzzleComplete = true;
            } else {
                puzzleComplete = false;
                break;
            }
        }

        if (puzzleComplete) {
            puzzleCompeleteTextView.setVisibility(View.VISIBLE);
            puzzleCompeleteTextView.setText(currentImageShown.getWord());
            Log.i("Word", currentImageShown.getWord());
            puzzleCompeleteTextView.setAnimation(slide_down);
            nextPuzzleButton.setVisibility(View.VISIBLE);
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nextPuzzleButton:
                //TODO add like a lot more images, preferably 20 images in total
                setPuzzlePiece(); //Setting next Image
                nextPuzzleButton.setVisibility(View.GONE);
                puzzleCompeleteTextView.setVisibility(View.GONE);
                break;
            case R.id.pauseButton:
                PauseDialog pauseDialog = new PauseDialog(PuzzleActivity.this);

                pauseDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                pauseDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
                pauseDialog.show();
                pauseDialog.getWindow().getDecorView().setSystemUiVisibility(getWindow().getDecorView().getSystemUiVisibility());
                pauseDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
                break;
            default:
                break;
        }
    }

    private void setMascot() {
        DisplayMetrics displayMetrics = new DisplayMetrics(); //Getting Screen height + width
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int userHeight = displayMetrics.heightPixels;
        int userWidth = displayMetrics.widthPixels;

        //Layout params for Puzzle Image
        //TODO Remove hardcoding
        rootLL = findViewById(R.id.rootLL);
        RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp2.height = (userHeight / 7) * 4; // Dynamically set height
        lp2.width = (userWidth / 4) * 3;
        lp2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lp2.addRule(RelativeLayout.CENTER_VERTICAL);
        rootLL.setLayoutParams(lp2);
        rootLL.requestLayout();
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
        String text = "你们好！";
        switch (mascotPosition) {
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

        if (mascotPosition == 4) {
            mascotPosition = 0;
        } else {
            mascotPosition++;
        }
        speechText.setText(text);
    }


}
