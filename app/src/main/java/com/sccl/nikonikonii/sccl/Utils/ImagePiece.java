package com.sccl.nikonikonii.sccl.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.sccl.nikonikonii.sccl.Activities.PuzzleActivity;
import com.sccl.nikonikonii.sccl.R;

public class ImagePiece extends android.support.v7.widget.AppCompatImageView{

    private PuzzleActivity puzzleActivity;
    private int originalIndex;
    private int currentIndex;
    private boolean clicked;

    public ImagePiece(Context context, PuzzleActivity pA,Bitmap bitmap, int originalIndex, int currentIndex) {
        super(context);
        puzzleActivity = pA;
        setImageBitmap(bitmap);
        clicked = false;
        this.originalIndex = originalIndex;
        this.currentIndex = currentIndex;
        setPadding(2,2,2,2);
        setBackgroundColor(Color.argb(255, 0, 0, 0));
        setAdjustViewBounds(true);
    }

    public void setCurrentIndex(int index){
        this.currentIndex = index;
    }

    public int getCurrentIndexIndex(){
        return currentIndex;
    }

    public void setOriginalIndex(int originalIndex) {
        this.originalIndex = originalIndex;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public int getOriginalIndex(){
        return originalIndex;
    }

    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_UP){
            return click();
        }
        return true;
    }

    public boolean click(){
        Log.d("Click event", "Image clicked");
        if (!clicked) {
            setScaleX(1.2f);
            setScaleY(1.2f);
            clicked = true;
        } else {
            setScaleX(1.0f);
            setScaleY(1.0f);
            clicked = false;
        }
        //TODO Sound
        puzzleActivity.switchPiece(this);
        return true;
    }

    public void unScale() {
        this.setScaleY(1.0f);
        this.setScaleX(1.0f);
    }
}
