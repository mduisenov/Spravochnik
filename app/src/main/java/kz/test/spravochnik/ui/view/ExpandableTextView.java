package kz.test.spravochnik.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.SparseBooleanArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import kz.test.spravochnik.BuildConfig;
import kz.test.spravochnik.R;

public class ExpandableTextView extends LinearLayout implements OnClickListener {

    private static final float DEFAULT_ANIM_ALPHA_START = 0.7f;
    private static final int DEFAULT_ANIM_DURATION = 300;
    private static final int MAX_COLLAPSED_LINES = 8;
    private static final String TAG;
    private float animAlphaStart;
    private boolean animating;
    private int animationDuration;
    private Drawable collapseDrawable;
    private View collapseTxt;
    private boolean collapsed;
    private int collapsedHeight;
    private SparseBooleanArray collapsedStatus;
    private CharSequence content;
    protected ImageView expandCollapseIcon;
    private Drawable expandDrawable;
    private View expandTxt;
    private View expandableControls;
    private int marginBetweenTxtAndBottom;
    private int maxCollapsedLines;
    private int position;
    private boolean relayout;
    private int textHeightWithMaxLines;
    protected TextView textView;

    private MyRunnable myRunnable = new MyRunnable();

    class AnimationListenerImpl implements AnimationListener {
        AnimationListenerImpl() {
        }

        public void onAnimationStart(Animation animation) {
            ExpandableTextView.applyAlphaAnimation(ExpandableTextView.this.textView, ExpandableTextView.this.animAlphaStart);
        }

        public void onAnimationEnd(Animation animation) {
            ExpandableTextView.this.clearAnimation();
            ExpandableTextView.this.animating = false;
            ExpandableTextView.this.setText(ExpandableTextView.this.content);
        }

        public void onAnimationRepeat(Animation animation) {
        }
    }

    class MyRunnable implements Runnable {
        MyRunnable() {
        }

        public void run() {
            ExpandableTextView.this.marginBetweenTxtAndBottom = ExpandableTextView.this.getHeight() - ExpandableTextView.this.textView.getHeight();
        }
    }

    protected class ExpandCollapseAnimation extends Animation {
        private final int mEndHeight;
        private final int mStartHeight;
        private final View mTargetView;

        public ExpandCollapseAnimation(View view, int startHeight, int endHeight) {
            this.mTargetView = view;
            this.mStartHeight = startHeight;
            this.mEndHeight = endHeight;
            setDuration((long) ExpandableTextView.this.animationDuration);
            setInterpolator(new AccelerateDecelerateInterpolator());
        }

        protected void applyTransformation(float interpolatedTime, Transformation t) {
            int newHeight = (int) ((((float) (this.mEndHeight - this.mStartHeight)) * interpolatedTime) + ((float) this.mStartHeight));
            ExpandableTextView.this.textView.setMaxHeight(newHeight - ExpandableTextView.this.marginBetweenTxtAndBottom);
            if (Float.compare(ExpandableTextView.this.animAlphaStart, 1.0f) != 0) {
                ExpandableTextView.applyAlphaAnimation(ExpandableTextView.this.textView, ExpandableTextView.this.animAlphaStart + ((1.0f - ExpandableTextView.this.animAlphaStart) * interpolatedTime));
            }
            this.mTargetView.getLayoutParams().height = newHeight;
            this.mTargetView.requestLayout();
        }

        public void initialize(int width, int height, int parentWidth, int parentHeight) {
            super.initialize(width, height, parentWidth, parentHeight);
        }

        public boolean willChangeBounds() {
            return true;
        }
    }

    static {
        TAG = ExpandableTextView.class.getSimpleName();
    }

    public ExpandableTextView(Context context) {
        super(context);
        this.collapsed = true;
    }

