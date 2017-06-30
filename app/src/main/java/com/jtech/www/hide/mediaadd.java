package com.jtech.www.hide;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.face.www.medialib.mediapicker;
import com.face.www.medialib.mediatype;

import java.util.ArrayList;

public class mediaadd extends AppCompatActivity {
    Button encrypt,cancel;
    GridView gridview;
    Bitmap[] bitmap;
    String[] names,path;
    AlertDialog.Builder dialog;
    AlertDialog alert;
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
                       parent.getChildAt(pre).setBackgroundColor(Color.parseColor("#ffffff"));
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

                        path=new String[list.size()];
                        int i=0,j=0;
                        String temp[];
                        Bitmap tempbitmap;
                        for (Parcelable parcel : list) {
                            Uri uri = (Uri) parcel;
                            String sourcepath=getRealPathFromURI(getBaseContext(),uri);
                            /// do things here with each image source path.
                           temp=sourcepath.split("/");
                            path[i]=sourcepath;
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

encrypt.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
encrypttask task=new encrypttask();
        task.execute(names);

    }
});



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

    private class encrypttask extends AsyncTask<String[],String,Void> {
        cryptodb sql = new cryptodb();
        String con = "content://com.log.www/students";
        Uri uri = Uri.parse(con);
        String thumb;
        Bitmap bitmaptest1,bitmaptest2;

        boolean flag=false;

        ProgressDialog pd;

        protected Void doInBackground(String[]... filetemp) {

            Cursor c = managedQuery(uri, null, null, null, "password");
            String file[] = new String[filetemp.length];
            file = filetemp[0];
            c.moveToFirst();

            String pass = c.getString(c.getColumnIndex("password"));
            String f;
            for (int i = 0; i < file.length; i++) {
                try {
                    f = file[i];

                    publishProgress(f);
                    for (int k = 0; k < 123455600; k++) {
                    }
                    bitmaptest1=mediatype.generateThumb(f,getBaseContext());
                    thumb =filestore.getThumbPath(f);


                    if(!(mediatype.type(f)==mediatype.TYPE_DOCUMENT)) {
                        mediatype.saveThumb(thumb, mediatype.generateThumb(f, getBaseContext()));
                        bitmaptest2 = BitmapFactory.decodeFile(thumb);
                    }
                    else

                    {
                        bitmaptest1= BitmapFactory.decodeResource(getResources(),R.mipmap.doc);
                        bitmaptest1= Bitmap.createScaledBitmap(bitmaptest1,200,200,false);
                        mediatype.saveThumb(thumb,bitmaptest1);
                        bitmaptest2 = BitmapFactory.decodeFile(thumb);
                    }
                    if(bitmaptest2.sameAs(bitmaptest1)) {
                        ContentValues values = new ContentValues();
                        values.put(sql.NAME, f);
                        values.put(sql.PASSWORD, pass + "123454321");
                        values.put(sql.Status, "false");
                        Uri uri = getContentResolver().insert(sql.CONTENT_URI, values);


                    }else {
                        Log.d("bitmap test failure **","HERE IS PBLM");
                        flag=true;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("dierr **********","HERE IS PBLM");
                    flag=true;
                }
            }
            return null;
        }

        protected void onPreExecute() {
            dialog=new AlertDialog.Builder(mediaadd.this);
            dialog.setCancelable(false);
            dialog.setTitle("Warning");
            dialog.setMessage("The External Storage is not Writable.you may have mounted it as Storage Device.so file on it canot be encrypted");
            dialog.setIcon(R.mipmap.ic_launcher);
            dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alert=dialog.create();
            if((!mediapicker.isExternalStorageWritable()))
            {
                alert.show()  ;

            }
            else
            {
                pd = new ProgressDialog(mediaadd.this);
                pd.setMessage("Processing files");
                pd.setIndeterminate(true);
                pd.setTitle("Please Wait.....");
                pd.setCancelable(false);
                pd.show();
            }



        }


        protected void onPostExecute(Void aVoid) {

            try {
                pd.dismiss();
                if(flag)
                {
                    dialog.setMessage("failed to access some files,try moving them to some other directory");
                    alert=dialog.create();
                    alert.show();

                    dialog.setTitle(" ");
                    dialog.setMessage("Do you want to go to Wizard");
                    dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(mediaadd.this,MainActivity.class));
                            finish();

                        }
                    });
                    dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                    alert=dialog.create();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


        protected void onProgressUpdate(String... values) {

            pd.dismiss();
            pd.setMessage( "Processing"+values[0]);
            pd.show();

        }
    }

}

