package com.jtech.www.hide;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.io.File;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * Created by others on 22-06-2017.
 */

public class menuaction {
File file;
    String temp[],name,files;
    String id
            ;
    public void menu(PopupMenu menu, final Context context, final String path)
    {
       menu.getMenuInflater().inflate(R.menu.options,menu.getMenu());
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(context,item.getTitle()+" "+path,Toast.LENGTH_SHORT).show();
                cryptodb sql=new cryptodb();
                String URL = "content://com.cryptodb.www/students";
                Uri students = Uri.parse(URL);
               // Cursor c = context.managedQuery(students, null, null, null, "name");
                Cursor c=context.getContentResolver().query(students,null,null,null,"name");

                if (c.moveToFirst()) {
                    do {
                        files = c.getString(c.getColumnIndex(sql.NAME));
                        if(files.equals(path))
                        {
                            id=c.getString(c.getColumnIndex(sql._ID));
                            Toast.makeText(context,"found:"+c.getString(c.getColumnIndex(sql.NAME))+" id"+id, Toast.LENGTH_LONG).show();

                            break;
                        }


                    }
                    while (c.moveToNext());
                }
                try {
                    file=new File(path);
                    temp=path.split(Pattern.quote("/"));
                    name=temp[temp.length-1];
                    if (item.getTitle().toString().equals("Delete")) {

                       if(file.delete()) {
                           file=new File(filestore.getThumbPath(path));
                           file.delete();
                           Toast.makeText(context, name + " deleted", Toast.LENGTH_SHORT).show();
                           context.getContentResolver().delete(cryptodb.CONTENT_URI,cryptodb._ID+"="+id,null);
                       }
                        else
                       {
                           file=new File(filestore.getThumbPath(path));
                           file.delete();
                           context.getContentResolver().delete(cryptodb.CONTENT_URI,cryptodb._ID+"="+id,null);
                           Toast.makeText(context, name + " not deleted", Toast.LENGTH_SHORT).show();
                       }
                    }
                    else if(item.getTitle().toString().equals("Details"))
                    {
                        Date date=new Date(file.lastModified());
                        int size=(int)file.length()/(1024*1024);

                        AlertDialog.Builder dialog=new AlertDialog.Builder(context);
                        dialog.setCancelable(true);
                        dialog.setTitle("name");
                        dialog.setMessage("name:"+name+"\n size:"+size+"MB\nLast modified:"+date);
                        dialog.setIcon(R.mipmap.lock);
                        AlertDialog alert=dialog.create();
                        alert.show();

                    }
                }catch (Exception e){}
                return true;
            }
        });
        menu.show();
    }
}
