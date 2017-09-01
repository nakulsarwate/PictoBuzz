package com.pictoreal.pictobuzz;

/**
 * Created by shivani on 26/1/17.
 */

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class FirebaseBackgroundService extends Service {


    private DatabaseReference mPostReference;
    //private Firebase f = new Firebase("https://somedemo.firebaseio-demo.com/");
    private ValueEventListener handler;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i("===FirebaseBackgroundS","onstart");

        return super.onStartCommand(intent, flags, startId);


    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("===FirebaseBackgroundS","oncreate");
       /* mPostReference = FirebaseDatabase.getInstance().getReference()
                .child("College");
        mPostReference.keepSynced(true);
        //
        handler = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot arg0) {
                postNotif(arg0.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        };

        mPostReference.addValueEventListener(handler);*/
    }

   /* private void postNotif(String notifString) {
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        int icon = R.drawable.com_facebook_button_icon;
        Notification notification = new Notification(icon, "Firebase" + Math.random(), System.currentTimeMillis());
//		notification.flags |= Notification.FLAG_AUTO_CANCEL;
        Context context = getApplicationContext();
        CharSequence contentTitle = "Background" + Math.random();
        Intent notificationIntent = new Intent(context, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
        //notification.setLatestEventInfo(context, contentTitle, notifString, contentIntent);
        mNotificationManager.notify(1, notification);
    }*/

}
