package kz.test.spravochnik.data;

import android.app.Application;
import android.content.SharedPreferences;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.path.android.jobqueue.JobManager;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.otto.Bus;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import kz.test.spravochnik.bus.MainThreadEventBus;
import kz.test.spravochnik.data.model.db.DatabaseHelper;
import kz.test.spravochnik.data.prefs.PreferenceWrapper;
import kz.test.spravochnik.jobqueue.MyJobManager;

@Module
public final class DataModule {
    static final int DISK_CACHE_SIZE = 52428800;

    static OkHttpClient createOkHttpClient(Application app) {
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(6, TimeUnit.SECONDS);
        client.setCache(new Cache(new File(app.getCacheDir(), "http"), DISK_CACHE_SIZE));
        return client;
    }

    @Singleton
    @Provides
    SharedPreferences provideSharedPreferences(Application app) {
        return app.getSharedPreferences("spravochnik", 0);
    }

    @Singleton
    @Provides
    JobManager provideJobManager(Application application) {
        return new MyJobManager(application);
    }

    @Singleton
    @Provides
    Bus provideEventBus() {
        return new MainThreadEventBus();
    }

    @Singleton
    @Provides
    public DatabaseHelper provideDatabaseHelper(Application application) {
        return OpenHelperManager.getHelper(application, DatabaseHelper.class);
    }
}
