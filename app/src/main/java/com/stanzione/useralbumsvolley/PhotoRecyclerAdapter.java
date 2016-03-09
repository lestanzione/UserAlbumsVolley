package com.stanzione.useralbumsvolley;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.stanzione.useralbumsvolley.entity.Photo;
import com.stanzione.useralbumsvolley.entity.User;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class PhotoRecyclerAdapter extends RecyclerView.Adapter<PhotoRecyclerAdapter.ViewHolder>{

    public interface OnPhotoSelect {
        void photoSelected(long id);
    }

    private ArrayList<Photo> photoArrayList;
    private Context context;
    private WeakReference<OnPhotoSelect> activity;

    public PhotoRecyclerAdapter(Context context, ArrayList<Photo> photoArrayList, OnPhotoSelect activity) {
        this.photoArrayList = photoArrayList;
        this.context = context;
        this.activity = new WeakReference<OnPhotoSelect>(activity);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_cell, null);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final Photo photo = photoArrayList.get(position);

        //holder.userNameTextView.setText(photo.getName());

    }

    @Override
    public int getItemCount() {
        return (null != photoArrayList ? photoArrayList.size() : 0);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CardView photoCardView;
        public ImageView photoImageView;

        public ViewHolder(View photoView) {
            super(photoView);
            this.photoCardView = (CardView) photoView.findViewById(R.id.photoCardView);
            this.photoImageView = (ImageView) photoView.findViewById(R.id.photoImageView);
        }
    }

}