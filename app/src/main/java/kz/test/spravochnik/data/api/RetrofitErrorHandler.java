package kz.test.spravochnik.data.api;

import retrofit.ErrorHandler;
import retrofit.RetrofitError;

public class RetrofitErrorHandler implements ErrorHandler {
    public Throwable handleError(RetrofitError error) {
        return ApiException.convert(error);
    }
}
