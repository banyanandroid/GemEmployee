package banyan.com.gememployee;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

import java.util.HashMap;
import java.util.Map;

import banyan.com.gememployee.global.AppConfig;
import banyan.com.gememployee.global.SessionManager;

/**
 * Created by User on 2/8/2017.
 */

public class Activity_Login extends Activity {

    Button btn_login;
    EditText edt_username, edt_password;

    String user_name, user_password;

    private static long back_pressed;

    private static final String TAG_NAME = "emp_name";
    private static final String TAG_ID = "id";
    String TAG = "reg";

    ProgressDialog pDialog;
    public static RequestQueue queue;

    String str_user_name, str_user_id;

    // Session Manager Class
    SessionManager session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Session Manager
        session = new SessionManager(getApplicationContext());

        btn_login = (Button) findViewById(R.id.login_btn_login);
        edt_username = (EditText) findViewById(R.id.login_edt_name);
        edt_password = (EditText) findViewById(R.id.login_edt_password);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                user_name = edt_username.getText().toString();
                user_password = edt_password.getText().toString();

                if (user_name.equals("")) {
                    edt_username.setError("Please Enter Username");
                } else if (user_password.equals("")) {
                    edt_password.setError("Please Enter Password");
                } else {
                    pDialog = new ProgressDialog(Activity_Login.this);
                    pDialog.setMessage("Please wait...");
                    pDialog.show();
                    pDialog.setCancelable(false);
                    queue = Volley.newRequestQueue(Activity_Login.this);
                    Function_Authentication();
                }

            }
        });


    }

    /********************************
     * User Authentication
     *********************************/

    private void Function_Authentication() {

        String str_url = "http://gemservice.in/employee_app/WebService.php?operation=login" + "&username=" + user_name + "&password=" + user_password;

        System.out.println("Final URL : " + str_url);

        StringRequest request = new StringRequest(Request.Method.GET,
                str_url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                try {
                    JSONObject obj = new JSONObject(response);

                    if (response.toString().equals("{\"result\":\"failed\"}")) {
                        TastyToast.makeText(getApplicationContext(), "Bad Credentials Buddy !", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                    } else {
                        JSONObject result = obj.getJSONObject("result");
                        System.out.println("SRRRR : " + result);
                        JSONObject user_details = result.getJSONObject("userdetails");

                        String str_id = user_details.getString("emp_code");
                        String str_user_name = user_details.getString("emp_name");

                        session.createLoginSession(str_user_name, str_id);
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                        finish();

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
                TastyToast.makeText(getApplicationContext(), "Something Went Wrong Buddy", TastyToast.LENGTH_LONG, TastyToast.ERROR);
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("operation", "login");
                params.put("useremail", user_name);
                params.put("password", user_password);

                System.out.println("useremail" + user_name);
                System.out.println("password" + user_password);

                return params;
            }

        };

        // Adding request to request queue
        queue.add(request);
    }

    @Override
    public void onBackPressed() {

        if (back_pressed + 2000 > System.currentTimeMillis()) {

            this.moveTaskToBack(true);
        } else {
            Toast.makeText(getBaseContext(), "Press once again to exit!",
                    Toast.LENGTH_SHORT).show();

        }
        back_pressed = System.currentTimeMillis();
    }

}
