package com.face.www.medialib;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by jemshid on 13-06-2017.
 */

public class mediatype {


    private static final HashSet<String> video=new HashSet<>(Arrays.asList("avi","mkv","flv","ogg","mov","wmv","mp4","mpg","mpeg","3gp"));
    private  static final  HashSet<String> audio=new HashSet<>(Arrays.asList("mp3","wav","aac","ogg","wma","m4a"));
    private  static final  HashSet<String> image=new HashSet<>(Arrays.asList("gif","jpeg","png","jpg"));
    private static final  HashSet<String> document=new HashSet<>(Arrays.asList("ppt","doc","pptx","pdf","docx","odt","rtf","txt","pot","potx","xml"));

    public  static int TYPE_VIDEO=100;
    public static int TYPE_AUDIO=200;
    public static int TYPE_IMAGE=300;
    public static int TYPE_DOCUMENT=400;
    public static int TYPE_NOT_FOUND=555;
    /** to determine type of file*/
    public static int type(String file)
    {

        try {
            int ext;
            String token[] = file.split("\\.");
            ext = token.length-1 ;


        if (video.contains(token[ext])) return TYPE_VIDEO;
        else if (audio.contains(token[ext])) return TYPE_AUDIO;
        else if (image.contains(token[ext])) return TYPE_IMAGE;
        else if (document.contains(token[ext])) return TYPE_DOCUMENT;
        else return TYPE_NOT_FOUND;
    }catch(Exception e)
        {
            Log.d("ERROR 123",e.getMessage());
            return TYPE_NOT_FOUND;
        }

    }
    /** to generate bitmap for file*/
    public static Bitmap generateThumb(String file, Context context)
    {
        int id;
        Bitmap thumb=null;
        id=type(file);
        try {
            if (id == TYPE_VIDEO) {
                thumb = ThumbnailUtils.createVideoThumbnail(file, MediaStore.Video.Thumbnails.MINI_KIND);
                thumb= Bitmap.createScaledBitmap(thumb,200,200,false);
            } else if (id == TYPE_IMAGE) {
                thumb = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(file),
                        250, 250);
                thumb= Bitmap.createScaledBitmap(thumb,200,200,false);
            }
            else if (id == TYPE_DOCUMENT) {
               // thumb = BitmapFactory.decodeResource(Resources.getSystem(),R.mipmap.document);
                  thumb=null;
            }else {
                Toast.makeText(context, "unsupported thumbnail type", Toast.LENGTH_SHORT).show();
            }


        }catch (Exception e)
        {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d("thumb error ****","failed thumb");
            e.printStackTrace();
        }

        return thumb;
    }

    /** to save bitmap to directory */
    public static  void  saveThumb(String file ,Bitmap bmp)
    {
        FileOutputStream out = null;

        try {
            if(mediapicker.isExternalStorageWritable()) Log.d("readable**********","readable");
            else Log.d("readable**********","readable");
            File filereal=new File(file);
            Log.d("file **************",file);
                out = new FileOutputStream(filereal);
                bmp.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
                // PNG is a loss less format, the compression factor (100) is ignored


        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


}
