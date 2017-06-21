package com.jtech.www.hide;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.widget.PopupMenu;

import com.face.www.medialib.mediatype;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Created by others on 16-06-2017.
 */

public class filestore {
    public  static  String getThumbPath(String file)
    {
        File test=new File(file);

                if(test.exists()) Log.d("file **********",file+" exists");
        else Log.d("file **********",file+" donot exists");
        String  token[]=file.split(Pattern.quote("/"));
        file="";
        for(int i=0;i<token.length-1;i++)
        {
            file+=token[i]+"/";
        }
        file+="."+token[token.length-1]+".png";

        try{
            test=new File(file);
            test.createNewFile();
        }catch (Exception e)
        {
            Log.d("************",e.getMessage());
        }
        return file;

    }
    /** getting file from storage */
    public  static String[] getmedia(Activity activity,int filter)
    {
        ArrayList<String> result=new ArrayList<>();
        String fil,Thumb;
        cryptodb sql=new cryptodb();
        String URL = "content://com.cryptodb.www/students";
        Uri students = Uri.parse(URL);
        Cursor c =activity.managedQuery(students, null, null, null, "name");
        if (c.moveToFirst()) {
            do {
                fil=c.getString(c.getColumnIndex(sql.NAME));
      if(mediatype.type(fil)==filter) {

               result.add(fil);


               }


            }
            while (c.moveToNext());
        }
        return result.toArray(new String[result.size()]);
    }


    /** getting thumbs from storage */
public  static Bitmap[] storedthumb(String[] file)
{
    ArrayList<Bitmap> bit=new ArrayList<>();
    Bitmap temp;
    for(String f:file)
    {
        f=getThumbPath(f);
        temp= BitmapFactory.decodeFile(f);
        bit.add(temp);
    }
    return bit.toArray(new Bitmap[bit.size()
            ]);
}


}
