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
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.preference.PreferenceManager;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
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
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

import banyan.com.gememployee.global.SessionManager;


/**
 * Created by User on 2/18/2017.
 */

public class Activity_Pending_Complaint_Update extends AppCompatActivity {

    private Toolbar mToolbar;
    AnimatedRecordingView mRecordingView;
    ImageView img_post_image;
    TextView txt_audname;
    Button btn_submit, upload_btn_stop_record;
    Spinner spn_status;
    LinearLayout linear_image, linear_audio, linear_audi;
    EditText edt_description;


    float vol;

    String str_function = "";
    String imagepath1 = "";

    /*************************************
     * For Image Capture
     *****************************************/

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
    String encodedstring = "";

    private Uri fileUri;

    private GoogleApiClient client;

    /*************************************
     * For Audio Capture
     *****************************************/

    private static final String AUDIO_RECORDER_FILE_EXT_3GP = ".mp4";
    private static final String AUDIO_RECORDER_FILE_EXT_MP4 = ".mp3";
    private static final String AUDIO_RECORDER_FOLDER = "AudioRecorder";
    private MediaRecorder recorder = null;
    private int currentFormat = 0;
    private int output_formats[] = {MediaRecorder.OutputFormat.MPEG_4, MediaRecorder.OutputFormat.THREE_GPP};
    private String file_exts[] = {AUDIO_RECORDER_FILE_EXT_MP4, AUDIO_RECORDER_FILE_EXT_3GP};

    String str_audio_path = "";

    // Session Manager Class
    SessionManager session;

