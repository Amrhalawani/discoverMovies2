package amrhal.example.com.discovermovies2;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amr hal on 03/03/2018.
 */

public class Util {
    static String Results_jsonArray = "results";
    static String movieTitle = "title";

    public static String pic_base_url = "http://image.tmdb.org/t/p/";
    public static String pic_size_url = "w185";
    static String posterUrl;

    public static List<MovieModel> parseJsonCTList(String json) {
        List<MovieModel> listOfMovies = new ArrayList<>();
        try {

            JSONObject jsonobj = new JSONObject(json);

            JSONArray resultJsonArray = jsonobj.getJSONArray(Results_jsonArray);

            for (int i = 0; i < resultJsonArray.length(); i++) {

                JSONObject jsonObject = (JSONObject) resultJsonArray.get(i);

                String movieName = jsonObject.getString(movieTitle);
                String posterPath = jsonObject.getString("poster_path");
                posterUrl = pic_base_url + pic_size_url + posterPath;
                MovieModel movieModel = new MovieModel(movieName, posterUrl);
                listOfMovies.add(movieModel);

            }
            Log.e("tag", "util.class list.size=" + listOfMovies.size());
            return listOfMovies;

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("tag", "******Error in parsing = " + e.getLocalizedMessage() + "******* " + e.getMessage());
        }

        return null;

    }

    public static MovieModel parsejsonCTmovieObject(String json, int position) {


        try {


            JSONObject jsonobj = new JSONObject(json);

            JSONArray resultJsonArray = jsonobj.getJSONArray(Results_jsonArray);

            JSONObject jsonObject = (JSONObject) resultJsonArray.get(position);

            String movieName = jsonObject.getString(movieTitle);
            String movieAvg = jsonObject.getString("vote_average");
            String movieReleasDate = jsonObject.getString("release_date").substring(0,7);

            String movieOverview = jsonObject.getString("overview");
            String posterPath = jsonObject.getString("poster_path");
            posterUrl = pic_base_url + pic_size_url + posterPath;
            Log.e("TAG", "parsejsonCTmovieObject: " + movieName + "." + movieReleasDate + "." +movieAvg + "." + movieOverview);
            MovieModel movieModel = new MovieModel(movieName, posterUrl, movieReleasDate, movieAvg, movieOverview);

            return movieModel;

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("tag", "******Error in parsing = " + e.getLocalizedMessage() + "******* " + e.getMessage());
        }

        return new MovieModel("", "", "", "", "");
    }
}
