package amrhal.example.com.discovermovies2.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Amr Halawani on 10/05/2018.
 */

public class VideoModel implements Parcelable {
    String name; //video name
    String key;
    String youtubethubnial;


    protected VideoModel(Parcel in) {
        name = in.readString();
        key = in.readString();
        youtubethubnial = in.readString();
    }

    public static final Creator<VideoModel> CREATOR = new Creator<VideoModel>() {
        @Override
        public VideoModel createFromParcel(Parcel in) {
            return new VideoModel(in);
        }

        @Override
        public VideoModel[] newArray(int size) {
            return new VideoModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(key);
        dest.writeString(youtubethubnial);
    }
}
