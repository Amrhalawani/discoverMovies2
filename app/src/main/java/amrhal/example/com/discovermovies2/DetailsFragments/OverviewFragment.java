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

import amrhal.example.com.discovermovies2.R;

/**
 * Created by Amr Halawani on 05/05/2018.
 */

public class OverviewFragment extends Fragment {
    String Synopsis, original_title, original_lang, alloriginal_title;

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
            Synopsis = "Synopsis: \n" + getArguments().getString("Synopsis");
            original_title = getArguments().getString("original_title");
            original_lang = getArguments().getString("original_lang");
            alloriginal_title = "Original Title: " + original_title + " (" + original_lang + "). \n";

        } else {
            // Toast.makeText(getActivity(), "getArguments() == null", Toast.LENGTH_SHORT).show();
        }
        View inflatedview = inflater.inflate(R.layout.detailsfrag_overview, container, false);
        TextView overviewTV = inflatedview.findViewById(R.id.over_viewfragmentID);
        TextView original_lange = inflatedview.findViewById(R.id.original_titleID);


        original_lange.setText(alloriginal_title);


        overviewTV.setText(Synopsis);

        return inflatedview;
    }



    @Override
    public void onAttach(Context context) {

        super.onAttach(context);


    }


}
