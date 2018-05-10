package amrhal.example.com.discovermovies2.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Amr Halawani on 10/05/2018.
 */

public class ReviewModel implements Parcelable{
String author;
String content;

    public ReviewModel(String author, String content) {
        this.author = author;
        this.content = content;
    }

    protected ReviewModel(Parcel in) {
        author = in.readString();
        content = in.readString();
    }

    public static final Creator<ReviewModel> CREATOR = new Creator<ReviewModel>() {
        @Override
        public ReviewModel createFromParcel(Parcel in) {
            return new ReviewModel(in);
        }

        @Override
        public ReviewModel[] newArray(int size) {
            return new ReviewModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(author);
        dest.writeString(content);
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
