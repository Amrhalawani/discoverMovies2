/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package amrhal.example.com.discovermovies2.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import amrhal.example.com.discovermovies2.database.MovieContract.MovieEntry;


public class MovieDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "favmoviesdb.db";

    private static final int DATABASE_VERSION = 1;

    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the pets table
        String SQL_CREATE_PETS_TABLE = "CREATE TABLE " + MovieEntry.TABLE_NAME + " ("
                + MovieContract.MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + MovieEntry.COLUMN_TITLE + " TEXT , "
                + MovieEntry.COLUMN_POSTER_PATH + " TEXT, "
                + MovieEntry.COLUMN_RELEASEDATE + " TEXT, "
                + MovieEntry.COLUMN_VOTEAVERAGE + " TEXT, "
                + MovieEntry.COLUMN_SYNOPSIS + " TEXT, "
                + MovieEntry.COLUMN_ORIGINAL_LANGUAGE + " TEXT, "
                + MovieEntry.COLUMN_ORIGINAL_TITLE + " TEXT, "
                + MovieEntry.COLUMN_MOVIE_ID + " TEXT, "
                + MovieEntry.COLUMN_BACKDROP_PATH + " TEXT);";
        //INTEGER NOT NULL DEFAULT 0

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_PETS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // The database is still at version 1, so there's nothing to do be done here.
    }
}