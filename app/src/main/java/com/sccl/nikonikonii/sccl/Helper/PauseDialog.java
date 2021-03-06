package com.sccl.nikonikonii.sccl.Helper;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.sccl.nikonikonii.sccl.R;


public class PauseDialog extends Dialog implements View.OnClickListener{

    public PauseDialog(@NonNull Context context) {
        super(context);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // Remove title
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // Full Screen
        setContentView(R.layout.pause_dialog);

        ImageButton button = (ImageButton) findViewById(R.id.close);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        dismiss();
    }
}
