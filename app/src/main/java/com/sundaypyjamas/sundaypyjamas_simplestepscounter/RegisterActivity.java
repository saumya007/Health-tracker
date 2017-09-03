package com.sundaypyjamas.sundaypyjamas_simplestepscounter;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.TextAppearanceSpan;
import android.text.style.TypefaceSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sundaypyjamas.sundaypyjamas_simplestepscounter.Util.Util;
import com.sundaypyjamas.sundaypyjamas_simplestepscounter.beans.userprofile;

import org.w3c.dom.Text;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class RegisterActivity extends AppCompatActivity {
    // declaration of edit text for user input of user name, email, phone number, email, password and confirm password
    private EditText sp_uname_reg, sp_pass_reg, sp_email_reg, sp_cnfp_reg, sp_phone;
    private CheckBox sp_check_tnc; // check box for terms and conditions
    private TextView sp_tnc_text,sp_reg_title; // terms and conditions text
    private Button sp_register, sp_back; // buttons for registration and go back to main screen
    // instance of firebase authentication client
    private FirebaseAuth mAuth;
    private TextInputLayout sp_unamel,sp_emaill,sp_passl,sp_cnfpassl,sp_phonel;
    // firebase database reference for making a user data base
    DatabaseReference sp_firebaseref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        sp_firebaseref = FirebaseDatabase.getInstance().getReference();
        sp_uname_reg = (EditText) findViewById(R.id.username_card_register);
        sp_email_reg = (EditText) findViewById(R.id.email_card_register);
        sp_pass_reg = (EditText) findViewById(R.id.etPassword);
        sp_cnfp_reg = (EditText) findViewById(R.id.cnf_pass);
        sp_phone = (EditText) findViewById(R.id.phone_register);
        sp_tnc_text = (TextView) findViewById(R.id.text_tnc_register);
        sp_check_tnc = (CheckBox) findViewById(R.id.checkbox_tnc);
        sp_register = (Button) findViewById(R.id.register_button_card);
        sp_back = (Button) findViewById(R.id.back);
        sp_reg_title = (TextView)findViewById(R.id.header_register_card);
        sp_unamel = (TextInputLayout)findViewById(R.id.etUsernameReg);
        sp_emaill = (TextInputLayout)findViewById(R.id.etEmailLayout);
        sp_passl = (TextInputLayout)findViewById(R.id.etPasswordLayout);
        sp_cnfpassl = (TextInputLayout)findViewById(R.id.cnf_pass_layput);
        sp_phonel = (TextInputLayout)findViewById(R.id.etphoneLayout);
        sp_emaill = (TextInputLayout)findViewById(R.id.etEmailLayout);
        Util.setRalewayThin(getApplicationContext(), sp_reg_title,sp_uname_reg, sp_tnc_text, sp_email_reg, sp_pass_reg, sp_cnfp_reg, sp_phone, sp_register, sp_back);
        Util.setRalewayThin(getApplicationContext(),sp_unamel,sp_phonel,sp_cnfpassl,sp_passl,sp_emaill);
        // setting the part of license agreement text to bold red
        SpannableString span = new SpannableString(sp_tnc_text.getText().toString());
        span.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getApplicationContext(), R.color.reddark)), 21, span.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        span.setSpan(new StyleSpan(Typeface.BOLD), 21, span.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        sp_tnc_text.setText(span); // apply the spannable string to a the license agreement text
        // implentation of drag animation on back to login button
        final int width_btn = sp_back.getLayoutParams().width;
        sp_back.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int xw = v.getLayoutParams().width;

                DisplayMetrics display = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(display);
                int screen_width = display.widthPixels;
                int new_width_btn = width_btn;
                int x = (int) event.getX();
                x = x - (int) (screen_width * 0.25);
                if (x <= 0) {
                    x = 0;
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    new_width_btn = new_width_btn + x;
                    Log.e("x", x + "width" + new_width_btn);
                    v.getLayoutParams().width = new_width_btn;
                    sp_back.setText("");
                    v.requestLayout();
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (xw <= 0.90 * screen_width) {
                        sp_back.setText("Login");
                        v.getLayoutParams().width = width_btn;
                        v.requestLayout();
                    } else if (xw > 0.9 * screen_width) {
                        sp_back.setAlpha(1.0f - ((float) xw / (float) screen_width));
                        sp_back.setVisibility(View.GONE);
                        // start register activity from main activity
                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }

                return true;
            }
        });
       /* sp_pass_reg.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0; // unique id for left drawble
                final int DRAWABLE_TOP = 1; // unique id for top drawble
                final int DRAWABLE_RIGHT = 2; // unique id for right drawble
                final int DRAWABLE_BOTTOM = 3; // unique id for bottom drawble
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (event.getRawX() >= (sp_pass_reg.getRight() - sp_pass_reg.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if (sp_pass_reg.getInputType() == InputType.TYPE_TEXT_VARIATION_PASSWORD) // getting the input type of edit text
                        {
                            sp_pass_reg.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD); // set the input type of edit text
                        } else {
                            sp_pass_reg.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        }
                        return true;
                    }
                }
                else if(event.getAction() ==MotionEvent.ACTION_UP)
                {
                    sp_pass_reg.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);

                }
                return false;
            }
        });*/
       /* sp_cnfp_reg.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0; // unique id for left drawble
                final int DRAWABLE_TOP = 1; // unique id for top drawble
                final int DRAWABLE_RIGHT = 2; // unique id for right drawble
                final int DRAWABLE_BOTTOM = 3; // unique id for bottom drawble
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (event.getRawX() >= (sp_cnfp_reg.getRight() - sp_cnfp_reg.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if (sp_cnfp_reg.getInputType() == InputType.TYPE_TEXT_VARIATION_PASSWORD) // getting the input type of edit text
                        {
                            sp_cnfp_reg.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD); // set the input type of edit text
                        } else {
                            sp_cnfp_reg.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        }
                        return true;
                    }
                }
                else if(event.getAction() ==MotionEvent.ACTION_UP)
                {
                    sp_cnfp_reg.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);

                }
                return false;
            }
        });*/
        sp_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sp_uname_reg.getText().toString().isEmpty() || sp_email_reg.toString().isEmpty() || sp_phone.toString().isEmpty() ||
                        sp_pass_reg.getText().toString().isEmpty() || sp_cnfp_reg.getText().toString().isEmpty()) {
                    if (sp_uname_reg.getText().toString().isEmpty()) {
                        Toasty.error(RegisterActivity.this, "Please enter a valid username", Toast.LENGTH_SHORT).show();
                        sp_uname_reg.requestFocus();
                    } else if (sp_email_reg.getText().toString().isEmpty()) {
                        Toasty.error(RegisterActivity.this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
                        sp_email_reg.requestFocus();
                    } else if (sp_phone.getText().toString().isEmpty()) {
                        Toasty.error(RegisterActivity.this, "Please enter a valid phone number", Toast.LENGTH_SHORT).show();
                        sp_phone.requestFocus();
                    } else if (sp_pass_reg.getText().toString().isEmpty()) {
                        Toasty.error(RegisterActivity.this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
                        sp_pass_reg.requestFocus();
                    } else if (sp_cnfp_reg.getText().toString().isEmpty()) {
                        Toasty.error(RegisterActivity.this, "Confirm password cannot be empty", Toast.LENGTH_SHORT).show();
                        sp_cnfp_reg.requestFocus();
                    }
                } else if (!sp_pass_reg.getText().toString().equals(sp_cnfp_reg.getText().toString())) {
                    Toasty.error(RegisterActivity.this, "Password and Confirm password dont match",
                            Toast.LENGTH_SHORT).show();
                    sp_cnfp_reg.requestFocus();
                }
                else if(sp_phone.getText().toString().length() <10)
                {
                    Toasty.error(RegisterActivity.this,"Please enter a valid phone number",Toast.LENGTH_SHORT).show();
                }
                else if(!sp_check_tnc.isChecked()){
                    Toasty.error(RegisterActivity.this,"Please accept the license agreement",Toast.LENGTH_SHORT).show();
                    sp_check_tnc.requestFocus();
                }
                else {
                    String email_reg = sp_uname_reg.getText().toString() + "@sundaypyjamas.com";
                    mAuth.createUserWithEmailAndPassword(email_reg, sp_pass_reg.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                ArrayList<userprofile> sp_profile = new ArrayList<>();
                                sp_profile.add(new userprofile(sp_email_reg.getText().toString(), sp_phone.getText().toString(), sp_pass_reg.getText().toString()));
                                sp_firebaseref.child(sp_uname_reg.getText().toString()).push().setValue(sp_profile);
                                Toasty.success(getApplicationContext(), "Successfully Registered", Toast.LENGTH_SHORT).show();
                            } else {
                                if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                    if (sp_pass_reg.getText().toString().length() < 6) {
                                        Toasty.error(RegisterActivity.this, "Password should be of 6 characters or more",
                                                Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toasty.error(RegisterActivity.this, "Either Username or Password is incorrect",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                } else if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                    Toasty.error(getApplicationContext(), "The username is not available. Please select another username", Toast.LENGTH_SHORT).show();
                                    sp_uname_reg.requestFocus();
                                } else {
                                    Toasty.error(RegisterActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
                }
            }
        });
    }


}
