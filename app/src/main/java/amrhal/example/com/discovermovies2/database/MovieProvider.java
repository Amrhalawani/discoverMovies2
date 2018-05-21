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
                selection = MovieContract.MovieEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
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
//        final int match = mUriMatcher.match(uri);
//        switch (match) {
//            case 1:
//                return updatePet(uri, contentValues, selection, selectionArgs);
//            case 2:
//                // For the PET_ID code, extract out the ID from the URI,
//                // so we know which row to update. Selection will be "_id=?" and selection
//                // arguments will be a String array containing the actual ID.
//                selection = MovieEntry._ID + "=?";
//                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
//                return updatePet(uri, contentValues, selection, selectionArgs);
//            default:
//                throw new IllegalArgumentException("Update is not supported for " + uri);
//        }
//    }

//    /**
//     * Update pets in the database with the given content values. Apply the changes to the rows
//     * specified in the selection and selection arguments (which could be 0 or 1 or more pets).
//     * Return the number of rows that were successfully updated.
//     */
//    private int updatePet(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
//        // If the {@link MovieEntry#COLUMN_PET_NAME} key is present,
//        // check that the name value is not null.
//        if (values.containsKey(MovieEntry.COLUMN_PET_NAME)) {
//            String name = values.getAsString(MovieEntry.COLUMN_PET_NAME);
//            if (name == null) {
//                throw new IllegalArgumentException("Pet requires a name");
//            }
//        }
//
//        // If the {@link MovieEntry#COLUMN_PET_GENDER} key is present,
//        // check that the gender value is valid.
//        if (values.containsKey(MovieEntry.COLUMN_PET_GENDER)) {
//            Integer gender = values.getAsInteger(MovieEntry.COLUMN_PET_GENDER);
//            if (gender == null || !MovieEntry.isValidGender(gender)) {
//                throw new IllegalArgumentException("Pet requires valid gender");
//            }
//        }
//
//        // If the {@link MovieEntry#COLUMN_PET_WEIGHT} key is present,
//        // check that the weight value is valid.
//        if (values.containsKey(MovieEntry.COLUMN_PET_WEIGHT)) {
//            // Check that the weight is greater than or equal to 0 kg
//            Integer weight = values.getAsInteger(MovieEntry.COLUMN_PET_WEIGHT);
//            if (weight != null && weight < 0) {
//                throw new IllegalArgumentException("Pet requires valid weight");
//            }
//        }
//
//        // No need to check the breed, any value is valid (including null).
//
//        // If there are no values to update, then don't try to update the database
//        if (values.size() == 0) {
//            return 0;
//        }
//
//        // Otherwise, get writeable database to update the data
//        SQLiteDatabase database = mMovieDbHelper.getWritableDatabase();
//
//        // Perform the update on the database and get the number of rows affected
//        int rowsUpdated = database.update(MovieEntry.TABLE_NAME, values, selection, selectionArgs);
//
//        // If 1 or more rows were updated, then notify all listeners that the data at the
//        // given URI has changed
//        if (rowsUpdated != 0) {
//            getContext().getContentResolver().notifyChange(uri, null);
//        }
//
//
//        // Returns the number of database rows affected by the update statement nOfRowAffected
//        return rowsUpdated;
        return 0;
    }


}
