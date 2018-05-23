package amrhal.example.com.discovermovies2;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import amrhal.example.com.discovermovies2.database.MovieContract;
import amrhal.example.com.discovermovies2.database.MovieContract.MovieEntry;

public class FavDetailsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    String title, posterUrl, avg, releaseDate, synopsis, originalLang;

    TextView movieTitleTV, movieAvgTV, moviereleaseDateTV, movieOverviewTV, original;
    ImageView posterIV;

    Uri mCurrentUri;
    String movie_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_details);

        setupUi();
       // mCurrentUri = getIntent().getData();
        movie_id = getIntent().getExtras().getString("id");

        //  Log.e("TAG", "FavDetailsActivity onCreate: url is" + mCurrentUri.toString());

        //update ui when onLoadFinished method excuet
        getLoaderManager().initLoader(0, null, this);
    }

    private void setupUi() {
        Log.e("TAG", "favdetailsactivity opened ");
        movieTitleTV = findViewById(R.id.title_Fav);
        movieAvgTV = findViewById(R.id.avarege_Fav);
        moviereleaseDateTV = findViewById(R.id.releasedate_Fav);
        original = findViewById(R.id.original_titleID_Fav);
        movieOverviewTV = findViewById(R.id.over_viewID_Fav);
        posterIV = findViewById(R.id.poster_detail_fav);

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(this, "favdetatils activity onBackPressed", Toast.LENGTH_SHORT).show();

        finish();
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        //query
        String[] projection = {
                MovieEntry._ID,
                MovieEntry.COLUMN_TITLE,
                MovieEntry.COLUMN_POSTER_PATH,
                MovieEntry.COLUMN_RELEASEDATE,
                MovieEntry.COLUMN_VOTEAVERAGE,
                MovieEntry.COLUMN_SYNOPSIS,
                MovieEntry.COLUMN_ORIGINAL_LANGUAGE,
                MovieEntry.COLUMN_ORIGINAL_TITLE
        };

        String selection = MovieContract.MovieEntry.COLUMN_MOVIE_ID + "=?";

        String[] selectionArgs = new String[]{movie_id};

        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(this,   // Parent activity context
                MovieEntry.CONTENT_URiUnknown_id,   // Provider content URI to query
                projection,             // Columns to include in the resulting Cursor
                selection,                   //  selection clause
                selectionArgs,                   // No selection arguments
                null);// Default sort order

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        //cursor consists of one row and the only one;
        if (cursor.moveToFirst()) {

// Find the columns of pet attributes that we're interested in
            int titleColumnIndex = cursor.getColumnIndex(MovieEntry.COLUMN_TITLE);
            int releaseDataColumnIndex = cursor.getColumnIndex(MovieEntry.COLUMN_RELEASEDATE);
            int voteColumnIndex = cursor.getColumnIndex(MovieEntry.COLUMN_VOTEAVERAGE);
            int originalLangColumnIndex = cursor.getColumnIndex(MovieEntry.COLUMN_ORIGINAL_LANGUAGE);
            int originialTitleColumnIndex = cursor.getColumnIndex(MovieEntry.COLUMN_ORIGINAL_TITLE);
            int synopsisColumnIndex = cursor.getColumnIndex(MovieEntry.COLUMN_SYNOPSIS);
            int posterUrlColumnIndex = cursor.getColumnIndex(MovieEntry.COLUMN_POSTER_PATH);


// Extract out the value from the Cursor for the given column index
            title = cursor.getString(titleColumnIndex);
            releaseDate = cursor.getString(releaseDataColumnIndex);
            avg = cursor.getString(voteColumnIndex);
            originalLang = cursor.getString(originialTitleColumnIndex) + " (" + cursor.getString(originalLangColumnIndex) + ").";
            synopsis = cursor.getString(synopsisColumnIndex);
            posterUrl = cursor.getString(posterUrlColumnIndex);

            String a = title + "/" + releaseDate + "/" + avg + "/" + originalLang + "/" + synopsis + "/" + posterUrl;
            Log.e("TAG", "onLoadFinished: a= " + a);
            //set data
            movieTitleTV.setText(title);
            moviereleaseDateTV.setText(releaseDate);
            movieAvgTV.setText(avg);
            original.setText(originalLang);
            movieOverviewTV.setText(synopsis);

            Picasso.get()
                    .load(posterUrl)
                    .placeholder(R.drawable.user_placeholder)
                    .into(posterIV);
        }
        cursor.close();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        //clear out all fields
        Log.e("TAG", "fav details onLoadreset");
        movieTitleTV.setText("");
        moviereleaseDateTV.setText("");
        movieAvgTV.setText("");
        original.setText("");
        movieOverviewTV.setText("");
    }
}
