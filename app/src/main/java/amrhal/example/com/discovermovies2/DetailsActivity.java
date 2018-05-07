package amrhal.example.com.discovermovies2;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.squareup.picasso.Picasso;

import amrhal.example.com.discovermovies2.DetailsFragments.OverviewFragment;
import amrhal.example.com.discovermovies2.DetailsFragments.ReviewsFragment;
import amrhal.example.com.discovermovies2.DetailsFragments.TrailersFragment;


public class DetailsActivity extends AppCompatActivity {

    public static String EXTRA_POSITION = "position";
    public static String EXTRA_TITLE = "title";
    public static String EXTRA_POSTER = "poster";
    public static String EXTRA_AVG = "avg";
    public static String EXTRA_DATE = "date";
    public static String EXTRA_OVERVIEW = "overview";
    public static String pic_base_url = "http://image.tmdb.org/t/p/";
    public static String pic_size_url = "w185";
    String title, poster, avg, date, overview;
    TextView movieTitleTV, movieAvgTV, moviereleaseDateTV, movieOverviewTV;
    ImageView posterIV, backgroundAlpha;

    FragmentManager frag;
    FragmentTransaction transaction;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            transaction = frag.beginTransaction();
            switch (item.getItemId()) {

                case R.id.overviewFragID:
                    transaction.replace(R.id.details_mainfragment, new OverviewFragment()).commit();
                    return true;
                case R.id.reviewsFragID:
                    transaction.replace(R.id.details_mainfragment, new ReviewsFragment()).commit();

                    return true;
                case R.id.TrailersFragID:
                    transaction.replace(R.id.details_mainfragment, new TrailersFragment()).commit();
                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_layout);

        frag = getFragmentManager();

        BottomNavigationViewEx navigationEx =findViewById(R.id.navigationDetailsID);

        navigationEx.enableAnimation(true);
        navigationEx.enableShiftingMode(true);
        navigationEx.enableItemShiftingMode(false);
        navigationEx.setIconVisibility(false);
        navigationEx.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

//        BottomNavigationView navigation = findViewById(R.id.navigationDetailsID);
//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        setupUi();
        //  updateUI();
        updateUI1();


    }

    private void updateUI1() {
        MovieModel movieModel = getIntent().getExtras().getParcelable("Movieobject");

        //   MovieModel movieModel = new MovieModel("ahmed","http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg ", "10/10/2010","5.9", "sadasda", "10", "156", "10", "1dasd", false);

        title = movieModel.getTitle();

        poster = movieModel.getPosterUrl();

        avg = movieModel.getVoteAverage();

        date = movieModel.getReleaseDate();

        overview = movieModel.getSynopsis();

        movieTitleTV.setText(title);
        Picasso.get()
                .load(poster)
                .placeholder(R.drawable.user_placeholder)
                .into(posterIV);

        movieAvgTV.setText(avg);
        moviereleaseDateTV.setText(date);
        movieOverviewTV.setText(overview);

//        Picasso.get()
//                .load(poster)
//                .centerCrop()
//                .resize(120, 180)
//                .placeholder(R.drawable.user_placeholder)
//                .into(backgroundAlpha);
    }

    private void setupUi() {

        movieTitleTV = findViewById(R.id.title);
        movieAvgTV = findViewById(R.id.avarege);
        moviereleaseDateTV = findViewById(R.id.releasedate);
        movieOverviewTV = findViewById(R.id.over_view);
        posterIV = findViewById(R.id.poster_detail);

    }

//    private void updateUI() {
//        title = getIntent().getExtras().getString(EXTRA_TITLE);
//        poster = getIntent().getExtras().getString(EXTRA_POSTER);
//        avg = getIntent().getExtras().getString(EXTRA_AVG);
//        date = getIntent().getExtras().getString(EXTRA_DATE);
//        overview = getIntent().getExtras().getString(EXTRA_OVERVIEW);
//        movieTitleTV.setText(title);
//        Picasso.get()
//                .load(poster)
//                .placeholder(R.drawable.user_placeholder)
//                .into(posterIV);
//
//        movieAvgTV.setText(avg);
//        moviereleaseDateTV.setText(date);
//        movieOverviewTV.setText(overview);
//
//        Picasso.get()
//                .load(poster)
//                .centerCrop()
//                .resize(120, 180)
//                .placeholder(R.drawable.user_placeholder)
//                .into(backgroundAlpha);
//    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {

        super.onStop();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
