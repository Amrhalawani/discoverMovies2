package amrhal.example.com.discovermovies2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import amrhal.example.com.discovermovies2.Models.MovieModel;

/**
 * Created by Amr hal on 3/03/2018.
 */

public class RecyclerAdaptor extends RecyclerView.Adapter<RecyclerAdaptor.myViewholder> {
    private static final String TAG = "TAG";
    List<MovieModel> list = new ArrayList<>();
    Context context;
    LayoutInflater layoutInflater;
    public OnItemClickListener onItemClickListener;


    public RecyclerAdaptor(Context context) {

        this.context = context;
        Log.e(TAG, "RecyclerAdaptor: " + "context is " + context.getClass().toString() + " / list size is " + list.size());
        //  layoutInflater = LayoutInflater.from(context);
    }



    @Override
    public myViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.e(TAG, "onCreateViewHolder: ");

        layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_list, parent, false);

        myViewholder myViewholder = new myViewholder(view);

        return myViewholder;
    }

    @Override
    public void onBindViewHolder(myViewholder holder, int position) {
        Log.e(TAG, "onBindViewHolder: ");
//"http://i.imgur.com/DvpvklR.png"
        MovieModel movieModel = list.get(position);

        // Picasso.get().setIndicatorsEnabled(true);
        Picasso.get()
                .load(movieModel.getPosterUrl())
                .placeholder(R.drawable.user_placeholder)
                .into(holder.imageView);


    }


    @Override
    public int getItemCount() {

        return list.size();
    }

    public void updateData(List newList) {
        this.list = newList;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    //------------------------------------------------------------------------------------------------------------------------------------------
    class myViewholder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;


        public myViewholder(View itemView) {
            super(itemView);
            Log.e(TAG, "myViewholder: ");
            imageView = itemView.findViewById(R.id.movieposterIV_ID);
            imageView.setOnClickListener(this);

        }

        @Override
    public void onClick(View view) {
        if (onItemClickListener != null)
            onItemClickListener.onItemClick(getAdapterPosition());
    }
}

}
