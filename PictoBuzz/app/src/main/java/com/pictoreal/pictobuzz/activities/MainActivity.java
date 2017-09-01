package com.pictoreal.pictobuzz.activities;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.pictoreal.pictobuzz.R;
import com.pictoreal.pictobuzz.fragments.ChildEventListenerService;
import com.pictoreal.pictobuzz.fragments.FragmentCardView;
import com.pictoreal.pictobuzz.fragments.FragmentCollege;
import com.pictoreal.pictobuzz.fragments.FragmentContact;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
  //  public static String intentViewNotices="College";
    public static String intentViewPictoreal="Pictoreal";
    public static  String intentViewCollege="Temp/Comp";
    public static String intentViewieee = "PISB";
    public static String intentViewComp = "College/Comp";
    public static String intentViewIt = "College/IT";
    public static String intentViewEntc = "College/EnTc";
    public static String intentViewFE = "College/FE";
    public static String intentViewPasc = "PASC";
    public static String intentViewArtCirle = "ArtCircle";
    public static String intentViewSports = "Sports";

    //PutExtra key to strat new activity
    public static String intentType="TypeOfList";
    public static  String intentForContact = "ContactUs";
    FirebaseAuth mAuth ;
    private static  String TAG="===MainActivity";
//5/1/2017
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);       //to display icons with original color in navigation drawer

        //start the service    ---25/03/17
       Intent intent = new Intent(this, ChildEventListenerService.class);
        if (isMyServiceRunning() == false) {
            Log.e("===MAINACTIVITY","Service STARTING");

            startService(intent);
        }


        //startService(new Intent(this, ChildEventListenerService.class));
        //startService(new Intent(this,FirebaseBackgroundService.class));

       /* Bundle bundle = new Bundle();
        bundle.putString(intentType,intentViewNotices);*/

      android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        FragmentCollege fragmentCollege=new FragmentCollege();
        //fragmentCollege.setArguments(bundle);
       ft.replace(R.id.frame_layout,fragmentCollege);
       ft.commit();


       mAuth = FirebaseAuth.getInstance();

    }//[END onCreate]

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            // do your stuff
        } else {
            signInAnonymously();
        }


    }

    private void signInAnonymously() {
        mAuth.signInAnonymously().addOnSuccessListener(this, new  OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                // do your stuff
            }
        })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Log.e(TAG, "signInAnonymously:FAILURE", exception);
                    }
                });
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_pictoreal) {

            //View Pictoreal Notices
            Bundle bundle = new Bundle();
            bundle.putString(intentType,intentViewPictoreal);
            android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            FragmentCardView fragmentCardView=new FragmentCardView();
            fragmentCardView.setArguments(bundle);
            ft.replace(R.id.frame_layout,fragmentCardView);
            ft.commit();


            // Handle the camera action
        } else if (id == R.id.nav_college) {

            //View Pictoreal Notices
            android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            FragmentCollege fragmentCollege=new FragmentCollege();
            //fragmentCollege.setArguments(bundle);
            ft.replace(R.id.frame_layout,fragmentCollege);
            ft.commit();
        } else if (id == R.id.nav_share) {

        }  else if(id == R.id.nav_pasc){
            Bundle bundle = new Bundle();
            bundle.putString(intentType,intentViewPasc);
            android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            FragmentCardView fragmentCardView=new FragmentCardView();
            fragmentCardView.setArguments(bundle);
            ft.replace(R.id.frame_layout,fragmentCardView);
            ft.commit();

        } else if(id == R.id.nav_pisb){
            Bundle bundle = new Bundle();
            bundle.putString(intentType,intentViewieee);
            android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            FragmentCardView fragmentCardView=new FragmentCardView();
            fragmentCardView.setArguments(bundle);
            ft.replace(R.id.frame_layout,fragmentCardView);
            ft.commit();

        }else if(id == R.id.nav_artcircle){
            Bundle bundle = new Bundle();
            bundle.putString(intentType,intentViewArtCirle);
            android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            FragmentCardView fragmentCardView=new FragmentCardView();
            fragmentCardView.setArguments(bundle);
            ft.replace(R.id.frame_layout,fragmentCardView);
            ft.commit();
        }else if(id == R.id.nav_contact){
            Bundle bundle = new Bundle();
            android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            FragmentContact fragmentContact=new FragmentContact();
            fragmentContact.setArguments(bundle);
            ft.replace(R.id.frame_layout,fragmentContact);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private boolean isMyServiceRunning() {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (ChildEventListener.class.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

}
