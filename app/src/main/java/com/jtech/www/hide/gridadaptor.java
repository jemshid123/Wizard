package com.jtech.www.hide;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jtech.www.hide.R;

/**
 * Created by others on 14-06-2017.
 */

public class gridadaptor extends BaseAdapter {
    Context context;
    Bitmap Thumb[];
    String name[];

    LayoutInflater li;
    public gridadaptor(Context con,Bitmap Thumb[],String name[])
    {
        this.context=con;
        this.Thumb=Thumb;
        this.name=name;
    }
    public int getCount()
    {
        return name.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class holder
    {
        ImageView iv;
        TextView tv;
    }

    @Override

    public View getView(int position, View convertView, ViewGroup parent)
    {
        try{
            li=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       View view=li.inflate(R.layout.customgrid,null);
        holder hold=new holder();
            Bitmap bit;
        hold.iv=(ImageView)view.findViewById(R.id.thumb);
        hold.tv=(TextView)view.findViewById(R.id.name);
            bit=Thumb[position];
        hold.iv.setImageBitmap(bit);
        hold.tv.setText(name[position]);
            hold.iv.setPadding(8,8,8,8);
            hold.iv.setCropToPadding(true);
        return  view;
         }catch (Exception e)
        {
            Log.d("Error in grid adaptor",e.getMessage());

            return  null;
        }
    }
}
