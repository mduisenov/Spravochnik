package kz.test.spravochnik.misc;

import android.annotation.TargetApi;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kz.test.spravochnik.R;

public class NetworkProgressView extends FrameLayout {
    private OnClickListener mOnRetryClickListener;
    @Bind({R.id.oopsMessage})
    TextView mTextViewOopsMessage;
    @Bind({R.id.oopsLayout})
    View oopsLayout;
    @Bind({R.id.progressLayout})
    View progressLayout;

    public NetworkProgressView(Context context) {
        super(context);
        inflate(getContext(), R.layout.layout_network_progress, this);
        ButterKnife.bind(this);
        setLayoutParams(generateDefaultLayoutParams());
    }

    public NetworkProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(getContext(), R.layout.layout_network_progress, this);
        ButterKnife.bind(this);
        setLayoutParams(generateDefaultLayoutParams());
    }

    public NetworkProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(getContext(), R.layout.layout_network_progress, this);
        ButterKnife.bind(this);
        setLayoutParams(generateDefaultLayoutParams());
    }

    @TargetApi(21)
    public NetworkProgressView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        inflate(getContext(), R.layout.layout_network_progress, this);
        ButterKnife.bind(this);
        setLayoutParams(generateDefaultLayoutParams());
    }

    public void retry() {
        this.oopsLayout.setVisibility(View.GONE);
        this.progressLayout.setVisibility(View.VISIBLE);
    }

    public void setOnRetryClickListener(OnClickListener onRetryClickListener) {
        this.mOnRetryClickListener = onRetryClickListener;
    }

    public void onError() {
        onError(null);
    }

    public void onError(String message) {
        if (!TextUtils.isEmpty(message)) {
            this.mTextViewOopsMessage.setText(message);
        }
        this.oopsLayout.setVisibility(View.VISIBLE);
        this.progressLayout.setVisibility(View.GONE);
    }

    public void onSuccess() {
        setVisibility(View.GONE);
    }

    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-1, -1);
    }

    @OnClick({R.id.buttonRetry})
    public void onRetryButtonClicked(View v) {
        if (this.mOnRetryClickListener != null) {
            this.mOnRetryClickListener.onClick(v);
        }
    }
}
