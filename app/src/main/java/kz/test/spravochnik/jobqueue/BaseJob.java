package kz.test.spravochnik.jobqueue;

import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;
import com.squareup.otto.Bus;

import javax.inject.Inject;

import kz.test.spravochnik.data.api.ApiException;
import kz.test.spravochnik.data.api.error.ApiError;
import timber.log.Timber;

public abstract class BaseJob extends Job {

    private static final int RETRY_LIMIT = 3;
    private ApiError apiError;
    private ApiException mApiException;

    @Inject
    Bus mBus;

    protected abstract void onInject();

    public BaseJob(Params params) {
        super(params);
    }

    public void onAdded() {
        onInject();
        Timber.d("%s onAdded()", getClass().getName());
    }

    protected void onCancel() {
        Timber.e("onCancel %s", getApiException());
    }

    protected boolean shouldReRunOnThrowable(Throwable throwable) {
        if (throwable instanceof ApiException) {
            mApiException = (ApiException) throwable;
            Timber.e("ApiException: " + mApiException);

            apiError = mApiException.getBody(ApiError.class);
            return mApiException.canRetry();
        }
        Timber.e(throwable, "Error in job");
//        Crashlytics.logException(throwable);
        return false;
    }

    protected int getRetryLimit() {
        return RETRY_LIMIT;
    }

    public ApiException getApiException() {
        return mApiException;
    }
}
