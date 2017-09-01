package com.pictoreal.pictobuzz.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.pictoreal.pictobuzz.R;

/**
 * Created by shivani on 25/2/17.
 */

public class full_screen_view extends AppCompatActivity{

    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_view);

        imageView = (ImageView)findViewById(R.id.image_view_full);
        Intent intent = getIntent();
        Bitmap bitmap = (Bitmap) intent.getParcelableExtra("BitmapImage");
        imageView.setImageBitmap(bitmap);
    }
}
