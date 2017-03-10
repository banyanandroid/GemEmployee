package banyan.com.gememployee;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import banyan.com.gememployee.Adapter.Completed_complaints_Adapter;
import banyan.com.gememployee.global.SessionManager;

/**
 * Created by Jo on 7/27/2016.
 */
public class Services_Tab_PendingOngoing extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener  {

    private Toolbar mToolbar;

    public static RequestQueue queue;
    String TAG = "Complaints";
    private static final String TAG_NAME = "name";

    public static final String TAG_ID = "id";
    public static final String TAG_USER_CODE = "usercode";
    public static final String TAG_COMP_NO = "complaint_no";
    public static final String TAG_COMP_DATE = "complaint_date";
    public static final String TAG_PRODUCT_NAME = "product_name";
    public static final String TAG_MODEL = "model";
    public static final String TAG_COMP_CATE = "complaint_category";
    public static final String TAG_COMP_TYPE = "comp_type";
    public static final String TAG_PUR_THROUGH = "pur_through";
    public static final String TAG_MCSLNO = "mcslno";
    public static final String TAG_WARRANTY = "warrantystatus";
    public static final String TAG_CUSTOMER = "customer";
    public static final String TAG_ADDRESS = "address";
    public static final String TAG_STREET = "street";
    public static final String TAG_LANDMARK = "landmark";
    public static final String TAG_CITY = "city";
    public static final String TAG_CONATACT_PERSON = "cpersonname";
    public static final String TAG_PHONENO = "phone_no";
    public static final String TAG_ADDON_PHONENO = "addon_phone_no";
    public static final String TAG_CELLNO = "cell_no";
    public static final String TAG_EMAIL = "email";
    public static final String TAG_ADDON_EMAIL = "addon_email";
    public static final String TAG_FAX = "fax";
    public static final String TAG_COMPAINT = "nature_of_complaints";
    public static final String TAG_SER_ENG_CODE = "service_engineer_allotment";
    public static final String TAG_COMP_ATTEND_DATE = "comp_attending_date";
    public static final String TAG_COMP_CLOSING_DATE = "comp_closing_date";
    public static final String TAG_STATUS = "status";
    public static final String TAG_REG_TIMESTAMP = "comp_reg_timestamp";
    public static final String TAG_ATTENDCALL_TIMESTAMP = "attencall_timestamp";
    public static final String TAG_CALL_CLOSING_TIMESTAMP = "complete_report_timestamp";


    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView listView;

    static ArrayList<HashMap<String, String>> complaint_list;

    HashMap<String, String> params = new HashMap<String, String>();

    public Completed_complaints_Adapter adapter;

    String str_user_id, result;