    String str_send_user_id, str_comp_number, str_comp_status, str_aud_name, str_process_desc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_complaint_update);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        // Session Variables
        session = new SessionManager(Activity_Pending_Complaint_Update.this);
        // get user data from session
        HashMap<String, String> user = session.getUserDetails();
        // name
        str_send_user_id = user.get(SessionManager.KEY_USER_ID);

        // Complaint Number
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());

        str_comp_number = sharedPreferences.getString("str_pending_select_comp_number", "str_pending_select_comp_number");

        permissionStatus = getSharedPreferences("permissionStatus", MODE_PRIVATE);

        img_post_image = (ImageView) findViewById(R.id.complaintupdate_img_post);
        btn_submit = (Button) findViewById(R.id.complaint_pending_update);
        mRecordingView = (AnimatedRecordingView) findViewById(R.id.recording);
        upload_btn_stop_record = (Button) findViewById(R.id.upload_btn_stop_record);
        txt_audname = (TextView) findViewById(R.id.upload_txt_aud_name);

        spn_status = (Spinner) findViewById(R.id.updatecomplaint_spinner_status);

        linear_image = (LinearLayout) findViewById(R.id.linear_image_layout);
        linear_audio = (LinearLayout) findViewById(R.id.linear_audio_layout);
        linear_audi = (LinearLayout) findViewById(R.id.linear_audiosample);

        edt_description = (EditText) findViewById(R.id.complaintupdate_edt_cus_report);

        /*************************************
         * Runtime Permission call
         * *****************************************/

        try {
            Check_Permission();
        } catch (Exception e) {

        }

        img_post_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (imagepath1.equals("")) {
                    str_function = "image";
                    proceedAfterPermission();
                } else {
                    TastyToast.makeText(Activity_Pending_Complaint_Update.this, "Img is already is der", TastyToast.LENGTH_LONG, TastyToast.INFO);
                }

            }
        });

        mRecordingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (str_audio_path.equals("")) {
                    try {
                        str_function = "audio";
                        mRecordingView.start();
                        mRecordingView.loading();
                        proceedAfterPermission();

                    } catch (Exception e) {

                    }
                } else {
                    TastyToast.makeText(Activity_Pending_Complaint_Update.this, "Aud already is der", TastyToast.LENGTH_LONG, TastyToast.INFO);
                }

            }
        });

        upload_btn_stop_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    mRecordingView.stop();
                    stopRecording();
                    upload_btn_stop_record.setVisibility(View.GONE);
                    txt_audname.setVisibility(View.VISIBLE);
                    txt_audname.setText("" + str_audio_path);
                } catch (Exception e) {

                }
            }
        });

        /*******************************
         *  Spinner Loaders
         * ******************************/

        // Product and Product Model Spinner

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(Activity_Pending_Complaint_Update.this, R.array.status, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_status.setAdapter(adapter);

        // Spinner Product Interface
        spn_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                str_comp_status = parent.getItemAtPosition(pos).toString();

                if (str_comp_status.equals("null")) {

                    TastyToast.makeText(Activity_Pending_Complaint_Update.this, "Please Select Status", TastyToast.LENGTH_LONG, TastyToast.INFO);

                } else if (str_comp_status.equals("Completed")) {

                    linear_image.setVisibility(View.VISIBLE);
                    linear_audio.setVisibility(View.VISIBLE);
                    linear_audi.setVisibility(View.VISIBLE);
                    mRecordingView.setVisibility(View.VISIBLE);

                } else {

                    linear_image.setVisibility(View.VISIBLE);
                    linear_audio.setVisibility(View.GONE);
                    linear_audi.setVisibility(View.GONE);
                    mRecordingView.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Another interface callback
            }

        });


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                str_process_desc = edt_description.getText().toString();

                if (str_comp_status.equals("null")) {
                    TastyToast.makeText(Activity_Pending_Complaint_Update.this, "Please Select a Status", TastyToast.LENGTH_LONG, TastyToast.INFO);
                } else if (str_comp_status.equals("Completed")) {

                    if (str_process_desc.equals("")) {
                        TastyToast.makeText(Activity_Pending_Complaint_Update.this, "Please Enter Description", TastyToast.LENGTH_LONG, TastyToast.INFO);
                    } else if (encodedstring.equals("")) {
                        TastyToast.makeText(Activity_Pending_Complaint_Update.this, "Please Capture an Image", TastyToast.LENGTH_LONG, TastyToast.INFO);
                    } else {

                        if (str_aud_name != null && !str_aud_name.isEmpty()) {

                            System.out.println("Welcome :: " + str_aud_name);
                            System.out.println("Welcome :: " + str_aud_name);
                            System.out.println("Welcome :: " + str_aud_name);
                            System.out.println("Welcome :: " + str_aud_name);

                            try {
                                pDialog = new ProgressDialog(Activity_Pending_Complaint_Update.this);
                                pDialog.setMessage("Please wait...");
                                pDialog.show();
                                pDialog.setCancelable(false);
                                doFileUpload();
                            } catch (Exception e) {

                            }
                        } else {

                            System.out.println("Welcome :: FIN " + str_aud_name);
                            System.out.println("Welcome :: FIN" + str_aud_name);
                            System.out.println("Welcome :: FIN" + str_aud_name);
                            System.out.println("Welcome :: FIN" + str_aud_name);

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

                } else {

                    System.out.println("INSIDE ELSE BLOCK");

                    System.out.println("INSIDE ELSE BLOCK" + str_comp_status);

                    if (str_comp_status.equals("")) {
                        TastyToast.makeText(Activity_Pending_Complaint_Update.this, "Please Enter Description", TastyToast.LENGTH_LONG, TastyToast.INFO);
                    } else {
                        try {
                            System.out.println("OTHER PROCESS :: " + str_comp_status);
                            System.out.println("OTHER PROCESS :: " + encodedstring);

                            pDialog = new ProgressDialog(Activity_Pending_Complaint_Update.this);
                            pDialog.setMessage("Please wait...");
                            pDialog.show();
                            pDialog.setCancelable(false);
                            queue = Volley.newRequestQueue(Activity_Pending_Complaint_Update.this);
                            Register_Complaint_for_other();

                            System.out.println("OTHER PROCESS  CALLED:: ");
                        } catch (Exception e) {

                        }
                    }


                }
            }
        });

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /*************************************
     * Runtime Permission call
     *****************************************/

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
                TastyToast.makeText(Activity_Pending_Complaint_Update.this, "Internal Error", TastyToast.LENGTH_LONG, TastyToast.ERROR);
            }

        } else if (str_function.equals("audio")) {

            if (str_audio_path.equals("")) {
                startRecording();
                upload_btn_stop_record.setVisibility(View.VISIBLE);

            } else {
                TastyToast.makeText(Activity_Pending_Complaint_Update.this, "Internal Error", TastyToast.LENGTH_LONG, TastyToast.ERROR);

            }

        } else {

        }

    }

    /*************************************
     * Image Capture Functio Begins
     *****************************************/

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


        BitmapFactory.Options options = new BitmapFactory.Options();

        // Image location URL
        imagepath1 = fileUri.getPath();
        Log.e("path", "----------------" + imagepath1);

        // Image
        Bitmap bmBitmap = BitmapFactory.decodeFile(imagepath1);
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        bmBitmap.compress(Bitmap.CompressFormat.JPEG, 25, bao);
        byte[] ba = bao.toByteArray();
        encodedstring = Base64.encodeToString(ba, 0);
        img_post_image.setImageBitmap(bmBitmap);
        Log.e("base64", "-----" + encodedstring);


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


    /**************************************
     * Function Audio Recording Begins
     ***********************************/

    private void startRecording() {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(output_formats[currentFormat]);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile(getFilename());
        recorder.setOnErrorListener(errorListener);
        recorder.setOnInfoListener(infoListener);
        try {
            recorder.prepare();
            recorder.start();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stopRecording() {
        if (null != recorder) {
            recorder.stop();
            recorder.reset();
            recorder.release();
            recorder = null;
        }
    }

    private String getFilename() {
        String filepath = Environment.getExternalStorageDirectory().getPath();
        File file = new File(filepath, AUDIO_RECORDER_FOLDER);
        if (!file.exists()) {
            file.mkdirs();
        }

        System.out.println("path" + file.getAbsolutePath() + "/" + System.currentTimeMillis() + file_exts[currentFormat]);
        str_audio_path = file.getAbsolutePath() + "/" + System.currentTimeMillis() + file_exts[currentFormat];
        str_aud_name = System.currentTimeMillis() + file_exts[currentFormat];
        return (file.getAbsolutePath() + "/" + System.currentTimeMillis() + file_exts[currentFormat]);

    }

    private MediaRecorder.OnErrorListener errorListener = new MediaRecorder.OnErrorListener() {
        @Override
        public void onError(MediaRecorder mr, int what, int extra) {
            //Toast.makeText(MainActivity.this, "Error: " + what + ", " + extra, Toast.LENGTH_SHORT).show();
        }
    };

    private MediaRecorder.OnInfoListener infoListener = new MediaRecorder.OnInfoListener() {
        @Override
        public void onInfo(MediaRecorder mr, int what, int extra) {
            // Toast.makeText(MainActivity.this, "Warning: " + what + ", " + extra, Toast.LENGTH_SHORT).show();
        }
    };

    private void doFileUpload() {

        System.out.println("CALLED INSIDE");
        System.out.println("CALLED INSIDE");
        System.out.println("CALLED INSIDE");
        System.out.println("CALLED INSIDE");
        System.out.println("CALLED INSIDE");

        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        DataInputStream inStream = null;
        String existingFileName = str_audio_path;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        String responseFromServer = "";
        String urlString = "http://gemservice.in/employee_app/2017_upload_audio.php";

        System.out.println("URL : " + urlString);

        try {

            System.out.println("CALLED INSIDE 1");

            //------------------ CLIENT REQUEST
            FileInputStream fileInputStream = new FileInputStream(new File(existingFileName));
            // open a URL connection to the Servlet
            URL url = new URL(urlString);
            // Open a HTTP connection to the URL
            conn = (HttpURLConnection) url.openConnection();
            // Allow Inputs
            conn.setDoInput(true);
            // Allow Outputs
            conn.setDoOutput(true);
            // Don't use a cached copy.
            conn.setUseCaches(false);
            // Use a post method.
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            conn.setUseCaches(false);
            dos = new DataOutputStream(conn.getOutputStream());
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\"" + existingFileName + "\"" + lineEnd);
            dos.writeBytes(lineEnd);
            // create a buffer of maximum size
            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];
            // read file and write it into form...167181282
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            while (bytesRead > 0) {

                dos.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            }

            // send multipart form data necesssary after file data...
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
            // close streams
            Log.e("Debug", "File is written");
            fileInputStream.close();
            dos.flush();
            dos.close();

            try {
                queue = Volley.newRequestQueue(Activity_Pending_Complaint_Update.this);
                Register_Complaint();
            } catch (Exception e) {

            }

        } catch (MalformedURLException ex) {
            Log.e("Debug", "error: " + ex.getMessage(), ex);
        } catch (IOException ioe) {
            Log.e("Debug", "error: " + ioe.getMessage(), ioe);
        }

        //------------------ read the SERVER RESPONSE
        try {

            inStream = new DataInputStream(conn.getInputStream());
            String str;

            while ((str = inStream.readLine()) != null) {

                Log.e("Debug", "Server Response " + str);

            }

            inStream.close();

        } catch (IOException ioex) {
            Log.e("Debug", "error: " + ioex.getMessage(), ioex);
        }
    }

    /***************************************
     * Function Completed Complaints
     ************************************/

    private void Register_Complaint() {

        String str_register_complaint = "http://gemservice.in/employee_app/upload_image.php";

        System.out.println("REG CAME" );
        System.out.println("REG CAME" );
        System.out.println("REG CAME" );
        System.out.println("REG CAME" );
        File source = new File(imagepath1);

        StringRequest request = new StringRequest(Request.Method.POST,
                str_register_complaint, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG_NAME, response.toString());
                Log.d("Complaint_Number", response.toString());

                try {
                    FunctionAlert();
                } catch (Exception e) {

                }

                try {

                    JSONObject obj = new JSONObject(response);
                    int success = obj.getInt("success");

                    System.out.println("REG" + success);
                    System.out.println("REG" + success);
                    System.out.println("REG" + success);
                    System.out.println("REG" + success);


                    if (success == 1) {

                        TastyToast.makeText(Activity_Pending_Complaint_Update.this, "Posted Successfully :)", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                        pDialog.hide();
                        FunctionAlert();


                    } else {

                        TastyToast.makeText(Activity_Pending_Complaint_Update.this, "Something Went Wrong :(", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                        pDialog.hide();

                    }
                } catch (Exception e) {
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

                params.put("image", encodedstring);
                params.put("user", str_send_user_id);
                params.put("comp_number", str_comp_number);
                params.put("aud_name", str_aud_name);
                params.put("status", str_comp_status);
                params.put("desc", str_process_desc);

                System.out.println("Image : " + encodedstring);
                System.out.println("user : " + str_send_user_id);
                System.out.println("comp_number : " + str_comp_number);
                System.out.println("aud_name : " + str_aud_name);
                System.out.println("status : " + str_comp_status);
                System.out.println("desc : " + str_process_desc);

                return checkParams(params);
            }

            private Map<String, String> checkParams(Map<String, String> map) {
                Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, String> pairs = (Map.Entry<String, String>) it.next();
                    if (pairs.getValue() == null) {
                        map.put(pairs.getKey(), "");
                    }
                }
                return map;
            }

        };

        int socketTimeout = 50000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        // Adding request to request queue
        queue.add(request);
    }


    /***************************************
     * Function Completed for Other Process
     ************************************/

    private void Register_Complaint_for_other() {

        String str_url_not = "http://gemservice.in/employee_app/upload_image_other_process.php";

        File source = new File(imagepath1);

        StringRequest request = new StringRequest(Request.Method.POST,
                str_url_not, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG_NAME, response.toString());
                Log.d("Complaint_Number", response.toString());
                try {
                    FunctionAlert();
                } catch (Exception e) {

                }

                try {

                    JSONObject obj = new JSONObject(response);
                    int success = obj.getInt("success");

                    System.out.println("REG" + success);

                    if (success == 1) {

                        TastyToast.makeText(Activity_Pending_Complaint_Update.this, "Posted Successfully :)", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                        pDialog.hide();
                        FunctionAlert();


                    } else {

                        TastyToast.makeText(Activity_Pending_Complaint_Update.this, "Something Went Wrong :(", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                        pDialog.hide();

                    }
                } catch (Exception e) {
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

                params.put("image", encodedstring);
                params.put("comp_number", str_comp_number);
                params.put("status", str_comp_status);
                params.put("desc", str_process_desc);

                System.out.println(" Image : " + encodedstring);
                System.out.println(" comp_number : " + str_comp_number);
                System.out.println(" status : " + str_comp_status);
                System.out.println(" desc : " + str_process_desc);

                return params;
            }

        };
        int socketTimeout = 50000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        // Adding request to request queue
        queue.add(request);
    }

    private void FunctionAlert() {

        new android.app.AlertDialog.Builder(Activity_Pending_Complaint_Update.this)
                .setTitle("Gem India")
                .setMessage("Complaint Updated Successfully :)")
                .setIcon(R.mipmap.ic_launcher)

                .setPositiveButton("Done",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {

                                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(i);
                                finish();

                                dialog.dismiss();


                            }
                        }).show();
    }

}