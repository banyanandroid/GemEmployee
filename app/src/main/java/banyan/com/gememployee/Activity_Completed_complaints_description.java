package banyan.com.gememployee;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by User on 2/17/2017.
 */

public class Activity_Completed_complaints_description extends AppCompatActivity {

    String str_id, str_user_code, str_comp_number, str_date, str_product_name, str_model, str_comp_cate, str_comp_type, str_purchased_throug, str_mcslno,
            str_warranty, str_customer_name, str_address, str_street, str_landmark, str_city, str_contact_person_name, str_phone_no, str_addon_phone_no,
            str_cellno, str_email, str_addon_email, str_fax_no, str_complaint, str_engineer_alloted, str_comp_attending_date, str_comp_closing_date, str_status,
            str_comp_reg_time, str_call_attending_time, str_call_closing_time;

    TextView txt_user_code, txt_comp_number, txt_date, txt_product_name, txt_model, txt_cpm_cate, txt_comp_type, txt_purchased_throug, txt_mcslno,
            txt_warranty, txt_customer_name, txt_adress, txt_street, txt_landmark, txt_city, txt_contact_person, txt_phone_no, txt_addon_phoneno,
            txt_cellno, txt_email, txt_addon_email, txt_fax, txt_complaint, txt_engg_alloted, txt_comp_attending_date, txt_comp_closing_date, txt_closing_date, txt_status,
            txt_comp_reg_time, txt_call_attending_time, txt_call_closing_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_complaints_description);


        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(Activity_Completed_complaints_description.this);

        str_id = sharedPreferences.getString("schedule_id", "schedule_id");
        str_user_code = sharedPreferences.getString("schedule_id", "schedule_id");
        str_comp_number = sharedPreferences.getString("schedule_id", "schedule_id");
        str_date = sharedPreferences.getString("schedule_id", "schedule_id");
        str_product_name = sharedPreferences.getString("schedule_id", "schedule_id");
        str_model = sharedPreferences.getString("schedule_id", "schedule_id");
        str_comp_cate = sharedPreferences.getString("schedule_id", "schedule_id");
        str_comp_type = sharedPreferences.getString("schedule_id", "schedule_id");
        str_purchased_throug = sharedPreferences.getString("schedule_id", "schedule_id");
        str_mcslno = sharedPreferences.getString("schedule_id", "schedule_id");
        str_warranty = sharedPreferences.getString("schedule_id", "schedule_id");
        str_customer_name = sharedPreferences.getString("schedule_id", "schedule_id");
        str_address = sharedPreferences.getString("schedule_id", "schedule_id");
        str_street = sharedPreferences.getString("schedule_id", "schedule_id");
        str_landmark = sharedPreferences.getString("schedule_id", "schedule_id");
        str_city = sharedPreferences.getString("schedule_id", "schedule_id");
        str_contact_person_name = sharedPreferences.getString("schedule_id", "schedule_id");
        str_phone_no = sharedPreferences.getString("schedule_id", "schedule_id");
        str_addon_phone_no = sharedPreferences.getString("schedule_id", "schedule_id");
        str_cellno = sharedPreferences.getString("schedule_id", "schedule_id");
        str_email = sharedPreferences.getString("schedule_id", "schedule_id");
        str_addon_email = sharedPreferences.getString("schedule_id", "schedule_id");
        str_fax_no = sharedPreferences.getString("schedule_id", "schedule_id");
        str_complaint = sharedPreferences.getString("schedule_id", "schedule_id");
        str_engineer_alloted = sharedPreferences.getString("schedule_id", "schedule_id");
        str_comp_attending_date = sharedPreferences.getString("schedule_id", "schedule_id");
        str_comp_closing_date = sharedPreferences.getString("schedule_id", "schedule_id");
        str_status = sharedPreferences.getString("schedule_id", "schedule_id");
        str_comp_reg_time = sharedPreferences.getString("schedule_id", "schedule_id");
        str_call_attending_time = sharedPreferences.getString("schedule_id", "schedule_id");
        str_call_closing_time = sharedPreferences.getString("schedule_id", "schedule_id");

    }

}