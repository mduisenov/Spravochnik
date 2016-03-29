package kz.test.spravochnik.data.api;

import kz.test.spravochnik.data.api.error.ApiError;
import retrofit.RetrofitError;
import retrofit.RetrofitError.Kind;

public class ApiException extends RuntimeException {
    private final RetrofitError retrofitError;

    ApiException(RetrofitError retrofitError) {
        super(createExceptionMessage(retrofitError));
        setStackTrace(retrofitError.getStackTrace());
        this.retrofitError = retrofitError;
    }

    private static String createExceptionMessage(RetrofitError retrofitError) {
        if (retrofitError.getMessage() != null) {
            return retrofitError.getMessage();
        }
        if (retrofitError.getResponse() != null) {
            return "Status: " + retrofitError.getResponse().getStatus();
        }
        return "unknown error";
    }

    private int createExceptionStatus(RetrofitError retrofitError) {
        if (retrofitError.getResponse() != null) {
            return retrofitError.getResponse().getStatus();
        }
        return 0;
    }

    public boolean canRetry() {
        if (this.retrofitError.getResponse() == null) {
            return true;
        }
        int status = this.retrofitError.getResponse().getStatus();
        Kind kind = this.retrofitError.getKind();
        return status > 499 || kind == Kind.UNEXPECTED || kind == Kind.NETWORK;
    }

    public <T> T getBody(Class<T> t) {
        return (T) this.retrofitError.getBodyAs(t);
    }

    public ApiError getApiError() {
        return (ApiError) this.retrofitError.getBodyAs(ApiError.class);
    }

    public RetrofitError getRetrofitError() {
        return this.retrofitError;
    }

    public static ApiException convert(RetrofitError retrofitError) {
        return new ApiException(retrofitError);
    }
}
