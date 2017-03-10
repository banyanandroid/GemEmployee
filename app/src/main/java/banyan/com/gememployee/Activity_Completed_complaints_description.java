package banyan.com.gememployee;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.sdsmdg.tastytoast.TastyToast;

/**
 * Created by Jo on 2/17/2017.
 */

public class Activity_Completed_complaints_description extends AppCompatActivity {

    private Toolbar mToolbar;

    String str_id, str_user_code, str_comp_number, str_date, str_product_name, str_model, str_comp_cate, str_comp_type, str_purchased_throug, str_mcslno,
            str_warranty, str_customer_name, str_address, str_street, str_landmark, str_city, str_contact_person_name, str_phone_no, str_addon_phone_no,
            str_cellno, str_email, str_addon_email, str_fax_no, str_complaint, str_engineer_alloted, str_comp_attending_date, str_comp_closing_date, str_status,
            str_comp_reg_time, str_call_attending_time, str_call_closing_time, str_service_des, str_image;

    TextView txt_user_code, txt_comp_number, txt_date, txt_product_name, txt_model, txt_cpm_cate, txt_comp_type, txt_purchased_throug, txt_mcslno,
            txt_warranty, txt_customer_name, txt_adress, txt_street, txt_landmark, txt_city, txt_contact_person, txt_phone_no, txt_addon_phoneno,
            txt_cellno, txt_email, txt_addon_email, txt_fax, txt_complaint, txt_engg_alloted, txt_comp_attending_date, txt_comp_closing_date, txt_closing_date, txt_status,
            txt_comp_reg_time, txt_call_attending_time, txt_call_closing_time, txt_service_des;

    ImageView img_completed;

