package com.memo.deep.openmyeye.ui.view;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class CustomItemDecoration extends RecyclerView.ItemDecoration {

    private int mLeft;
    private int mTop;
    private int mRight;
    private int mBottom;

    public CustomItemDecoration(int left, int top, int right, int bottom) {
        mLeft = left;
        mTop = top;
        mRight = right;
        mBottom = bottom;
    }

    /**
     * @param outRect 边界
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(mLeft, mTop, mRight, mBottom);
    }
}
