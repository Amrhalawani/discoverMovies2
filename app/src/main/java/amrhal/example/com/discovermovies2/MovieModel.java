package amrhal.example.com.discovermovies2;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Amr hal on 3/03/2018.
 */

public class MovieModel implements Parcelable{

    // this is the result of an array object called results
    //https://api.themoviedb.org/3/movie/top_rated?api_key={}

    //title, release date, movie poster, vote average, and plot synopsis.
    @SerializedName("title")
    String title;

    @SerializedName("poster_path")
    String poster_path;

    @SerializedName("releaseDate")
    String releaseDate;

    @SerializedName("voteAverage")
    String voteAverage;

    @SerializedName("synopsis")
    String synopsis;

    @SerializedName("original_language")
    String original_language;

    @SerializedName("original_title")
    String original_title;

    @SerializedName("id")
    String id;

    @SerializedName("backdrop_path")
    String backdrop_path;

    @SerializedName("adult")
    boolean adult;

    public MovieModel(String title, String poster_path) {
        this.title = title;
        this.poster_path = poster_path;
    }


    public MovieModel(String title, String poster_path, String releaseDate, String voteAverage,
                      String synopsis, String original_language, String original_title,
                      String id, String backdrop_path, boolean adult) {
        this.title = title;
        this.poster_path = poster_path;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
        this.synopsis = synopsis;
        this.original_language = original_language;
        this.original_title = original_title;
        this.id = id;
        this.backdrop_path = backdrop_path;
        this.adult = adult;
    }

    protected MovieModel(Parcel in) {
        title = in.readString();
        poster_path = in.readString();
        releaseDate = in.readString();
        voteAverage = in.readString();
        synopsis = in.readString();
        original_language = in.readString();
        original_title = in.readString();
        id = in.readString();
        backdrop_path = in.readString();
        adult = in.readByte() != 0;
    }

    public static final Creator<MovieModel> CREATOR = new Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel in) {
            return new MovieModel(in);
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };

    public String getPoster_path() {
        return poster_path;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getmovieId() {
        return id;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public boolean isAdult() {
        return adult;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterUrl() {
        return poster_path;
    }

    public String getReleaseDate() {
        return releaseDate;
    }


    public String getVoteAverage() {
        return voteAverage;
    }


    public String getSynopsis() {
        return synopsis;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(poster_path);
        dest.writeString(releaseDate);
        dest.writeString(voteAverage);
        dest.writeString(synopsis);
        dest.writeString(original_language);
        dest.writeString(original_title);
        dest.writeString(id);
        dest.writeString(backdrop_path);
        dest.writeByte((byte) (adult ? 1 : 0));
    }
}
