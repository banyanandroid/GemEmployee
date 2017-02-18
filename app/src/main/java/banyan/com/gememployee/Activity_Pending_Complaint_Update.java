package banyan.com.gememployee;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
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
    float vol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_complaint_update);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        img_post_image = (ImageView) findViewById(R.id.complaintupdate_img_post);
        mRecordingView = (AnimatedRecordingView) findViewById(R.id.recording);


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



}
