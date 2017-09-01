package com.pictoreal.pictobuzz.activities;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pictoreal.pictobuzz.CheckInternetConnection;
import com.pictoreal.pictobuzz.DownloadFile;
import com.pictoreal.pictobuzz.MyAdapter;
import com.pictoreal.pictobuzz.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.pictoreal.pictobuzz.CheckInternetConnection.INTERNET_STATUS_AVAILABLE;
import static com.pictoreal.pictobuzz.CheckInternetConnection.INTERNET_STATUS_UNAVAILABLE;

public class ViewItem extends AppCompatActivity {

   /*
   * 10/2/2017
   * Akshay Patel
   *
   * */

    private static Handler connectionStatusHandler;
    private String post_type;
    String key1;
    private String postUrl;
    private Long postNoticeNumber;
    private ArrayList<String> Image1;
    private ArrayList<String> FileUrl;
    public static Context mContext;

    private ImageView mPostSingleimage;
    private TextView mPostTitle;
    private TextView mPostdesc;
    private TextView mPostDate;
    private TextView mPostOther;
    private TextView mPostNoticeNumber;
    private Button mPostDownload;
    private RecyclerView mRecyclerView;
    String postTitle ;


    private static String TAG="===ViewItem";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item);

        DatabaseReference mPostReference;
        mContext = getBaseContext();
        post_type = getIntent().getExtras().getString("typeOfList");
        key1 = getIntent().getExtras().getString("KEY");
        mPostReference = FirebaseDatabase.getInstance().getReference()
                .child(post_type);

        setUiReferences();
        mPostReference.child(key1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String postDate=((String)dataSnapshot.child("DateofUpload").getValue()).split(",")[0];
                String postDescription = (String) dataSnapshot.child("Description").getValue();
                String postImage = (String) dataSnapshot.child("Image").getValue();
                String postOther=(String)dataSnapshot.child("Other").getValue();
                postTitle = (String) dataSnapshot.child("Title").getValue();
                postUrl= (String) dataSnapshot.child("Url").getValue();
                FileUrl=(ArrayList<String>)dataSnapshot.child("FileUrl").getValue();
                postNoticeNumber=(Long)dataSnapshot.child("NoticeNumber").getValue();
                Image1=(ArrayList<String>)dataSnapshot.child("Image1").getValue();


              try {

                  if(Image1!=null){
                      Log.i(TAG, "DataType of Image1" + dataSnapshot.child("Image1").getValue().getClass().getName());
                      String[] imageUrlArray = Image1.toArray(new String[Image1.size()]);
                      MyAdapter myAdapter = new MyAdapter(ViewItem.this, imageUrlArray);
                      RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(ViewItem.this, 2);
                      mRecyclerView.setLayoutManager(mLayoutManager);
                      mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                      mRecyclerView.setAdapter(myAdapter);
                  }

                  if(postDate!=null) {
                      mPostDate.setText("Date of Upload : "+ postDate + "\n");
                  }
                  if(postDescription!=null) {
                      mPostdesc.setText(postDescription);
                  }

                  if(!postImage.isEmpty()&&postImage!=null) {
                      Picasso.with(ViewItem.this).load(postImage).into(mPostSingleimage);
                  }
                  else {mPostSingleimage.setVisibility(View.GONE);}
                  if(postNoticeNumber!=null) {
                      mPostNoticeNumber.setText(mPostNoticeNumber.getText() + String.valueOf(postNoticeNumber) + "\n");
                  }

                  if(postOther!=null) {
                      mPostOther.setText(postOther);
                  }
                  if(postTitle!=null) {
                      mPostTitle.setText(postTitle + "\n");
                      mPostTitle.setTypeface(null, Typeface.BOLD);    //Notice title==bold  added on 25/2/17
                  }



                if(FileUrl==null)
                    mPostDownload.setVisibility(View.INVISIBLE);
                else
                    mPostDownload.setOnClickListener(buttonDownloadClick);

                if(postOther!=null)
                    mPostOther.setVisibility(View.VISIBLE);
              }
              catch (NullPointerException e)
              {
                  Log.i(TAG,"NULL POINTER EXCEPTION in loading data");

              }
            }//[END on Datachanged ]

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }// [End Function onCreate]


    //[Startfunction onClickButtonDownload]

    View.OnClickListener buttonDownloadClick=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            connectionStatusHandler=new Handler(){
                @Override
                public void handleMessage(Message msg){
                    if(msg.what == INTERNET_STATUS_AVAILABLE){
                        try {
                            if (FileUrl != null)
                                for (int i = 0; i < FileUrl.size(); i++) {
                                    Log.i(TAG, "ON BUTTON DOWNLAOD CLICK TRYING TO DOWNLOAD");
                                    new DownloadFile(getBaseContext()).execute(new String[]{FileUrl.get(i), post_type, String.valueOf(postNoticeNumber) + postTitle, String.valueOf(postNoticeNumber) + "-(" + (i + 1) + ")"});
                                }
                        }
                        catch (NullPointerException e)
                        {
                            Log.i(TAG,"NULL POINTER IN BUTTON DOWNLOAD CLICK FILEURL");
                        }
                    }else if(msg.what == INTERNET_STATUS_UNAVAILABLE)
                    {
                        Toast.makeText(ViewItem.this,"Internet connection unavailable",Toast.LENGTH_SHORT).show();

                        Log.i(TAG,"Error at handleMessage connectionStatusHandler OnClick");
                    }
                }
            };

            CheckInternetConnection checkInternetConnection=new CheckInternetConnection(getApplicationContext(),connectionStatusHandler);
            checkInternetConnection.execute();

        }//[End on Click]
    };
    //[End function onClickButtonDownload]



    private  void setUiReferences(){

        mRecyclerView=(RecyclerView)findViewById(R.id.recycler_view_image1);
        mPostTitle = (TextView) findViewById(R.id.textView_notice_title);
        mPostdesc = (TextView) findViewById(R.id.textView_description);
        mPostSingleimage = (ImageView) findViewById(R.id.imageView2);
        mPostDate=(TextView)findViewById(R.id.textView_date);
        mPostOther=(TextView)findViewById(R.id.textView_other);
        mPostNoticeNumber=(TextView)findViewById(R.id.textView_notice_number);
        mPostDownload=(Button)findViewById(R.id.button_url);

    }

    public static Context getContext() {
        return mContext;
    }


}// [End class ViewItem]







