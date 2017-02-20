package banyan.com.gememployee;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.haozhang.lib.AnimatedRecordingView;
import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


/**
 * Created by User on 2/18/2017.
 */

public class Activity_Pending_Complaint_Update extends AppCompatActivity {

    private Toolbar mToolbar;
    AnimatedRecordingView mRecordingView;
    ImageView img_post_image;
    Button btn_submit;
    float vol;

    String str_function = "";
    String imagepath1 = "";


    //18-2-17 *** 11:46 pm

    private static final int PERMISSION_CALLBACK_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    private SharedPreferences permissionStatus;
    String[] permissionsRequired = new String[]{Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO};
    private boolean sentToSettings = false;

    // Camera activity request codes
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;

    public static final int MEDIA_TYPE_IMAGE = 1;
    private final static int RESULT_LOAD_IMAGE = 3;
    private static final int REQUEST_CAMERA1 = 4;

    private static final String TAG_NAME = "name";
    ProgressDialog pDialog;
    public static RequestQueue queue;

    Uri photoPath, mImageUri;
    String encodedstring;

    private Uri fileUri;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_complaint_update);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        // 18-2-17

        permissionStatus = getSharedPreferences("permissionStatus", MODE_PRIVATE);

        img_post_image = (ImageView) findViewById(R.id.complaintupdate_img_post);
        btn_submit = (Button) findViewById(R.id.complaint_pending_update);
        mRecordingView = (AnimatedRecordingView) findViewById(R.id.recording);

        try {
            Check_Permission();
        } catch (Exception e) {

        }

        img_post_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                str_function = "image";
                proceedAfterPermission();


            }
        });

        mRecordingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                str_function = "image";

                mRecordingView.start();
                mRecordingView.loading();
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if (encodedstring.equals("")){

               }else {
                   try {
                       pDialog = new ProgressDialog(Activity_Pending_Complaint_Update.this);
                       pDialog.setMessage("Please wait...");
                       pDialog.show();
                       pDialog.setCancelable(false);
                       queue = Volley.newRequestQueue(Activity_Pending_Complaint_Update.this);
                       Register_Complaint();
                   } catch (Exception e) {

                   }
               }
            }
        });

     /*   // start recording animation
        mRecordingView.start();
        // set the mic volume
        mRecordingView.setVolume(vol);
        // start loading animation
        mRecordingView.loading();
        // start finished animation
      //  mRecordingView.stop();*/

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void Check_Permission() {

        if (ActivityCompat.checkSelfPermission(Activity_Pending_Complaint_Update.this, permissionsRequired[0]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(Activity_Pending_Complaint_Update.this, permissionsRequired[1]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(Activity_Pending_Complaint_Update.this, permissionsRequired[2]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(Activity_Pending_Complaint_Update.this, permissionsRequired[3]) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(Activity_Pending_Complaint_Update.this, permissionsRequired[0])
                    || ActivityCompat.shouldShowRequestPermissionRationale(Activity_Pending_Complaint_Update.this, permissionsRequired[1])
                    || ActivityCompat.shouldShowRequestPermissionRationale(Activity_Pending_Complaint_Update.this, permissionsRequired[2])
                    || ActivityCompat.shouldShowRequestPermissionRationale(Activity_Pending_Complaint_Update.this, permissionsRequired[3])) {
                //Show Information about why you need the permission
                AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Pending_Complaint_Update.this);
                builder.setTitle("Need Multiple Permissions");
                builder.setMessage("This app needs Camera and Location permissions.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ActivityCompat.requestPermissions(Activity_Pending_Complaint_Update.this, permissionsRequired, PERMISSION_CALLBACK_CONSTANT);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            } else if (permissionStatus.getBoolean(permissionsRequired[0], false)) {
                //Previously Permission Request was cancelled with 'Dont Ask Again',
                // Redirect to Settings after showing Information about why you need the permission
                AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Pending_Complaint_Update.this);
                builder.setTitle("Need Multiple Permissions");
                builder.setMessage("This app needs Camera and Location permissions.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        sentToSettings = true;
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                        Toast.makeText(getBaseContext(), "Go to Permissions to Grant  Camera and Location", Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            } else {
                //just request the permission
                ActivityCompat.requestPermissions(Activity_Pending_Complaint_Update.this, permissionsRequired, PERMISSION_CALLBACK_CONSTANT);
            }

            Toast.makeText(getApplicationContext(), "Need a Permission", Toast.LENGTH_LONG).show();

            SharedPreferences.Editor editor = permissionStatus.edit();
            editor.putBoolean(permissionsRequired[0], true);
            editor.commit();
        } else {
            //You already have the permission, just go ahead.
            proceedAfterPermission();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CALLBACK_CONSTANT) {
            //check if all permissions are granted
            boolean allgranted = false;
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    allgranted = true;
                } else {
                    allgranted = false;
                    break;
                }
            }

            if (allgranted) {
                proceedAfterPermission();
            } else if (ActivityCompat.shouldShowRequestPermissionRationale(Activity_Pending_Complaint_Update.this, permissionsRequired[0])
                    || ActivityCompat.shouldShowRequestPermissionRationale(Activity_Pending_Complaint_Update.this, permissionsRequired[1])
                    || ActivityCompat.shouldShowRequestPermissionRationale(Activity_Pending_Complaint_Update.this, permissionsRequired[2])
                    || ActivityCompat.shouldShowRequestPermissionRationale(Activity_Pending_Complaint_Update.this, permissionsRequired[3])) {
//                txtPermissions.setText("Permissions Required");
                AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Pending_Complaint_Update.this);
                builder.setTitle("Need Multiple Permissions");
                builder.setMessage("This app needs Camera and Location permissions.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ActivityCompat.requestPermissions(Activity_Pending_Complaint_Update.this, permissionsRequired, PERMISSION_CALLBACK_CONSTANT);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            } else {
                Toast.makeText(getBaseContext(), "Unable to get Permission", Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (sentToSettings) {
            if (ActivityCompat.checkSelfPermission(Activity_Pending_Complaint_Update.this, permissionsRequired[0]) == PackageManager.PERMISSION_GRANTED) {
                //Got Permission
                proceedAfterPermission();
            }
        }
    }


    public void proceedAfterPermission() {

        if (str_function.equals("image")) {

            if (imagepath1.equals("")) {
                Function_capture_Image();
            } else {

            }

        } else if (str_function.equals("audio")) {

        } else {

        }

    }

    public void Function_capture_Image() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save file url in bundle as it will be null on screen orientation
        // changes
        outState.putParcelable("file_uri", fileUri);
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        // get the file url
        fileUri = savedInstanceState.getParcelable("file_uri");
    }

    /*     * Receiving activity result method will be called after closing the camera*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if the result is capturing Image
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == Activity_Pending_Complaint_Update.this.RESULT_OK) {

                // successfully captured the image
                // launching upload activity
                launchUploadActivity(true);


            } else if (resultCode == Activity_Pending_Complaint_Update.this.RESULT_CANCELED) {

                // user cancelled Image capture
                Toast.makeText(Activity_Pending_Complaint_Update.this,
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();

            } else {
                // failed to capture image
                Toast.makeText(Activity_Pending_Complaint_Update.this,
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }

        } else if (requestCode == REQUEST_CAMERA1) {

            File f2 = new File(Environment.getExternalStorageDirectory()
                    .toString());
            for (File temp : f2.listFiles()) {
                if (temp.getName().equals("temp.jpg")) {
                    f2 = temp;
                    break;
                }
            }
            try {
                Bitmap bm;
                BitmapFactory.Options btmapOptions = new BitmapFactory.Options();

                bm = BitmapFactory.decodeFile(f2.getAbsolutePath(),
                        btmapOptions);

                bm = Bitmap.createScaledBitmap(bm, 170, 170, true);
                img_post_image.setImageBitmap(bm);

                String path = Environment
                        .getExternalStorageDirectory()
                        + File.separator
                        + "Phoenix" + File.separator + "default";
                f2.delete();
                OutputStream fOut = null;
                File file = new File(path, String.valueOf(System
                        .currentTimeMillis()) + ".jpg");

                imagepath1 = file.getPath();

                try {
                    fOut = new FileOutputStream(file);
                    bm.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
                    fOut.flush();
                    fOut.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private void launchUploadActivity(boolean isImage) {

        imagepath1 = fileUri.getPath();

        Bitmap bm;

        BitmapFactory.Options options = new BitmapFactory.Options();

        // options.inSampleSize = 6;

        bm = BitmapFactory.decodeFile(imagepath1, options);

        bm = Bitmap.createScaledBitmap(bm, 170, 170, true);


        img_post_image.setImageBitmap(bm);


        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 25, stream);
        byte[] byte_arr = stream.toByteArray();
        // Encode Image to String
       encodedstring = Base64.encodeToString(byte_arr, 0);
    }

    /* * ------------ Helper Methods ----------------------*/


/*     * Creating file uri to store image/video*/

    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }


/*     * returning image / video*/

    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                Config.IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {

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
        } else {
            return null;
        }

        return mediaFile;
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Activity_Pending_Complaint_Update Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }


    /***************************************
     *  Function upload Image
     * ************************************/

    private void Register_Complaint() {

        String str_register_complaint = "http://gemservice.in/employee_app/upload_image.php";

        StringRequest request = new StringRequest(Request.Method.POST,
                str_register_complaint, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG_NAME, response.toString());
                Log.d("Complaint_Number", response.toString());

                TastyToast.makeText(Activity_Pending_Complaint_Update.this, response.toString(), TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                try {
                    JSONObject obj = new JSONObject(response);
                    int success = obj.getInt("success");

                    System.out.println("REG" + success);

                    if (success == 1) {

                        TastyToast.makeText(Activity_Pending_Complaint_Update.this, "Posted Successfully :)", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                        pDialog.hide();


                    } else {

                        TastyToast.makeText(Activity_Pending_Complaint_Update.this, "Something Went Wrong :(", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                        pDialog.hide();

                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }


                pDialog.hide();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.hide();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("image", imagepath1);

                return params;
            }

        };

        // Adding request to request queue
        queue.add(request);
    }

}
