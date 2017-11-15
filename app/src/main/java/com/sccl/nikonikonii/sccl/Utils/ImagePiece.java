package com.sccl.nikonikonii.sccl.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.View;
import android.widget.RelativeLayout;

public class ImagePiece extends android.support.v7.widget.AppCompatImageView implements View.OnClickListener{

    private int originalIndex;
    private int currentIndex;

    public ImagePiece(Context context, Bitmap bitmap, int originalIndex) {
        super(context);
        setImageBitmap(bitmap);
        this.originalIndex = originalIndex;
        setPadding(1,1,1,1);
        setBackgroundColor(Color.argb(255, 255, 0, 0));
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        lp.setMargins(0,0,0,0);
        setAdjustViewBounds(true);
        this.setLayoutParams(lp);
        this.requestLayout();
    }

    public void setCurrentIndex(int index){
        this.currentIndex = index;
    }

    public int getCurrentIndexIndex(){
        return currentIndex;
    }

    public int getOriginalIndex(){
        return originalIndex;
    }

    @Override
    public void onClick(View view) {
        setRotation(getRotation()+90);
    }
}
