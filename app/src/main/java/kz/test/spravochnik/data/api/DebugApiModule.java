package kz.test.spravochnik.data.api;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import kz.test.spravochnik.data.prefs.StringPreference;
import retrofit.Endpoint;
import retrofit.Endpoints;

@Module(includes = {ApiModule.class})
public final class DebugApiModule {
    @Singleton
    @Provides
    Endpoint provideEndpoint(@ApiEndpoint StringPreference apiEndpoint) {
        return Endpoints.newFixedEndpoint(apiEndpoint.get());
    }
}
