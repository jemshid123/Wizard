package com.face.www.medialib;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.github.angads25.filepicker.controller.DialogSelectionListener;
import com.github.angads25.filepicker.model.DialogConfigs;
import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by jemshid on 13-06-2017.
 */


public class mediapicker  {
    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    onSelect pick;
 String[] extension=new String[]{"avi","mkv","flv","ogg","mov","wmv","mp4","mpg","mpeg","3gp","gif","jpeg","png","jpg","ppt","doc","pptx","pdf","docx","odt","rtf","txt","pot","potx","xml"};


    public void setOnSelect(onSelect listener)
    {
        this.pick=listener;
    }

    public  void  filter(int flag,Context context)
   {

       if (flag == mediatype.TYPE_VIDEO)
       {
       this.extension=new String[]{"avi","mkv","flv","ogg","mov","wmv","mp4","mpg","mpeg","3gp"};
       }
       else if (flag == mediatype.TYPE_IMAGE)
       {
           this.extension=new String[]{"gif","jpeg","png","jpg"};

       }
       else  if(flag == mediatype.TYPE_DOCUMENT)
       {
           this.extension=new String[]{"ppt","doc","pptx","pdf","docx","odt","rtf","txt","pot","potx","xml"};
       }
       else if(flag == mediatype.TYPE_AUDIO)
       {
           this.extension=new String[]{"mp3","wav","aac","ogg","wma","m4a"};
       }

   }

    public  void  customFilter(String[] filter)
    {
        extension=filter;
    }

    public void show(Activity activity)
    {
        verifyStoragePermissions(activity);
        DialogProperties properties = new DialogProperties();
        properties.selection_mode = DialogConfigs.MULTI_MODE;
        properties.selection_type = DialogConfigs.FILE_SELECT;
        properties.root = new File(DialogConfigs.DEFAULT_DIR);
        properties.error_dir = new File(DialogConfigs.DEFAULT_DIR);
        properties.offset = new File(DialogConfigs.DEFAULT_DIR);
        properties.extensions = extension;
        FilePickerDialog dialog = new FilePickerDialog(activity,properties);
        dialog.setTitle("Select a File");
        dialog.show();
        dialog.setDialogSelectionListener(new DialogSelectionListener() {
            @Override
            public void onSelectedFilePaths(String[] files) {
              pick.Result(files);
            }

        });
    }


    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    /** Checks if external storage is available for read and write */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /** Checks if external storage is available to at least read */
    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }


    public  static void copy(String src,String des)
    {
        String strSourceFile=src;
        String strDestinationFile=des;

        try
        {
            //create FileInputStream object for source file
            FileInputStream fin = new FileInputStream(strSourceFile);

            //create FileOutputStream object for destination file
            FileOutputStream fout = new FileOutputStream(strDestinationFile);

            byte[] b = new byte[1024];
            int noOfBytes = 0;

            System.out.println("Copying file using streams");

            //read bytes from source file and write to destination file
            while( (noOfBytes = fin.read(b)) != -1 )
            {
                fout.write(b, 0, noOfBytes);
            }

            Log.d("file coppy *********","file coppied "+strSourceFile+" "+strDestinationFile);

            //close the streams
            fin.close();
            fout.close();

        }
        catch(Exception fnf)
        {
            Log.d("file coppy *********","file not coppied "+strSourceFile+" "+strDestinationFile);


            fnf.printStackTrace();
        }

    }


}
