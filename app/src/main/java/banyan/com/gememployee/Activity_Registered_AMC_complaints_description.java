package banyan.com.gememployee;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.sdsmdg.tastytoast.TastyToast;

/**
 * Created by Jo on 2/17/2017.
 */

public class Activity_Registered_AMC_complaints_description extends AppCompatActivity {

    private Toolbar mToolbar;

    String str_call_number;

    String str_id, str_user_code, str_comp_number, str_date, str_product_name, str_model,str_model_no, str_comp_cate, str_comp_type, str_purchased_throug, str_mcslno,
            str_warranty, str_customer_name, str_address, str_street, str_landmark, str_city, str_contact_person_name, str_phone_no, str_addon_phone_no,
            str_cellno, str_email, str_addon_email, str_fax_no, str_complaint, str_engineer_alloted, str_comp_attending_date, str_comp_closing_date, str_status,
            str_comp_reg_time, str_call_attending_time, str_call_closing_time;

    TextView txt_user_code, txt_comp_number, txt_product_name, txt_model, txt_purchased_throug, txt_mcslno, txt_customer_name,
            txt_street, txt_landmark, txt_city, txt_contact_person, txt_phone_no, txt_addon_phoneno,
            txt_cellno, txt_email, txt_addon_email, txt_fax,txt_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_complaints_amc_description);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        System.out.println("Just in");

        txt_comp_number = (TextView) findViewById(R.id.complaint_des_txtview_comp_number);
        txt_user_code = (TextView) findViewById(R.id.complaint_des_txtview_complaint_reg_by);
        txt_product_name = (TextView) findViewById(R.id.complaint_des_txtview_product_type);
        txt_model = (TextView) findViewById(R.id.complaint_des_txtview_model);
        txt_purchased_throug = (TextView) findViewById(R.id.complaint_des_txtview_purchace);
        txt_mcslno = (TextView) findViewById(R.id.complaint_des_txtview_mcslno);
        txt_customer_name = (TextView) findViewById(R.id.complaint_des_txtview_cus_name);
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
        txt_status = (TextView) findViewById(R.id.complaint_des_txtview_status);

        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());


        str_id = sharedPreferences.getString("str_amc_select_id", "str_amc_select_id");
        str_user_code = sharedPreferences.getString("str_amc_select_user_code", "str_amc_select_user_code");
        str_comp_number = sharedPreferences.getString("str_amc_select_comp_number", "str_amc_select_comp_number");
        str_product_name = sharedPreferences.getString("str_amc_select_product_name", "str_amc_select_product_name");
        str_model = sharedPreferences.getString("str_amc_select_model", "str_amc_select_model");
        str_model_no = sharedPreferences.getString("str_amc_select_model_no", "str_amc_select_model_no");
        str_purchased_throug = sharedPreferences.getString("str_amc_select_purchased_throug", "str_amc_select_purchased_throug");
        str_mcslno = sharedPreferences.getString("str_amc_select_mcslno", "str_amc_select_mcslno");
        str_customer_name = sharedPreferences.getString("str_amc_select_customer_name", "str_amc_select_customer_name");
        str_street = sharedPreferences.getString("str_amc_select_street", "str_amc_select_street");
        str_landmark = sharedPreferences.getString("str_amc_select_landmark", "str_amc_select_landmark");
        str_city = sharedPreferences.getString("str_amc_select_city", "str_amc_select_city");
        str_contact_person_name = sharedPreferences.getString("str_amc_select_contact_person_name", "str_amc_select_contact_person_name");
        str_phone_no = sharedPreferences.getString("str_amc_select_phone_no", "str_amc_select_phone_no");
        str_addon_phone_no = sharedPreferences.getString("str_amc_select_addon_phone_no", "str_amc_select_addon_phone_no");
        str_cellno = sharedPreferences.getString("str_amc_select_cellno", "str_amc_select_cellno");
        str_email = sharedPreferences.getString("str_amc_select_email", "str_amc_select_email");
        str_addon_email = sharedPreferences.getString("str_amc_select_addon_email", "str_amc_select_addon_email");
        str_fax_no = sharedPreferences.getString("str_amc_select_amc_freq", "str_amc_select_amc_freq");
        str_status = sharedPreferences.getString("str_amc_select_status", "str_amc_select_status");



        System.out.println("GET VALUE str_status : " + str_status);

        try {
            txt_comp_number.setText(str_comp_number);
            txt_user_code.setText(str_user_code);
            txt_product_name.setText(str_product_name);
            txt_model.setText(str_model);
            txt_purchased_throug.setText(str_purchased_throug);
            txt_mcslno.setText(str_mcslno);
            txt_customer_name.setText(str_customer_name);
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
            txt_status.setText(str_status);

        } catch (Exception e) {

        }

        txt_phone_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                str_call_number = str_phone_no;
                if (str_call_number.equals("")) {
                    TastyToast.makeText(Activity_Registered_AMC_complaints_description.this, "Number Not Available :(", TastyToast.LENGTH_LONG, TastyToast.ERROR);
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
                    TastyToast.makeText(Activity_Registered_AMC_complaints_description.this, "Number Not Available :(", TastyToast.LENGTH_LONG, TastyToast.ERROR);
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
                    TastyToast.makeText(Activity_Registered_AMC_complaints_description.this, "Number Not Available :(", TastyToast.LENGTH_LONG, TastyToast.ERROR);
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

        new android.app.AlertDialog.Builder(Activity_Registered_AMC_complaints_description.this)
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
                            if (ActivityCompat.checkSelfPermission(Activity_Registered_AMC_complaints_description.this,
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