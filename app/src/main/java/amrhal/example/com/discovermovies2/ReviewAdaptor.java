package amrhal.example.com.discovermovies2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import amrhal.example.com.discovermovies2.Models.ReviewModel;

/**
 * Created by Amr Halawani on 10/05/2018.
 */

public class ReviewAdaptor extends RecyclerView.Adapter<ReviewAdaptor.myViewholder> {

    private static final String TAG = "TAG";
    List<ReviewModel> list = new ArrayList<>();
    Context context;
    LayoutInflater layoutInflater;

    public ReviewAdaptor(Context context) {
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
        View view = layoutInflater.inflate(R.layout.item_review, parent, false);

        ReviewAdaptor.myViewholder myViewholder = new ReviewAdaptor.myViewholder(view);

        return myViewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull myViewholder holder, int position) {
        ReviewModel reviewModel = list.get(position);
        holder.auther.setText(reviewModel.getAuthor());
        holder.content.setText(reviewModel.getContent());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class myViewholder extends RecyclerView.ViewHolder {
        TextView content;
        TextView auther;


        public myViewholder(View itemView) {
            super(itemView);
            Log.e(TAG, "myViewholder: ");
            content = itemView.findViewById(R.id.contentID);
            auther = itemView.findViewById(R.id.autherName);
        }
    }
}
