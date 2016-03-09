package com.stanzione.useralbumsvolley;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

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

    private RecyclerView albumsRecyclerView;

    private ArrayList<Album> albumArrayList;

    private long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_albums);

        albumsRecyclerView = (RecyclerView) findViewById(R.id.albumsRecyclerView);

        userId = getIntent().getLongExtra("USER_ID", 0);

    }


    @Override
    protected void onStart() {
        super.onStart();

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

                        for(int i=0; i<response.length(); i++){

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
                    }
                }
        );
        // Add the request to the RequestQueue.
        queue.add(jsonArrayRequest);

    }

    private void showAlbums(){
        AlbumRecyclerAdapter albumRecyclerAdapter = new AlbumRecyclerAdapter(AlbumsActivity.this, albumArrayList, this);
        albumsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        albumsRecyclerView.setAdapter(albumRecyclerAdapter);
        albumsRecyclerView.addItemDecoration(new UserRecyclerDecoration(20, 20, 10, 10));
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

}
