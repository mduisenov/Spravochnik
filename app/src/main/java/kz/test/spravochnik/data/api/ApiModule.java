package kz.test.spravochnik.data.api;

import com.squareup.okhttp.OkHttpClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import kz.test.spravochnik.data.api.service.Services;
import retrofit.Endpoint;
import retrofit.ErrorHandler;
import retrofit.RestAdapter;
import retrofit.RestAdapter.Builder;
import retrofit.client.Client;
import retrofit.client.OkClient;

@Module
public final class ApiModule {

    public static final String PRODUCTION_API_URL = "http://data.egov.kz";

    @Singleton
    @Provides
    Client provideClient(OkHttpClient client) {
        return new OkClient(client);
    }

    @Singleton
    @Provides
    RestAdapter provideRestAdapter(ErrorHandler errorHandler, Endpoint endpoint, Client client) {
        return new Builder().setClient(client).setErrorHandler(errorHandler).setEndpoint(endpoint).build();
    }

    @Provides
    ErrorHandler provideErrorHandler() {
        return new RetrofitErrorHandler();
    }

    @Singleton
    @Provides
    Services provideContentService(RestAdapter restAdapter) {
        return restAdapter.create(Services.class);
    }

}
