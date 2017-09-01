package com.pictoreal.pictobuzz;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import com.pictoreal.pictobuzz.activities.ViewItem;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by row_hammer on 19/3/17.
 */

//[Start CheckInternetConnection]
public class CheckInternetConnection extends AsyncTask<Void, Void, Void> {

    public static int INTERNET_STATUS_AVAILABLE=0;
    public static int INTERNET_STATUS_UNAVAILABLE=-1;



    boolean status;
    private static String TAG ="===CheckInternet";
    private Context context;
    private Handler connectionStatusHandler;
    public CheckInternetConnection(Context context, Handler connectionStatusHandler)
    {
        this.context=context;
        this.connectionStatusHandler=connectionStatusHandler;

    }
    @Override
    protected Void doInBackground(Void... voids) {
        //Check if Download Button is pressed
        status=isConnected(context);
        Log.i(TAG," DoInbackground Connection Status" + status);
        return null;
    }
    @Override
    protected void onPreExecute() {
        Log.i(TAG," onPreExecute Checking Connection Status");
    }

    public boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager)context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            try {
                URL url = new URL("http://www.google.com/");
                HttpURLConnection urlc = (HttpURLConnection)url.openConnection();
                urlc.setRequestProperty("User-Agent", "test");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(1000); // mTimeout is in seconds
                urlc.connect();
                if (urlc.getResponseCode() == 200) {
                    connectionStatusHandler.sendEmptyMessage(INTERNET_STATUS_AVAILABLE);
                    return true;
                } else {
                    connectionStatusHandler.sendEmptyMessage(INTERNET_STATUS_UNAVAILABLE);
                    return false;
                }
            } catch (IOException e) {
                Log.i("warning", "Error checking internet connection", e);
                connectionStatusHandler.sendEmptyMessage(INTERNET_STATUS_UNAVAILABLE);
                return false;
            }
        }
        connectionStatusHandler.sendEmptyMessage(INTERNET_STATUS_UNAVAILABLE);
        return false;
    }
}//[End Async Class]