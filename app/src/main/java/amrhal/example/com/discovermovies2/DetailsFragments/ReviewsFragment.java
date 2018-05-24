package amrhal.example.com.discovermovies2.DetailsFragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import amrhal.example.com.discovermovies2.BuildConfig;
import amrhal.example.com.discovermovies2.MainActivity;
import amrhal.example.com.discovermovies2.Models.ReviewModel;
import amrhal.example.com.discovermovies2.MoviesInterface;
import amrhal.example.com.discovermovies2.PDetailsActivity;
import amrhal.example.com.discovermovies2.R;
import amrhal.example.com.discovermovies2.RecyclerAdaptor;
import amrhal.example.com.discovermovies2.ReviewAdaptor;
import amrhal.example.com.discovermovies2.utils.Util;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Amr Halawani on 05/05/2018.
 */

public class ReviewsFragment extends Fragment {
    List<ReviewModel> list = new ArrayList<>();
    String id;
    RecyclerView recyclerView;

    public ReviewsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflatedview = inflater.inflate(R.layout.detailsfrag_reviews, container, false);
        if (getArguments() != null) {
            id = getArguments().getString("id");
        } else {
            Toast.makeText(getActivity(), "list getArguments() == null", Toast.LENGTH_SHORT).show();
        }
        list = new ArrayList<>();
        recyclerView = inflatedview.findViewById(R.id.recyclerviewRecviewID);
        getReviews();
        return inflatedview;
    }

    public void getReviews() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PDetailsActivity.base_url)
                .build();

        MoviesInterface moviesInterface = retrofit.create(MoviesInterface.class);

        moviesInterface.getMovieReviews(id, BuildConfig.API_KEY).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.body() != null) {
                    ReviewAdaptor reviewAdaptor = new ReviewAdaptor(getActivity());
                    recyclerView.setAdapter(reviewAdaptor);
                    recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));

                    try {
                        String jsonResponse = response.body().string();

                        list = Util.parseJsonMovieReview(jsonResponse);
                        reviewAdaptor.updateData(list);
                        Toast.makeText(getActivity(), "retrofitResp reviewFrag id ="+id+"listsize"+ list.size(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getActivity(), "Failure", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
       // Toast.makeText(context, "ReviewsFragment launched", Toast.LENGTH_SHORT).show();

    }


}