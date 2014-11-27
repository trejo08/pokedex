package net.gr33nme3dia.alex.pokedex;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by skynet on 11-26-14.
 */
public class ImageDownloader extends AsyncTask<Void, Integer, Void> {
    private ProgressBar pb;
    private String url;
    private Button save;
    private Context c;
    private int progress;
    private ImageView img;
    private Bitmap bmp;
    private TextView percent;
    private ImageLoaderListener listener;

    /*****Constructor*******/
    public ImageDownloader(String url, ProgressBar pb, Button save, ImageView img, TextView percent, Context c, Bitmap bmp, ImageLoaderListener listener) {
        this.url = url;
        this.pb = pb;
        this.save = save;
        this.img = img;
        this.percent = percent;
        this.bmp = bmp;
        this.listener = listener;
    }

    public interface ImageLoaderListener {
        void onImageDownloaded(Bitmap bmp);
    }

    @Override
    protected void onPreExecute(){
        progress = 0;
        pb.setVisibility(View.VISIBLE);
        percent.setVisibility(View.VISIBLE);
        Toast.makeText(c, "Starting Downlad", Toast.LENGTH_SHORT).show();

        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void...arg0){
        bmp = getBitmapFromURL(url);
        while (progress < 100) {
            progress += 1;
            publishProgress(progress);
        /*--- an image download usually happens very fast so you would not notice
         * how the ProgressBar jumps from 0 to 100 percent. You can use the method below
         * to visually "slow down" the download and see the progress bein updated ---*/
            SystemClock.sleep(200);
        }
        return null;
    }

    public static Bitmap getBitmapFromURL(String link) {
    /*--- this method downloads an Image from the given URL,
     *  then decodes and returns a Bitmap object
     ---*/
        try {
            URL url = new URL(link);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);

            return myBitmap;

        } catch (IOException e) {
            e.printStackTrace();
            Log.e("getBmpFromUrl error: ", e.getMessage().toString());
            return null;
        }
    }

}
