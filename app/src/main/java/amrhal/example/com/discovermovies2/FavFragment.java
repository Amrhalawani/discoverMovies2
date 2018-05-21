package amrhal.example.com.discovermovies2;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import amrhal.example.com.discovermovies2.database.MovieDbHelper;
import amrhal.example.com.discovermovies2.database.MovieContract.MovieEntry;


public class FavFragment extends Fragment implements View.OnClickListener, LoaderManager.LoaderCallbacks<Cursor> {
    Cursor cursor;
    SQLiteDatabase db;
    favCursorAdaptor favCursorAdaptor;

    public FavFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflatedview = inflater.inflate(R.layout.fragment_fav, container, false);
        Button btn_fake = inflatedview.findViewById(R.id.btn_fakedateID);
        btn_fake.setOnClickListener(this);

        Button btn_deleteAll = inflatedview.findViewById(R.id.btn_deleteAlldataID);
        btn_deleteAll.setOnClickListener(this);

        MovieDbHelper mDbHelper = new MovieDbHelper(getActivity());
        db = mDbHelper.getReadableDatabase();


        //  displayDatabaseInfo();

        // ListView listView = inflatedview.findViewById(R.id.list);
        GridView gridView = inflatedview.findViewById(R.id.Gridlist);

        favCursorAdaptor = new favCursorAdaptor(getActivity(), cursor);

        // listView.setAdapter(favCursorAdaptor);
        gridView.setAdapter(favCursorAdaptor);

        // Find and set empty view on the ListView, so that it only shows when the list has 0 items.
        View emptyView = inflatedview.findViewById(R.id.empty_title_text);
        // listView.setEmptyView(emptyView);
        gridView.setEmptyView(emptyView);
        // Kick off the loader
        getLoaderManager().initLoader(0, null, this);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getActivity(), FavDetailsActivity.class);

                Uri currentPetUri = ContentUris.withAppendedId(MovieEntry.CONTENT_URI, position + 1); //+1 duo to this position started from 0 and table started from 1

                intent.setData(currentPetUri);

                startActivity(intent);

            }
        });

        return inflatedview;
    }



    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
        Toast.makeText(context, "FavFragment launched", Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_fakedateID:

                insertFakeMovie();

                Toast.makeText(getActivity(), "you pressed on fake button ", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btn_deleteAlldataID:

                deleteAllMovies();

                Toast.makeText(getActivity(), "Delete all Movies ", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void insertFakeMovie() {
        ContentValues values = new ContentValues(); //key value

        values.put(MovieEntry.COLUMN_TITLE, "movie");

        String posterpathfullUrl = "http://image.tmdb.org/t/p/w154" + "/y31QB9kn3XSudA15tV7UWQ9XLuW.jpg";

        values.put(MovieEntry.COLUMN_POSTER_PATH, posterpathfullUrl);

        Uri newUri = getActivity().getApplicationContext().getContentResolver().insert(MovieEntry.CONTENT_URI, values);
        Log.e("TAG", "insertFake movie: and newrowid is= " + newUri);
    }

    private void deleteAllMovies() {
        //this didn't delete auto increment column so the solution is
        // https://stackoverflow.com/questions/1601697/sqlite-reset-primary-key-field
        int rowsDeleted = getActivity().getApplicationContext().getContentResolver().delete(MovieEntry.CONTENT_URI, null, null);

        db.execSQL("delete from sqlite_sequence where name = 'favmovies'");

        Log.v("TAG", rowsDeleted + " rows deleted from pet database");
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        //query
        String[] projection = {
                MovieEntry._ID,
                MovieEntry.COLUMN_TITLE,
                MovieEntry.COLUMN_POSTER_PATH};

        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(getActivity(),   // Parent activity context
                MovieEntry.CONTENT_URI,   // Provider content URI to query
                projection,             // Columns to include in the resulting Cursor
                null,                   // No selection clause
                null,                   // No selection arguments
                null);                  // Default sort order
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor1) {
        favCursorAdaptor.swapCursor(cursor1);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        favCursorAdaptor.swapCursor(null);

    }
}
