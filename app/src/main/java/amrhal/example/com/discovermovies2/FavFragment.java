package amrhal.example.com.discovermovies2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


public class FavFragment extends Fragment {

    public FavFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflatedview = inflater.inflate(R.layout.fragment_fav, container, false);

        return inflatedview;
    }


    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
        Toast.makeText(context, "FavFragment launched", Toast.LENGTH_SHORT).show();

    }




}
