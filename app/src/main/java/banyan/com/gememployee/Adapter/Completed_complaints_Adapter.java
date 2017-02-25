package banyan.com.gememployee.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.HashMap;

import banyan.com.gememployee.R;
import banyan.com.gememployee.Services_Tab_CompletedComplaints;


public class Completed_complaints_Adapter extends BaseAdapter {
    private Activity activity;
    private Context context;
    private LinearLayout singleMessageContainer;

    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater = null;

    private String[] bgColors;

    public Completed_complaints_Adapter(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data = d;
        bgColors = activity.getApplicationContext().getResources().getStringArray(R.array.movie_serial_bg);
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (convertView == null)
            v = inflater.inflate(R.layout.list_item_services_completes, null);

        TextView comp_no = (TextView) v.findViewById(R.id.list_item_completed_comp_no);
        TextView cus_name = (TextView) v.findViewById(R.id.list_item_completed_cus_name);
        TextView comp_status = (TextView) v.findViewById(R.id.list_item_completed_comp_status);

        HashMap<String, String> result = new HashMap<String, String>();
        result = data.get(position);

        comp_no.setText(result.get(Services_Tab_CompletedComplaints.TAG_COMP_NO));
        cus_name.setText(result.get(Services_Tab_CompletedComplaints.TAG_CUSTOMER));
        comp_status.setText(result.get(Services_Tab_CompletedComplaints.TAG_STATUS));

        String str_product = result.get(Services_Tab_CompletedComplaints.TAG_PRODUCT_NAME);

        if (str_product.equals("DRYER")){
            comp_no.setBackgroundColor(Color.parseColor("#42A5F5"));
        }else if (str_product.equals("CHILLER")){
            comp_no.setBackgroundColor(Color.parseColor("#5C6BC0"));
        }else if (str_product.equals("COOLING TOWER")){
            comp_no.setBackgroundColor(Color.parseColor("#7E57C2"));
        }else if (str_product.equals("OTHERS")){
            comp_no.setBackgroundColor(Color.parseColor("#FFB300"));
        }

        return v;

    }

}