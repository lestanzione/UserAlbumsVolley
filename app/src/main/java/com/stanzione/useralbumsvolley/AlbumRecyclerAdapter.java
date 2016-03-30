package com.stanzione.useralbumsvolley;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.stanzione.useralbumsvolley.entity.Album;
import com.stanzione.useralbumsvolley.entity.User;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import com.like.*;

public class AlbumRecyclerAdapter extends RecyclerView.Adapter<AlbumRecyclerAdapter.ViewHolder>{

    public interface OnAlbumSelect {
        void albumSelected(long id, String title);
    }

    private ArrayList<Album> albumArrayList;
    private Context context;
    private WeakReference<OnAlbumSelect> activity;

    public AlbumRecyclerAdapter(Context context, ArrayList<Album> albumArrayList, OnAlbumSelect activity) {
        this.albumArrayList = albumArrayList;
        this.context = context;
        this.activity = new WeakReference<OnAlbumSelect>(activity);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.album_row, null);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final Album album = albumArrayList.get(position);

        holder.albumFavoriteButton.setLiked(false);
        holder.albumLikeButton.setLiked(false);
        holder.albumTitleTextView.setText(album.getTitle());

        Drawable drawable = null;

        switch (position+1){
            case 1:
                drawable = ContextCompat.getDrawable(context, R.drawable.album_1);
                break;
            case 2:
                drawable = ContextCompat.getDrawable(context, R.drawable.album_2);
                break;
            case 3:
                drawable = ContextCompat.getDrawable(context, R.drawable.album_3);
                break;
            case 4:
                drawable = ContextCompat.getDrawable(context, R.drawable.album_4);
                break;
            case 5:
                drawable = ContextCompat.getDrawable(context, R.drawable.album_5);
                break;
            case 6:
                drawable = ContextCompat.getDrawable(context, R.drawable.album_6);
                break;
            case 7:
                drawable = ContextCompat.getDrawable(context, R.drawable.album_7);
                break;
            case 8:
                drawable = ContextCompat.getDrawable(context, R.drawable.album_8);
                break;
            case 9:
                drawable = ContextCompat.getDrawable(context, R.drawable.album_9);
                break;
            case 10:
                drawable = ContextCompat.getDrawable(context, R.drawable.album_10);
                break;
        }


        holder.albumImageView.setImageDrawable(drawable);

        holder.albumCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.get().albumSelected(album.getId(), album.getTitle());
            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != albumArrayList ? albumArrayList.size() : 0);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CardView albumCardView;
        public TextView albumTitleTextView;
        public ImageView albumImageView;
		public LikeButton albumFavoriteButton;
		public LikeButton albumLikeButton;

        public ViewHolder(View userView) {
            super(userView);
            this.albumCardView = (CardView) userView.findViewById(R.id.albumCardView);
            this.albumTitleTextView = (TextView) userView.findViewById(R.id.albumTitleTextView);
            this.albumImageView = (ImageView) userView.findViewById(R.id.albumImageView);
			this.albumFavoriteButton = (LikeButton) userView.findViewById(R.id.albumFavoriteButton);
			this.albumLikeButton = (LikeButton) userView.findViewById(R.id.albumLikeButton);
        }
    }

}
