package com.pictoreal.pictobuzz;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pictoreal.pictobuzz.activities.ViewItem;
import com.pictoreal.pictobuzz.activities.full_screen_view;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

/**
 * Created by Akshay Patel on 15/2/17.
 *
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private String[] mDataset;
    private Context mContext;
    private static  String TAG="===MyViewHolder";

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public  class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        private ImageView mImageView;
        public MyViewHolder(View v) {
            super(v);
            Log.i(TAG," DataType of ");

            mTextView = (TextView) v.findViewById(R.id.textView_post_image);
            mImageView=(ImageView)v.findViewById(R.id.image_view_thumbnail);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(Context mContext,String[] myDataset) {
        mDataset = myDataset;
        this.mContext=mContext;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        // create a new view
        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post_image, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {


        //Need to chanage this code
        setImageId(mContext,holder.mImageView,mDataset[position]);
        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.mImageView.buildDrawingCache();
                Bitmap bitmap = holder.mImageView.getDrawingCache();

                Intent intent = new Intent(ViewItem.getContext(), full_screen_view.class);
                intent.putExtra("BitmapImage", bitmap);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ViewItem.getContext().startActivity(intent);

                Toast.makeText(mContext,"Click to see fullscreen Image",Toast.LENGTH_SHORT).show();
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }//[End getItemCount]



    private void  setImageId(final Context ctx, final ImageView imageView, final String Image)
    {


        Picasso.with(ctx).load(Image).networkPolicy(NetworkPolicy.OFFLINE).into(imageView, new Callback() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onError() {
                Picasso.with(ctx).load(Image).into(imageView); //if image not found offline, go online and find them 7/1/17
                //made ctx, Image final 7/1/17
            }
        });

    }

}//[End MyAdapter]


