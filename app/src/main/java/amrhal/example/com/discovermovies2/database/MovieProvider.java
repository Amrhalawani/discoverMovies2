package amrhal.example.com.discovermovies2.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.util.Log;

/**
 * Created by Amr Halawani on 4/8/2018.
 */

public class MovieProvider extends ContentProvider {

    private static final String LOG_TAG = "tag";

    private MovieDbHelper mMovieDbHelper;

    public static final UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        mUriMatcher.addURI(MovieContract.MovieEntry.CONTENT_AUTHORITY, MovieContract.MovieEntry.FAVMOVIES, 1);

        mUriMatcher.addURI(MovieContract.MovieEntry.CONTENT_AUTHORITY, MovieContract.MovieEntry.FAVMOVIES + "/#", 2);
    }

    @Override
    public boolean onCreate() {

        mMovieDbHelper = new MovieDbHelper(getContext());

        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        SQLiteDatabase db = mMovieDbHelper.getReadableDatabase();

        int match = mUriMatcher.match(uri);
        Cursor cursor;

        switch (match) {
            case 1:
                cursor = db.query(
                        MovieContract.MovieEntry.TABLE_NAME,   // The table to query
                        projection,            // The columns to return
                        null,                  // The columns for the WHERE clause
                        null,                  // The values for the WHERE clause
                        null,                  // Don't group the rows
                        null,                  // Don't filter by row groups
                        null);
                Log.e(LOG_TAG, "query: case 1");
                break;

//-------------------

            case 2:
                Log.e(LOG_TAG, "query: case 2");
                selection = MovieContract.MovieEntry._ID + "=?";
                //get id from uri
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                Log.e("TAG", "selection is"+selection+ " selectionArgs="+selectionArgs[0]);

                cursor = db.query(
                        MovieContract.MovieEntry.TABLE_NAME,   // The table to query
                        projection,            // The columns to return
                        selection,                  // The columns for the WHERE clause
                        selectionArgs,                  // The values for the WHERE clause
                        null,                  // Don't group the rows
                        null,                  // Don't filter by row groups
                        null);
                //this means like SELECT
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }

        // If the data at this URI changes, then we know we need to update the Cursor.
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        int match = mUriMatcher.match(uri);

        switch (match) {
            case 1:
                return insertMovie(uri, values);

            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }

    }

    private Uri insertMovie(Uri uri, ContentValues values) {
        SQLiteDatabase database = mMovieDbHelper.getWritableDatabase();

        long _id = database.insert(MovieContract.MovieEntry.TABLE_NAME, null, values);
        // If the ID is -1, then the insertion failed. Log an error and return null.
        if (_id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }
        // Notify all listeners that the data has changed for the pet content URI
        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, _id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        SQLiteDatabase database = mMovieDbHelper.getWritableDatabase();

        int rowsDeleted;

        final int match = mUriMatcher.match(uri);
        switch (match) {
            case 1:
                rowsDeleted = database.delete(MovieContract.MovieEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case 2:
                selection = MovieContract.MovieEntry.COLUMN_MOVIE_ID + "=?";
              //  selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                rowsDeleted = database.delete(MovieContract.MovieEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }

        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection,
                      String[] selectionArgs) {
        return 0;
    }


}
