package com.stanzione.useralbumsvolley;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.stanzione.useralbumsvolley.entity.Album;
import com.stanzione.useralbumsvolley.entity.User;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class AlbumsActivity extends AppCompatActivity implements AlbumRecyclerAdapter.OnAlbumSelect {

    private static final String TAG = UsersActivity.class.getSimpleName();

    private Toolbar toolbar;
    private CoordinatorLayout coordinatorLayout;
    private RecyclerView albumsRecyclerView;
    private UserRecyclerDecoration userRecyclerDecoration;
    private ProgressBar progressBar;

    private ArrayList<Album> albumArrayList;

    private long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_albums);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Albums");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.albumsCoordinatorLayout);
        albumsRecyclerView = (RecyclerView) findViewById(R.id.albumsRecyclerView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        userId = getIntent().getLongExtra("USER_ID", 0);

        userRecyclerDecoration = new UserRecyclerDecoration(20, 20, 10, 10);

    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {

        Log.d(TAG, "onSaveInstanceState - AlbumsActivity");
        savedInstanceState.putSerializable("SAVED_ALBUMS", albumArrayList);

        super.onSaveInstanceState(savedInstanceState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        Log.d(TAG, "onRestoreInstanceState - AlbumsActivity");
        albumArrayList = (ArrayList<Album>) savedInstanceState.getSerializable("SAVED_ALBUMS");

    }

    @Override
    protected void onResume() {
        super.onResume();

        if(albumArrayList != null){
            showAlbums();
        }
        else {

            progressBar.setVisibility(View.VISIBLE);

            // Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(this);
            final String url = "http://jsonplaceholder.typicode.com/albums?userId=" + userId;

            Log.d(TAG, "Starting Volley Request");

            // Request a JSON array response from the provided URL.
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                    new Response.Listener<JSONArray>() {

                        @Override
                        public void onResponse(JSONArray response) {

                            Log.d(TAG, "Albums found: " + response.length());

                            albumArrayList = new ArrayList<Album>();

                            Gson gson = new GsonBuilder().create();

                            for (int i = 0; i < response.length(); i++) {

                                try {
                                    Album album = gson.fromJson(String.valueOf(response.getJSONObject(i)), Album.class);

                                    Log.d(TAG, album.getTitle());

                                    albumArrayList.add(album);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                            showAlbums();

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(TAG, "Error: " + error);
                    progressBar.setVisibility(View.INVISIBLE);
                    Snackbar.make(coordinatorLayout, "Could not get albums from server", Snackbar.LENGTH_SHORT).show();
                }
            }
            );
            // Add the request to the RequestQueue.
            queue.add(jsonArrayRequest);

        }

    }

    private void showAlbums(){
        AlbumRecyclerAdapter albumRecyclerAdapter = new AlbumRecyclerAdapter(AlbumsActivity.this, albumArrayList, this);
        albumsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        albumsRecyclerView.setAdapter(albumRecyclerAdapter);

        albumsRecyclerView.removeItemDecoration(userRecyclerDecoration);
        albumsRecyclerView.addItemDecoration(userRecyclerDecoration);

        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void albumSelected(long id, String title) {
        Log.d(TAG, "id selected: " + id);
        Log.d(TAG, "title selected: " + title);

        Intent intent = new Intent(getApplicationContext(), PhotosActivity.class);
        intent.putExtra("ALBUM_ID", id);
        intent.putExtra("ALBUM_TITLE", title);

        startActivity(intent);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

}
