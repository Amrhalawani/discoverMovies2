package amrhal.example.com.discovermovies2;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import amrhal.example.com.discovermovies2.database.MovieContract.*;

/**
 * Created by Amr Halawani on 18/05/2018.
 */

public class favCursorAdaptor extends CursorAdapter {

    public favCursorAdaptor(Context context, Cursor c) {
        super(context, c, 0);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        return LayoutInflater.from(context).inflate(R.layout.fav_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView nameTextView = view.findViewById(R.id.text_view_fav_id);
        int nameColumnIndex = cursor.getColumnIndex(MovieEntry.COLUMN_TITLE);
        String movieName = cursor.getString(nameColumnIndex);

        ImageView imageFavMovie = view.findViewById(R.id.imageviewfavmovie_ID);
        int posterColumnIndex = cursor.getColumnIndex(MovieEntry.COLUMN_POSTER_PATH);
        String posterPathUrl = cursor.getString(posterColumnIndex);
        Log.e("TAG", "favCursorAdaptor bindView: posterPathUrl" + posterPathUrl);
        Picasso.get()
                .load(posterPathUrl)
                .placeholder(R.drawable.user_placeholder)
                .into(imageFavMovie);

        nameTextView.setText(movieName);
    }



}
