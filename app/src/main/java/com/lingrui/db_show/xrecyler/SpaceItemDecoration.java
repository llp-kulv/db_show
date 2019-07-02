package com.lingrui.db_show.xrecyler;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    private int mSpace = 0;

    public SpaceItemDecoration(int space) {
        this.mSpace = space;
    }

    private int getSpanCount(RecyclerView parent) {
        // 列数
        int spanCount = -1;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {

            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            spanCount = ((StaggeredGridLayoutManager) layoutManager)
                    .getSpanCount();
        }
        return spanCount;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//        int position = parent.getChildAdapterPosition(view); // item position
//        int spanCount = getSpanCount(parent);
//        int column = position % spanCount; // item column
//
//        if (false) {
//            outRect.left = mVerticalSpaceHeight - column * mVerticalSpaceHeight / spanCount; // spacing - column * ((1f / spanCount) * spacing)
//            outRect.right = (column + 1) * mVerticalSpaceHeight / spanCount; // (column + 1) * ((1f / spanCount) * spacing)
//
//            if (position < spanCount) { // top edge
//                outRect.top = mVerticalSpaceHeight;
//            }
//            outRect.bottom = mVerticalSpaceHeight; // item bottom
//        } else {
//            outRect.left = column * mVerticalSpaceHeight / spanCount; // column * ((1f / spanCount) * spacing)
//            outRect.right = mVerticalSpaceHeight - (column + 1) * mVerticalSpaceHeight / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
//            if (position >= spanCount) {
//                outRect.top = mVerticalSpaceHeight; // item top
//            }
//        }

        outRect.left = mSpace;
        outRect.right = mSpace;
        outRect.bottom = mSpace;

        // Add top margin only for the first item to avoid double space between items
        if (parent.getChildPosition(view) == 0) {
            outRect.top = mSpace;
        }

    }
}
