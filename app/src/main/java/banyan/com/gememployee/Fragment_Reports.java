package banyan.com.gememployee;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

import banyan.com.gememployee.Adapter.CustomGridViewAdapter;
import banyan.com.gememployee.Model.Item;

/**
 * Created by Jo on 7/27/2016.
 */
public class Fragment_Reports extends Fragment {

    GridView gridView;
    ArrayList<Item> gridArray = new ArrayList<Item>();
    CustomGridViewAdapter customGridAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(
                R.layout.fragment_reports, container, false);

        try {
            gridArray.clear();
        } catch (Exception e) {
        }
        Bitmap Ironing = BitmapFactory.decodeResource(this.getResources(), R.drawable.gr_iron);
        Bitmap Laundry = BitmapFactory.decodeResource(this.getResources(), R.drawable.gr_laundry);
        Bitmap Builders = BitmapFactory.decodeResource(this.getResources(), R.drawable.gr_builders);
        Bitmap Corprenter = BitmapFactory.decodeResource(this.getResources(), R.drawable.gr_carprenter);
        Bitmap Welders = BitmapFactory.decodeResource(this.getResources(), R.drawable.gr_welder);
        Bitmap Painters = BitmapFactory.decodeResource(this.getResources(), R.drawable.gr_painter);

        gridArray.add(new Item(Ironing, "All"));
        gridArray.add(new Item(Laundry, "Warranty / Complaints"));
        gridArray.add(new Item(Builders, "Out of Warranty or Services"));
        gridArray.add(new Item(Corprenter, "Erection"));
        gridArray.add(new Item(Welders, "Pre Commisioning / Commisioning"));
        gridArray.add(new Item(Painters, "AMC"));

        gridView = (GridView) rootView.findViewById(R.id.gridView1);
        customGridAdapter = new CustomGridViewAdapter(getActivity(), R.layout.reports_row_grid, gridArray);
        gridView.setAdapter(customGridAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                    Intent i = new Intent(getActivity(), Reports_Tab_All.class);
                    startActivity(i);                }
                if (position == 1) {

                    Intent i = new Intent(getActivity(), Reports_Tab_Warranty_Complaint.class);
                    startActivity(i);
                }

                if (position == 2) {

                    Intent i = new Intent(getActivity(), Reports_Tab_OutofWarranty_Service.class);
                    startActivity(i);
                }

                if (position == 3) {

                    Intent i = new Intent(getActivity(), Reports_Tab_Erection.class);
                    startActivity(i);
                }

                if (position == 4) {

                    Intent i = new Intent(getActivity(), Reports_Tab_PreCommisioning_Commisioning.class);
                    startActivity(i);
                }

                if (position == 5) {

                    Intent i = new Intent(getActivity(), Reports_Tab_AMC.class);
                    startActivity(i);
                }
            }
        });

        return rootView;
    }
}