package com.bhushan.testapplication.activity;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bhushan.testapplication.R;
import com.bhushan.testapplication.others.Global;
import com.bhushan.testapplication.others.ViewDialog;
import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    TextView name,pincode,address,phno,email,username;
    ImageView useredit,passup;
    CircleImageView profilepic;
    ViewDialog progressDialoge;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name = findViewById(R.id.name);
        address = findViewById(R.id.address);
        pincode = findViewById(R.id.pincode);
        email = findViewById(R.id.email);
        phno = findViewById(R.id.phno);
        useredit = findViewById(R.id.useredit);
        username = findViewById(R.id.username);
        passup = findViewById(R.id.passup);
        profilepic = findViewById(R.id.profilepic);
        progressDialoge = new ViewDialog(ProfileActivity.this);

        name.setText(Global.name);
        address.setText(Global.address);
        pincode.setText(Global.pincode);
        email.setText(Global.email);
        phno.setText(Global.phno);
        username.setText(Global.username);
        Glide.with(this).load(Global.picpath).into(profilepic);

        useredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserNamePopup();
            }
        });

        passup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    public void updateUserNamePopup() {
        final Dialog dialog = new Dialog(ProfileActivity.this);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.change_username);
        CardView buttonNo = dialog.findViewById(R.id.no);
        CardView buttonYes = dialog.findViewById(R.id.yes);
        final EditText username = dialog.findViewById(R.id.username);
        buttonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserName(username.getText().toString().trim(),dialog);
               // dialog.dismiss();
            }
        });
        buttonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                dialog.dismiss();
            }
        });
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    private void updateUserName(String usern,Dialog dialog) {
        if (usern.isEmpty()) {
            username.setError("User Name is required");
            username.requestFocus();
            return;
        }

        if (usern.length() < 8) {
            username.setError("Enter a valid User name");
            username.requestFocus();
            return;
        }

    }

    public void changePasswordPopup() {
        final Dialog dialog = new Dialog(ProfileActivity.this);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.change_password);
        CardView buttonNo = dialog.findViewById(R.id.no);
        CardView buttonYes = dialog.findViewById(R.id.yes);
        final EditText oldpass = dialog.findViewById(R.id.oldpass);
        final EditText newpass = dialog.findViewById(R.id.newpass);
        final EditText confnewpass = dialog.findViewById(R.id.confnewpass);
        buttonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword(oldpass.getText().toString().trim(),newpass.getText().toString().trim(),confnewpass.getText().toString().trim(),dialog);
                // dialog.dismiss();
            }
        });
        buttonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    private void changePassword(String oldpass,String newpass,String confnewpass,Dialog dialog) {
        if (oldpass.isEmpty()) {
            return;
        }

        if (oldpass.length() < 8) {
            return;
        }

        if (newpass.isEmpty()) {
            return;
        }

        if (newpass.length() < 8) {
            return;
        }

        if (confnewpass.isEmpty()) {
            return;
        }

        if (confnewpass.length() < 8) {
            return;
        }

        if (!newpass.equals(confnewpass)) {
            return;
        }



    }
}
