package banyan.com.gememployee;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
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

import banyan.com.gememployee.Adapter.AMC_complaints_Adapter;
import banyan.com.gememployee.Adapter.Completed_complaints_Adapter;
import banyan.com.gememployee.global.SessionManager;

/**
 * Created by Jo on 7/27/2016.
 */
public class Services_Tab_Reg_AMC_Complaints extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener  {

    private Toolbar mToolbar;

    public static RequestQueue queue;
    String TAG = "Complaints";
    private static final String TAG_NAME = "name";

    public static final String TAG_ID = "id";
    public static final String TAG_USER_CODE = "usercode";
    public static final String TAG_AMC_NO = "amc_no";
    public static final String TAG_PRODUCT_NAME = "product_name";
    public static final String TAG_MODEL = "model";
    public static final String TAG_MODEL_NO = "model_no";
    public static final String TAG_PUR_THROUGH = "pur_through";
    public static final String TAG_MCSLNO = "mcslno";
    public static final String TAG_CUSTOMER = "customer";
    public static final String TAG_STREET = "street";
    public static final String TAG_LANDMARK = "landmark";
    public static final String TAG_CITY = "city";
    public static final String TAG_CONATACT_PERSON = "cpersonname";
    public static final String TAG_PHONENO = "phone_no";
    public static final String TAG_ADDON_PHONENO = "addon_phone_no";
    public static final String TAG_CELLNO = "cell_no";
    public static final String TAG_EMAIL = "email";
    public static final String TAG_ADDON_EMAIL = "addon_email";
    public static final String TAG_ADDON_FREQUENCY = "amc_frequency";
    public static final String TAG_STATUS = "status";


    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView listView;

    static ArrayList<HashMap<String, String>> complaint_list;

    HashMap<String, String> params = new HashMap<String, String>();

    public AMC_complaints_Adapter adapter;

    String str_user_id, result;

