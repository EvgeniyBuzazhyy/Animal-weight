package com.example.evgeniy_pc.weightinganimal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Pigs extends Cows implements View.OnClickListener
{
    Button buttonSave, buttonInfo, buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pigs);
        buttonSave = (Button) findViewById(R.id.buttonSave);
        buttonInfo = (Button) findViewById(R.id.buttonInfo);
        buttonBack = (Button) findViewById(R.id.buttonBack);

        //Set new data sizes for pigs
        arrayForPickerBodyLength = new String[]{"60","64","68","72","76","80","84","88","92","96","100","104","108","112","116","120","124","128","132","136","140","144","148"};
        arrayForPickerBust = new String[]{"38","42","46","50","54","58","62","66","70","74","78","82","86","90","94","98","102","106","110","114","118","122","126","130","134","138","142","146","150"};

        firstDataBodyLength = "60";
        firstDataBust = "38";

        //Start create numberPickers and open data base
        creatingNumberPickers();
    }
//------------------------------------------------------------------------------------------ OnClick
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.buttonSaveWeight:
                if (weight!=null)
                {
                    //Saved data in SQL database
                    boolean statusAdd = dbHelper.addData(Integer.parseInt(weight), "Pigs");
                    if (statusAdd)
                    {
                        Toast.makeText(this, weight_saved, Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(this, weight_is_not_calculated, Toast.LENGTH_SHORT).show();
                break;
            case R.id.buttonCount:
                textViewWeight.setText(getWeight(firstDataBodyLength,firstDataBust,1));
                break;
            case R.id.linerLayout:
                textViewWeight.setText(weight_count);
                break;
            case R.id.buttonBack:
                buttonBack.startAnimation(anim);
                finish();
                break;
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
        }
    }
}
