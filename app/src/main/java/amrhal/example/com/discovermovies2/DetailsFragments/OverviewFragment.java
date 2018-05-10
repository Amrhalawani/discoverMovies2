package amrhal.example.com.discovermovies2.DetailsFragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import amrhal.example.com.discovermovies2.PDetailsActivity;
import amrhal.example.com.discovermovies2.R;

/**
 * Created by Amr Halawani on 05/05/2018.
 */

public class OverviewFragment extends Fragment {
    String myValue;

    public OverviewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (getArguments() != null) {
            myValue = getArguments().getString("message");
        } else {
            Toast.makeText(getActivity(), "getArguments() == null", Toast.LENGTH_SHORT).show();
        }
        View inflatedview = inflater.inflate(R.layout.detailsfrag_overview, container, false);
        TextView overviewTV = inflatedview.findViewById(R.id.over_viewfragmentID);

        overviewTV.setText(myValue);
        return inflatedview;
    }


    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
        Toast.makeText(context, "OverviewFragment launched", Toast.LENGTH_SHORT).show();

    }


}
