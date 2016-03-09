package com.stanzione.useralbumsvolley;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class UserRecyclerDecoration extends RecyclerView.ItemDecoration {

    private int spacingLeft;
    private int spacingRight;
    private int spacingTop;
    private int spacingBottom;

    public UserRecyclerDecoration(int spacingLeft, int spacingRight, int spacingTop, int spacingBottom) {
        this.spacingLeft = spacingLeft;
        this.spacingRight = spacingRight;
        this.spacingTop = spacingTop;
        this.spacingBottom = spacingBottom;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        outRect.top = spacingTop;
        outRect.bottom = spacingBottom;
        outRect.left = spacingLeft;
        outRect.right = spacingRight;

    }

}
