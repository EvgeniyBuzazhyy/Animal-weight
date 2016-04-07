package com.example.evgeniy_pc.weightinganimal;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

// --- Activity show information how using this app -
public class Info extends Activity implements View.OnClickListener
{
    Animation anim;
    Button buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);
        anim = AnimationUtils.loadAnimation(this, R.anim.myscale);
        buttonBack = (Button) findViewById(R.id.back);
    }

    @Override
    public void onClick(View v)
    {
        buttonBack.startAnimation(anim);
        finish();
    }
}
