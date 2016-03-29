package kz.test.spravochnik;

import javax.inject.Singleton;

import dagger.Component;
import kz.test.spravochnik.data.DebugDataModule;
import kz.test.spravochnik.di.SpravochnikAppModule;
import kz.test.spravochnik.di.SpravochnikGraph;
import kz.test.spravochnik.ui.DebugUiModule;

@Singleton
@Component(modules = {
        SpravochnikAppModule.class,
        DebugDataModule.class,
        DebugUiModule.class
})
public interface SpravochnikComponent extends SpravochnikGraph {

    final class Initializer {
        static SpravochnikGraph init(SpravochnikApp app) {
            return DaggerSpravochnikComponent.builder().spravochnikAppModule(new SpravochnikAppModule(app)).build();
        }

        private Initializer() {
        }
    }
}
