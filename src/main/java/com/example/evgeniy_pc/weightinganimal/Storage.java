package com.example.evgeniy_pc.weightinganimal;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Toast;
import java.util.ArrayList;

// --- Activity show saved data in ExpandableList ---
public class Storage extends Cows implements View.OnClickListener
{
    ArrayList<Data> data;
    ExpandableListView elvMain;
    String[] pigs = new String[] {};
    String[] cows = new String[] {};
    String[] calves = new String[] {};
    Button back;

    CustomAdapter adapter;
//---------------------------------------------------------------------------------- Create Activity
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.storage);
        back = (Button) findViewById(R.id.back);

        //1 - Get arrays with data for each type
        pigs = dbHelper.getSavedData(1);
        cows = dbHelper.getSavedData(2);
        calves = dbHelper.getSavedData(3);
        //2 - Create full arrayList
        data  = fillCollection();
        //Create adapter
        adapter = new CustomAdapter(this, data, this);

        elvMain = (ExpandableListView) findViewById(R.id.elvMain);
        elvMain.setAdapter(adapter);
    }

    //-------------------------------------------- The method of removing the item from the database
    public boolean deleteItem(String item, String nameGroup)
    {
        boolean deletedItem = false;
        //Get data for deleting
        //From string we get value date and time for using in sql request
        //Get Data
        String dateString = String.valueOf(item.subSequence(0,10));
        //Get time
        String timeString = String.valueOf(item.subSequence(14,22));

        //Call method deleting
        deletedItem = dbHelper.deleteItemDB(dateString, timeString, nameGroup);
        if (deletedItem)
            Toast.makeText(this, deleted, Toast.LENGTH_SHORT).show();

        return deletedItem;
    }

//-------------------------------------------------------- Filling collection for sending on adapter
    public ArrayList<Data> fillCollection()
    {
        // Group 1
        String titleItemGrop = "Pigs";
        Data d1 = new Data(titleItemGrop);
        for(int i=0; i<pigs.length;i++)
        {
            d1.gropAndChild.add(pigs[i]);
        }

        // Group 2
        titleItemGrop = "Cows";
        Data d2 = new Data(titleItemGrop);
        for(int i=0; i<cows.length;i++)
        {
            d2.gropAndChild.add(cows[i]);
        }

        // Group 3
        titleItemGrop = "Calves";
        Data d3 = new Data(titleItemGrop);
        for(int i=0; i<calves.length;i++)
        {
            d3.gropAndChild.add(calves[i]);
        }

        //Add to ArrayList
        ArrayList<Data> all = new ArrayList<Data>();
        all.add(d1);
        all.add(d2);
        all.add(d3);

        return all;
    }
//------------------------------------------------------------------------------------------ OnClick
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.back:
            back.startAnimation(anim);
            finish();
            break;
        }
    }
}