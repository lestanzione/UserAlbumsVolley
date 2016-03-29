package com.stanzione.useralbumsvolley;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

        holder.albumTitleTextView.setText(album.getTitle());
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
		public LikeButton albumFavoriteButton;

        public ViewHolder(View userView) {
            super(userView);
            this.albumCardView = (CardView) userView.findViewById(R.id.albumCardView);
            this.albumTitleTextView = (TextView) userView.findViewById(R.id.albumTitleTextView);
			this.albumFavoriteButton = (LikeButton) userView.findViewById(R.id.albumFavoriteButton);
        }
    }

}
