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

import android.net.Uri;
import android.provider.BaseColumns;

public final class MovieContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private MovieContract() {
    }

    public static final class MovieEntry implements BaseColumns {

        public final static String TABLE_NAME = "favmovies";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_TITLE = "title";
        public final static String COLUMN_POSTER_PATH = "posterpath";
        public final static String COLUMN_RELEASEDATE = "releaseDate";
        public final static String COLUMN_VOTEAVERAGE = "voteAverage";
        public final static String COLUMN_SYNOPSIS = "synopsis";
        public final static String COLUMN_ORIGINAL_LANGUAGE = "original_language";
        public final static String COLUMN_ORIGINAL_TITLE = "original_title";
        public final static String COLUMN_MOVIE_ID = "id";
        public final static String COLUMN_BACKDROP_PATH = "backdrop_path";

        ///////////////////////////////////
        public static final String CONTENT_AUTHORITY = "amrhal.example.com.discovermovies2";

        public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

        public static final String FAVMOVIES = "favmovies";

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, FAVMOVIES);

    }

}

