package amrhal.example.com.discovermovies2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import amrhal.example.com.discovermovies2.Eventbustest.EventMassege;

import amrhal.example.com.discovermovies2.Models.MovieModel;
import amrhal.example.com.discovermovies2.utils.Util;
import okhttp3.ResponseBody;
import retrofit2.Call;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class MainActivity extends AppCompatActivity {


    private static final String TAG = "TAG";
    private static final String SAVESTATE = "saveinstance";
    private static final String SCROLL_STATE_KEY = "scrollstate";
    private static final String MOVIE_LIST_KEY = "movielistkey";
    private static final String SHAREDP_KEY = "sharedname";
    private static final String SHAREDP_INT_KEY = "poportop";
    int POPorTOP = 0;
    static final int POPselected = 0;
    static final int TOPselected = 1;

    String fragemnt_TAG;

    RecyclerView recyclerView;
    List<MovieModel> list;
    RecyclerAdaptor recyclerAdaptor;
    Button btnRetry;
    TextView connectTointernetTV;
    ProgressBar progressBar;

    FragmentManager fragMan;
    FragmentTransaction transaction;
    FavFragment favFragment;

    BottomNavigationView navigation;

    private static Bundle mBundleRViewState; // for save recycler state
    Parcelable parcelable;
    GridLayoutManager gridLayoutManager;

    public static final String base_url = "http://api.themoviedb.org/3/";
    private static final String api_key = BuildConfig.API_KEY;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            transaction = fragMan.beginTransaction();
            switch (item.getItemId()) {

                case R.id.popularityID:

                    getSharedPreferences(SHAREDP_KEY, MODE_PRIVATE).edit().putInt(SHAREDP_INT_KEY, POPselected).commit();

                    transaction.remove(favFragment).commit();
                    if (list.isEmpty()) {

                        recyclerView.setVisibility(View.GONE);
                        EventBus.getDefault().post(new EventMassege("Can't access to internet"));

                    } else {
                        getPopularMovies();
                        connectTointernetTV.setVisibility(View.INVISIBLE);
                        btnRetry.setVisibility(View.INVISIBLE);
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                    return true;
                case R.id.top_ratedID:

                    boolean a = getSharedPreferences(SHAREDP_KEY, MODE_PRIVATE).edit().putInt(SHAREDP_INT_KEY, TOPselected).commit();

                    transaction.remove(favFragment).commit();
                    if (list.isEmpty()) {
                        recyclerView.setVisibility(View.GONE);
                        EventBus.getDefault().post(new EventMassege("Can't access to internet"));
                    } else {
                        getTopRatedMovies();
                        connectTointernetTV.setVisibility(View.INVISIBLE);
                        btnRetry.setVisibility(View.INVISIBLE);
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                    return true;

                case R.id.fev_movies:

                    if (fragMan.findFragmentByTag(fragemnt_TAG) == null) {

                        transaction.add(R.id.container, favFragment, fragemnt_TAG).commit();
                        return true;
                    } else {
                        Log.e(TAG, "onNavigationItemSelected: fragment already added");
                    }

            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragMan = getFragmentManager();
        fragemnt_TAG = "fav_fragment";
        favFragment = new FavFragment();

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        list = new ArrayList<>();
        connectTointernetTV = findViewById(R.id.emptyViewID);
        btnRetry = findViewById(R.id.button);
        progressBar = findViewById(R.id.progress);

        recyclerView = findViewById(R.id.recyclerviewID);
        recyclerView.setSaveEnabled(true);
        recyclerAdaptor = new RecyclerAdaptor(MainActivity.this);
        gridLayoutManager = new GridLayoutManager(MainActivity.this, numberOfColumns());


        recyclerView.setLayoutManager(gridLayoutManager);


        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (list.isEmpty()) {
            Log.e(TAG, "on Create (for visibility) when list.is empty= " + list.isEmpty());
            connectTointernetTV.setVisibility(View.VISIBLE);
            btnRetry.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        }


//        if (savedInstanceState != null) {
//            mBundleRViewState = savedInstanceState;
//            parcelable = savedInstanceState.getParcelable(SCROLL_STATE_KEY);
//
//            if (parcelable != null) {
//                recyclerView.getLayoutManager().onRestoreInstanceState(parcelable);
//  //              recyclerView.setAdapter(recyclerAdaptor);
//            }
//        }
        POPorTOP = getSharedPreferences(SHAREDP_KEY, MODE_PRIVATE).getInt(SHAREDP_INT_KEY, 0);
        if (POPorTOP == POPselected) {
            // navigation.setSelectedItemId();
            getPopularMovies();

        } else if (POPorTOP == TOPselected) {
            getTopRatedMovies();
            //navigation.setSelectedItemId(R.id.top_ratedID);

        }


    }
//
//    @Override
//    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
//        super.onSaveInstanceState(outState, outPersistentState);
//
//        outState.putParcelableArrayList(MOVIE_LIST_KEY, (ArrayList<MovieModel>) list);
//        outState.putParcelable(SCROLL_STATE_KEY, recyclerView.getLayoutManager().onSaveInstanceState());
//
//
//    }


    private int numberOfColumns() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        // You can change this divider to adjust the size of the poster
        int widthDivider = 400;
        int width = displayMetrics.widthPixels;
        int nColumns = width / widthDivider;
        if (nColumns < 2) return 2; //to keep the grid aspect
        return nColumns;
    }

    public void getPopularMovies() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base_url)
                .build();

        MoviesInterface moviesInterface = retrofit.create(MoviesInterface.class);
        moviesInterface.getPopularMovies(api_key).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.body() != null) {

                    try {
                        final String jsontResponse = response.body().string();

                        list = Util.parseJsonCTList(jsontResponse);
                        recyclerAdaptor.updateData(list);
                        connectTointernetTV.setVisibility(View.INVISIBLE);
                        btnRetry.setVisibility(View.INVISIBLE);
                        progressBar.setVisibility(View.INVISIBLE);

                        recyclerView.setAdapter(recyclerAdaptor);

                        recyclerAdaptor.setOnItemClickListener(new RecyclerAdaptor.OnItemClickListener() {
                            @Override
                            public void onItemClick(int position) {

                                MovieModel movieModel = Util.parsejsonCTmovieObject(jsontResponse, position);

                                Intent intent = new Intent(MainActivity.this, PDetailsActivity.class);
                                intent.putExtra("Movieobject", movieModel);
                                startActivity(intent);
                            }
                        });

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }


        });
    }


    public void getTopRatedMovies() {
        final Retrofit retrofit = new Retrofit.Builder().baseUrl(base_url).build();
        MoviesInterface moviesInterface = retrofit.create(MoviesInterface.class);

        moviesInterface.getTopRatedMovies(api_key).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String JsonBody = "";
                    if (response.body() != null) {
                        JsonBody = response.body().string();

                        list = Util.parseJsonCTList(JsonBody);
                        recyclerAdaptor.updateData(list);
                        connectTointernetTV.setVisibility(View.INVISIBLE);
                        btnRetry.setVisibility(View.INVISIBLE);
                        progressBar.setVisibility(View.INVISIBLE);

                        recyclerView.setAdapter(recyclerAdaptor);

                        final String finalJsonBody = JsonBody;
                        recyclerAdaptor.setOnItemClickListener(new RecyclerAdaptor.OnItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                //   Toast.makeText(MainActivity.this, "Clicked on Pos " + position, Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(MainActivity.this, PDetailsActivity.class);
                                MovieModel movieModel = Util.parsejsonCTmovieObject(finalJsonBody, position);
                                intent.putExtra("Movieobject", movieModel);
                                startActivity(intent);
                            }
                        });

                    } else {
                        Log.e(TAG, "Retrofit onResponse: response.body() = null");
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }

        });
    }

    @Override
    protected void onStart() {
        EventBus.getDefault().register(this);
        super.onStart();
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMassege(EventMassege event) {

        final Snackbar mysnackbar = Snackbar.make(findViewById(R.id.container), event.message,
                Snackbar.LENGTH_INDEFINITE);
        mysnackbar.setAction("Retry", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mysnackbar.dismiss();
                getPopularMovies();
            }
        })
                .show();
    }

    public void testEventBus(View view) {
        EventBus.getDefault().post(new EventMassege("First, Check your Internet Connection and then click Retry"));
    }

}


//<integer-array name="deletedMovies">
//<item>441614</item>
//<item>337167</item>
//<item>315837</item>
//<item>339964</item>
//<item>433310</item>
//<item>431530</item>
//<item>341013</item>
//<item>339404</item>
//<item>637</item>
//<item>680</item>
//<item>539</item>
//<item>19404</item>
//<item>55960</item>
//<item>550</item>
//<item>399055</item>
//<item>401981</item>
//<item>141052</item>
//<item>353486</item>
//<item>338970</item>
//</integer-array>
////this method in MainActivity to modify movieList and delete
////movies before populating UI

//private void modifyMovieList(List<MovieModel> list){
//        int[] deletedMoviesID= getResources().getIntArray(R.array.deletedMovies);
//        ModifyMovieList.modifyList(list,deletedMoviesID);
//        }
////I created a seperate class to include this method
//public static void modifyList(List<MovieModel> list,int[] deletedMoviesID){
//        Iterator<MovieModel> iterator= list.iterator();
//        while (iterator.hasNext()) {
//        int id = iterator.next().getId();
//        for (int deletedMovie : deletedMoviesID) {
//        if (id == deletedMovie) {
//        iterator.remove();
//        break;
//}}}}
