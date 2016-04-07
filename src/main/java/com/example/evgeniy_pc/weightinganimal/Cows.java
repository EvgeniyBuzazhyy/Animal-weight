package com.example.evgeniy_pc.weightinganimal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;

//The user selects size from number picker and push button "count"
// class doing request in DB and get answer weight animal

public class Cows extends Activity implements View.OnClickListener
{
    final String LOG_TAG = "myLogs";
    NumberPicker numberPickerBodyLength, numberPickerBust;
    //Sizes
    String [] arrayForPickerBodyLength = new String[] {"122","126","130","134","138","142","146","150","154","158","162","166","170","174","178","182","190"};
    String [] arrayForPickerBust = new String[] {"136","140","144","148","152","156","160","164","168","172","176","180","184","188","192","196","200","204","208","212","216","220"};
    DataBaseHelper dbHelper;
    Cursor cursor;
    String firstDataBodyLength = "122",firstDataBust = "136";
    String weight = null;
    TextView textViewWeight;
    Button buttonSave, buttonInfo, buttonBack;
    String weight_count, kg, weight_is_not_known, weight_saved, error, weight_is_not_calculated, deleted;

    Animation anim;
//---------------------------------------------------------------------------------- Create Activity
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cows);
        buttonSave = (Button) findViewById(R.id.buttonSave);
        buttonInfo = (Button) findViewById(R.id.buttonInfo);
        buttonBack = (Button) findViewById(R.id.buttonBack);
        anim = AnimationUtils.loadAnimation(this, R.anim.myscale);

        // Getting strings from resources
        weight_count = getResources().getString(R.string.weight_count);
        kg = getResources().getString(R.string.kg);
        weight_is_not_known = getResources().getString(R.string.weight_is_not_known);
        weight_saved = getResources().getString(R.string.weight_saved);
        error = getResources().getString(R.string.error);
        weight_is_not_calculated = getResources().getString(R.string.weight_is_not_calculated);
        deleted = getResources().getString(R.string.deleted);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
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
                    boolean statusAdd = dbHelper.addData(Integer.parseInt(weight), "Cows");
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
                textViewWeight.setText(getWeight(firstDataBodyLength,firstDataBust,2));
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

//------------------------------------- Method create number Pickers, fill data and adding Listeners
    public void creatingNumberPickers()
    {
        textViewWeight = (TextView) findViewById(R.id.textViewWeight);

        numberPickerBodyLength = (NumberPicker) findViewById(R.id.numberPicker1);
        numberPickerBodyLength.setMinValue(0);
        numberPickerBodyLength.setMaxValue(arrayForPickerBodyLength.length-1);
        numberPickerBodyLength.setDisplayedValues(arrayForPickerBodyLength);

        numberPickerBodyLength.setOnValueChangedListener(new NumberPicker.OnValueChangeListener()
        {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal)
            {
                firstDataBodyLength = arrayForPickerBodyLength[picker.getValue()];
            }
        });
        //------------------------------------------------------------------------------------------
        numberPickerBust = (NumberPicker) findViewById(R.id.numberPicker2);
        numberPickerBust.setMinValue(0);
        numberPickerBust.setMaxValue(arrayForPickerBust.length-1);
        numberPickerBust.setDisplayedValues(arrayForPickerBust);

        numberPickerBust.setOnValueChangedListener(new NumberPicker.OnValueChangeListener()
        {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal)
            {
                firstDataBust = arrayForPickerBust[picker.getValue()];
            }
        });
        //------------------------------------------------------------------------------------------
        // Connecting to data base
        cursor = null;
        try
        {
            dbHelper = new DataBaseHelper(this);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

//--------------------------------------------------------------------- Method getting weight animal
    public String getWeight(String firstDataBodyLength,String firstDataBust,int type)
    {
        String result;
        weight = dbHelper.getWeightInDB(cursor, firstDataBodyLength, firstDataBust,type);

            if (weight != null & weight != "0")
            {
                result = weight_count + " "+weight +" "+ kg;
            }
            else
            {
                result = weight_is_not_known;
            }
        return result;
    }
}
