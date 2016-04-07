package com.example.evgeniy_pc.weightinganimal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class Calves  extends Cows implements View.OnClickListener
{
    Button buttonSave, buttonInfo,buttonBack;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calves);
        buttonSave = (Button) findViewById(R.id.buttonSave);
        buttonInfo = (Button) findViewById(R.id.buttonInfo);
        buttonBack = (Button) findViewById(R.id.buttonBack);

        //Set new data sizes for calves
        arrayForPickerBodyLength = new String[]{"90","92","94","96","98","100","102","104","106","108","110","112","114","116","118","120","122","124","126"};
        arrayForPickerBust = new String[]{"84","86","88","90","92","94","96","98","100","102","104","106","108","110","112","114","116","118","120","122","124","126","128","130"};

        firstDataBodyLength = "90";
        firstDataBust = "84";

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
                    boolean statusAdd = dbHelper.addData(Integer.parseInt(weight), "Calves");
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
                textViewWeight.setText(getWeight(firstDataBodyLength,firstDataBust,3));
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
