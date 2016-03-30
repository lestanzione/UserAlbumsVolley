package com.stanzione.useralbumsvolley;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.stanzione.useralbumsvolley.entity.User;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class UsersActivity extends AppCompatActivity implements UserRecyclerAdapter.OnUserSelect{

    private static final String TAG = UsersActivity.class.getSimpleName();

    private Toolbar toolbar;
    private Button getUsersButton;
    private RecyclerView usersRecyclerView;
    private UserRecyclerDecoration userRecyclerDecoration;
    private ProgressBar progressBar;

    private ArrayList<User> userArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Users");
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setHomeButtonEnabled(true);

        getUsersButton = (Button) findViewById(R.id.getUsersButton);
        usersRecyclerView = (RecyclerView) findViewById(R.id.usersRecyclerView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        getUsersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUsers();
            }
        });

        userRecyclerDecoration = new UserRecyclerDecoration(20, 20, 10, 10);

    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {

        Log.d(TAG, "onSaveInstanceState - UsersActivity");
        savedInstanceState.putSerializable("SAVED_USERS", userArrayList);

        super.onSaveInstanceState(savedInstanceState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        Log.d(TAG, "onRestoreInstanceState - UsersActivity");
        userArrayList = (ArrayList<User>) savedInstanceState.getSerializable("SAVED_USERS");
        showUsers();

    }

    private void getUsers(){

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        final String url = "http://jsonplaceholder.typicode.com/users";

        Log.d(TAG, "Starting Volley Request");

        progressBar.setVisibility(View.VISIBLE);

        // Request a JSON array response from the provided URL.
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        Log.d(TAG, "Users found: " + response.length());

                        userArrayList = new ArrayList<User>();

                        Gson gson = new GsonBuilder().create();

                        for(int i=0; i<response.length(); i++){

                            try {
                                User user = gson.fromJson(String.valueOf(response.getJSONObject(i)), User.class);

                                Log.d(TAG, user.getName());

                                userArrayList.add(user);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        showUsers();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "Error: " + error);
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }
        );
        // Add the request to the RequestQueue.
        queue.add(jsonArrayRequest);

    }

    private void showUsers(){
        UserRecyclerAdapter userRecyclerAdapter = new UserRecyclerAdapter(UsersActivity.this, userArrayList, this);
        usersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        usersRecyclerView.setAdapter(userRecyclerAdapter);

        //remove current item decoration
        //we need to do that to prevent doubling the margin if the user runs the search again
        usersRecyclerView.removeItemDecoration(userRecyclerDecoration);

        usersRecyclerView.addItemDecoration(userRecyclerDecoration);

        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void userSelected(long id) {
        Log.d(TAG, "id selected: " + id);

        Intent intent = new Intent(this, AlbumsActivity.class);
        intent.putExtra("USER_ID", id);
        startActivity(intent);

    }

}
