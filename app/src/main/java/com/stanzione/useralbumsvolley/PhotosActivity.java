package com.stanzione.useralbumsvolley;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class PhotosActivity extends AppCompatActivity implements PhotoRecyclerAdapter.OnPhotoSelect {

    private static final String TAG = UsersActivity.class.getSimpleName();

    private TextView photoAlbumTitleTextView;
    private RecyclerView photosRecyclerView;
    private UserRecyclerDecoration userRecyclerDecoration;

    private ArrayList<Photo> photoArrayList;

    private long albumId;
    private String albumTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);

        photoAlbumTitleTextView = (TextView) findViewById(R.id.photosAlbumTitleTextView);
        photosRecyclerView = (RecyclerView) findViewById(R.id.photosRecyclerView);

        albumId = getIntent().getLongExtra("ALBUM_ID", 0);
        albumTitle = getIntent().getStringExtra("ALBUM_TITLE");

        photoAlbumTitleTextView.setText(albumTitle);

        userRecyclerDecoration = new UserRecyclerDecoration(20, 20, 10, 10);

    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {

        Log.d(TAG, "onSaveInstanceState - PhotosActivity");
        savedInstanceState.putSerializable("SAVED_PHOTOS", photoArrayList);

        super.onSaveInstanceState(savedInstanceState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        Log.d(TAG, "onRestoreInstanceState - PhotosActivity");
        photoArrayList = (ArrayList<Photo>) savedInstanceState.getSerializable("SAVED_PHOTOS");

    }

    @Override
    protected void onResume() {
        super.onResume();

        if(photoArrayList != null){
            showPhotos();
        }
        else {

            // Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(this);
            final String url = "http://jsonplaceholder.typicode.com/photos?albumId=" + albumId;

            Log.d(TAG, "Starting Volley Request");

            // Request a JSON array response from the provided URL.
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                    new Response.Listener<JSONArray>() {

                        @Override
                        public void onResponse(JSONArray response) {

                            Log.d(TAG, "Photos found: " + response.length());

                            photoArrayList = new ArrayList<Photo>();

                            Gson gson = new GsonBuilder().create();

                            for (int i = 0; i < response.length(); i++) {

                                try {
                                    Photo photo = gson.fromJson(String.valueOf(response.getJSONObject(i)), Photo.class);

                                    Log.d(TAG, photo.getTitle());
                                    downloadImage(photo.getThumbnailUrl(), i);

                                    photoArrayList.add(photo);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                            showPhotos();

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(TAG, "Error: " + error);
                }
            }
            );
            // Add the request to the RequestQueue.
            queue.add(jsonArrayRequest);

        }

    }

    public void downloadImage(String url, final int photoPosition) {

        RequestQueue queue = Volley.newRequestQueue(this);

        // Request a JSON array response from the provided URL.
        ImageRequest imageRequest = new ImageRequest(url,
                new Response.Listener<Bitmap>() {

                    @Override
                    public void onResponse(Bitmap bitmap) {
                        photoArrayList.get(photoPosition).setThumbnailBitmap(bitmap);
                        photosRecyclerView.getAdapter().notifyDataSetChanged();
                    }
                }, 150, 150, ImageView.ScaleType.CENTER_INSIDE, null, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "Error: " + error);
                    }
                }
        );

        // Add the request to the RequestQueue.
        queue.add(imageRequest);

    }

    private void showPhotos(){
        PhotoRecyclerAdapter photoRecyclerAdapter = new PhotoRecyclerAdapter(PhotosActivity.this, photoArrayList, this);
        photosRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        photosRecyclerView.setAdapter(photoRecyclerAdapter);

        photosRecyclerView.removeItemDecoration(userRecyclerDecoration);
        photosRecyclerView.addItemDecoration(userRecyclerDecoration);
    }

    @Override
    public void photoSelected(long id) {
        Log.d(TAG, "id selected: " + id);
    }
}
