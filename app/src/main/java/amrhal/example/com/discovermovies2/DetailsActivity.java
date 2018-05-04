package amrhal.example.com.discovermovies2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class DetailsActivity extends AppCompatActivity {

    public static String EXTRA_POSITION = "position";
    public static String EXTRA_TITLE = "title";
    public static String EXTRA_POSTER = "poster";
    public static String EXTRA_AVG = "avg";
    public static String EXTRA_DATE = "date";
    public static String EXTRA_OVERVIEW = "overview";

    String title, poster, avg, date, overview;

    int pos;
    TextView movieTitleTV, movieAvgTV, moviereleaseDateTV, movieOverviewTV;
    ImageView posterIV,backgroundAlpha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_layout);
        EventBus.getDefault().register(this);

        setupUi();
        updateUI();


    }

    private void setupUi() {
        pos = getIntent().getExtras().getInt(EXTRA_POSITION);
        movieTitleTV = findViewById(R.id.title);
        movieAvgTV = findViewById(R.id.avarege);
        moviereleaseDateTV = findViewById(R.id.releasedate);
        movieOverviewTV = findViewById(R.id.over_view);
        posterIV = findViewById(R.id.poster_detail);
        backgroundAlpha = findViewById(R.id.backgroundscrom);
    }

    private void updateUI() {
        title = getIntent().getExtras().getString(EXTRA_TITLE);
        poster = getIntent().getExtras().getString(EXTRA_POSTER);
        avg = getIntent().getExtras().getString(EXTRA_AVG);
        date = getIntent().getExtras().getString(EXTRA_DATE);
        overview = getIntent().getExtras().getString(EXTRA_OVERVIEW);
        movieTitleTV.setText(title);
        Picasso.get()
                .load(poster)
                .placeholder(R.drawable.user_placeholder)
                .into(posterIV);

        movieAvgTV.setText(avg);
        moviereleaseDateTV.setText(date);
        movieOverviewTV.setText(overview);

        Picasso.get()
                .load(poster)
                .centerCrop()
                .resize(120,180)
                .placeholder(R.drawable.user_placeholder)
                .into(backgroundAlpha);
    }

    @Override
    public void onStart() {
        super.onStart();
        //EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMovieModel(MovieModel movieModel) {
        Log.e("TAG", "onMovieModel eventbus: tragered");
        movieTitleTV.setText(movieModel.getTitle());
        movieAvgTV.setText(movieModel.getVoteAverage());
        moviereleaseDateTV.setText(movieModel.getReleaseDate());
        movieOverviewTV.setText(movieModel.getSynopsis());

        Picasso.get()
                .load(movieModel.getPosterUrl())
                .placeholder(R.drawable.user_placeholder)
                .into(posterIV);
    }


    public void testeventdetailActivtu(View view) {
        String s = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS7Q-E7qY6NWOnBg5DkhEwnkyQR2yIPc262CLQA2yrEfWt3U5Vl";
        EventBus.getDefault().post(new MovieModel("ahmed", s, "13/13/2013", "5.7", "asdasdasd"));


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
