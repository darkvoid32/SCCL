package com.sccl.nikonikonii.sccl.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.sccl.nikonikonii.sccl.R;
import com.sccl.nikonikonii.sccl.Utils.CanvasView;

public class PuzzleActivity extends AppCompatActivity{

    private CanvasView customCanvas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);

        customCanvas = (CanvasView) findViewById(R.id.canvasView);

    }

    public void clearCanvas(View v) {
        customCanvas.clearCanvas();
    }

}
