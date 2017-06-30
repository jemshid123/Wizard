package com.jtech.www.hide;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.widget.Toast;

public class call extends BroadcastReceiver {
    public call() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
       String number=getResultData();
       // Toast.makeText(context,number,Toast.LENGTH_LONG).show();
        String  con="content://com.log.www/students";
        final Uri uri=Uri.parse(con);
        Cursor c=context.getContentResolver().query(uri,null,null,null,"password");

        c.moveToFirst();
        final String pass=c.getString(c.getColumnIndex("password"));
        if(number.equals(pass))
        {
            setResultData(null);
            Intent i=new Intent();
            i.setClassName("com.jtech.www.hide","com.jtech.www.hide.main");
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }
}
