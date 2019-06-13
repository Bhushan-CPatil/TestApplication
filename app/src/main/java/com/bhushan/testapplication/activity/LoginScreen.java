package com.bhushan.testapplication.activity;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bhushan.testapplication.R;
import com.bhushan.testapplication.api.RetrofitClient;
import com.bhushan.testapplication.others.Global;
import com.bhushan.testapplication.others.ViewDialog;
import com.bhushan.testapplication.pojo.DefaultResponse;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginScreen extends AppCompatActivity {

    public EditText username;
    public TextInputEditText password;
    public RelativeLayout rl;
    public Button login;
    public TextView newuser;
    ViewDialog progressDialoge;
    boolean allgranted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.activity_login_screen);
        getSupportActionBar().hide();
        username = findViewById(R.id.uid);
        password = findViewById(R.id.pass);
        login = findViewById(R.id.login);
        rl = findViewById(R.id.rl);
        newuser = findViewById(R.id.newuser);
        progressDialoge = new ViewDialog(LoginScreen.this);

        newuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (allgranted) {
                        Intent intent = new Intent(LoginScreen.this, RegisterActivity.class);
                        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(LoginScreen.this, R.anim.trans_left_in, R.anim.trans_left_out).toBundle();
                        startActivity(intent, bndlanimation);
                    } else {
                        requestStoragePermission();
                    }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dologin();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        requestStoragePermission();
    }

    public void dologin() {
        Vibrator vibrator = (Vibrator) getSystemService(LoginScreen.this.VIBRATOR_SERVICE);
        vibrator.vibrate(100);

            if (allgranted) {

//            Log.d("pass encrypt --->", Global.password);
                final String lid = username.getText().toString().trim();
                String pass = password.getText().toString().trim();

                if (lid.isEmpty()) {
                    username.setError("User Name is required");
                    username.requestFocus();
                    return;
                }

                if (lid.length() < 8) {
                    username.setError("Enter a valid User name");
                    username.requestFocus();
                    return;
                }

                if (pass.isEmpty()) {
                    password.setError("Password is required");
                    password.requestFocus();
                    return;
                }

                if (pass.length() < 8) {
                    password.setError("Enter a valid password");
                    password.requestFocus();
                    return;
                }

                progressDialoge.show();
                Call<DefaultResponse> call = RetrofitClient
                        .getInstance().getApi().login(lid, pass);
                call.enqueue(new Callback<DefaultResponse>() {

                    @Override
                    public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                        DefaultResponse dResponse = response.body();

                        progressDialoge.dismiss();

                        if (dResponse.isAccess()) {

                            Intent intent = new Intent(LoginScreen.this, MainActivity.class);
                            Global.username = username.getText().toString().trim();
                            Bundle bndlanimation = ActivityOptions.makeCustomAnimation(LoginScreen.this, R.anim.trans_left_in, R.anim.trans_left_out).toBundle();
                            startActivity(intent, bndlanimation);
                            finish();

                        } else {
                            Snackbar snackbar = Snackbar.make(rl, dResponse.getErrormsg(), Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                    }

                    @Override
                    public void onFailure(Call<DefaultResponse> call, Throwable t) {
                        progressDialoge.dismiss();
                        if (t instanceof IOException) {
                            Snackbar snackbar = Snackbar.make(rl, "Internet Issue ! Failed to process your request !", Snackbar.LENGTH_LONG);
                            snackbar.show();
                        } else {
                            Snackbar snackbar = Snackbar.make(rl, "Data Conversion Issue ! Contact to admin", Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                    }
                });
            } else {
                requestStoragePermission();
            }
    }

    private void requestStoragePermission() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            //Toast.makeText(getApplicationContext(), "All permissions are granted!", Toast.LENGTH_SHORT).show();
                            allgranted = true;
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            showSettingsDialog();
                        }

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginScreen.this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }
}
