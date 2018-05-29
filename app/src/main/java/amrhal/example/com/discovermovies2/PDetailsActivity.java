package amrhal.example.com.discovermovies2;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import amrhal.example.com.discovermovies2.database.MovieContract.MovieEntry;


public class PDetailsActivity extends AppCompatActivity {
    public static String pic_base_url = "http://image.tmdb.org/t/p/";
    public static String pic_size_url = "w185";
    public static String MOVIE_MODEL_KEY = "moviemodel";


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

    MovieModel movieModel;

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

        movieModel = getIntent().getExtras().getParcelable("Movieobject");

        // fe error elly howa already commit da cuz transaction = fragMan.beginTransaction() called again

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
        navigationEx.setCurrentItem(0); //this is good
        flag = favstatus();
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
        //this method for handle ckick only not check status before i clicked , so you have to
        // true if first icon is visible, false if second one is visible.
        fab = findViewById(R.id.favButton);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (flag) {
                    fab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_fav_checked));
                    Toast.makeText(PDetailsActivity.this, "Added to Favourites", Toast.LENGTH_SHORT).show();
                    insertFavmovie();
                    flag = false;

                } else if (!flag) {
                    fab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_fav_unchecked));
                    DeleteFavMovie();
                    Toast.makeText(PDetailsActivity.this, "Removed from Favourites", Toast.LENGTH_SHORT).show();
                    flag = true;
                }
            }
        });
    }

    boolean favstatus() {
        //   int rowDeleted = getContentResolver().q(MovieContract.MovieEntry.CONTENT_URiUnknown_id, null, selectionArgs);
        String[] selectionArgs = {String.valueOf(id)};

        String[] projection = {
                MovieEntry.COLUMN_MOVIE_ID};
        //CONTENT_URiUnknown_id bed7'ól beh case 2 fe provider mish akter

        Cursor cursor = getContentResolver().query(MovieEntry.CONTENT_URiUnknown_id, projection, null, selectionArgs, null);
        int cursurcount = cursor.getCount();
        if (cursor.getCount() != 0 && cursor.getCount() != -1) {
            cursor.close(); //todo
            fab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_fav_checked));
            return false;
        }
        cursor.close(); //todo
        return true;
    }

    private void DeleteFavMovie() {
        String[] selectionArgs = {String.valueOf(id)};
        //CONTENT_URiUnknown_id bed7'ól beh case 2 fe provider mish akter
        int rowDeleted = getContentResolver().delete(MovieEntry.CONTENT_URiUnknown_id, null, selectionArgs);

        Log.e("TAG", " row deleted from pet database=" + rowDeleted + " selection args=" + selectionArgs[0]);
    }

    private void insertFavmovie() {
        ContentValues values = new ContentValues(); //key value

        values.put(MovieEntry.COLUMN_TITLE, title);
        values.put(MovieEntry.COLUMN_POSTER_PATH, poster);
        values.put(MovieEntry.COLUMN_RELEASEDATE, date);
        values.put(MovieEntry.COLUMN_VOTEAVERAGE, avg);
        values.put(MovieEntry.COLUMN_SYNOPSIS, Synopsis);
        values.put(MovieEntry.COLUMN_ORIGINAL_LANGUAGE, original_lang);
        values.put(MovieEntry.COLUMN_ORIGINAL_TITLE, original_title);
        values.put(MovieEntry.COLUMN_MOVIE_ID, id);
        values.put(MovieEntry.COLUMN_BACKDROP_PATH, backdrop_path);


        String posterpathfullUrl = "http://image.tmdb.org/t/p/w154" + "/y31QB9kn3XSudA15tV7UWQ9XLuW.jpg";

        // values.put(MovieContract.MovieEntry.COLUMN_POSTER_PATH, posterpathfullUrl);

        Uri newUri = getContentResolver().insert(MovieEntry.CONTENT_URI, values);

        Log.e("TAG", "insert movie: and newrowid is= " + newUri);
    }


    private void setupUi() {

        movieTitleTV = findViewById(R.id.title);
        movieAvgTV = findViewById(R.id.avarege);
        moviereleaseDateTV = findViewById(R.id.releasedate);
        // movieOverviewTV = findViewById(R.id.over_view);
        posterIV = findViewById(R.id.poster_detail);
        backdrop_img = findViewById(R.id.backImagecollapsedID);

    }



    private void updateUI1() {
        //   MovieModel movieModel = new MovieModel("ahmed","http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg ", "10/10/2010","5.9", "sadasda", "10", "156", "10", "1dasd", false);


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
                .fit()
                .centerCrop()
                .into(backdrop_img);

        movieAvgTV.setText(avg);
        moviereleaseDateTV.setText(date);
        //  movieOverviewTV.setText(synopsis);
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
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
