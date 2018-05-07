package amrhal.example.com.discovermovies2.DetailsFragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import amrhal.example.com.discovermovies2.R;

/**
 * Created by Amr Halawani on 05/05/2018.
 */

public class TrailersFragment extends Fragment {

    public TrailersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflatedview = inflater.inflate(R.layout.detailsfrag_trailers, container, false);

        return inflatedview;
    }


    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
        Toast.makeText(context, "TrailersFragment launched", Toast.LENGTH_SHORT).show();

    }




}