package com.stanzione.useralbumsvolley;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
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

import com.makeramen.roundedimageview.RoundedImageView;
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
		holder.userCityTextView.setText("from " + user.getAddress().getCity());

        Drawable drawable = null;

        switch (position+1){
            case 1:
                drawable = ContextCompat.getDrawable(context, R.drawable.user_1);
                break;
            case 2:
                drawable = ContextCompat.getDrawable(context, R.drawable.user_2);
                break;
            case 3:
                drawable = ContextCompat.getDrawable(context, R.drawable.user_3);
                break;
            case 4:
                drawable = ContextCompat.getDrawable(context, R.drawable.user_4);
                break;
            case 5:
                drawable = ContextCompat.getDrawable(context, R.drawable.user_5);
                break;
            case 6:
                drawable = ContextCompat.getDrawable(context, R.drawable.user_6);
                break;
            case 7:
                drawable = ContextCompat.getDrawable(context, R.drawable.user_7);
                break;
            case 8:
                drawable = ContextCompat.getDrawable(context, R.drawable.user_8);
                break;
            case 9:
                drawable = ContextCompat.getDrawable(context, R.drawable.user_9);
                break;
            case 10:
                drawable = ContextCompat.getDrawable(context, R.drawable.user_10);
                break;
        }


        holder.userImageView.setImageDrawable(drawable);

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
		public TextView userCityTextView;
        public RoundedImageView userImageView;
        public Button userAlbumsButton;

        public ViewHolder(View userView) {
            super(userView);
            //this.userCardView = (CardView) userView.findViewById(R.id.userCardView);
            this.userNameTextView = (TextView) userView.findViewById(R.id.userNameTextView);
			this.userCityTextView = (TextView) userView.findViewById(R.id.userCityTextView);
            this.userImageView = (RoundedImageView) userView.findViewById(R.id.userImageView);
            this.userAlbumsButton = (Button) userView.findViewById(R.id.userAlbumsButton);
        }
    }

}
