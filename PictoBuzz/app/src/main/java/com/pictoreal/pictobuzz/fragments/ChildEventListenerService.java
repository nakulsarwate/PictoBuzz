package com.pictoreal.pictobuzz.fragments;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pictoreal.pictobuzz.R;
import com.pictoreal.pictobuzz.activities.MainActivity;

import static com.pictoreal.pictobuzz.activities.MainActivity.intentViewArtCirle;
import static com.pictoreal.pictobuzz.activities.MainActivity.intentViewComp;
import static com.pictoreal.pictobuzz.activities.MainActivity.intentViewEntc;
import static com.pictoreal.pictobuzz.activities.MainActivity.intentViewFE;
import static com.pictoreal.pictobuzz.activities.MainActivity.intentViewIt;
import static com.pictoreal.pictobuzz.activities.MainActivity.intentViewPasc;
import static com.pictoreal.pictobuzz.activities.MainActivity.intentViewPictoreal;
import static com.pictoreal.pictobuzz.activities.MainActivity.intentViewieee;

public class ChildEventListenerService extends Service {

    private DatabaseReference mPostReferenceComp;
    private DatabaseReference mPostReferencePASC;
    private DatabaseReference mPostReferencePISB;
    private DatabaseReference mPostReferenceArtCircle;
    private DatabaseReference mPostReferencePictoreal;
    private DatabaseReference mPostReferenceFE;
    private DatabaseReference mPostReferenceIT;
    private DatabaseReference mPostReferenceEnTc;

    @Override
    public void onCreate() {
        Log.e("===Service","onCreate()");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //onTaskRemoved(intent);
        Log.e("===Service","onStartCommand()");

        mPostReferenceComp = FirebaseDatabase.getInstance().getReference()
                .child(intentViewComp);
        mPostReferencePASC = FirebaseDatabase.getInstance().getReference().child(intentViewPasc);
        mPostReferencePISB = FirebaseDatabase.getInstance().getReference().child(intentViewieee);
        mPostReferencePictoreal = FirebaseDatabase.getInstance().getReference().child(intentViewPictoreal);
        mPostReferenceArtCircle = FirebaseDatabase.getInstance().getReference().child(intentViewArtCirle);
        mPostReferenceFE = FirebaseDatabase.getInstance().getReference().child(intentViewFE);
        mPostReferenceIT = FirebaseDatabase.getInstance().getReference().child(intentViewIt);
        mPostReferenceEnTc=FirebaseDatabase.getInstance().getReference().child(intentViewEntc);

        ChildEventListener c = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Intent targetIntent = new Intent(getApplicationContext(), MainActivity.class);
                PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0, targetIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                Notification noti = new android.support.v7.app.NotificationCompat.Builder(getApplicationContext())
                        .setContentTitle("NOTIFICATION")
                        .setContentText("Child Added")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentIntent(contentIntent).build();

                noti.flags |= Notification.FLAG_AUTO_CANCEL;

                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                notificationManager.notify(0, noti);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };


        //College Referncees
        mPostReferenceComp.addChildEventListener(c);
        mPostReferenceEnTc.addChildEventListener(c);
        mPostReferenceIT.addChildEventListener(c);
        mPostReferenceFE.addChildEventListener(c);

        //Other References
        mPostReferencePASC.addChildEventListener(c);
        mPostReferencePISB.addChildEventListener(c);
        mPostReferenceArtCircle.addChildEventListener(c);
        mPostReferencePictoreal.addChildEventListener(c);

        Toast.makeText(getApplicationContext(),"Serivce",Toast.LENGTH_LONG).show();

        return START_STICKY_COMPATIBILITY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /*@Override
    public void onTaskRemoved(Intent rootIntent) {
        Intent restartService =  new Intent(getApplicationContext(), this.getClass());
        restartService.setPackage(getPackageName());
        startService(restartService);
        super.onTaskRemoved(rootIntent);
    }
*/
    @Override
    public void onDestroy() {

        super.onDestroy();
    }
}
