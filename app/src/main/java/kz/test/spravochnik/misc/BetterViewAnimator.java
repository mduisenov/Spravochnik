package kz.test.spravochnik.misc;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ViewAnimator;

public class BetterViewAnimator extends ViewAnimator {
    public BetterViewAnimator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setDisplayedChildId(int id) {
        if (getDisplayedChildId() != id) {
            int count = getChildCount();
            for (int i = 0; i < count; i++) {
                if (getChildAt(i).getId() == id) {
                    setDisplayedChild(i);
                    return;
                }
            }
            throw new IllegalArgumentException("No view with CINEMA_ID " + id);
        }
    }

    public int getDisplayedChildId() {
        return getChildAt(getDisplayedChild()).getId();
    }
}
