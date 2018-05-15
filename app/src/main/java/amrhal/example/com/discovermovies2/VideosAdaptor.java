package amrhal.example.com.discovermovies2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import amrhal.example.com.discovermovies2.Models.ReviewModel;
import amrhal.example.com.discovermovies2.Models.VideoModel;

/**
 * Created by Amr Halawani on 10/05/2018.
 */

public class VideosAdaptor extends RecyclerView.Adapter<VideosAdaptor.myViewholder> {

    private static final String TAG = "TAG";
    List<VideoModel> list = new ArrayList<>();
    Context context;
    LayoutInflater layoutInflater;
    public OnItemClickListener onItemClickListener;
    String YoutubeThumbnailbase = "https://img.youtube.com/vi/kf8X-MtsufY/mqdefault.jpg";
    String youtubekey;

    public VideosAdaptor(Context context) {
        this.context = context;
    }


    public void updateData(List newList) {
        this.list = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public myViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_video, parent, false);

        VideosAdaptor.myViewholder myViewholder = new VideosAdaptor.myViewholder(view);

        return myViewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull myViewholder holder, int position) {

        VideoModel videoModel = list.get(position);

        holder.name.setText(videoModel.getmName());
        youtubekey = videoModel.getKey();

        Picasso.get()
                .load(thumbnailURL(videoModel.getKey()))
                .placeholder(R.drawable.user_placeholder)
                .fit()
                .into(holder.thumbnail);

    }

    private String thumbnailURL(String ourkey) {

        String YoutubeThumbnailbase = "https://img.youtube.com/vi/";
        String endurl = "/mqdefault.jpg";

        return YoutubeThumbnailbase + ourkey + endurl;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position, String key);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    class myViewholder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name;
        ImageView thumbnail;

        public myViewholder(View itemView) {
            super(itemView);
            Log.e(TAG, "myViewholder: ");
            name = itemView.findViewById(R.id.video_nameID);
            thumbnail = itemView.findViewById(R.id.video_thumbnial);
            thumbnail.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null)
                onItemClickListener.onItemClick(getAdapterPosition(), youtubekey);

        }
    }
}