    // Session Manager Class
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_services_pendingongoing);

        // Session Variables
        session = new SessionManager(getApplicationContext());

        // get user data from session
        HashMap<String, String> user = session.getUserDetails();

        // name
        str_user_id = user.get(SessionManager.KEY_USER_ID);

        // Hashmap for ListView
        complaint_list = new ArrayList<HashMap<String, String>>();

        listView = (ListView) findViewById(R.id.listView);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);

        // Hashmap for ListView
        complaint_list = new ArrayList<HashMap<String, String>>();

        swipeRefreshLayout.setOnRefreshListener(this);

        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);

                                        try {
                                            queue = Volley.newRequestQueue(getApplicationContext());
                                            Function_GetPending_jobs();

                                        } catch (Exception e) {
                                            // TODO: handle exception
                                        }
                                    }
                                }
        );


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                String str_select_id = complaint_list.get(position).get(TAG_ID);
                String str_select_user_code = complaint_list.get(position).get(TAG_USER_CODE);
                String str_select_comp_number = complaint_list.get(position).get(TAG_COMP_NO);
                String str_select_date = complaint_list.get(position).get(TAG_COMP_DATE);
                String str_select_product_name = complaint_list.get(position).get(TAG_PRODUCT_NAME);
                String str_select_model = complaint_list.get(position).get(TAG_MODEL);
                String str_select_comp_cate = complaint_list.get(position).get(TAG_COMP_CATE);
                String str_select_comp_type = complaint_list.get(position).get(TAG_COMP_TYPE);
                String str_select_purchased_throug = complaint_list.get(position).get(TAG_PUR_THROUGH);
                String str_select_mcslno = complaint_list.get(position).get(TAG_MCSLNO);
                String str_select_warranty = complaint_list.get(position).get(TAG_WARRANTY);
                String str_select_customer_name = complaint_list.get(position).get(TAG_CUSTOMER);
                String str_select_address = complaint_list.get(position).get(TAG_ADDRESS);
                String str_select_street = complaint_list.get(position).get(TAG_STREET);
                String str_select_landmark = complaint_list.get(position).get(TAG_LANDMARK);
                String str_select_city = complaint_list.get(position).get(TAG_CITY);
                String str_select_contact_person_name = complaint_list.get(position).get(TAG_CONATACT_PERSON);
                String str_select_phone_no = complaint_list.get(position).get(TAG_PHONENO);
                String str_select_addon_phone_no = complaint_list.get(position).get(TAG_ADDON_PHONENO);
                String str_select_cellno = complaint_list.get(position).get(TAG_CELLNO);
                String str_select_email = complaint_list.get(position).get(TAG_EMAIL);
                String str_select_addon_email = complaint_list.get(position).get(TAG_ADDON_EMAIL);
                String str_select_fax_no = complaint_list.get(position).get(TAG_FAX);
                String str_select_complaint = complaint_list.get(position).get(TAG_COMPAINT);
                String str_select_engineer_alloted = complaint_list.get(position).get(TAG_SER_ENG_CODE);
                String str_select_comp_attending_date = complaint_list.get(position).get(TAG_COMP_ATTEND_DATE);
                String str_select_comp_closing_date = complaint_list.get(position).get(TAG_COMP_CLOSING_DATE);
                String str_select_status = complaint_list.get(position).get(TAG_STATUS);
                String str_select_comp_reg_time = complaint_list.get(position).get(TAG_REG_TIMESTAMP);
                String str_select_call_attending_time = complaint_list.get(position).get(TAG_ATTENDCALL_TIMESTAMP);
                String str_select_call_closing_time = complaint_list.get(position).get(TAG_CALL_CLOSING_TIMESTAMP);


                System.out.println("Clicked Chika str_select_status" + str_select_status);
                System.out.println("Clicked Chika str_select_comp_reg_time" + str_select_comp_reg_time);

                SharedPreferences sharedPreferences = PreferenceManager
                        .getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("str_pending_select_id", str_select_id);
                editor.putString("str_pending_select_user_code", str_select_user_code);
                editor.putString("str_pending_select_comp_number", str_select_comp_number);
                editor.putString("str_pending_select_date", str_select_date);
                editor.putString("str_pending_select_product_name", str_select_product_name);
                editor.putString("str_pending_select_model", str_select_model);
                editor.putString("str_pending_select_comp_cate", str_select_comp_cate);
                editor.putString("str_pending_select_comp_type", str_select_comp_type);
                editor.putString("str_pending_select_purchased_throug", str_select_purchased_throug);
                editor.putString("str_pending_select_mcslno", str_select_mcslno);
                editor.putString("str_pending_select_warranty", str_select_warranty);
                editor.putString("str_pending_select_customer_name", str_select_customer_name);
                editor.putString("str_pending_select_address", str_select_address);
                editor.putString("str_pending_select_street", str_select_street);
                editor.putString("str_pending_select_landmark", str_select_landmark);
                editor.putString("str_pending_select_city", str_select_city);
                editor.putString("str_pending_select_contact_person_name", str_select_contact_person_name);
                editor.putString("str_pending_select_phone_no", str_select_phone_no);
                editor.putString("str_pending_select_addon_phone_no", str_select_addon_phone_no);
                editor.putString("str_pending_select_cellno", str_select_cellno);
                editor.putString("str_pending_select_email", str_select_email);
                editor.putString("str_pending_select_addon_email", str_select_addon_email);
                editor.putString("str_pending_select_fax_no", str_select_fax_no);
                editor.putString("str_pending_select_complaint", str_select_complaint);
                editor.putString("str_pending_select_engineer_alloted", str_select_engineer_alloted);
                editor.putString("str_pending_select_comp_attending_date", str_select_comp_attending_date);
                editor.putString("str_pending_select_comp_closing_date", str_select_comp_closing_date);
                editor.putString("str_pending_select_status", str_select_status);
                editor.putString("str_pending_select_comp_reg_time", str_select_comp_reg_time);
                editor.putString("str_pending_select_call_attending_time", str_select_call_attending_time);
                editor.putString("str_pending_select_call_closing_time", str_select_call_closing_time);

                editor.commit();

                Intent i = new Intent(getApplicationContext(), Activity_Pending_complaints_description.class);
                startActivity(i);
            }

        });
    }

    /**
     * This method is called when swipe refresh is pulled down
     */
    @Override
    public void onRefresh() {
        try {
            complaint_list.clear();
            queue = Volley.newRequestQueue(getApplicationContext());
            Function_GetPending_jobs();

        } catch (Exception e) {
            // TODO: handle exception
        }
    }


    /********************************
     * User GetCompleted_jobs
     *********************************/

    private void Function_GetPending_jobs() {

        String str_url = "http://gemservice.in/employee_app/2017_Webservice.php?operation=Complaints_pending&new="+str_user_id;

        StringRequest request = new StringRequest(Request.Method.GET,
                str_url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                try {
                    JSONObject obj = new JSONObject(response);

                    if (response.toString().equals("{\"status\":\"no data found\"}")) {
                        TastyToast.makeText(getApplicationContext(), "No Data Found !", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                    } else {
                        JSONArray arr;
                        arr = obj.getJSONArray("status");

                        for (int i = 0; arr.length() > i; i++) {
                            JSONObject obj1 = arr.getJSONObject(i);

                            String str_id = obj1.getString(TAG_ID);
                            String str_user_code = obj1.getString(TAG_USER_CODE);
                            String str_comp_number = obj1.getString(TAG_COMP_NO);
                            String str_date = obj1.getString(TAG_COMP_DATE);
                            String str_product_name = obj1.getString(TAG_PRODUCT_NAME);
                            String str_model = obj1.getString(TAG_MODEL);
                            String str_comp_cate = obj1.getString(TAG_COMP_CATE);
                            String str_comp_type = obj1.getString(TAG_COMP_TYPE);
                            String str_purchased_throug = obj1.getString(TAG_PUR_THROUGH);
                            String str_mcslno = obj1.getString(TAG_MCSLNO);
                            String str_warranty = obj1.getString(TAG_WARRANTY);
                            String str_customer_name = obj1.getString(TAG_CUSTOMER);
                            String str_address = obj1.getString(TAG_ADDRESS);
                            String str_street = obj1.getString(TAG_STREET);
                            String str_landmark = obj1.getString(TAG_LANDMARK);
                            String str_city = obj1.getString(TAG_CITY);
                            String str_contact_person_name = obj1.getString(TAG_CONATACT_PERSON);
                            String str_phone_no = obj1.getString(TAG_PHONENO);
                            String str_addon_phone_no = obj1.getString(TAG_ADDON_PHONENO);
                            String str_cellno = obj1.getString(TAG_CELLNO);
                            String str_email = obj1.getString(TAG_EMAIL);
                            String str_addon_email = obj1.getString(TAG_ADDON_EMAIL);
                            String str_fax_no = obj1.getString(TAG_FAX);
                            String str_complaint = obj1.getString(TAG_COMPAINT);
                            String str_engineer_alloted = obj1.getString(TAG_SER_ENG_CODE);
                            String str_comp_attending_date = obj1.getString(TAG_COMP_ATTEND_DATE);
                            String str_comp_closing_date = obj1.getString(TAG_COMP_CLOSING_DATE);
                            String str_status = obj1.getString(TAG_STATUS);
                            String str_comp_reg_time = obj1.getString(TAG_REG_TIMESTAMP);
                            String str_call_attending_time = obj1.getString(TAG_ATTENDCALL_TIMESTAMP);
                            String str_call_closing_time = obj1.getString(TAG_CALL_CLOSING_TIMESTAMP);

                            // creating new HashMap
                            HashMap<String, String> map = new HashMap<String, String>();

                            // adding each child node to HashMap key => value
                            map.put(TAG_ID, str_id);
                            map.put(TAG_USER_CODE, str_user_code);
                            map.put(TAG_COMP_NO, str_comp_number);
                            map.put(TAG_COMP_DATE, str_date);
                            map.put(TAG_PRODUCT_NAME, str_product_name);
                            map.put(TAG_MODEL, str_model);
                            map.put(TAG_COMP_CATE, str_comp_cate);
                            map.put(TAG_COMP_TYPE, str_comp_type);
                            map.put(TAG_PUR_THROUGH, str_purchased_throug);
                            map.put(TAG_MCSLNO, str_mcslno);
                            map.put(TAG_WARRANTY, str_warranty);
                            map.put(TAG_CUSTOMER, str_customer_name);
                            map.put(TAG_ADDRESS, str_address);
                            map.put(TAG_STREET, str_street);
                            map.put(TAG_LANDMARK, str_landmark);
                            map.put(TAG_CITY, str_city);
                            map.put(TAG_CONATACT_PERSON, str_contact_person_name);
                            map.put(TAG_PHONENO, str_phone_no);
                            map.put(TAG_ADDON_PHONENO, str_addon_phone_no);
                            map.put(TAG_CELLNO, str_cellno);
                            map.put(TAG_EMAIL, str_email);
                            map.put(TAG_ADDON_EMAIL, str_addon_email);
                            map.put(TAG_FAX, str_fax_no);
                            map.put(TAG_COMPAINT, str_complaint);
                            map.put(TAG_SER_ENG_CODE, str_engineer_alloted);
                            map.put(TAG_COMP_ATTEND_DATE, str_comp_attending_date);
                            map.put(TAG_COMP_CLOSING_DATE, str_comp_closing_date);
                            map.put(TAG_STATUS, str_status);
                            map.put(TAG_REG_TIMESTAMP, str_comp_reg_time);
                            map.put(TAG_ATTENDCALL_TIMESTAMP, str_call_attending_time);
                            map.put(TAG_CALL_CLOSING_TIMESTAMP, str_call_closing_time);

                            complaint_list.add(map);

                            System.out.println("HASHMAP ARRAY" + complaint_list);


                            adapter = new Completed_complaints_Adapter(Services_Tab_PendingOngoing.this,
                                    complaint_list);
                            listView.setAdapter(adapter);


                        }

                    }

                    swipeRefreshLayout.setRefreshing(false);

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                TastyToast.makeText(getApplicationContext(), "Something Went Wrong Buddy", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                swipeRefreshLayout.setRefreshing(false);
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                return params;
            }

        };

        // Adding request to request queue
        queue.add(request);
    }
}
