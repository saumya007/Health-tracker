package com.sundaypyjamas.sundaypyjamas_simplestepscounter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initbackground();
    }

    private void initbackground() {
        ImageView sp_back_register = (ImageView) findViewById(R.id.register_background);

        Glide.with(this).load(R.drawable.background).centerCrop().into(sp_back_register);
    }
}
