package com.jtech.www.hide;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.Toast;


import com.face.www.medialib.mediapicker;
import com.face.www.medialib.mediatype;
import com.face.www.medialib.onSelect;
import com.github.angads25.filepicker.controller.DialogSelectionListener;
import com.github.angads25.filepicker.model.DialogConfigs;
import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.ViewPagerItem;
import com.ogaclejapan.smarttablayout.utils.ViewPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.ViewPagerItems;
import com.ogaclejapan.smarttablayout.utils.v13.Bundler;
import com.ogaclejapan.smarttablayout.utils.v13.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v13.FragmentPagerItems;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import it.neokree.materialtabs.MaterialTabHost;


public class main extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    //YOU CAN EDIT THIS TO WHATEVER YOU WANT
    File VIDEOTHUMB;
    File IMAGETHUMB;
    File DOCUMENTTHUMB;
    int PICK_IMAGE_MULTIPLE = 1;
    String imageEncoded;
    List<String> imagesEncodedList;
AlertDialog.Builder dialog;
AlertDialog alert;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



//Tab setup
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("VIDEO"));
        tabLayout.addTab(tabLayout.newTab().setText("PHOTO"));
        tabLayout.addTab(tabLayout.newTab().setText("DOCUMENT"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final pagerAdapter adapter = new pagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        //Tab actions



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /* if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.file) {



            //medialib
            mediapicker mp=new mediapicker();

            mp.setOnSelect(new onSelect() {
                @Override
                public void Result(String[] file) {



                        try {
 for(String t:file)
 {
     //Toast.makeText(getBaseContext(),t,Toast.LENGTH_LONG).show();
 }
                          encrypttask task=new encrypttask();
                            task.execute(file);


                        } catch (Exception e) {
                            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }


                }
            });
            mp.show(this);


        } else if(id==R.id.photo)
        {

            Intent intent = new Intent();
// Show only images, no videos or anything else
            intent.setType("image/*");

            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
// Always show the chooser (if there are multiple options available)
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);

        }
        else if(id==R.id.video)
        {

            Intent intent = new Intent();
// Show only images, no videos or anything else
            intent.setType("video/*");

            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
// Always show the chooser (if there are multiple options available)
            startActivityForResult(Intent.createChooser(intent, "Select Video"), 1);

        }
        else if(id==R.id.document)
        {

            Intent intent = new Intent();
// Show only images, no videos or anything else
            intent.setType("document/*");

            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
// Always show the chooser (if there are multiple options available)
            startActivityForResult(Intent.createChooser(intent, "Select Video"), 1);

        }
        else
        if(R.id.nav_manage==id)
        {
startActivity(new Intent(main.this,settings.class));
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            // When an Image is picked
            if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                imagesEncodedList = new ArrayList<String>();
                if(data.getData()!=null){

                    Uri mImageUri=data.getData();

                    // Get the cursor
                    Cursor cursor = getContentResolver().query(mImageUri,
                            filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imageEncoded  = cursor.getString(columnIndex);
                    cursor.close();

                }else {
                    if (data.getClipData() != null) {
                        ClipData mClipData = data.getClipData();
                        ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
                        for (int i = 0; i < mClipData.getItemCount(); i++) {

                            ClipData.Item item = mClipData.getItemAt(i);
                            Uri uri = item.getUri();
                            mArrayUri.add(uri);
                            // Get the cursor
                            Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
                            // Move to first row
                            cursor.moveToFirst();

                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            imageEncoded  = cursor.getString(columnIndex);
                            imagesEncodedList.add(imageEncoded);
                            cursor.close();

                        }


                        Log.v("LOG_TAG", "Selected Item" + mArrayUri.size());
                        String path[]=new String[mArrayUri.size()];
                        int i=0;
                        for(Uri uri:mArrayUri)
                        {

                            path[i]=getRealPathFromURI(getBaseContext(),uri);
                            //Toast.makeText(getBaseContext(),path[i],Toast.LENGTH_SHORT).show();
                            i++;
                        }
                        encrypttask task=new encrypttask();
                        task.execute(path);

                    }
                }
            } else {
                Toast.makeText(this, "You haven't picked Item",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG)
                    .show();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


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
            dialog=new AlertDialog.Builder(main.this);
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
                pd = new ProgressDialog(main.this);
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