    // Session Manager Class
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_services_amc);

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
                                            Function_GetReg_jobs();

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
                String str_select_comp_number = complaint_list.get(position).get(TAG_AMC_NO);
                String str_select_product_name = complaint_list.get(position).get(TAG_PRODUCT_NAME);
                String str_select_model = complaint_list.get(position).get(TAG_MODEL);
                String str_select_model_no = complaint_list.get(position).get(TAG_MODEL_NO);
                String str_select_purchased_throug = complaint_list.get(position).get(TAG_PUR_THROUGH);
                String str_select_mcslno = complaint_list.get(position).get(TAG_MCSLNO);
                String str_select_customer_name = complaint_list.get(position).get(TAG_CUSTOMER);
                String str_select_street = complaint_list.get(position).get(TAG_STREET);
                String str_select_landmark = complaint_list.get(position).get(TAG_LANDMARK);
                String str_select_city = complaint_list.get(position).get(TAG_CITY);
                String str_select_contact_person_name = complaint_list.get(position).get(TAG_CONATACT_PERSON);
                String str_select_phone_no = complaint_list.get(position).get(TAG_PHONENO);
                String str_select_addon_phone_no = complaint_list.get(position).get(TAG_ADDON_PHONENO);
                String str_select_cellno = complaint_list.get(position).get(TAG_CELLNO);
                String str_select_email = complaint_list.get(position).get(TAG_EMAIL);
                String str_select_addon_email = complaint_list.get(position).get(TAG_ADDON_EMAIL);
                String str_select_amc_freq = complaint_list.get(position).get(TAG_ADDON_FREQUENCY);
                String str_select_status = complaint_list.get(position).get(TAG_STATUS);

                System.out.println("Clicked Chika str_select_comp_number" + str_select_comp_number);

                SharedPreferences sharedPreferences = PreferenceManager
                        .getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("str_amc_select_id", str_select_id);
                editor.putString("str_amc_select_user_code", str_select_user_code);
                editor.putString("str_amc_select_comp_number", str_select_comp_number);
                editor.putString("str_amc_select_product_name", str_select_product_name);
                editor.putString("str_amc_select_model", str_select_model);
                editor.putString("str_amc_select_model_no", str_select_model_no);
                editor.putString("str_amc_select_purchased_throug", str_select_purchased_throug);
                editor.putString("str_amc_select_mcslno", str_select_mcslno);
                editor.putString("str_amc_select_customer_name", str_select_customer_name);
                editor.putString("str_amc_select_street", str_select_street);
                editor.putString("str_amc_select_landmark", str_select_landmark);
                editor.putString("str_amc_select_city", str_select_city);
                editor.putString("str_amc_select_contact_person_name", str_select_contact_person_name);
                editor.putString("str_amc_select_phone_no", str_select_phone_no);
                editor.putString("str_amc_select_addon_phone_no", str_select_addon_phone_no);
                editor.putString("str_amc_select_cellno", str_select_cellno);
                editor.putString("str_amc_select_email", str_select_email);
                editor.putString("str_amc_select_addon_email", str_select_addon_email);
                editor.putString("str_amc_select_amc_freq", str_select_amc_freq);
                editor.putString("str_amc_select_status", str_select_status);
                editor.commit();

                Intent i = new Intent(getApplicationContext(), Activity_Registered_AMC_complaints_description.class);
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
            Function_GetReg_jobs();

        } catch (Exception e) {
            // TODO: handle exception
        }
    }


    /********************************
     * User GetCompleted_jobs
     *********************************/

    public void Function_GetReg_jobs() {

        String str_url = "http://gemservice.in/employee_app/amc_list.php";

        String tag_json_obj = "json_obj_req";
        System.out.println("CAME 1");
        StringRequest request = new StringRequest(Request.Method.POST,
                str_url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                try {
                    JSONObject obj = new JSONObject(response);
                    int success = obj.getInt("success");

                    if (success == 1) {

                        JSONArray arr;

                        arr = obj.getJSONArray("amc");

                        for (int i = 0; arr.length() > i; i++) {
                            JSONObject obj1 = arr.getJSONObject(i);

                            String str_id = obj1.getString(TAG_ID);
                            String str_user_code = obj1.getString(TAG_USER_CODE);
                            String str_comp_number = obj1.getString(TAG_AMC_NO);
                            String str_product_name = obj1.getString(TAG_PRODUCT_NAME);
                            String str_model = obj1.getString(TAG_MODEL);
                            String str_model_no = obj1.getString(TAG_MODEL_NO);
                            String str_purchased_throug = obj1.getString(TAG_PUR_THROUGH);
                            String str_mcslno = obj1.getString(TAG_MCSLNO);
                            String str_customer_name = obj1.getString(TAG_CUSTOMER);
                            String str_street = obj1.getString(TAG_STREET);
                            String str_landmark = obj1.getString(TAG_LANDMARK);
                            String str_city = obj1.getString(TAG_CITY);
                            String str_contact_person_name = obj1.getString(TAG_CONATACT_PERSON);
                            String str_phone_no = obj1.getString(TAG_PHONENO);
                            String str_addon_phone_no = obj1.getString(TAG_ADDON_PHONENO);
                            String str_cellno = obj1.getString(TAG_CELLNO);
                            String str_email = obj1.getString(TAG_EMAIL);
                            String str_addon_email = obj1.getString(TAG_ADDON_EMAIL);
                            String str_freq = obj1.getString(TAG_ADDON_FREQUENCY);
                            String str_status = obj1.getString(TAG_STATUS);

                            // creating new HashMap
                            HashMap<String, String> map = new HashMap<String, String>();

                            // adding each child node to HashMap key => value
                            map.put(TAG_ID, str_id);
                            map.put(TAG_USER_CODE, str_user_code);
                            map.put(TAG_AMC_NO, str_comp_number);
                            map.put(TAG_PRODUCT_NAME, str_product_name);
                            map.put(TAG_MODEL, str_model);
                            map.put(TAG_PUR_THROUGH, str_purchased_throug);
                            map.put(TAG_MCSLNO, str_mcslno);
                            map.put(TAG_CUSTOMER, str_customer_name);
                            map.put(TAG_STREET, str_street);
                            map.put(TAG_LANDMARK, str_landmark);
                            map.put(TAG_CITY, str_city);
                            map.put(TAG_CONATACT_PERSON, str_contact_person_name);
                            map.put(TAG_PHONENO, str_phone_no);
                            map.put(TAG_ADDON_PHONENO, str_addon_phone_no);
                            map.put(TAG_CELLNO, str_cellno);
                            map.put(TAG_EMAIL, str_email);
                            map.put(TAG_ADDON_EMAIL, str_addon_email);
                            map.put(TAG_ADDON_FREQUENCY, str_freq);
                            map.put(TAG_STATUS, str_status);

                            complaint_list.add(map);

                            System.out.println("HASHMAP ARRAY" + complaint_list);


                            adapter = new AMC_complaints_Adapter(Services_Tab_Reg_AMC_Complaints.this,
                                    complaint_list);
                            listView.setAdapter(adapter);


                        }

                    } else if (success == 0) {
                        TastyToast.makeText(getApplicationContext(), "No Data Found !", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                // stopping swipe refresh
                swipeRefreshLayout.setRefreshing(false);
                //  pDialog.hide();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                TastyToast.makeText(getApplicationContext(), "No Data Found !", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                // stopping swipe refresh
                swipeRefreshLayout.setRefreshing(false);

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("user_id", str_user_id); // replace as str_id

                System.out.println("user_id" + str_user_id);

                return params;
            }

        };

        // Adding request to request queue
        queue.add(request);
    }
}
