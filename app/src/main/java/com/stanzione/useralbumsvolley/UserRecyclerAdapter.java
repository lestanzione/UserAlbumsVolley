package com.stanzione.useralbumsvolley;

import android.app.Activity;
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

import com.stanzione.useralbumsvolley.entity.User;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class UserRecyclerAdapter extends RecyclerView.Adapter<UserRecyclerAdapter.ViewHolder>{

    public interface OnUserSelect {
        void userSelected(long id);
    }

    private ArrayList<User> userArrayList;
    private Context context;
    private WeakReference<OnUserSelect> activity;

    public UserRecyclerAdapter(Context context, ArrayList<User> userArrayList, OnUserSelect activity) {
        this.userArrayList = userArrayList;
        this.context = context;
        this.activity = new WeakReference<OnUserSelect>(activity);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_row, null);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final User user = userArrayList.get(position);

        holder.userNameTextView.setText(user.getName());
        holder.userAlbumsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.get().userSelected(user.getId());
            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != userArrayList ? userArrayList.size() : 0);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CardView userCardView;
        public TextView userNameTextView;
        public Button userAlbumsButton;

        public ViewHolder(View userView) {
            super(userView);
            //this.userCardView = (CardView) userView.findViewById(R.id.userCardView);
            this.userNameTextView = (TextView) userView.findViewById(R.id.userNameTextView);
            this.userAlbumsButton = (Button) userView.findViewById(R.id.userAlbumsButton);
        }
    }

}