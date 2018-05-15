package amrhal.example.com.discovermovies2;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.squareup.picasso.Picasso;

import amrhal.example.com.discovermovies2.DetailsFragments.OverviewFragment;
import amrhal.example.com.discovermovies2.DetailsFragments.ReviewsFragment;
import amrhal.example.com.discovermovies2.DetailsFragments.TrailersFragment;
import amrhal.example.com.discovermovies2.Models.MovieModel;


public class PDetailsActivity extends AppCompatActivity {
    public static String pic_base_url = "http://image.tmdb.org/t/p/";
    public static String pic_size_url = "w185";

    String title, poster, avg, date, Synopsis, original_lang, original_title, id, backdrop_path;
    boolean adult;
    TextView movieTitleTV, movieAvgTV, moviereleaseDateTV, movieOverviewTV;
    ImageView posterIV, backdrop_img;
    FragmentManager frag;
    FragmentTransaction transaction;
    FloatingActionButton fab;
    boolean flag;
    OverviewFragment overviewFragment;
    ReviewsFragment reviewsFragment;
    TrailersFragment trailersFragment;

    public static final String base_url = "http://api.themoviedb.org/3/";
    protected static final String api_key = BuildConfig.API_KEY;
    private static final String ReviewsKey = "reviews";

    String url = "http://api.themoviedb.org/3/movie/539/videos?api_key=";
    String youtubeUrl = "https://www.youtube.com/watch?v=deWPlnlN_Dc";
    String YoutubeThumbnailUrl = "https://img.youtube.com/vi/kf8X-MtsufY/mqdefault.jpg";

//    To fetch trailers you will want to make a request to the /movie/{id}/videos endpoint.
//    To fetch reviews you will want to make a request to the /movie/{id}/reviews endpoint.

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            transaction = frag.beginTransaction();
            switch (item.getItemId()) {

                case R.id.overviewFragID:
                    toOverviewFragment();
                    transaction.replace(R.id.details_mainfragment, overviewFragment).commit();
                    return true;
                case R.id.reviewsFragID:
                    toReviewsFragment();
                    transaction.replace(R.id.details_mainfragment, reviewsFragment).commit();

                    return true;
                case R.id.TrailersFragID:
                    toTrailersFragment();
                    transaction.replace(R.id.details_mainfragment, trailersFragment).commit();

                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        setContentView(R.layout.activity_pdetails);


        //todo fe error elly howa already commit da cuz transaction = frag.beginTransaction() called
        //todo el textview bta3 1st frag haeshta3'al lama tdos
        frag = getFragmentManager();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        BottomNavigationViewEx navigationEx = findViewById(R.id.navigationDetailsID);

        navigationEx.enableAnimation(true);
        navigationEx.enableShiftingMode(true);
        navigationEx.enableItemShiftingMode(false);
        navigationEx.setIconVisibility(false);


        setupUi();
        updateUI1();
        floatingbuttonhandleClicks();

        navigationEx.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        // toOverviewFragment();  // i think this is not the best practice "the fragment called twice 1st from xml and 2nd here, and in the first one getargumant == null
        navigationEx.setCurrentItem(0); //R.id.overviewFragID didn't work
    }


    private void toOverviewFragment() {
        overviewFragment = new OverviewFragment();
        Bundle bundle = new Bundle();
        bundle.putString("Synopsis", Synopsis);
        bundle.putString("original_title", original_title);
        bundle.putString("original_lang", original_lang);
        overviewFragment.setArguments(bundle);
    }

    private void toTrailersFragment() {
        trailersFragment = new TrailersFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        trailersFragment.setArguments(bundle);
    }

    private void toReviewsFragment() {
        reviewsFragment = new ReviewsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        reviewsFragment.setArguments(bundle);
    }


    private void floatingbuttonhandleClicks() {

        //this method for handle ckick only not check status before i clicked , so you have to #todo make another method to retreve the status before you click
        flag = true; // true if first icon is visible, false if second one is visible.

        fab = findViewById(R.id.favButton);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag) {
                    fab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_fav_checked));
                    Toast.makeText(PDetailsActivity.this, "Added to Favourites", Toast.LENGTH_SHORT).show();
                    flag = false;

                } else if (!flag) {
                    fab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_fav_unchecked));
                    Toast.makeText(PDetailsActivity.this, "Removed from Favourites", Toast.LENGTH_SHORT).show();
                    flag = true;
                }
            }
        });
    }


    private void setupUi() {

        movieTitleTV = findViewById(R.id.title);
        movieAvgTV = findViewById(R.id.avarege);
        moviereleaseDateTV = findViewById(R.id.releasedate);
        // movieOverviewTV = findViewById(R.id.over_view);
        posterIV = findViewById(R.id.poster_detail);
        backdrop_img = findViewById(R.id.backImagecollapsedID);
        //fragments
    }

    private void updateUI1() {
        //   MovieModel movieModel = new MovieModel("ahmed","http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg ", "10/10/2010","5.9", "sadasda", "10", "156", "10", "1dasd", false);

        MovieModel movieModel = getIntent().getExtras().getParcelable("Movieobject");

        title = movieModel.getTitle();

        poster = movieModel.getPosterUrl();

        avg = movieModel.getVoteAverage();

        date = movieModel.getReleaseDate();

        Synopsis = movieModel.getSynopsis();

        original_lang = movieModel.getOriginal_language();

        original_title = movieModel.getOriginal_title();

        backdrop_path = movieModel.getBackdrop_path();

        id = movieModel.getmovieId();

        Toast.makeText(PDetailsActivity.this, "" + backdrop_path, Toast.LENGTH_SHORT).show();

        movieTitleTV.setText(title);
        Picasso.get()
                .load(poster)
                .placeholder(R.drawable.user_placeholder)
                .into(posterIV);

        Picasso.get()
                .load(backdrop_path)
                .placeholder(R.drawable.user_placeholder)
                .into(backdrop_img);

        movieAvgTV.setText(avg);
        moviereleaseDateTV.setText(date);
        //  movieOverviewTV.setText(overview);
        getSupportActionBar().setTitle(title);

    }

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Toast.makeText(PDetailsActivity.this, "android.R.id.home", Toast.LENGTH_SHORT).show();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
