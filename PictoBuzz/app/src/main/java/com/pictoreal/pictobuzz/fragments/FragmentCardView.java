package com.pictoreal.pictobuzz.fragments;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.pictoreal.pictobuzz.PostDetails;
import com.pictoreal.pictobuzz.R;
import com.pictoreal.pictobuzz.activities.MainActivity;
import com.pictoreal.pictobuzz.activities.ViewItem;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

/**
 * Created by Akshay Patel on 3/1/17.
 *
 */

public class FragmentCardView extends Fragment
{
    View V;
    private RecyclerView mTitleList ;
    private DatabaseReference mPostReference;
    private static String typeOfList; //changed to static
   // private String title="Title";
    private Query orderByNoticeNumber;
   // private ValueEventListener mPostListener;
    private static String TAG ="FRAGMENTCARD VIEW";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        V=inflater.inflate(R.layout.fragment_card_view,container,false);
        //Set reference to RecyclerView
        mTitleList =(RecyclerView)V.findViewById(R.id.title_list);
        mTitleList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getActivity());
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        mTitleList.setLayoutManager(linearLayoutManager);
        typeOfList= getArguments().getString(MainActivity.intentType);
        connectToDatabase();


        return V;
    }//end of onCreateView




    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<PostDetails,FragmentCardView.PostViewHolder> firebaseRecyclerAdapter= new FirebaseRecyclerAdapter<PostDetails, FragmentCardView.PostViewHolder>
                (       PostDetails.class,
                        R.layout.post_row,
                        FragmentCardView.PostViewHolder.class,
                        orderByNoticeNumber

                ) {



            @Override
            protected void populateViewHolder(PostViewHolder viewHolder, PostDetails model, int position) {

              try{

                viewHolder.setTitleId(model.getTitle());
                viewHolder.setDescriptionId(model.getDescription());
                Context context=getActivity();
                  final String post_key = getRef(position).getKey();   //get the key of the post to view  6/1/17

                  //to view each notice seperately -- 6/1/17 Shivani Mehendarge
                  viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View view) {
                          Log.e("===ON CLICK","VIEW CLICKED");
                          Intent singleViewIntent = new Intent(view.getContext(),ViewItem.class);
                          singleViewIntent.putExtra("typeOfList",typeOfList);
                          singleViewIntent.putExtra("KEY",post_key);
                          startActivity(singleViewIntent);
                      }
                  });



                  if(!model.getImage().isEmpty())
                  {
             viewHolder.setImageId(context,model.getImage());
                      Log.i(TAG," Template image to be shown"+ model.getImage());
                  }
                  else
                  {
                      Log.i(TAG," NO Template image to be shown");

                  }

              }catch (IllegalArgumentException a)
              {
                  Log.i(TAG,"Illegeal argument exception" );
                  a.printStackTrace();
              }

            }//[End onpopulateViewHolder]

        };

        // Add value event listener to the post
        // [START post_value_event_listener]
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Intent resultIntent = new Intent(getContext(), MainActivity.class);
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(getContext());
// Adds the back stack for the Intent (but not the Intent itself)
                stackBuilder.addParentStack(MainActivity.class);
// Adds the Intent that starts the Activity to the top of the stack
                stackBuilder.addNextIntent(resultIntent);
                PendingIntent resultPendingIntent =
                        stackBuilder.getPendingIntent(
                                0,
                                PendingIntent.FLAG_UPDATE_CURRENT
                        );
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("===", "loadPost:onCancelled", databaseError.toException());
                // [START_EXCLUDE]
                /*Toast.makeText(PostDetailActivity.this, "Failed to load post.",
                        Toast.LENGTH_SHORT).show();
                */// [END_EXCLUDE]
            }
        };

        mPostReference.addValueEventListener(postListener);
        // [END post_value_event_listener]
  //      mPostListener = postListener;
        mTitleList.setAdapter(firebaseRecyclerAdapter);

    }//[END onStart// ]

    private boolean connectToDatabase()
    {
        mPostReference = FirebaseDatabase.getInstance().getReference()
                .child(typeOfList);
        mPostReference.keepSynced(true);
        orderByNoticeNumber=mPostReference.orderByChild("NoticeNumber").startAt(1);//.orderByChild("NoticeNumber");
        //orderByNoticeNumber=     orderByNoticeNumber.orderByChild("NoticeNumber");
        return  true;

    }//[End connect to database]

      public static  class PostViewHolder extends RecyclerView.ViewHolder{
        View mView;
          //private final Context context;

        public PostViewHolder(View itemView) {
            super(itemView);
            mView=itemView;

        }

        private  void  setTitleId(String Title)
        {
            TextView post_title=(TextView)mView.findViewById(R.id.post_title);
            post_title.setText(Title);
        }

        private void  setImageId(final Context ctx, final String Image)
        {
            final ImageView post_image = (ImageView) mView.findViewById(R.id.post_image);
            if(!Image.isEmpty()) {


                //Picasso.with(ctx).load(Image).into(post_image);
                Picasso.with(ctx).load(Image).networkPolicy(NetworkPolicy.OFFLINE).into(post_image, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        Picasso.with(ctx).load(Image).into(post_image); //if image not found offline, go online and find them 7/1/17
                        //made ctx, Image final 7/1/17
                    }
                });
            }
            else
            {
                //post_image.setVisibility(View.INVISIBLE);
            }

        }
        private  void  setDescriptionId(String Description)
        {
            String finalDescription=Description;
            TextView post_desc=(TextView)mView.findViewById(R.id.post_description);
            if(!TextUtils.isEmpty( Description)){
                if( Description.length() >= 20){
                    finalDescription=  Description.substring(0, 20)+"...";
                }
            }
            post_desc.setText(finalDescription);
        }
//5/1/2017
    }//end of PostViewholder
}//end of Class
