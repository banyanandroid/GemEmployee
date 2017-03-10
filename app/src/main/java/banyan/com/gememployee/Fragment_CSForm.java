package banyan.com.gememployee;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sdsmdg.tastytoast.TastyToast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Jo on 7/27/2016.
 */
public class Fragment_CSForm extends Fragment {

    TextView date;
    EditText feedback, customer;
    Button btn_submit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_cs_form, null);

        date = (TextView) rootview.findViewById(R.id.cs_form_txt_date);

        customer = (EditText) rootview.findViewById(R.id.csform_edt_cust_name);
        feedback = (EditText) rootview.findViewById(R.id.csform_edt_feedback);

        btn_submit = (Button) rootview.findViewById(R.id.cs_form_complaint_btn_submit);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String str_customer = customer.getText().toString();
                String str_feedback = feedback.getText().toString();

                if (str_customer.equals("")) {
                    TastyToast.makeText(getActivity(), "Please Enter Customer Name", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_feedback.equals("")) {
                    TastyToast.makeText(getActivity(), "Please Enter Some Feedback", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else {
                    TastyToast.makeText(getActivity(), "Form Posted Successfully", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                }

            }
        });


        /********************************
         *  Load Current Day
         * *********************************/

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("dd / MM / yyyy");
        String strDate = mdformat.format(calendar.getTime());
        date.setText("" + strDate);

        return rootview;
    }
}
