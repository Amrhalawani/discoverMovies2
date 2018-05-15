package amrhal.example.com.discovermovies2.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Amr Halawani on 10/05/2018.
 */

public class VideoModel implements Parcelable {
    String name; //video name
    String key;

    public String getmName() {
        return name;
    }

    public String getKey() {
        return key;
    }

    public VideoModel(String name, String key) {
        this.name = name;
        this.key = key;
    }

    protected VideoModel(Parcel in) {
        name = in.readString();
        key = in.readString();
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
    }
}
