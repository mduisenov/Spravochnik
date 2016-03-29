package kz.test.spravochnik;

import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;
import javax.inject.Inject;

import hugo.weaving.DebugLog;
import kz.test.spravochnik.di.SpravochnikGraph;
import kz.test.spravochnik.ui.ActivityHierarchyServer;
import timber.log.Timber;

public class SpravochnikApp extends MultiDexApplication {

    private static SpravochnikApp spravochnikApp;

    @Inject
    ActivityHierarchyServer activityHierarchyServer;
    private SpravochnikGraph component;

    public static SpravochnikApp get() {
        return spravochnikApp;
    }

    public void onCreate() {
        super.onCreate();

        if(BuildConfig.DEBUG){
            Timber.plant(new Timber.DebugTree());
        }else{
            // TODO: Enable any crash report system here. Ether Mint or Crashlytics for example
            Fabric.with(this, new Crashlytics());
        }

        MultiDex.install(getApplicationContext());
        spravochnikApp = this;
        buildComponentAndInject();
        registerActivityLifecycleCallbacks(activityHierarchyServer);
   }

    @DebugLog
    public void buildComponentAndInject() {
        component = SpravochnikComponent.Initializer.init(this);
        component.inject(this);
    }

    public SpravochnikGraph component() {
        return component;
    }
}
