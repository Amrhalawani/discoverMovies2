package amrhal.example.com.discovermovies2.DetailsFragments;

import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import amrhal.example.com.discovermovies2.Models.MovieModel;
import amrhal.example.com.discovermovies2.Models.ReviewModel;
import amrhal.example.com.discovermovies2.Models.VideoModel;
import amrhal.example.com.discovermovies2.MoviesInterface;
import amrhal.example.com.discovermovies2.PDetailsActivity;
import amrhal.example.com.discovermovies2.R;
import amrhal.example.com.discovermovies2.RecyclerAdaptor;
import amrhal.example.com.discovermovies2.ReviewAdaptor;
import amrhal.example.com.discovermovies2.VideosAdaptor;
import amrhal.example.com.discovermovies2.utils.Util;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Amr Halawani on 14/05/2018.
 */

public class TrailersFragment extends Fragment {
    String id;
    List<VideoModel> list = new ArrayList<>();
    RecyclerView recyclerView;

    public TrailersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflatedview = inflater.inflate(R.layout.detailsfrag_trailers, container, false);
        if (getArguments() != null) {
            id = getArguments().getString("id");
        } else {
         //   Toast.makeText(getActivity(), "list getArguments() videos == null", Toast.LENGTH_SHORT).show();
        }
        list = new ArrayList<>();
        recyclerView = inflatedview.findViewById(R.id.recyclerviewVideosID);
        getVideos();
        return inflatedview;
    }

    public void getVideos() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PDetailsActivity.base_url)
                .build();

        MoviesInterface moviesInterface = retrofit.create(MoviesInterface.class);

        moviesInterface.getMovieVideos(id, BuildConfig.API_KEY).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.body() != null) {
                    VideosAdaptor videosAdaptor = new VideosAdaptor(getActivity());
                    recyclerView.setAdapter(videosAdaptor);
                    recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

                    try {
                        String jsonResponse = response.body().string();

                        list = Util.parseJsonMovieVideo(jsonResponse);
                        videosAdaptor.updateData(list);
                       // Toast.makeText(getActivity(), "retrofitResp Videos id =" + id + "  listsize" + list.size(), Toast.LENGTH_SHORT).show();

                        videosAdaptor.setOnItemClickListener(new VideosAdaptor.OnItemClickListener() {
                            @Override
                            public void onItemClick(int position, String key) {
                                //GpAuCG6iUcA

                                Intent youtubeIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + key));


                                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + key));

                                //if youtube app not found video can open from browser
                                try {
                                    youtubeIntent.putExtra("force_fullscreen", true);
                                    startActivity(youtubeIntent);
                                } catch (ActivityNotFoundException e) {
                                    webIntent.putExtra("force_fullscreen", true);
                                    startActivity(webIntent);
                                }
                            }
                        });

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
      //  Toast.makeText(context, "TrailersFragment launched", Toast.LENGTH_SHORT).show();

    }

}