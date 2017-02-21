package banyan.com.gememployee;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Jo on 7/27/2016.
 */
public class Fragment_CSForm extends Fragment {

    TextView date;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_cs_form, null);

        date = (TextView) rootview.findViewById(R.id.cs_form_txt_date);

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