    String str_call_number;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_complaints_description);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        txt_date = (TextView) findViewById(R.id.complaint_des_txtview_date);
        txt_comp_number = (TextView) findViewById(R.id.complaint_des_txtview_comp_number);
        txt_user_code = (TextView) findViewById(R.id.complaint_des_txtview_complaint_reg_by);
        txt_cpm_cate = (TextView) findViewById(R.id.complaint_des_txtview_complaint_cate);
        txt_comp_type = (TextView) findViewById(R.id.complaint_des_txtview_comp_type);
        txt_product_name = (TextView) findViewById(R.id.complaint_des_txtview_product_type);
        txt_model = (TextView) findViewById(R.id.complaint_des_txtview_model);
        txt_purchased_throug = (TextView) findViewById(R.id.complaint_des_txtview_purchace);
        txt_mcslno = (TextView) findViewById(R.id.complaint_des_txtview_mcslno);
        txt_warranty = (TextView) findViewById(R.id.complaint_des_txtview_warranty);
        txt_customer_name = (TextView) findViewById(R.id.complaint_des_txtview_cus_name);
        txt_adress = (TextView) findViewById(R.id.complaint_des_txtview_address);
        txt_street = (TextView) findViewById(R.id.complaint_des_txtview_street);
        txt_landmark = (TextView) findViewById(R.id.complaint_des_txtview_landmark);
        txt_city = (TextView) findViewById(R.id.complaint_des_txtview_city);
        txt_contact_person = (TextView) findViewById(R.id.complaint_des_txtview_contact_person_name);
        txt_phone_no = (TextView) findViewById(R.id.complaint_des_txtview_phoneno);
        txt_addon_phoneno = (TextView) findViewById(R.id.complaint_des_txtview_addon_phone);
        txt_cellno = (TextView) findViewById(R.id.complaint_des_txtview_cellno);
        txt_email = (TextView) findViewById(R.id.complaint_des_txtview_email);
        txt_addon_email = (TextView) findViewById(R.id.complaint_des_txtview_adon_email);
        txt_fax = (TextView) findViewById(R.id.complaint_des_txtview_fax);
        txt_complaint = (TextView) findViewById(R.id.complaint_des_txtview_natureof_complaint);
        txt_engg_alloted = (TextView) findViewById(R.id.complaint_des_txtview_engineer_alloted);
        txt_comp_attending_date = (TextView) findViewById(R.id.complaint_des_txtview_comp_attending_date);
        txt_comp_closing_date = (TextView) findViewById(R.id.complaint_des_txtview_comp_closing_date);
        txt_status = (TextView) findViewById(R.id.complaint_des_txtview_status);
        txt_comp_reg_time = (TextView) findViewById(R.id.complaint_des_txtview_comp_reg_time);
        txt_call_attending_time = (TextView) findViewById(R.id.complaint_des_txtview_call_attending_time);
        txt_call_closing_time = (TextView) findViewById(R.id.complaint_des_txtview_call_closing_time);
        txt_service_des = (TextView) findViewById(R.id.complaint_des_txtview_task_desc);

        img_completed = (ImageView) findViewById(R.id.complaint_complete_img);


        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());

        str_id = sharedPreferences.getString("str_select_id", "str_select_id");
        str_user_code = sharedPreferences.getString("str_select_user_code", "str_select_user_code");
        str_comp_number = sharedPreferences.getString("str_select_comp_number", "str_select_comp_number");
        str_date = sharedPreferences.getString("str_select_date", "str_select_date");
        str_product_name = sharedPreferences.getString("str_select_product_name", "str_select_product_name");
        str_model = sharedPreferences.getString("str_select_model", "str_select_model");
        str_comp_cate = sharedPreferences.getString("str_select_comp_cate", "str_select_comp_cate");
        str_comp_type = sharedPreferences.getString("str_select_comp_type", "str_select_comp_type");
        str_purchased_throug = sharedPreferences.getString("str_select_purchased_throug", "str_select_purchased_throug");
        str_mcslno = sharedPreferences.getString("str_select_mcslno", "str_select_mcslno");
        str_warranty = sharedPreferences.getString("str_select_warranty", "str_select_warranty");
        str_customer_name = sharedPreferences.getString("str_select_customer_name", "str_select_customer_name");
        str_address = sharedPreferences.getString("str_select_address", "str_select_address");
        str_street = sharedPreferences.getString("str_select_street", "str_select_street");
        str_landmark = sharedPreferences.getString("str_select_landmark", "str_select_landmark");
        str_city = sharedPreferences.getString("str_select_city", "str_select_city");
        str_contact_person_name = sharedPreferences.getString("str_select_contact_person_name", "str_select_contact_person_name");
        str_phone_no = sharedPreferences.getString("str_select_phone_no", "str_select_phone_no");
        str_addon_phone_no = sharedPreferences.getString("str_select_addon_phone_no", "str_select_addon_phone_no");
        str_cellno = sharedPreferences.getString("str_select_cellno", "str_select_cellno");
        str_email = sharedPreferences.getString("str_select_email", "str_select_email");
        str_addon_email = sharedPreferences.getString("str_select_addon_email", "str_select_addon_email");
        str_fax_no = sharedPreferences.getString("str_select_fax_no", "str_select_fax_no");
        str_complaint = sharedPreferences.getString("str_select_complaint", "str_select_complaint");
        str_engineer_alloted = sharedPreferences.getString("str_select_engineer_alloted", "str_select_engineer_alloted");
        str_comp_attending_date = sharedPreferences.getString("str_select_comp_attending_date", "str_select_comp_attending_date");
        str_comp_closing_date = sharedPreferences.getString("str_select_comp_closing_date", "str_select_comp_closing_date");
        str_status = sharedPreferences.getString("str_select_status", "str_select_status");
        str_comp_reg_time = sharedPreferences.getString("str_select_comp_reg_time", "str_select_comp_reg_time");
        str_call_attending_time = sharedPreferences.getString("str_select_call_attending_time", "str_select_call_attending_time");
        str_call_closing_time = sharedPreferences.getString("str_select_call_closing_time", "str_select_call_closing_time");
        str_service_des = sharedPreferences.getString("str_service_des", "str_service_des");
        str_image = sharedPreferences.getString("str_image", "str_image");

        System.out.println("GET VALUE str_call_attending_time : " + str_call_attending_time);
        System.out.println("GET VALUE str_call_closing_time : " + str_call_closing_time);
        System.out.println("GET VALUE str_call_attending_time : " + str_call_attending_time);
        System.out.println("GET VALUE str_call_closing_time : " + str_call_closing_time);
        System.out.println("GET VALUE str_call_attending_time : " + str_call_attending_time);
        System.out.println("GET VALUE str_call_closing_time : " + str_call_closing_time);
        System.out.println("GET VALUE str_call_attending_time : " + str_call_attending_time);
        System.out.println("GET VALUE str_call_closing_time : " + str_call_closing_time);

        txt_call_attending_time.setText(str_call_attending_time);
        txt_call_closing_time.setText(str_call_closing_time);

        String str_img_path = "http://gemservice.in/employee_app/uploadedimages/" + str_image;
        Glide.with(getApplicationContext()).load(str_img_path)
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(img_completed);



        try {
            txt_user_code.setText(str_user_code);
            txt_comp_number.setText(str_comp_number);
            txt_date.setText(str_date);
            txt_product_name.setText(str_product_name);
            txt_model.setText(str_model);
            txt_cpm_cate.setText(str_comp_cate);
            txt_comp_type.setText(str_comp_type);
            txt_purchased_throug.setText(str_purchased_throug);
            txt_mcslno.setText(str_mcslno);
            txt_warranty.setText(str_warranty);
            txt_customer_name.setText(str_customer_name);
            txt_adress.setText(str_address);
            txt_street.setText(str_street);
            txt_landmark.setText(str_landmark);
            txt_city.setText(str_city);
            txt_contact_person.setText(str_contact_person_name);
            txt_phone_no.setText(str_phone_no);
            txt_addon_phoneno.setText(str_addon_phone_no);
            txt_cellno.setText(str_cellno);
            txt_email.setText(str_email);
            txt_addon_email.setText(str_addon_email);
            txt_fax.setText(str_fax_no);
            txt_complaint.setText(str_complaint);
            System.out.println("TEST LEVEL STATUS" + str_service_des);
            txt_status.setText(str_status);
            txt_comp_reg_time.setText(str_comp_reg_time);
            txt_service_des.setText(str_service_des);
            txt_engg_alloted.setText(str_engineer_alloted);
            txt_comp_attending_date.setText(str_comp_attending_date);
            txt_comp_closing_date.setText(str_comp_closing_date);
            txt_closing_date.setText(str_comp_closing_date);


        } catch (Exception e) {

        }

        txt_phone_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                str_call_number = str_phone_no;
                if (str_call_number.equals("")) {
                    TastyToast.makeText(Activity_Completed_complaints_description.this, "Number Not Available :(", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                } else {
                    FunctionCAllAlert(str_call_number);
                }

            }
        });

        txt_cellno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                str_call_number = str_cellno;
                if (str_call_number.equals("")) {
                    TastyToast.makeText(Activity_Completed_complaints_description.this, "Number Not Available :(", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                } else {
                    FunctionCAllAlert(str_call_number);
                }
            }
        });

        txt_addon_phoneno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                str_call_number = str_addon_phone_no;
                if (str_call_number.equals("")) {
                    TastyToast.makeText(Activity_Completed_complaints_description.this, "Number Not Available :(", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                } else {
                    FunctionCAllAlert(str_call_number);
                }
            }
        });

        txt_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent mailClient = new Intent(Intent.ACTION_VIEW);
                mailClient.setClassName("com.google.android.gm", "com.google.android.gm.ConversationListActivity");
                startActivity(mailClient);
            }
        });
    }

    private void FunctionCAllAlert(final String str_number) {

        new android.app.AlertDialog.Builder(Activity_Completed_complaints_description.this)
                .setTitle("Gem India")
                .setMessage("Want to make a call to this Number?\n" + str_number)
                .setIcon(R.mipmap.ic_launcher)
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.dismiss();
                            }
                        }).setPositiveButton("Done",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        try {
                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse("tel:" + str_number));
                            if (ActivityCompat.checkSelfPermission(Activity_Completed_complaints_description.this,
                                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                return;
                            }
                            startActivity(callIntent);
                        } catch (Exception e) {

                        }
                    }
                }).show();
    }

}