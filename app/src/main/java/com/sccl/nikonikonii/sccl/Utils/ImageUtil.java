package com.sccl.nikonikonii.sccl.Utils;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.widget.ImageView;

import java.util.ArrayList;

public class ImageUtil {

    public ImageUtil(){}

    public ArrayList<Bitmap> splitImage(ImageView image){
        int rows = 3, columns = 4;

        int splitHeight, splitWidth;

        ArrayList<Bitmap> splitImages = new ArrayList<Bitmap>(rows*columns);

        BitmapDrawable drawable = (BitmapDrawable) image.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        Log.i("ImageSplit Y Total", String.valueOf(bitmap.getHeight()));
        Log.i("ImageSplit X Total", String.valueOf(bitmap.getWidth()));
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);
        Log.i("ImageSplit Y Total", String.valueOf(scaledBitmap.getHeight()));
        Log.i("ImageSplit X Total", String.valueOf(scaledBitmap.getWidth()));

        splitHeight = scaledBitmap.getHeight()/rows;
        splitWidth = scaledBitmap.getWidth()/columns;

        int yCoord = 0;
        for(int x=0; x<rows; x++){
            int xCoord = 0;
            for(int y=0; y<columns; y++){
                splitImages.add(Bitmap.createBitmap(scaledBitmap, xCoord, yCoord, splitWidth, splitHeight));
                xCoord += splitWidth;
                Log.i("ImageSplit X", String.valueOf(xCoord));
            }
            yCoord += splitHeight;
            Log.i("ImageSplit Y", String.valueOf(yCoord));
        }
        Log.i("ImageSplit Y Total", String.valueOf(scaledBitmap.getHeight()));
        Log.i("ImageSplit X Total", String.valueOf(scaledBitmap.getWidth()));

        return splitImages;
    }
}
