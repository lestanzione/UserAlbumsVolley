package com.stanzione.useralbumsvolley;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.stanzione.useralbumsvolley.entity.Album;
import com.stanzione.useralbumsvolley.entity.Photo;
import com.stanzione.useralbumsvolley.entity.User;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class PhotoRecyclerAdapter extends RecyclerView.Adapter<PhotoRecyclerAdapter.ViewHolder>{

    private static final String TAG = PhotoRecyclerAdapter.class.getSimpleName();

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

        holder.photoImageView.setImageBitmap(photo.getThumbnailBitmap());

        //downloadImage(photo.getThumbnailUrl(), holder.photoImageView);

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

    public void downloadImage(String url, final ImageView imageView) {

        RequestQueue queue = Volley.newRequestQueue(context);

        // Request a JSON array response from the provided URL.
        ImageRequest imageRequest = new ImageRequest(url,
                new Response.Listener<Bitmap>() {

                    @Override
                    public void onResponse(Bitmap bitmap) {
                        imageView.setImageBitmap(bitmap);
                    }
                }, 150, 150, null, null, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "Error: " + error);
                    }
                }
        );
        // Add the request to the RequestQueue.
        queue.add(imageRequest);

        /*
        Bitmap bm = null;

        try {
            URL aURL = new URL(url);
            URLConnection conn = aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
        } catch (IOException e) {
            Log.e(TAG, "Error getting the image from server : " + e.getMessage().toString());
        }

        return bm;
        */

    }

}