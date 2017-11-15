package com.sccl.nikonikonii.sccl.Utils;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.widget.ImageView;

import java.util.ArrayList;

public class ImageUtil {
    public int picHeight, picWidth;

    public ImageUtil(){}

    public ArrayList<Bitmap> splitImage(ImageView image){
        int rows = 3, columns = 4;

        int splitHeight, splitWidth;

        ArrayList<Bitmap> splitImages = new ArrayList<Bitmap>(rows*columns);

        BitmapDrawable drawable = (BitmapDrawable) image.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);

        splitHeight = scaledBitmap.getHeight()/rows;
        splitWidth = scaledBitmap.getWidth()/columns;

        int yCoord = 0;
        for(int x=0; x<rows; x++){
            int xCoord = 0;
            for(int y=0; y<columns; y++){
                splitImages.add(Bitmap.createBitmap(scaledBitmap, xCoord, yCoord, splitWidth, splitHeight));
                Log.i("Pieces size x", String.valueOf(xCoord));
                xCoord += splitWidth;
            }
            yCoord += splitHeight;
        }

        return splitImages;
    }
}
