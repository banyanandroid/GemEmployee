package banyan.com.gememployee;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import banyan.com.gememployee.global.SessionManager;

/**
 * Created by Jo on 2/9/2017.
 */
public class Fragment_AMC_ComplaintRegister extends Fragment {

    String TAG = "Complaints";
    private static final String TAG_NAME = "name";

    // Session Manager Class
    SessionManager session;

    EditText edt_customername, edt_street, edt_landmark, edt_city, edt_pincode, edt_contact_person_name, edt_phone_no,
            edt_customer_cellno, edt_email, edt_addon_phone, edt_addon_email, edt_model, edt_model_number,
            edt_mc1, edt_mc2, edt_mc3;

    TextView txt_date;

    Button btn_reset, btn_submit;

    Spinner spn_product, spn_product_model, spn_purchaced_through;

    LinearLayout layout_model;

    String str_selected_product, str_selected_model, str_selected_dealer;
    String str_delar, str_complaint_number;

    String str_send_date, str_send_user_id, str_send_complaint_date, str_send_product_name, str_send_model, str_send_model_number,
            str_send_complaint_category, str_send_complaint_type, str_send_pur_through, str_Send_mc1, str_send_mc2, str_send_mc3,
            str_send_warranty_status, str_send_customer_name, str_send_address1, str_send_address2, str_send_address3, str_send_address4,
            str_send_contact_person_name, str_send_phone_number,str_cell_number, str_send_addon_phone_number, str_send_email, str_send_addon_email;

    String str_year, str_warranty_type;
    String str_month = "month";
    String str_current_year;
    String str_current_month;

    ArrayList<String> Arraylist_dealers = null;
    String[] Arraylist_product_model;

    /*String[] Arraylist_complaint_category;
    String[] Arraylist_complaint_type;*/


