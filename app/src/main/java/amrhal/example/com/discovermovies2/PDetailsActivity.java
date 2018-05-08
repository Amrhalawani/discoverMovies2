package amrhal.example.com.discovermovies2;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
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

public class PDetailsActivity extends AppCompatActivity {
    public static String pic_base_url = "http://image.tmdb.org/t/p/";
    public static String pic_size_url = "w185";

    String title, poster, avg, date, overview, original_lang, original_title, id, backdrop_path;
    boolean adult;
    TextView movieTitleTV, movieAvgTV, moviereleaseDateTV, movieOverviewTV;
    ImageView posterIV, backgroundAlpha;
    FragmentManager frag;
    FragmentTransaction transaction;
    FloatingActionButton fab;
    boolean flag;

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
        setContentView(R.layout.activity_pdetails);

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
        navigationEx.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        setupUi();
        updateUI1();
        floatingbuttonhandleClicks();

    }

    private void floatingbuttonhandleClicks() {

        //this method for handle ckick only not check status before i clicked.
        flag = true; // true if first icon is visible, false if second one is visible.

        fab = findViewById(R.id.favButton);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (flag) {

                    fab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_fav_checked));
                    flag = false;

                } else if (!flag) {

                    fab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_fav_unchecked));
                    flag = true;

                }

            }
        });
    }


    private void setupUi() {

        movieTitleTV = findViewById(R.id.title);
        movieAvgTV = findViewById(R.id.avarege);
        moviereleaseDateTV = findViewById(R.id.releasedate);
        movieOverviewTV = findViewById(R.id.over_view);
        posterIV = findViewById(R.id.poster_detail);

    }


    private void updateUI1() {
        //   MovieModel movieModel = new MovieModel("ahmed","http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg ", "10/10/2010","5.9", "sadasda", "10", "156", "10", "1dasd", false);

        MovieModel movieModel = getIntent().getExtras().getParcelable("Movieobject");

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
