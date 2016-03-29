package kz.test.spravochnik.widget;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.State;
import android.view.View;

public class ListItemDecoration extends ItemDecoration {
    private int space;

    public ListItemDecoration(int space) {
        this.space = space;
    }

    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
        if (parent.getChildLayoutPosition(view) != parent.getAdapter().getItemCount()) {
            outRect.bottom = space;
        }
    }
}
