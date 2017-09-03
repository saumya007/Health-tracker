package com.sundaypyjamas.sundaypyjamas_simplestepscounter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.sundaypyjamas.sundaypyjamas_simplestepscounter.Util.Util;
import com.sundaypyjamas.sundaypyjamas_simplestepscounter.adapters.StepListener;
import com.sundaypyjamas.sundaypyjamas_simplestepscounter.helpers.StepDetector;

public class MainActivity extends AppCompatActivity {
    // declaration of textviews used in  login card
    private TextView sp_header_login, sp_other_indicator, sp_need_help;
    // declaration of  edit text  used in login card
    private EditText sp_uname, sp_pass;
    // Declaration of  buttons
    private Button sp_login_card, sp_register_back;

    // declaration of text input layout
    private TextInputLayout sp_unamel, sp_passl;
    // instance for firebase auth
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initBackgroundImage();
        sp_header_login = (TextView) findViewById(R.id.header_login_card);
        sp_other_indicator = (TextView) findViewById(R.id.optional_text_login_card);
        sp_uname = (EditText) findViewById(R.id.username_card_login);
        sp_pass = (EditText) findViewById(R.id.password_card_login);
        sp_unamel = (TextInputLayout) findViewById(R.id.etUsernameLogin);
        sp_passl = (TextInputLayout) findViewById(R.id.etPasswordLayout);
        sp_login_card = (Button) findViewById(R.id.login_button_card);
        sp_register_back = (Button) findViewById(R.id.register);
        sp_need_help = (TextView) findViewById(R.id.help);
        if (sp_register_back.getVisibility() == View.GONE) {
            sp_register_back.setVisibility(View.VISIBLE);
        }
    /*    // on touch listener on edit text to show and hide contents of edit text
        sp_pass.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0; // unique id for left drawble
                final int DRAWABLE_TOP = 1; // unique id for top drawble
                final int DRAWABLE_RIGHT = 2; // unique id for right drawble
                final int DRAWABLE_BOTTOM = 3; // unique id for bottom drawble
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (event.getRawX() >= (sp_pass.getRight() - sp_pass.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if (sp_pass.getInputType() == InputType.TYPE_TEXT_VARIATION_PASSWORD) // getting the input type of edit text
                        {
                            sp_pass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD); // set the input type of edit text
                        } else {
                            sp_pass.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        }
                        return true;
                    }
                }
                else if(event.getAction() ==MotionEvent.ACTION_UP)
                {
                    sp_pass.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);

                }
                return false;
            }
        });*/
        // settiing custom font for edit text, textview and buttons
        Util.setRalewayThin(getApplicationContext(), sp_header_login, sp_login_card, sp_other_indicator, sp_pass, sp_uname, sp_register_back, sp_need_help);

        Util.setRalewayThin(getApplicationContext(), sp_unamel, sp_passl);
        // implementation of drag animation on register button
        final int x = sp_register_back.getLayoutParams().width;
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("width", x);
        editor.commit();
        sp_register_back.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int y = v.getLayoutParams().width;

                int x1 = (int) event.getX();
                int newwidth = x;
                // getting display metrics for screen width
                DisplayMetrics displayMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int width = displayMetrics.widthPixels;
                if (x1 > 0) {
                    x1 = 0;
                }
                // drag action on register button
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    newwidth = y - x1 / 2;
                    sp_register_back.setText("");
                    v.getLayoutParams().width = newwidth;
                    v.requestLayout(); // update button state


                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y <= width * 0.90) {
                        sp_register_back.setText("Register");
                        v.getLayoutParams().width = x;
                        v.requestLayout(); // update button state
                    } else if (y > width * 0.90) {
                        sp_register_back.setAlpha(1.0f - ((float) y / (float) width));
                        sp_register_back.setVisibility(View.GONE);
                        // start register activity from main activity
                        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                        startActivity(intent);

                    }

                }
                return true;
            }
        });
        sp_login_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void initBackgroundImage() {
        ImageView imgBack = (ImageView) findViewById(R.id.image_back_main);
        // loading image on image view using glide
        Glide.with(this)
                .load(R.drawable.background) // loading the background image onto imageview
                .centerCrop() // cropping the image from center
                .into(imgBack);// mention the target to load the image on
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sp_register_back.getVisibility() == View.GONE) {
            // retrieving shared preference
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            sp_register_back.setAlpha(1.0f); // setting opacity to the button
            sp_register_back.getLayoutParams().width = sp.getInt("width", 400);
            sp_register_back.setVisibility(View.VISIBLE);
        }
    }
}
