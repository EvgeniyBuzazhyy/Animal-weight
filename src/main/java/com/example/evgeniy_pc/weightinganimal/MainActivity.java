package com.example.evgeniy_pc.weightinganimal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;


public class MainActivity extends Activity implements View.OnClickListener
{

    Button buttonSave, buttonInfo,buttonPigs,buttonCows,buttonCalves;
    Animation anim;
//---------------------------------------------------------------------------------- Create Activity
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonSave = (Button) findViewById(R.id.buttonSave);
        buttonInfo = (Button) findViewById(R.id.buttonInfo);
        buttonPigs = (Button) findViewById(R.id.buttonPigs);
        buttonCows = (Button) findViewById(R.id.buttonCows);
        buttonCalves = (Button) findViewById(R.id.buttonCalves);

        anim = AnimationUtils.loadAnimation(this, R.anim.myscale);
    }
//------------------------------------------------------------------------------------------ OnClick
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.buttonSave:
                buttonSave.startAnimation(anim);
                Intent intentSave = new Intent(this,Storage.class);
                startActivity(intentSave);
                break;
            case R.id.buttonInfo:
                buttonInfo.startAnimation(anim);
                Intent intentInfo = new Intent(this,Info.class);
                startActivity(intentInfo);
                break;
            case R.id.buttonPigs:
                buttonPigs.startAnimation(anim);
                Intent intentPigs = new Intent(this,Pigs.class);
                startActivity(intentPigs);
                break;
            case R.id.buttonCows:
                buttonCows.startAnimation(anim);
                Intent intentCows = new Intent(this,Cows.class);
                startActivity(intentCows);
                break;
            case R.id.buttonCalves:
                buttonCalves.startAnimation(anim);
                Intent intentCalvas = new Intent(this,Calves.class);
                startActivity(intentCalvas);
                break;
            case R.id.buttonExit:
                finish();
                break;
        }
    }
}