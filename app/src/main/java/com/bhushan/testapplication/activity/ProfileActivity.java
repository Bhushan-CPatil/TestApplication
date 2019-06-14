package com.bhushan.testapplication.activity;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bhushan.testapplication.R;
import com.bhushan.testapplication.api.RetrofitClient;
import com.bhushan.testapplication.others.Global;
import com.bhushan.testapplication.others.ViewDialog;
import com.bhushan.testapplication.pojo.DefaultResponse;
import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProfileActivity extends AppCompatActivity {

    TextView name,pincode,address,phno,email,username;
    ImageView useredit,passup;
    CircleImageView profilepic;
    ViewDialog progressDialoge;
    EditText usernamepop;
    EditText oldpass,newpass,confnewpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
                changePasswordPopup();
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
        usernamepop = dialog.findViewById(R.id.username);
        buttonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        buttonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserName(usernamepop.getText().toString().trim(),dialog);
                //dialog.dismiss();
            }
        });
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    private void updateUserName(final String usern, final Dialog dialog) {
        if (usern.isEmpty()) {
            usernamepop.setError("User Name is required");
            usernamepop.requestFocus();
            return;
        }

        if (usern.length() < 8) {
            usernamepop.setError("Enter a valid User name");
            usernamepop.requestFocus();
            return;
        }

        progressDialoge.show();
        Call<DefaultResponse> call = RetrofitClient.getInstance().getApi().ft_changeUsername(Global.uid,usern);
        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                progressDialoge.dismiss();
                DefaultResponse res =response.body();
                if(res.isAccess()){
                    Global.username = usern;
                    username.setText(Global.username);
                    dialog.dismiss();
                }else {
                    Toast.makeText(ProfileActivity.this, res.getErrormsg(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                progressDialoge.dismiss();
                Toast.makeText(ProfileActivity.this, "Something went wrong !", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void changePasswordPopup() {
        final Dialog dialog = new Dialog(ProfileActivity.this);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.change_password);
        CardView buttonNo = dialog.findViewById(R.id.no);
        CardView buttonYes = dialog.findViewById(R.id.yes);
        oldpass = dialog.findViewById(R.id.oldpass);
        newpass = dialog.findViewById(R.id.newpass);
        confnewpass = dialog.findViewById(R.id.confnewpass);
        buttonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        buttonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword(oldpass.getText().toString().trim(),newpass.getText().toString().trim(),confnewpass.getText().toString().trim(),dialog);
                // dialog.dismiss();

            }
        });
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    private void changePassword(String oldpass1, String newpass1, String confnewpass1, final Dialog dialog) {
        if (oldpass1.isEmpty()) {
            oldpass.setError("Old password is required");
            oldpass.requestFocus();
            return;
        }

        if (oldpass1.length() < 8) {
            oldpass.setError("Old password is to short");
            oldpass.requestFocus();
            return;
        }

        if (newpass1.isEmpty()) {
            newpass.setError("New password is required");
            newpass.requestFocus();
            return;
        }

        if (newpass1.length() < 8) {
            newpass.setError("New password is to short");
            newpass.requestFocus();
            return;
        }

        if (confnewpass1.isEmpty()) {
            confnewpass.setError("Confirm password is required");
            confnewpass.requestFocus();
            return;
        }

        if (confnewpass1.length() < 8) {
            confnewpass.setError("Confirm password is to short");
            confnewpass.requestFocus();
            return;
        }

        if (!newpass1.equals(confnewpass1)) {
            confnewpass.setError("Confirm password not matched");
            confnewpass.requestFocus();
            return;
        }

        progressDialoge.show();
        Call<DefaultResponse> call = RetrofitClient.getInstance().getApi().ft_updatePass(Global.uid,confnewpass1,oldpass1);
        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                progressDialoge.dismiss();
                DefaultResponse res =response.body();
                if(res.isAccess()){
                    dialog.dismiss();
                    Toast.makeText(ProfileActivity.this, "Password changed successfully", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(ProfileActivity.this, res.getErrormsg(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                progressDialoge.dismiss();
                Toast.makeText(ProfileActivity.this, "Something went wrong !", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        } return true;
    }

    @Override
    public void onBackPressed() {
        finish();
        ProfileActivity.this.overridePendingTransition(R.anim.trans_right_in,R.anim.trans_right_out);
    }
}