    public ExpandableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.collapsed = true;
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public ExpandableTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.collapsed = true;
        init(attrs);
    }

    private static boolean isPostHoneycomb() {
        return VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static void applyAlphaAnimation(View view, float alpha) {
        if (isPostHoneycomb()) {
            view.setAlpha(alpha);
            return;
        }
        AlphaAnimation alphaAnimation = new AlphaAnimation(alpha, alpha);
        alphaAnimation.setDuration(0);
        alphaAnimation.setFillAfter(true);
        view.startAnimation(alphaAnimation);
    }

    private static int getRealTextViewHeight(TextView textView) {
        return textView.getLayout().getLineTop(textView.getLineCount()) + (textView.getCompoundPaddingTop() + textView.getCompoundPaddingBottom());
    }

    public void onClick(View view) {
        if (this.expandCollapseIcon.getVisibility() == VISIBLE) {
            boolean z;
            Animation animation;
            z = !this.collapsed;
            this.collapsed = z;
            this.expandCollapseIcon.setImageDrawable(this.collapsed ? this.expandDrawable : this.collapseDrawable);
            if (this.collapsed) {
                this.expandTxt.setVisibility(VISIBLE);
                this.collapseTxt.setVisibility(GONE);
            } else {
                this.expandTxt.setVisibility(GONE);
                this.collapseTxt.setVisibility(VISIBLE);
            }
            if (this.collapsedStatus != null) {
                this.collapsedStatus.put(this.position, this.collapsed);
            }
            this.animating = true;
            if (this.collapsed) {
                animation = new ExpandCollapseAnimation(this, getHeight(), this.collapsedHeight);
            } else {
                animation = new ExpandCollapseAnimation(this, getHeight(), (getHeight() + this.textHeightWithMaxLines) - this.textView.getHeight());
            }
            animation.setFillAfter(true);
            animation.setAnimationListener(new AnimationListenerImpl());
            clearAnimation();
            startAnimation(animation);
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return this.animating;
    }

    public boolean onTouchEvent(MotionEvent event) {
        return this.animating || super.onTouchEvent(event);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
//        this.findViews();
    }

    protected void onMeasure(int n, int n2) {
        if (!this.relayout || this.getVisibility() == GONE) {
            super.onMeasure(n, n2);
        } else {
            this.relayout = false;
            if (this.expandableControls != null) {
                this.expandableControls.setVisibility(GONE);
            }
            this.expandCollapseIcon.setVisibility(GONE);
            this.expandTxt.setVisibility(GONE);
            this.collapseTxt.setVisibility(GONE);
            this.textView.setMaxLines(Integer.MAX_VALUE);
            super.onMeasure(n, n2);
            if (this.textView.getLineCount() <= this.maxCollapsedLines) return;
            {
                this.textHeightWithMaxLines = ExpandableTextView.getRealTextViewHeight(this.textView);
                if (this.collapsed) {
                    this.textView.setMaxLines(this.maxCollapsedLines);
                }
                if (this.expandableControls != null) {
                    this.expandableControls.setVisibility(VISIBLE);
                }
                this.expandCollapseIcon.setVisibility(VISIBLE);
                if (this.collapsed) {
                    this.expandTxt.setVisibility(VISIBLE);
                } else {
                    this.collapseTxt.setVisibility(VISIBLE);
                }
                super.onMeasure(n, n2);
                if (!this.collapsed) return;
                {
                    this.textView.post(myRunnable);
                    this.collapsedHeight = this.getMeasuredHeight();
                }
            }
        }
    }

    private void init(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ExpandableTextView);
        this.maxCollapsedLines = typedArray.getInt(R.styleable.ExpandableTextView_maxCollapsedLines, MAX_COLLAPSED_LINES);
        this.animationDuration = typedArray.getInt(R.styleable.ExpandableTextView_animDuration, DEFAULT_ANIM_DURATION);
        this.animAlphaStart = typedArray.getFloat(R.styleable.ExpandableTextView_animAlphaStart, DEFAULT_ANIM_ALPHA_START);
        this.expandDrawable = typedArray.getDrawable(R.styleable.ExpandableTextView_expandDrawable);
        this.collapseDrawable = typedArray.getDrawable(R.styleable.ExpandableTextView_collapseDrawable);
        if (this.expandDrawable == null || this.collapseDrawable == null) {
            throw new RuntimeException("Provide both expand and collapse drawables");
        }
        typedArray.recycle();
    }

//    private void findViews() {
//        this.textView = (TextView) findViewById(R.id.expandable_text);
//        this.expandCollapseIcon = (ImageView) findViewById(R.id.ic_expand_collapse);
//        this.expandCollapseIcon.setImageDrawable(this.collapsed ? this.expandDrawable : this.collapseDrawable);
//        this.expandTxt = findViewById(R.id.expand_txt);
//        this.collapseTxt = findViewById(R.id.collapse_txt);
//        this.expandableControls = findViewById(R.id.expandable_controls);
//        setOnClickListener(this);
//        this.collapseTxt.setVisibility(GONE);
//    }

    public void setText(CharSequence text, SparseBooleanArray collapsedStatus, int position) {
        this.collapsedStatus = collapsedStatus;
        this.position = position;
        boolean isCollapsed = collapsedStatus.get(position, true);
        clearAnimation();
        this.collapsed = isCollapsed;
        this.expandCollapseIcon.setImageDrawable(this.collapsed ? this.expandDrawable : this.collapseDrawable);
        setText(text);
        getLayoutParams().height = -2;
        requestLayout();
    }

    public CharSequence getText() {
        if (this.textView == null) {
            return BuildConfig.FLAVOR;
        }
        return this.textView.getText();
    }

    public void setText(CharSequence text) {
        this.relayout = true;
        this.content = text;
        this.textView.setText(text);
        setVisibility(TextUtils.isEmpty(text) ? GONE : VISIBLE);
    }
}
