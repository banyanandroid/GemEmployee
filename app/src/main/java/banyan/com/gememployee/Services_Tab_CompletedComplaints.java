package banyan.com.gememployee;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class Services_Tab_CompletedComplaints extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private ProgressDialog pDialog;
    public static RequestQueue queue;
    String TAG = "Complaints";
    private static final String TAG_NAME = "name";

    public static final String TAG_COMP_ID = "complaint_no";
    public static final String TAG_CUST_NAME = "customer";
    public static final String TAG_STATUS = "status";


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
        setContentView(R.layout.fragment_services_completed_complaints);

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
                                            Function_GetCompleted_jobs();

                                        } catch (Exception e) {
                                            // TODO: handle exception
                                        }
                                    }
                                }
        );

    }

    /**
     * This method is called when swipe refresh is pulled down
     */
    @Override
    public void onRefresh() {
        try {
            complaint_list.clear();
            queue = Volley.newRequestQueue(getApplicationContext());
            Function_GetCompleted_jobs();

        } catch (Exception e) {
            // TODO: handle exception
        }
    }


    /********************************
     * User GetCompleted_jobs
     *********************************/

    private void Function_GetCompleted_jobs() {

        String str_url = "http://gemservice.in/employee_app/WebService.php?operation=Complaints_completed&code=" + str_user_id + "&first=1&last=100";

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

                            String str_id = obj1.getString("id");
                            String str_user_code = obj1.getString("usercode");
                            String str_comp_number = obj1.getString("complaint_no");
                            String str_date = obj1.getString("complaint_date");
                            String str_comp_register_by = obj1.getString("customer");
                            String str_comp_type = obj1.getString("comp_type");
                            String str_product_name = obj1.getString("product_name");
                            String str_model = obj1.getString("model");
                            String str_purchased_throug = obj1.getString("pur_through");
                            String str_mcslno = obj1.getString("mcslno");
                            String str_warranty = obj1.getString("warrantystatus");
                            String str_customer_name = obj1.getString("customer");
                            String str_address = obj1.getString("address");
                            String str_contact_person_name = obj1.getString("cpersonname");
                            String str_phone_no = obj1.getString("phone_no");
                            String str_addon = obj1.getString("cell_no");
                            String str_email = obj1.getString("email");
                            String str_fax_no = obj1.getString("fax");
                            String str_nature_of_comp = obj1.getString("nature_of_complaints");
                            String str_engineer_alloted = obj1.getString("ser_eng_code");
                            String str_comp_attending_date = obj1.getString("comp_attending_date");
                            String str_comp_closing_date = obj1.getString("comp_closing_date");
                            String str_status = obj1.getString("status");
                            String str_comp_reg_time = obj1.getString("comp_reg_timestamp");
                            String str_call_attending_time = obj1.getString("attencall_timestamp");
                            String str_call_closing_time = obj1.getString("comp_closing_date");

                            // creating new HashMap
                            HashMap<String, String> map = new HashMap<String, String>();

                            // adding each child node to HashMap key => value
                            map.put(TAG_COMP_ID, str_comp_number);
                            map.put(TAG_CUST_NAME, str_comp_register_by);
                            map.put(TAG_STATUS, str_status);

                            complaint_list.add(map);

                            System.out.println("HASHMAP ARRAY" + complaint_list);


                            adapter = new Completed_complaints_Adapter(Services_Tab_CompletedComplaints.this,
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
                pDialog.hide();
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
