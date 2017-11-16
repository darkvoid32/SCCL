package com.sccl.nikonikonii.sccl.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

public class ImagePiece extends android.support.v7.widget.AppCompatImageView implements View.OnClickListener{

    private int originalIndex;
    private int currentIndex;
    private Bitmap bitmap;

    public ImagePiece(Context context, Bitmap bitmap, int originalIndex) {
        super(context);
        setImageBitmap(bitmap);
        this.originalIndex = originalIndex;
        this.bitmap = bitmap;
        setPadding(1,1,1,1);
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

    @Override
    public boolean performClick() {
        // Calls the super implementation, which generates an AccessibilityEvent
        // and calls the onClick() listener on the view, if any
        super.performClick();

        // Handle the action for the custom click here

        Log.i("Touching ImageView", String.valueOf(originalIndex));

        return true;
    }

    public int getOriginalIndex(){
        return originalIndex;
    }

    @Override
    public void onClick(View view) {
        setRotation(getRotation()+90);
    }
}
