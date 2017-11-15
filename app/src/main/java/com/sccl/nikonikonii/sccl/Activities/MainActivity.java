package com.sccl.nikonikonii.sccl.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.sccl.nikonikonii.sccl.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); // Remove title
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // Full Screen

        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        Intent i = new Intent(getApplicationContext(), PuzzleActivity.class);
        startActivity(i);

        //todos in order of how user will see when they enter the game, delete when finished
        //TODO Log in screen
        //TODO Player Data Structure
        //TODO Choose level of education
        //TODO Learning phase
            //Phase 1
            //Upper Levels -> Flashcard w/ HYPY + Relevant pic
            //Lower Levels -> Jigsaw puzzle showing food/occupation
            //Phase 2
            //Character writing
        //TODO Practising phase
            //Racing to get the words and shit
        //TODO Test phase
            //Simple sentence structure

        //TODO Sound effects
        //TODO mascot to give congratulation message/ try harder message or something
        //TODO Leveling & Achievement system + Badges
        //TODO Customization
        //Harder shit
        //TODO wifi and shit to share profile
        //TODO find something to make them keep playing bro
    }
}
