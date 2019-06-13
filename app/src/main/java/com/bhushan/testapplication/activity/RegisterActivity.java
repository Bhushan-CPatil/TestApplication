package com.bhushan.testapplication.activity;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bhushan.testapplication.R;
import com.bhushan.testapplication.api.RetrofitClient;
import com.bhushan.testapplication.others.ViewDialog;
import com.bhushan.testapplication.pojo.DefaultResponse;
import com.bumptech.glide.Glide;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    public ImageButton goback;
    public RelativeLayout rl;
    public String status;
    public boolean isimgcropped = false;
    public static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    public static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;
    final int CROP_PIC = 98;
    public Uri picUri;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    ViewDialog progressDialoge;
    public Uri fileUri; // file url to store image/video

    public CircleImageView acpimage;
    public EditText pimage, name, add, pincode, phno, email, username;
    public TextInputEditText pass, confpass;
    public Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        goback = findViewById(R.id.goback);
        pimage = findViewById(R.id.pimage);
        acpimage = findViewById(R.id.acpimage);
        name = findViewById(R.id.name);
        add = findViewById(R.id.add);
        pincode = findViewById(R.id.pincode);
        phno = findViewById(R.id.phno);
        email = findViewById(R.id.email);
        username = findViewById(R.id.username);
        pass = findViewById(R.id.pass);
        confpass = findViewById(R.id.confpass);
        register = findViewById(R.id.register);
        progressDialoge = new ViewDialog(RegisterActivity.this);
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginScreen.class);
                Bundle bndlanimation = ActivityOptions.makeCustomAnimation(RegisterActivity.this, R.anim.trans_right_in, R.anim.trans_right_out).toBundle();
                startActivity(intent, bndlanimation);
                finish();
            }
        });

        pimage.setFocusable(false);
        pimage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // capture picture
                captureImage();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doRegister();
            }
        });

        // Checking camera availability
        if (!isDeviceSupportCamera()) {
            Toast.makeText(getApplicationContext(),
                    "Sorry! Your device doesn't support camera",
                    Toast.LENGTH_LONG).show();
            // will close the app if the device does't have camera
            finish();
        }
    }

    private boolean isDeviceSupportCamera() {
        if (getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    private void captureImage() {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                            fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

                            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                            intent.putExtra("return-data", true);
                            // start the image capture Intent
                            //startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
                            startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();

    }

    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /**
     * returning image / video
     */
    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                RetrofitClient.IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                //Log.d(TAG, "Oops! Failed create "+ RetrofitClient.IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save file url in bundle as it will be null on screen orientation
        // changes
        outState.putParcelable("file_uri", fileUri);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // get the file url
        fileUri = savedInstanceState.getParcelable("file_uri");
    }


    /**
     * Receiving activity result method will be called after closing the camera
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if the result is capturing Image
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                //picUri = data.getData();
                //Log.d("pic uri 1",picUri.toString());
                performCrop();

                //todo call crop image method
                // successfully captured the image
                // launching upload activity


            } else if (resultCode == RESULT_CANCELED) {

                // user cancelled Image capture
                Toast.makeText(getApplicationContext(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();

            } else {
                // failed to capture image
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }

        } else if (requestCode == 98) {
            //picUri = data.getExtras();
            //Log.d("pic uri 2",picUri.toString());
            if (resultCode == RESULT_OK) {
                isimgcropped = true;
                launchUploadActivity(true);
            } else if (resultCode == RESULT_CANCELED) {
                isimgcropped = false;
                picUri = fileUri;
                launchUploadActivity(true);
                Toast.makeText(getApplicationContext(),
                        "Crop operation canceled !", Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to get image !", Toast.LENGTH_SHORT)
                        .show();
            }
            /*else if (resultCode == RESULT_CANCELED) {
                picUri = fileUri;
                launchUploadActivity(true);
                Toast.makeText(getApplicationContext(),
                        "Crop operation canceled !", Toast.LENGTH_SHORT)
                        .show();
            }*/
        } else if (requestCode == 3) {
            //picUri = data.getExtras();
            //Log.d("pic uri 2",picUri.toString());
            if (resultCode == RESULT_OK) {
                fileUri = Uri.parse(getRealPathFromURI(fileUri));
                isimgcropped = true;
                launchUploadActivity(true);
            } else if (resultCode == RESULT_CANCELED) {
                picUri = Uri.parse(getRealPathFromURI(fileUri));
                fileUri = Uri.parse(getRealPathFromURI(fileUri));
                isimgcropped = false;
                //Log.d("gallery pic path 2222",fileUri.toString());
                launchUploadActivity(true);
                Toast.makeText(getApplicationContext(),
                        "Crop operation canceled !", Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to get image !", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    private void launchUploadActivity(boolean isImage) {
        //todo write code here
        pimage.setVisibility(View.GONE);
        acpimage.setVisibility(View.VISIBLE);
        File file = new File(picUri.getPath());
        long fileSizeInBytes = file.length();
        long fileSizeInKB = fileSizeInBytes / 1024;
        if (file.exists() && fileSizeInKB > 5) {
            Glide.with(this).load(picUri.getPath()).into(acpimage);
        }
        /*Intent i = new Intent(CapNUpVstCard.this, UploadActivity.class);
        i.putExtra("filePath", picUri.getPath());
        i.putExtra("fileUri", fileUri.getPath());
        i.putExtra("isImage", isImage);
        i.putExtra("isimgcropped", isimgcropped);
        startActivity(i);
        finish();*/
    }

    private void performCrop() {
        // take care of exceptions
        try {
            // call the standard crop action intent (the user device may not
            // support it)
            //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Intent cropIntent = new Intent("com.android.camera.action.CROP");

            //cropIntent.setClassName("com.google.android.gallery3d", "com.android.gallery3d.app.CropImage");
            //cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            // indicate image type and Uri
            //cropIntent.setDataAndType(fileUri, "image/*");
            //Log.d("fileUri 1",fileUri.toString());
            cropIntent.setDataAndType(fileUri, "image/*");

            // set crop properties
            cropIntent.putExtra("crop", "true");

            // indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            // indicate output X and Y
            cropIntent.putExtra("outputX", 300);
            cropIntent.putExtra("outputY", 300);
            //cropIntent.putExtra("scaleUpIfNeeded",true);
            // retrieve data on return
            cropIntent.putExtra("return-data", true);

            File f = createNewFile();
            try {
                f.createNewFile();
            } catch (IOException ex) {
                // Log.e("io", ex.getMessage());
            }

            picUri = Uri.fromFile(f);
            cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, picUri);
            // start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, CROP_PIC);
            /*} else {
                picUri = fileUri;
                launchUploadActivity(true);
                Toast toast = Toast
                        .makeText(this, "Device version below 5.0 doesn't support the crop action !", Toast.LENGTH_SHORT);
                toast.show();
            }*/

        } catch (ActivityNotFoundException anfe) {

            picUri = fileUri;
            launchUploadActivity(true);
            Toast toast = Toast
                    .makeText(this, "This device doesn't support the crop action!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private File createNewFile() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());

        String prefix = "CRPIMG_" + timeStamp + ".jpg";

        File newDirectory = new File(Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                RetrofitClient.IMAGE_DIRECTORY_NAME);
        if (!newDirectory.exists()) {
            if (newDirectory.mkdir()) {
                //Log.d(CapNUpVstCard.this.getClass().getName(), newDirectory.getAbsolutePath()+" directory created");
            }
        }

        File file = new File(newDirectory, (prefix));
        if (file.exists()) {
            //this wont be executed
            file.delete();
            try {
                file.createNewFile();
            } catch (IOException e) {
                Toast.makeText(RegisterActivity.this, "File creation Failed ! Try again.", Toast.LENGTH_LONG).show();
            }
        }

        return file;
    }

    public String getRealPathFromURI(Uri contentUri) {

        // can post image
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(contentUri,
                proj, // Which columns to return
                null,       // WHERE clause; which rows to return (all rows)
                null,       // WHERE clause selection arguments (none)
                null); // Order-by clause (ascending by name)
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        return cursor.getString(column_index);
    }

    private class UploadFileToServer extends AsyncTask<Void, Integer, String> {
        @Override
        protected void onPreExecute() {
            // setting progress bar to zero
            progressDialoge.show();
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {

        }

        @Override
        protected String doInBackground(Void... params) {
            return uploadFile();
        }

        @SuppressWarnings("deprecation")
        private String uploadFile() {
            String responseString = null;

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(RetrofitClient.BASE_URL + "register.php");

            try {
                AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
                        new AndroidMultiPartEntity.ProgressListener() {

                            @Override
                            public void transferred(long num) {
                                //publishProgress((int) ((num / (float) totalSize) * 100));
                            }
                        });
                //Log.d("file path", filePath);
                File sourceFile = new File(picUri.getPath());
                if (!isimgcropped) {
                    File compressedImageFile = new Compressor(RegisterActivity.this).compressToFile(sourceFile);
                    // Adding file data to http body
                    entity.addPart("image", new FileBody(compressedImageFile));
                } else {
                    entity.addPart("image", new FileBody(sourceFile));
                }
                // Extra parameters if you want to pass to server

                entity.addPart("name", new StringBody(name.getText().toString()));
                entity.addPart("add", new StringBody(add.getText().toString()));
                entity.addPart("pin", new StringBody(pincode.getText().toString()));
                entity.addPart("phno", new StringBody(phno.getText().toString()));
                entity.addPart("email", new StringBody(email.getText().toString()));
                entity.addPart("username", new StringBody(username.getText().toString()));
                entity.addPart("pass", new StringBody(confpass.getText().toString()));

                //totalSize = entity.getContentLength();
                httppost.setEntity(entity);

                // Making server call
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity r_entity = response.getEntity();

                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    // Server response
                    responseString = EntityUtils.toString(r_entity);
                } else {
                    responseString = "Error occurred! Http Status Code: "
                            + statusCode;
                }

            } catch (ClientProtocolException e) {
                responseString = e.toString();
            } catch (IOException e) {
                responseString = e.toString();
            }

            return responseString;

        }

        @Override
        protected void onPostExecute(String result) {
            //Log.e(TAG, "Response from server: " + result);
            progressDialoge.dismiss();
            try {
                JSONObject jsonObject = new JSONObject(result);
                if (jsonObject.getBoolean("access")) {
                    // showing the server response in an alert dialog
                    finish();
                    RegisterActivity.this.overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
                    Toast.makeText(RegisterActivity.this, jsonObject.getString("errormsg"), Toast.LENGTH_LONG).show();

                } else {
                    Snackbar snackbar = Snackbar.make(rl, jsonObject.getString("errormsg"), Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            //super.onPostExecute(result);
        }

    }

    public void doRegister() {
        Vibrator vibrator = (Vibrator) getSystemService(RegisterActivity.this.VIBRATOR_SERVICE);
        vibrator.vibrate(100);


        final String usern = username.getText().toString().trim();
        String na = name.getText().toString().trim();
        String ad = add.getText().toString().trim();
        String pin = pincode.getText().toString().trim();
        String ph = phno.getText().toString().trim();
        String em = email.getText().toString().trim();
        String password = pass.getText().toString().trim();
        String cpassword = confpass.getText().toString().trim();


        if (pimage.isShown()) {
            pimage.setError("Upload Image first");
            pimage.requestFocus();
            return;
        }

        if (acpimage.isShown()) {
            if (acpimage.getDrawable() == null) {
                acpimage.setVisibility(View.GONE);
                pimage.setVisibility(View.VISIBLE);
                pimage.setError("Image not set");
                pimage.requestFocus();
                return;
            }
        }

        if (na.isEmpty()) {
            name.setError("Name is required");
            name.requestFocus();
            return;
        }
        if (ad.isEmpty()) {
            add.setError("Address is required");
            add.requestFocus();
            return;
        }
        if (pin.isEmpty()) {
            pincode.setError("Pincode is required");
            pincode.requestFocus();
            return;
        }
        if (pin.length() != 6) {
            pincode.setError("Enter a valid pincode");
            pincode.requestFocus();
            return;
        }
        if (ph.isEmpty()) {
            phno.setError("Mobile number is required");
            phno.requestFocus();
            return;
        }
        if (ph.length() != 10) {
            phno.setError("Enter a valid Mobile number");
            phno.requestFocus();
            return;
        }
        if (em.isEmpty()) {
            email.setError("Email is required");
            email.requestFocus();
            return;
        }
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
        if (password.isEmpty()) {
            pass.setError("Password is required");
            pass.requestFocus();
            return;
        }

        if (password.length() < 8) {
            pass.setError("Password should be 8 characters long ");
            pass.requestFocus();
            return;
        }

        if (cpassword.isEmpty()) {
            confpass.setError("Password is required");
            confpass.requestFocus();
            return;
        }

        if (cpassword.length() < 8) {
            confpass.setError("Password should be 8 characters long ");
            confpass.requestFocus();
            return;
        }

        if (!password.equals(cpassword)) {
            confpass.setError("Password not matched");
            confpass.requestFocus();
            return;
        }

        new UploadFileToServer().execute();

    }
}
