package banyan.com.gememployee;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import banyan.com.gememployee.global.AppConfig;

import static banyan.com.gememployee.Activity_Login.queue;

/**
 * Created by Jo on 2/9/2017.
 */
public class Fragment_ComplaintRegister extends Fragment {

    String TAG = "Complaints";
    private static final String TAG_NAME = "name";

    String str_selected_product, str_selected_model, str_selected_complaint_category, str_selected_complaint_type, str_selected_dealer;
    String str_delar;

    ArrayList<String> Arraylist_dealers = null;
    String[] Arraylist_product_model;
    String[] Arraylist_complaint_category;
    String[] Arraylist_complaint_type;


    ProgressDialog pDialog;
    public static RequestQueue queue;

    Spinner spn_product, spn_product_model, spn_complaint_category, spn_complaint_type, spn_purchaced_through;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_complaint_register, null);

        // Spinners
        spn_product = (Spinner) rootView.findViewById(R.id.complaint_spinner_product);
        spn_product_model = (Spinner) rootView.findViewById(R.id.complaint_spinner_product_model);
        spn_complaint_category = (Spinner) rootView.findViewById(R.id.complaint_spinner_product_category);
        spn_complaint_type = (Spinner) rootView.findViewById(R.id.complaint_spinner_complaint_type);
        spn_purchaced_through = (Spinner) rootView.findViewById(R.id.complaint_spinner_purchased_through);

        //String Array

        Arraylist_dealers = new ArrayList<String>();


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

                if (str_selected_product.equals("")) {

                    TastyToast.makeText(getActivity(), "Please Select Product", TastyToast.LENGTH_LONG, TastyToast.INFO);

                } else if (str_selected_product.equals("Select Product")) {

                    TastyToast.makeText(getActivity(), "Please Select a Valid Product", TastyToast.LENGTH_LONG, TastyToast.INFO);

                } else if (str_selected_product.equals("Dryer")) {

                    // Product Model Loder
                    Arraylist_product_model = new String[]{"Select Model", "NXG", "2KD", "2KD7", "2KW", "RAD", "HLN", "HLD", "SPD"};
                    List<String> produt_type = new ArrayList<String>(Arrays.asList(Arraylist_product_model));
                    ArrayAdapter<String> adapter_product_type = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, produt_type);
                    adapter_product_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spn_product_model.setAdapter(adapter_product_type);

                    // Complaint Type Loader
                    Arraylist_complaint_type = new String[]{"Select Complaint", "Autodrain Not opening/fully open", "Air Leak/Air pr.drop"
                            , "Compressor not running", "Electrical Related Issues", "Evaporater / Heat Exchanger Failure", "Fan Motor  not running"
                            , "Gas leakage/ LP Open", "High  / Low Dew Point/Moisture in line", "Noise/Vibration", "Performance Issues", "Tem.controller / PLC failure"
                            , "Trip  (OLR, SPP, MCB, LP, HP, LOW WATER)", "Others"};
                    List<String> compaint_type = new ArrayList<String>(Arrays.asList(Arraylist_complaint_type));
                    ArrayAdapter<String> adapter_compaint_type = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, compaint_type);
                    adapter_compaint_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spn_complaint_type.setAdapter(adapter_compaint_type);

                } else if (str_selected_product.equals("Chiller")) {

                    // Product Model Loder
                    Arraylist_product_model = new String[]{"Select Model", "CHILLER"};
                    List<String> produt_type = new ArrayList<String>(Arrays.asList(Arraylist_product_model));
                    ArrayAdapter<String> adapter_product_type = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, produt_type);
                    adapter_product_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spn_product_model.setAdapter(adapter_product_type);

                    // Complaint Type Loader
                    Arraylist_complaint_type = new String[]{"Select Complaint", "AFT / PHE Failure", "Compressor Failure", "Electrical Failure", "Fan Motor  Failure"
                            , "Flow Switch Failure", "FRP Tank Leakage", "Gas leakage", "high /Low pressure switch failure", "Noise", "Performance Issues", "Pump Failure"
                            , "Tem.controller / PLC failure", "Transit", "Trip  (OLR, SPP, MCB, LOW WATER)", "Others"};
                    List<String> compaint_type = new ArrayList<String>(Arrays.asList(Arraylist_complaint_type));
                    ArrayAdapter<String> adapter_compaint_type = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, compaint_type);
                    adapter_compaint_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spn_complaint_type.setAdapter(adapter_compaint_type);

                } else if (str_selected_product.equals("Cooling Tower")) {

                    // Product Model Loder
                    Arraylist_product_model = new String[]{"Select Model", "GCT +", "GCT", "SCT/SCB", "DRY COOLING TOWER"};
                    List<String> produt_type = new ArrayList<String>(Arrays.asList(Arraylist_product_model));
                    ArrayAdapter<String> adapter_product_type = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, produt_type);
                    adapter_product_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spn_product_model.setAdapter(adapter_product_type);

                    // Complaint Type Loader
                    Arraylist_complaint_type = new String[]{"Select Complaint", "Leak", "Fan Damage", "Motor Failure", "Noise/Vibration", "Performance Issues", "Spinkler Failure", "Others"};
                    List<String> compaint_type = new ArrayList<String>(Arrays.asList(Arraylist_complaint_type));
                    ArrayAdapter<String> adapter_compaint_type = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, compaint_type);
                    adapter_compaint_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spn_complaint_type.setAdapter(adapter_compaint_type);

                } else if (str_selected_product.equals("Others")) {

                    // Product Model Loder
                    Arraylist_product_model = new String[]{"Select Model", "SMALL PRODUCTS"};
                    List<String> produt_type = new ArrayList<String>(Arrays.asList(Arraylist_product_model));
                    ArrayAdapter<String> adapter_product_type = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, produt_type);
                    adapter_product_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spn_product_model.setAdapter(adapter_product_type);

                    // Complaint Type Loader
                    Arraylist_complaint_type = new String[]{"Select Complaint", "On Call"};
                    List<String> compaint_type = new ArrayList<String>(Arrays.asList(Arraylist_complaint_type));
                    ArrayAdapter<String> adapter_compaint_type = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, compaint_type);
                    adapter_compaint_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spn_complaint_type.setAdapter(adapter_compaint_type);

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

                if (str_selected_model.equals("")) {

                } else if (str_selected_model.equals("Select Model")) {

                    TastyToast.makeText(getActivity(), "Please Select a Valid Model", TastyToast.LENGTH_LONG, TastyToast.INFO);

                } else if (str_selected_model.equals("NXG")) {

                    Arraylist_complaint_category = new String[]{"Select Category", "COMMISSIONING", "COMPLAINT", "GENERAL", "AMC"};
                    List<String> complaint_category = new ArrayList<String>(Arrays.asList(Arraylist_complaint_category));
                    ArrayAdapter<String> adapter_complaint_category = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, complaint_category);
                    adapter_complaint_category.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spn_complaint_category.setAdapter(adapter_complaint_category);

                } else if (str_selected_model.equals("2KD")) {

                    Arraylist_complaint_category = new String[]{"Select Category", "COMMISSIONING", "COMPLAINT", "GENERAL", "AMC"};
                    List<String> complaint_category = new ArrayList<String>(Arrays.asList(Arraylist_complaint_category));
                    ArrayAdapter<String> adapter_complaint_category = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, complaint_category);
                    adapter_complaint_category.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spn_complaint_category.setAdapter(adapter_complaint_category);

                } else if (str_selected_model.equals("2KD7")) {

                    Arraylist_complaint_category = new String[]{"Select Category", "COMMISSIONING", "COMPLAINT", "GENERAL", "AMC"};
                    List<String> complaint_category = new ArrayList<String>(Arrays.asList(Arraylist_complaint_category));
                    ArrayAdapter<String> adapter_complaint_category = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, complaint_category);
                    adapter_complaint_category.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spn_complaint_category.setAdapter(adapter_complaint_category);

                } else if (str_selected_model.equals("2KW")) {

                    Arraylist_complaint_category = new String[]{"Select Category", "COMMISSIONING", "COMPLAINT", "GENERAL", "AMC"};
                    List<String> complaint_category = new ArrayList<String>(Arrays.asList(Arraylist_complaint_category));
                    ArrayAdapter<String> adapter_complaint_category = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, complaint_category);
                    adapter_complaint_category.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spn_complaint_category.setAdapter(adapter_complaint_category);

                } else if (str_selected_model.equals("RAD")) {

                    Arraylist_complaint_category = new String[]{"Select Category", "PRE COMMISSIONING", "COMMISSIONING", "COMPLAINT", "GENERAL", "AMC"};
                    List<String> complaint_category = new ArrayList<String>(Arrays.asList(Arraylist_complaint_category));
                    ArrayAdapter<String> adapter_complaint_category = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, complaint_category);
                    adapter_complaint_category.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spn_complaint_category.setAdapter(adapter_complaint_category);

                } else if (str_selected_model.equals("HLN")) {

                    Arraylist_complaint_category = new String[]{"Select Category", "COMMISSIONING", "COMPLAINT", "GENERAL", "AMC"};
                    List<String> complaint_category = new ArrayList<String>(Arrays.asList(Arraylist_complaint_category));
                    ArrayAdapter<String> adapter_complaint_category = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, complaint_category);
                    adapter_complaint_category.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spn_complaint_category.setAdapter(adapter_complaint_category);

                } else if (str_selected_model.equals("HLD")) {

                    Arraylist_complaint_category = new String[]{"Select Category", "COMMISSIONING", "COMPLAINT", "GENERAL", "AMC"};
                    List<String> complaint_category = new ArrayList<String>(Arrays.asList(Arraylist_complaint_category));
                    ArrayAdapter<String> adapter_complaint_category = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, complaint_category);
                    adapter_complaint_category.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spn_complaint_category.setAdapter(adapter_complaint_category);

                } else if (str_selected_model.equals("SPD")) {

                    Arraylist_complaint_category = new String[]{"Select Category", "COMMISSIONING", "COMPLAINT", "GENERAL", "AMC"};
                    List<String> complaint_category = new ArrayList<String>(Arrays.asList(Arraylist_complaint_category));
                    ArrayAdapter<String> adapter_complaint_category = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, complaint_category);
                    adapter_complaint_category.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spn_complaint_category.setAdapter(adapter_complaint_category);

                } else if (str_selected_model.equals("GCT +")) {

                    Arraylist_complaint_category = new String[]{"Select Category", "PRE COMMISSIONING", "ERECTION", "COMMISSIONING", "COMPLAINT", "GENERAL", "AMC"};
                    List<String> complaint_category = new ArrayList<String>(Arrays.asList(Arraylist_complaint_category));
                    ArrayAdapter<String> adapter_complaint_category = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, complaint_category);
                    adapter_complaint_category.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spn_complaint_category.setAdapter(adapter_complaint_category);

                } else if (str_selected_model.equals("GCT")) {

                    Arraylist_complaint_category = new String[]{"Select Category", "PRE COMMISSIONING", "ERECTION", "COMMISSIONING", "COMPLAINT", "GENERAL", "AMC"};
                    List<String> complaint_category = new ArrayList<String>(Arrays.asList(Arraylist_complaint_category));
                    ArrayAdapter<String> adapter_complaint_category = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, complaint_category);
                    adapter_complaint_category.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spn_complaint_category.setAdapter(adapter_complaint_category);

                } else if (str_selected_model.equals("SCT/SCB")) {

                    Arraylist_complaint_category = new String[]{"Select Category", "PRE COMMISSIONING", "ERECTION", "COMMISSIONING", "COMPLAINT", "GENERAL", "AMC"};
                    List<String> complaint_category = new ArrayList<String>(Arrays.asList(Arraylist_complaint_category));
                    ArrayAdapter<String> adapter_complaint_category = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, complaint_category);
                    adapter_complaint_category.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spn_complaint_category.setAdapter(adapter_complaint_category);

                } else if (str_selected_model.equals("DRY COOLING TOWER")) {

                    Arraylist_complaint_category = new String[]{"Select Category", "PRE COMMISSIONING", "ERECTION", "COMMISSIONING", "COMPLAINT", "GENERAL", "AMC"};
                    List<String> complaint_category = new ArrayList<String>(Arrays.asList(Arraylist_complaint_category));
                    ArrayAdapter<String> adapter_complaint_category = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, complaint_category);
                    adapter_complaint_category.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spn_complaint_category.setAdapter(adapter_complaint_category);

                } else if (str_selected_model.equals("CHILLER")) {

                    Arraylist_complaint_category = new String[]{"Select Category", "PRE COMMISSIONING", "COMMISSIONING", "COMPLAINT", "GENERAL", "AMC"};
                    List<String> complaint_category = new ArrayList<String>(Arrays.asList(Arraylist_complaint_category));
                    ArrayAdapter<String> adapter_complaint_category = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, complaint_category);
                    adapter_complaint_category.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spn_complaint_category.setAdapter(adapter_complaint_category);

                } else {

                    Arraylist_complaint_category = new String[]{"Select Category", "On calls"};
                    List<String> complaint_category = new ArrayList<String>(Arrays.asList(Arraylist_complaint_category));
                    ArrayAdapter<String> adapter_complaint_category = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, complaint_category);
                    adapter_complaint_category.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spn_complaint_category.setAdapter(adapter_complaint_category);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Another interface callback
            }

        });

        // Complaint Category Spinner Interface

        spn_complaint_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                str_selected_complaint_category = parent.getItemAtPosition(pos).toString();
                TastyToast.makeText(getActivity(), str_selected_complaint_category, TastyToast.LENGTH_LONG, TastyToast.INFO);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Another interface callback
            }

        });

        // Complaint Type Spinner Interface

        spn_complaint_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                str_selected_complaint_type = parent.getItemAtPosition(pos).toString();
                TastyToast.makeText(getActivity(), str_selected_complaint_type, TastyToast.LENGTH_LONG, TastyToast.INFO);
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

        try {
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Please wait...");
            pDialog.show();
            pDialog.setCancelable(false);
            queue = Volley.newRequestQueue(getActivity());
            Function_GetDelars();
        } catch (Exception e) {

        }

        return rootView;
    }

    /********************************
     * User Authentication
     *********************************/

    private void Function_GetDelars() {

        String url_dealers = "http://gemservice.in/employee_app/WebService.php?operation=dealerlist";

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


}
