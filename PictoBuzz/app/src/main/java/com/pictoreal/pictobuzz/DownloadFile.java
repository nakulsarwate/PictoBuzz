package com.pictoreal.pictobuzz;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import static android.content.Context.NOTIFICATION_SERVICE;


/**
 * Created by row_hammer on 19/3/17.
 *
 */
// DownloadFile AsyncTask
public class DownloadFile extends AsyncTask<String, Integer, String> {
    /**
     * USse this class to downlaod the file from internet.
     * Input: Url of image to download
     *
     *
     */
    private static  int notificationID=0;
    private static String TAG ="DownLoadFile";
    Context context;
    PopupWindowHandler popupWindowHandler;
    String fileName="";
    String filePath="";
    String fileLocation="";
    String fileExtension="";
    private  static  String STORAGE_FOLDER_NAME="PICTOBUZZ";
    public DownloadFile(Context context)
    {
        this.context=context;
        popupWindowHandler=new PopupWindowHandler(context);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        popupWindowHandler.setPopUp();
    }

    @Override
    protected String doInBackground(String... Strings) {

        try {

            /*0th Index Contains Url to Download
            * 1st index contains Post_type = college/comp,College/Entc,PISB,PASC ,etc
            * 2nd index contains Folder name to create e.g. PICTOBUZZ/Comp-70
            * 3rd index contains File name to save with
            */
            URL url = new URL(Strings[0]);
            URLConnection connection = url.openConnection();
            connection.connect();

            // Detect the file lenghth
            int fileLength = connection.getContentLength();

            // Locate storage location
            fileLocation= Environment.getExternalStorageDirectory()
                    .getPath();
            File pictoBuzzFolder = new File(Environment.getExternalStorageDirectory().toString()+"/"+STORAGE_FOLDER_NAME+"/"+Strings[1]+"/"+Strings[2]);
            if(!pictoBuzzFolder.exists())
            {
                //IF FOLDER DOES NOT EXIST MAKE ONE
               if(! pictoBuzzFolder.mkdirs())
               {
                   Log.i(TAG,"ERROR CREATING STORAGE FOLDER");
                   Toast.makeText(context,"ERROR CREATING STORAGE FOLDER",Toast.LENGTH_SHORT).show();
               }
            }

            fileLocation=fileLocation+"/"+STORAGE_FOLDER_NAME+"/"+Strings[1]+"/"+Strings[2];
            Uri uri = Uri.parse(Strings[0]);
            fileExtension= getMimeType(context,uri);
            fileName=Strings[1].replace("/","-")+String.valueOf(Strings[3]);
            filePath=fileLocation +"/"+fileName+"."+fileExtension;
            Log.i(TAG,"FILE PATH : "+ filePath);

            File file = new File(filePath);
            if(file.exists())
            {
                //File already present
                Log.i(TAG,"File already present");
            }
            {
                //File Does not exist
                // Download the file
                InputStream input = new BufferedInputStream(url.openStream());

                // Save the downloaded file

                //Earlier it was :

                OutputStream output = new FileOutputStream(filePath);

                byte data[] = new byte[1024];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    total += count;
                    // Publish the progress
                    publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                }

                // Close connection
                output.flush();
                output.close();
                input.close();

            }//[END else for file already present validation]

        } catch (Exception e) {
            // Error Log
            Log.e("TAG","Error in Downlaoding"+ e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        super.onProgressUpdate(progress);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
     //   Toast.makeText(ViewItem.this, "Download Complete", Toast.LENGTH_SHORT).show();
        CreateNotification(filePath, fileName,fileExtension,fileLocation);
        popupWindowHandler.dismissPopup();
    }

    public void CreateNotification(String filePath,String fileName,String fileMimeType,String fileLocation)
    {

        Log.i("===ViewItem"," Trying to open"+ filePath);
        Log.i("===ViewItem"," Extension of file"+  fileMimeType);
        Log.i("===ViewItem"," Name of file"+  fileName);
        Log.i("===ViewItem"," Location of file"+  fileLocation);


        File file = new File( filePath);
        //Uri uri = Uri.fromFile(file);

       // Intent intent = new Intent(Intent.ACTION_VIEW);
     //   intent.setAction("com.sec.android.app.myfiles.PICK_DATA_MULTIPLE");
     //   intent.setDataAndType(Uri.fromFile(file),  "*/*");
        Intent toLaunch = new Intent();
        toLaunch.setAction(android.content.Intent.ACTION_VIEW);
        toLaunch.setDataAndType(Uri.fromFile(file), MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileMimeType));  // you can also change jpeg to other types

        PendingIntent pIntent = PendingIntent.getActivity(context, 0,toLaunch, 0);
        Notification noti = new NotificationCompat.Builder(context)
                .setContentTitle("Download completed")
                .setContentText(fileName)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pIntent).build();

   //     noti.flags |= Notification.FLAG_AUTO_CANCEL;
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify(notificationID++, noti);
    }//[END CREATE NOTIFICATION]


    //[GET THE type of File form the Uri]
    public static String getMimeType(Context context, Uri uri) {
        String extension;

        //Check uri format to avoid null
        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            //If scheme is a content
            final MimeTypeMap mime = MimeTypeMap.getSingleton();
            extension = mime.getExtensionFromMimeType(context.getContentResolver().getType(uri));
        } else {
            //If scheme is a File
            //This will replace white spaces with %20 and also other special characters. This will avoid returning null values on file name with spaces and special characters.
            extension = MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(new File(uri.getPath())).toString());

        }

        return extension;
    } //[ END GET THE type of File form the Uri]




}//[END DownloadFile
