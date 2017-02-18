package banyan.com.gememployee;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.haozhang.lib.AnimatedRecordingView;

/**
 * Created by User on 2/18/2017.
 */

public class Activity_Pending_Complaint_Update extends AppCompatActivity {

    private Toolbar mToolbar;
    AnimatedRecordingView mRecordingView;
    ImageView img_post_image;
    Button btn_submit;
    float vol;


    //18-2-17 *** 11:46 pm

    private static final int PERMISSION_CALLBACK_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    private SharedPreferences permissionStatus;
    String[] permissionsRequired = new String[]{Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO};
    private boolean sentToSettings = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_complaint_update);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        // 18-2-17

        permissionStatus = getSharedPreferences("permissionStatus",MODE_PRIVATE);

        img_post_image = (ImageView) findViewById(R.id.complaintupdate_img_post);
        mRecordingView = (AnimatedRecordingView) findViewById(R.id.recording);

        try {
            Check_Permission();
        }catch (Exception e) {

        }


        img_post_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRecordingView.start();
                mRecordingView.loading();

            }
        });

        mRecordingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


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

    }

    public void Check_Permission(){

        if(ActivityCompat.checkSelfPermission(Activity_Pending_Complaint_Update.this, permissionsRequired[0]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(Activity_Pending_Complaint_Update.this, permissionsRequired[1]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(Activity_Pending_Complaint_Update.this, permissionsRequired[2]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(Activity_Pending_Complaint_Update.this, permissionsRequired[3]) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(Activity_Pending_Complaint_Update.this,permissionsRequired[0])
                    || ActivityCompat.shouldShowRequestPermissionRationale(Activity_Pending_Complaint_Update.this,permissionsRequired[1])
                    || ActivityCompat.shouldShowRequestPermissionRationale(Activity_Pending_Complaint_Update.this,permissionsRequired[2])
                    || ActivityCompat.shouldShowRequestPermissionRationale(Activity_Pending_Complaint_Update.this,permissionsRequired[3])){
                //Show Information about why you need the permission
                AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Pending_Complaint_Update.this);
                builder.setTitle("Need Multiple Permissions");
                builder.setMessage("This app needs Camera and Location permissions.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ActivityCompat.requestPermissions(Activity_Pending_Complaint_Update.this,permissionsRequired,PERMISSION_CALLBACK_CONSTANT);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            } else if (permissionStatus.getBoolean(permissionsRequired[0],false)) {
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
            }  else {
                //just request the permission
                ActivityCompat.requestPermissions(Activity_Pending_Complaint_Update.this,permissionsRequired,PERMISSION_CALLBACK_CONSTANT);
            }

            Toast.makeText(getApplicationContext(), "Need a Permission" , Toast.LENGTH_LONG).show();

            SharedPreferences.Editor editor = permissionStatus.edit();
            editor.putBoolean(permissionsRequired[0],true);
            editor.commit();
        } else {
            //You already have the permission, just go ahead.
            proceedAfterPermission();
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == PERMISSION_CALLBACK_CONSTANT){
            //check if all permissions are granted
            boolean allgranted = false;
            for(int i=0;i<grantResults.length;i++){
                if(grantResults[i]==PackageManager.PERMISSION_GRANTED){
                    allgranted = true;
                } else {
                    allgranted = false;
                    break;
                }
            }

            if(allgranted){
                proceedAfterPermission();
            } else if(ActivityCompat.shouldShowRequestPermissionRationale(Activity_Pending_Complaint_Update.this,permissionsRequired[0])
                    || ActivityCompat.shouldShowRequestPermissionRationale(Activity_Pending_Complaint_Update.this,permissionsRequired[1])
                    || ActivityCompat.shouldShowRequestPermissionRationale(Activity_Pending_Complaint_Update.this,permissionsRequired[2])
                    || ActivityCompat.shouldShowRequestPermissionRationale(Activity_Pending_Complaint_Update.this,permissionsRequired[3])){
//                txtPermissions.setText("Permissions Required");
                AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Pending_Complaint_Update.this);
                builder.setTitle("Need Multiple Permissions");
                builder.setMessage("This app needs Camera and Location permissions.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ActivityCompat.requestPermissions(Activity_Pending_Complaint_Update.this,permissionsRequired,PERMISSION_CALLBACK_CONSTANT);
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
                Toast.makeText(getBaseContext(),"Unable to get Permission",Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PERMISSION_SETTING) {
            if (ActivityCompat.checkSelfPermission(Activity_Pending_Complaint_Update.this, permissionsRequired[0]) == PackageManager.PERMISSION_GRANTED) {
                //Got Permission
                proceedAfterPermission();
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


    public void proceedAfterPermission(){

    }

}
