package com.jtech.www.hide;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.face.www.medialib.mediatype;

import java.util.ArrayList;

public class mediaadd extends AppCompatActivity {
    Button encrypt,cancel;
    GridView gridview;
    Bitmap[] bitmap;
    String[] names;
int pre=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mediaadd);
        encrypt=(Button)findViewById(R.id.encrypt);
        gridview=(GridView)findViewById(R.id.gridview) ;
        cancel=(Button)findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel.setBackgroundColor(Color.parseColor("#d14b4d"));
                try {
                    Thread.sleep(100);
                }catch (Exception e){ finish();}
                finish();
            }
        });
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                parent.getChildAt(position).setBackgroundColor(Color.parseColor("#091b4c"));

                   if((pre != -1)&&(pre != position)) {
                       parent.getChildAt(pre).setBackgroundColor(Color.parseColor("#000000"));
                   }
                    pre=position;
                }catch (Exception e){  parent.getChildAt(position).setBackgroundColor(Color.parseColor("#000"));}

            }
        });


                /** setting up grid view */


                try {
                    if (Intent.ACTION_SEND_MULTIPLE.equals(getIntent().getAction())
                            && getIntent().hasExtra(Intent.EXTRA_STREAM)) {
                        ArrayList<Parcelable> list =
                                getIntent().getParcelableArrayListExtra(Intent.EXTRA_STREAM);
                        bitmap=new Bitmap[list.size()];
                        names=new String[list.size()];

                        int i=0,j=0;
                        String temp[];
                        Bitmap tempbitmap;
                        for (Parcelable parcel : list) {
                            Uri uri = (Uri) parcel;
                            String sourcepath=getRealPathFromURI(getBaseContext(),uri);
                            /// do things here with each image source path.
                           temp=sourcepath.split("/");
                            j=temp.length-1;
                            names[i]=temp[j];
                            bitmap[i]=mediatype.generateThumb(sourcepath,getBaseContext());
                            i++;


                        }
                        Toast.makeText(getBaseContext(),"Items selected:"+names.length, Toast.LENGTH_SHORT).show();
                         //adding gridview to app
                        gridadaptor adaptor=new gridadaptor(getBaseContext(),bitmap,names);
                        gridview.setAdapter(adaptor);
                    }
                } catch (Exception e) {
                    Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                }





    }
    /** get real path to file from uri */
    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}

