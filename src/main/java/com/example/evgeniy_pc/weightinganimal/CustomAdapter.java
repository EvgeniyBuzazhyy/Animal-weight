package com.example.evgeniy_pc.weightinganimal;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.ArrayList;

public class CustomAdapter extends BaseExpandableListAdapter
{
    final String LOG_TAG = "myLogs";
    Context context;
    ArrayList<Data> data;
    LayoutInflater inflater;
    Storage storage;

//---------------------------------------------------------------------------- The class constructor
    public CustomAdapter(Context context, ArrayList<Data> data,Storage storage)
    {
        this.context=context;
        this.data=data;
        this.storage=storage;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

//----------------------------------------------------------------------------------- Get Child View
    @Override
    public View getChildView(final int groupPos, final int childPos, boolean isLastChild, View convertView, final ViewGroup parent)
    {

        if (convertView==null)
        {
            //Create inflater
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.item_list,null);
        }
            //Get data item
            String child = (String) getChild(groupPos, childPos);

            //Set data for text View and image for ImageButton
            TextView nameTv = (TextView) convertView.findViewById(R.id.textViewItemList);
            final ImageButton imageButton = (ImageButton) convertView.findViewById(R.id.imageButton1);

            imageButton.setFocusable(false);

            nameTv.setText(child);

            //Listener for image button
            final View finalConvertView = convertView;
            imageButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    //Delete item (query SQL)
                    String item = data.get(groupPos).gropAndChild.get(childPos);
                    boolean deletedItem = storage.deleteItem(item, String.valueOf(data.get(groupPos)));
                    //if deleted return true
                    if (deletedItem)
                    {
                        TextView tV = (TextView) finalConvertView.findViewById(R.id.textViewItemList);
                        tV.setTextColor(Color.RED);
                        Log.d(LOG_TAG, "Изменение ТЕКСТА ЦВЕТ" +"!!!");

                        //Vibration
                        imageButton.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

                        ImageButton bt = (ImageButton) finalConvertView.findViewById(R.id.imageButton1);
                        bt.setBackgroundResource(R.drawable.ic_deleted_on);
                        bt.setEnabled(false);
                    }
                }
            });
        return convertView;
    }

//----------------------------------------------------------------------------------- Get Group View
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
    {
        if (convertView==null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.group_item,null);
        }
        //Get object group
        Data d = (Data) getGroup(groupPosition);
        //Set name for group
        TextView nameTv = (TextView) convertView.findViewById(R.id.textViewGroupItem);

        String name = d.Name;
        nameTv.setText(name);
        //Find TextView in convertView
        TextView textViewDateGroupItem = (TextView) convertView.findViewById(R.id.textViewDateGroupItem);
        TextView textViewTimeGroupItem = (TextView) convertView.findViewById(R.id.textViewTimeGroupItem);
        TextView textViewWeightGroupItem = (TextView) convertView.findViewById(R.id.textViewWeightGroupItem);

        //if if the list is deployed
        if (isExpanded)
        {
            textViewDateGroupItem.setText("Date");
            textViewTimeGroupItem.setText("Time");
            textViewWeightGroupItem.setText("Weight");
        }
        //if the list is not deployed
        else
        {
            textViewDateGroupItem.setText("");
            textViewTimeGroupItem.setText("");
            textViewWeightGroupItem.setText("");
        }

        return convertView;
    }

//--------------------------------------------------------------------------------- Override methods
    @Override
    public int getChildrenCount(int groupPosw)
    {
        return data.get(groupPosw).gropAndChild.size();
    }

    @Override
    public Object getGroup(int groupPos)
    {
        return data.get(groupPos);
    }

    @Override
    public int getGroupCount()
    {
        return data.size();
    }

    @Override
    public Object getChild(int groupPos, int childPos)
    {
        return data.get(groupPos).gropAndChild.get(childPos);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition)
    {
        return 0;
    }

    @Override
    public long getGroupId(int groupPosition) { return 0; }

    @Override
    public boolean hasStableIds()
    {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition)
    {
        return true;
    }
}
