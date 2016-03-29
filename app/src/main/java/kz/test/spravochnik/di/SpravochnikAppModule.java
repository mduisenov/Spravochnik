package kz.test.spravochnik.di;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import kz.test.spravochnik.SpravochnikApp;
import kz.test.spravochnik.controllers.ControllerModule;

@Module(includes = {ControllerModule.class})

public final class SpravochnikAppModule {

    private final SpravochnikApp app;

    public SpravochnikAppModule(SpravochnikApp app) {
        this.app = app;
    }

    @Singleton
    @Provides
    Application provideApplication() {
        return this.app;
    }
}
