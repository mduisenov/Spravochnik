package kz.test.spravochnik.ui;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DebugUiModule {
    @Singleton
    @Provides
    AppContainer provideAppContainer() {
        return AppContainer.DEFAULT;
    }

    @Singleton
    @Provides
    ActivityHierarchyServer provideActivityHierarchyServer() {
        return ActivityHierarchyServer.NONE;
    }
}
