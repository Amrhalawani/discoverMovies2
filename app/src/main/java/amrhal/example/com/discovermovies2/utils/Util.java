package amrhal.example.com.discovermovies2.utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import amrhal.example.com.discovermovies2.Models.MovieModel;
import amrhal.example.com.discovermovies2.Models.ReviewModel;
import amrhal.example.com.discovermovies2.Models.VideoModel;

/**
 * Created by Amr hal on 03/03/2018.
 */

public class Util {
    static String Results_jsonArray = "results";
    static String movieTitle = "title";
    public static String pic_base_url = "http://image.tmdb.org/t/p/";
    public static String pic_size_urlW185 = "w185";
    public static String pic_size_urlW342 = "w342";
    public static String pic_size_urlW500 = "w500";

    static String posterPathUrl;
    static String posterBackUrl;

    public static List<MovieModel> parseJsonCTList(String json) {
        List<MovieModel> listOfMovies = new ArrayList<>();
        try {
            JSONObject jsonobj = new JSONObject(json);

            JSONArray resultJsonArray = jsonobj.getJSONArray(Results_jsonArray);

            for (int i = 0; i < resultJsonArray.length(); i++) {

                JSONObject jsonObject = (JSONObject) resultJsonArray.get(i);

                String movieName = jsonObject.getString(movieTitle);
                String posterPath = jsonObject.getString("poster_path");
                posterPathUrl = pic_base_url + pic_size_urlW185 + posterPath;
                MovieModel movieModel = new MovieModel(movieName, posterPathUrl);
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
            String movieReleasDate = jsonObject.getString("release_date").substring(0, 7);

            String movieOverview = jsonObject.getString("overview");
            String posterPath = jsonObject.getString("poster_path");

            String original_language = jsonObject.getString("original_language");
            String original_title = jsonObject.getString("original_title");
            String id = jsonObject.getString("id");
            String posterback = jsonObject.getString("backdrop_path");
            boolean adult = jsonObject.getBoolean("adult");

            posterPathUrl = pic_base_url + pic_size_urlW185 + posterPath;
            posterBackUrl = pic_base_url + pic_size_urlW500 + posterback;

            Log.e("TAG", "parsejsonCTmovieObject: " + movieName + "." + movieReleasDate + "." + movieAvg + "." + movieOverview);
            MovieModel movieModel = new MovieModel(movieName, posterPathUrl, movieReleasDate, movieAvg, movieOverview, original_language, original_title, id, posterBackUrl, adult);

            return movieModel;

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("tag", "******Error in parsing = " + e.getLocalizedMessage() + "******* " + e.getMessage());
        }

        return new MovieModel("", "", "", "", "", "", "", "", "", false);
    }

    public static List<VideoModel> parseJsonMovieVideo(String json) {

        List<VideoModel> listOfMovies = new ArrayList<>();
        try {
            JSONObject jsonobj = new JSONObject(json);

            JSONArray resultJsonArray = jsonobj.getJSONArray(Results_jsonArray);

            for (int i = 0; i < resultJsonArray.length(); i++) {

                JSONObject jsonObject = (JSONObject) resultJsonArray.get(i);

                String movieName = jsonObject.getString("name");
                String youtubeKey = jsonObject.getString("key");
                VideoModel videoModel = new VideoModel(movieName,youtubeKey);
                listOfMovies.add(videoModel);
            }
            return listOfMovies;

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("tag", "******Error in parsing parseJsonMovieVideo= " + e.getLocalizedMessage() + "******* " + e.getMessage());
        }

        return null;

    }

    public static List<ReviewModel> parseJsonMovieReview(String json) {
        List<ReviewModel> listOfreviews = new ArrayList<>();
        try {
            JSONObject jsonobj = new JSONObject(json);

            JSONArray resultJsonArray = jsonobj.getJSONArray(Results_jsonArray);

            for (int i = 0; i < resultJsonArray.length(); i++) {

                JSONObject jsonObject = (JSONObject) resultJsonArray.get(i);

                String AutherName = jsonObject.getString("author");
                String content = jsonObject.getString("content");
                ReviewModel reviewModel = new ReviewModel(AutherName, content);

                listOfreviews.add(reviewModel);

            }
            Log.e("tag", "util.class list.size of reviews=" + listOfreviews.size());
            return listOfreviews;

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("tag", "******Error in parsing reviews lis= " + e.getLocalizedMessage() + "******* " + e.getMessage());
        }

        return null;

    }



//    public static String parseJsonYoutubekey(String json) {
//
//        try {
//            JSONObject jsonobj = new JSONObject(json);
//
//            JSONArray resultJsonArray = jsonobj.getJSONArray(Results_jsonArray);
//
//            for (int i = 0; i < resultJsonArray.length(); i++) {
//
//                JSONObject jsonObject = (JSONObject) resultJsonArray.get(i);
//
//                String movieName = jsonObject.getString("name");
//                String youtubeKey = jsonObject.getString("key");
//
//            }
//            return "";
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//            Log.e("tag", "******Error in parsing parseJsonMovieVideo= " + e.getLocalizedMessage() + "******* " + e.getMessage());
//        }
//
//        return null;
//
//    }


}
