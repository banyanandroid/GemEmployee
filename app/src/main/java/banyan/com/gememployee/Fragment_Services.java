package banyan.com.gememployee;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Jo on 7/27/2016.
 */
public class Fragment_Services extends Fragment {

    Button btn_Completed_complaints, btn_pending_onprocess, btn_reg_complaints;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_services, null);


        btn_pending_onprocess = (Button) rootview.findViewById(R.id.servies_btn_pending_onprocess);
        btn_Completed_complaints = (Button) rootview.findViewById(R.id.servies_btn_completed_complaints);
        btn_reg_complaints = (Button) rootview.findViewById(R.id.servies_btn_reg_complaints);

        btn_pending_onprocess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getActivity(), Services_Tab_PendingOngoing.class);
                startActivity(i);

            }
        });

        btn_Completed_complaints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getActivity(), Services_Tab_CompletedComplaints.class);
                startActivity(i);

            }
        });

        btn_reg_complaints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getActivity(), Services_Tab_Reg_Complaints.class);
                startActivity(i);

            }
        });

        return rootview;
    }
}