    ProgressDialog pDialog;
    public static RequestQueue queue;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_amc_register, null);

        // TextView
        txt_date = (TextView) rootView.findViewById(R.id.amc_complaint_txtview_date);

        // Spinners
        spn_product = (Spinner) rootView.findViewById(R.id.amc_complaint_spinner_product);
        spn_product_model = (Spinner) rootView.findViewById(R.id.amc_complaint_spinner_product_model);
        spn_purchaced_through = (Spinner) rootView.findViewById(R.id.amc_complaint_spinner_purchased_through);

        // Edit Text

        edt_model = (EditText) rootView.findViewById(R.id.amc_complaint_model_no1);
        edt_model_number = (EditText) rootView.findViewById(R.id.amc_complaint_model_no);

        edt_mc1 = (EditText) rootView.findViewById(R.id.amc_complaint_edt_mcsl1);
        edt_mc2 = (EditText) rootView.findViewById(R.id.amc_complaint_edt_mcsl2);
        edt_mc3 = (EditText) rootView.findViewById(R.id.amc_complaint_edt_mcsl3);

        edt_customername = (EditText) rootView.findViewById(R.id.amc_complaint_edt_customer_name);
        edt_city = (EditText) rootView.findViewById(R.id.amc_complaint_edt_Address1);
        edt_street = (EditText) rootView.findViewById(R.id.amc_complaint_edt_Address2);
        edt_landmark = (EditText) rootView.findViewById(R.id.amc_complaint_edt_Address3);
        edt_pincode = (EditText) rootView.findViewById(R.id.amc_complaint_edt_Address4);
        edt_contact_person_name = (EditText) rootView.findViewById(R.id.amc_complaint_edt_contact_person);
        edt_phone_no = (EditText) rootView.findViewById(R.id.amc_complaint_edt_phone_no);
        edt_addon_phone = (EditText) rootView.findViewById(R.id.amc_complaint_edt_addon_phone_no);
        edt_customer_cellno = (EditText) rootView.findViewById(R.id.amc_complaint_edt_customer_cellno);
        edt_email = (EditText) rootView.findViewById(R.id.amc_complaint_edt_email);
        edt_addon_email = (EditText) rootView.findViewById(R.id.amc_complaint_edt_addon_email);

        // Button

        btn_reset = (Button) rootView.findViewById(R.id.amc_complaint_btn_reset);
        btn_submit = (Button) rootView.findViewById(R.id.amc_complaint_btn_submit);

        // Linear Layout
        layout_model = (LinearLayout) rootView.findViewById(R.id.amc_complaint_reg_linear_model);

        //String Array

        Arraylist_dealers = new ArrayList<String>();

        // Session Variables
        session = new SessionManager(getActivity());

        // get user data from session
        HashMap<String, String> user = session.getUserDetails();

        // name
        str_send_user_id = user.get(SessionManager.KEY_USER_ID);


        /********************************
         *  Load Current Day
         * *********************************/

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("dd / MM / yyyy");
        String strDate = mdformat.format(calendar.getTime());
        txt_date.setText("" + strDate);

        str_current_year = "" + calendar.get(Calendar.YEAR);
        str_current_month = "" + calendar.get(Calendar.MONTH);

        /*******************************
         *  Spinner Loaders
         * ******************************/

        // Product and Product Model Spinner

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.product_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_product.setAdapter(adapter);

        // Spinner Product Interface
        spn_product.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                str_selected_product = parent.getItemAtPosition(pos).toString();

                if (str_selected_product.equals("null")) {

                    TastyToast.makeText(getActivity(), "Please Select Product", TastyToast.LENGTH_LONG, TastyToast.INFO);

                } else if (str_selected_product.equals("DRYER")) {

                    layout_model.setVisibility(View.VISIBLE);
                    // Product Model Loder
                    Arraylist_product_model = new String[]{"NXG", "2KD", "2KD7", "2KW", "RAD", "HLN", "HLD", "SPD"};
                    List<String> produt_type = new ArrayList<String>(Arrays.asList(Arraylist_product_model));
                    ArrayAdapter<String> adapter_product_type = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, produt_type);
                    adapter_product_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spn_product_model.setAdapter(adapter_product_type);

                } else if (str_selected_product.equals("CHILLER")) {

                    layout_model.setVisibility(View.VISIBLE);
                    str_warranty_type = "CHILLER";
                    // Product Model Loder
                    Arraylist_product_model = new String[]{"CHA", "CHT"};
                    List<String> produt_type = new ArrayList<String>(Arrays.asList(Arraylist_product_model));
                    ArrayAdapter<String> adapter_product_type = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, produt_type);
                    adapter_product_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spn_product_model.setAdapter(adapter_product_type);

                } else if (str_selected_product.equals("COOLING TOWER")) {

                    layout_model.setVisibility(View.VISIBLE);
                    str_warranty_type = "DRY COOLING TOWER";

                    // Product Model Loder
                    Arraylist_product_model = new String[]{"GCT +", "GCT", "SCT/SCB", "DRY COOLING TOWER"};
                    List<String> produt_type = new ArrayList<String>(Arrays.asList(Arraylist_product_model));
                    ArrayAdapter<String> adapter_product_type = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, produt_type);
                    adapter_product_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spn_product_model.setAdapter(adapter_product_type);

                } else if (str_selected_product.equals("OTHERS")) {

                    layout_model.setVisibility(View.GONE);
                    str_warranty_type = "SMALL PRODUCTS";
                    // Product Model Loder
                    Arraylist_product_model = new String[]{"SMALL PRODUCTS"};
                    List<String> produt_type = new ArrayList<String>(Arrays.asList(Arraylist_product_model));
                    ArrayAdapter<String> adapter_product_type = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, produt_type);
                    adapter_product_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spn_product_model.setAdapter(adapter_product_type);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Another interface callback
            }

        });

        // Spinner Product Model Interface

        spn_product_model.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                str_selected_model = parent.getItemAtPosition(pos).toString();

                if (str_selected_model.equals("null")) {
                    TastyToast.makeText(getActivity(), "Please Select a Valid Model", TastyToast.LENGTH_LONG, TastyToast.INFO);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Another interface callback
            }

        });

        // Purchase Spinner Interface

        spn_purchaced_through.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                str_selected_dealer = Arraylist_dealers.get(arg2);
                System.out.println(str_selected_dealer);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        edt_mc2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (i == 1) {

                    str_month = edt_mc2.getText().toString();
                    int month = Integer.parseInt(str_month);

                    if (month >= 13) {
                        TastyToast.makeText(getActivity(), "Please Enter Valid Month", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });

        edt_mc3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (i == 3) {
                    str_year = edt_mc3.getText().toString();

                    int int_current_year = Integer.parseInt(str_current_year);
                    int select_year = Integer.parseInt(str_year);

                    if (select_year <= int_current_year) {
                        if (str_month.equals("month")) {
                            TastyToast.makeText(getActivity(), "Please Enter Month", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                        }  else {
                            System.out.println("start model :" + str_warranty_type);
                            System.out.println("start month :" + str_month);
                            System.out.println("start year :" + str_year);
                        }

                    } else {
                        TastyToast.makeText(getActivity(), "Please Enter Valid Year", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        try {
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Please wait...");
            pDialog.show();
            pDialog.setCancelable(false);
            queue = Volley.newRequestQueue(getActivity());
            Function_GetDelars();
        } catch (Exception e) {

        }


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                str_send_date = txt_date.getText().toString();
                str_send_product_name = str_selected_product;
                str_send_model = str_selected_model;
                String str_modelno1 = edt_model.getText().toString();
                String str_modelno = edt_model_number.getText().toString();
                str_send_model_number = str_modelno1 + str_modelno;
                str_send_pur_through = str_selected_dealer;
                str_Send_mc1 = edt_mc1.getText().toString();
                str_send_mc2 = edt_mc2.getText().toString();
                str_send_mc3 = edt_mc3.getText().toString();
                str_send_customer_name = edt_customername.getText().toString();
                str_send_address1 = edt_city.getText().toString();
                str_send_address2 = edt_street.getText().toString();
                str_send_address3 = edt_landmark.getText().toString();
                str_send_address4 = edt_pincode.getText().toString();
                str_send_contact_person_name = edt_contact_person_name.getText().toString();
                str_send_phone_number = edt_phone_no.getText().toString();
                str_cell_number = edt_customer_cellno.getText().toString();
                str_send_addon_phone_number = edt_addon_phone.getText().toString();
                str_send_email = edt_email.getText().toString();
                str_send_addon_email = edt_addon_email.getText().toString();

                if (str_send_user_id.equals("")) {
                    TastyToast.makeText(getActivity(), "User Id Not Found !", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                } else if (str_send_date.equals("")) {
                    TastyToast.makeText(getActivity(), "Date Error !", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                }else if (str_send_product_name.equals("null")) {
                    TastyToast.makeText(getActivity(), "Please Select a Valid Product", TastyToast.LENGTH_LONG, TastyToast.INFO);
                } else if (str_send_model.equals("null")) {
                    TastyToast.makeText(getActivity(), "Please Select a Valid Model", TastyToast.LENGTH_LONG, TastyToast.INFO);
                } else if (str_send_pur_through.equals("null")) {
                    TastyToast.makeText(getActivity(), "Please Select a Valid Dealer", TastyToast.LENGTH_LONG, TastyToast.INFO);
                } else if (str_Send_mc1.equals("")) {
                    TastyToast.makeText(getActivity(), "Please Enter MC / SL.No", TastyToast.LENGTH_LONG, TastyToast.INFO);
                    edt_mc1.setError("Please Enter a Valid Number");
                } else if (str_send_mc2.equals("")) {
                    TastyToast.makeText(getActivity(), "Please Enter MC / SL.No", TastyToast.LENGTH_LONG, TastyToast.INFO);
                    edt_mc2.setError("Please Enter a Valid Number");
                } else if (str_send_mc3.equals("")) {
                    TastyToast.makeText(getActivity(), "Please Enter MC / SL.No", TastyToast.LENGTH_LONG, TastyToast.INFO);
                    edt_mc3.setError("Please Enter a Valid Number");
                } else if (str_send_customer_name.equals("")) {
                    TastyToast.makeText(getActivity(), "Please enter Customer Name", TastyToast.LENGTH_LONG, TastyToast.INFO);
                    edt_customername.setError("Please Enter a Vaild Customer Name");
                } else if (str_send_address1.equals("")) {
                    TastyToast.makeText(getActivity(), "Please enter Valid City", TastyToast.LENGTH_LONG, TastyToast.INFO);
                    edt_city.setError("Please Enter a Vaild City");
                } else if (str_send_address2.equals("")) {
                    TastyToast.makeText(getActivity(), "Please enter Valid Street", TastyToast.LENGTH_LONG, TastyToast.INFO);
                    edt_street.setError("Please Enter a Vaild Street");
                } else if (str_send_address3.equals("")) {
                    TastyToast.makeText(getActivity(), "Please enter Valid Landmark", TastyToast.LENGTH_LONG, TastyToast.INFO);
                    edt_landmark.setError("Please Enter a Vaild Landmark");
                } else if (str_send_contact_person_name.equals("")) {
                    TastyToast.makeText(getActivity(), "Please enter Contact Person Name", TastyToast.LENGTH_LONG, TastyToast.INFO);
                    edt_contact_person_name.setError("Please Enter a Contact Person name");
                } else if (str_send_phone_number.equals("")) {
                    TastyToast.makeText(getActivity(), "Please enter Phone Number", TastyToast.LENGTH_LONG, TastyToast.INFO);
                    edt_phone_no.setError("Please Enter a Vaild Phone Number");
                }else if (str_send_email.equals("")) {
                    TastyToast.makeText(getActivity(), "Please enter Email", TastyToast.LENGTH_LONG, TastyToast.INFO);
                    edt_email.setError("Please Enter a Email");
                }  else if (str_cell_number.equals("")) {
                    TastyToast.makeText(getActivity(), "Please Customer Cell Number", TastyToast.LENGTH_LONG, TastyToast.INFO);
                    edt_customer_cellno.setError("Please Enter a Cell Number");
                } else if (str_send_address4.equals("")) {
                    TastyToast.makeText(getActivity(), "Please enter Pincode", TastyToast.LENGTH_LONG, TastyToast.INFO);
                    edt_pincode.setError("Please Enter a Pincode");
                } else {

                    System.out.println("str_send_date " + " : " + str_send_date);
                    System.out.println("str_send_product_name " + " : " + str_send_product_name);
                    System.out.println("str_send_model " + " : " + str_send_model);
                    System.out.println("str_send_model Numer" + " : " + str_send_model_number);
                    System.out.println("str_send_pur_through " + " : " + str_send_pur_through);
                    System.out.println("str_Send_mc1 " + " : " + str_Send_mc1);
                    System.out.println("str_send_mc2 " + " : " + str_send_mc2);
                    System.out.println("str_send_mc3 " + " : " + str_send_mc3);
                    System.out.println("str_send_customer_name " + " : " + str_send_customer_name);
                    System.out.println("str_send_address1 " + " : " + str_send_address1);
                    System.out.println("str_send_address2 " + " : " + str_send_address2);
                    System.out.println("str_send_address3 " + " : " + str_send_address3);
                    System.out.println("str_send_address4 " + " : " + str_send_address4);
                    System.out.println("str_send_contact_person_name " + " : " + str_send_contact_person_name);
                    System.out.println("str_send_phone_number " + " : " + str_send_phone_number);
                    System.out.println("str_send_cell_number " + " : " + str_cell_number);
                    System.out.println("str_send_addon_phone_number " + " : " + str_send_addon_phone_number);
                    System.out.println("str_send_email " + " : " + str_send_email);
                    System.out.println("str_send_addon_email " + " : " + str_send_addon_email);

                    try {
                        pDialog = new ProgressDialog(getActivity());
                        pDialog.setMessage("Please wait...");
                        pDialog.show();
                        pDialog.setCancelable(false);
                        queue = Volley.newRequestQueue(getActivity());
                        Register_Complaint();
                    } catch (Exception e) {

                    }


                }

            }
        });

        return rootView;
    }

    /********************************
     * User Function_GetDelars
     *********************************/

    private void Function_GetDelars() {

        String url_dealers = "http://gemservice.in/employee_app/2017_Webservice.php?operation=dealerlist";

        StringRequest request = new StringRequest(Request.Method.POST,
                url_dealers, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                Log.d("USER_LOGIN", response.toString());

                String str_response = response.toString();

                if (str_response.equals("")) {

                    TastyToast.makeText(getActivity(), "Internal Error !", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                } else {
                    try {
                        JSONObject obj = new JSONObject(response);
                        JSONArray arr;
                        arr = obj.getJSONArray("result");


                        for (int i = 0; arr.length() > i; i++) {
                            JSONObject obj1 = arr.getJSONObject(i);

                            str_delar = obj1.getString(TAG_NAME);

                            Arraylist_dealers.add(str_delar);

                        }
                        try {
                            spn_purchaced_through
                                    .setAdapter(new ArrayAdapter<String>(getActivity(),
                                            android.R.layout.simple_spinner_dropdown_item,
                                            Arraylist_dealers));

                        } catch (Exception e) {

                        }
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
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

                return params;
            }

        };

        // Adding request to request queue
        queue.add(request);
    }

    /********************************
     * Register Complaint
     *********************************/

    private void Register_Complaint() {

        String str_register_complaint = "http://gemservice.in/employee_app/add_amc_process.php";

        StringRequest request = new StringRequest(Request.Method.POST,
                str_register_complaint, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                FunctionAlert();
                Log.d(TAG, response.toString());
                Log.d("Complaint_Number", response.toString());

                try {
                    JSONObject obj = new JSONObject(response);
                    int success = obj.getInt("success");

                    System.out.println("REG" + success);

                    if (success == 1) {

                        TastyToast.makeText(getActivity(), "AMC Complaint Registered Successfully :)", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                        pDialog.hide();
                        try {

                            FunctionAlert();

                            edt_mc1.setText("");
                            edt_mc2.setText("");
                            edt_mc3.setText("");
                            edt_customername.setText("");
                            edt_city.setText("");
                            edt_street.setText("");
                            edt_landmark.setText("");
                            edt_contact_person_name.setText("");
                            edt_phone_no.setText("");
                            edt_addon_phone.setText("");
                            edt_customer_cellno.setText("");
                            edt_email.setText("");
                            edt_addon_email.setText("");

                        } catch (Exception e) {
                            // TODO: handle exception
                        }

                    } else {

                        TastyToast.makeText(getActivity(), "Something Went Wrong :(", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                        pDialog.hide();

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
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("customername", str_send_customer_name);
                params.put("city", str_send_address3);
                params.put("street", str_send_address1);
                params.put("landmark", str_send_address2);
                params.put("pin", str_send_address4);
                params.put("contactperson", str_send_contact_person_name);
                params.put("phoneno", str_send_phone_number);
                params.put("cellno", str_cell_number);
                params.put("email", str_send_email);
                params.put("addon_phoneno", str_send_addon_phone_number);
                params.put("addon_email", str_send_addon_email);
                params.put("mcsnum", str_Send_mc1);
                params.put("mcs2", str_send_mc2);
                params.put("mcs3", str_send_mc3);
                params.put("pd_name", str_send_product_name);
                params.put("model", str_send_model);
                params.put("model_num", str_send_model_number);
                params.put("pur_through", str_send_pur_through);
                params.put("user", str_send_user_id);

                System.out.println("customername"+ str_send_customer_name);
                System.out.println("city"+ str_send_address3);
                System.out.println("street"+ str_send_address1);
                System.out.println("landmark"+ str_send_address2);
                System.out.println("pin"+ str_send_address4);
                System.out.println("contactperson"+ str_send_contact_person_name);
                System.out.println("phoneno"+ str_send_phone_number);
                System.out.println("cellno"+ str_cell_number);
                System.out.println("email"+ str_send_email);
                System.out.println("addon_phoneno"+ str_send_addon_phone_number);
                System.out.println("addon_email"+ str_send_addon_email);
                System.out.println("mcsnum"+ str_Send_mc1);
                System.out.println("mcs2"+ str_send_mc2);
                System.out.println("mcs3"+ str_send_mc3);
                System.out.println("pd_name"+ str_send_product_name);
                System.out.println("model"+ str_send_model);
                System.out.println("model_num"+ str_send_model_number);
                System.out.println("pur_through"+ str_send_pur_through);
                System.out.println("user"+ str_send_user_id);


                return params;
            }

        };

        int socketTimeout = 50000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        // Adding request to request queue
        queue.add(request);
    }

    /***************************************
     * Complaint Registered Alert
     ******************************************/

    private void FunctionAlert() {

        new AlertDialog.Builder(getActivity())
                .setTitle("Gem India")
                .setMessage("AMC Complaint Registered Successfully :)")
                .setIcon(R.mipmap.ic_launcher)

                .setPositiveButton("Done",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {

                            }
                        }).show();
    }


}
