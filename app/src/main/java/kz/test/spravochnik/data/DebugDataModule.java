package kz.test.spravochnik.data;

import android.app.Application;
import android.content.SharedPreferences;
import com.squareup.okhttp.OkHttpClient;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.inject.Singleton;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import dagger.Module;
import dagger.Provides;
import kz.test.spravochnik.data.api.ApiEndpoint;
import kz.test.spravochnik.data.api.ApiEndpoints;
import kz.test.spravochnik.data.api.DebugApiModule;
import kz.test.spravochnik.data.prefs.StringPreference;

@Module(includes = {DataModule.class, DebugApiModule.class})
public final class DebugDataModule {

    static class X509TrustManagerImpl implements X509TrustManager {
        X509TrustManagerImpl() {
        }

        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }

    @Singleton
    @ApiEndpoint
    @Provides
    StringPreference provideEndpointPreference(SharedPreferences preferences) {
        return new StringPreference(preferences, "debug_endpoint", ApiEndpoints.PRODUCTION.url);
    }

    private static SSLSocketFactory createBadSslSocketFactory() {
        try {
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new TrustManager[]{new X509TrustManagerImpl()}, null);
            return context.getSocketFactory();
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }

    @Singleton
    @Provides
    OkHttpClient provideOkHttpClient(Application app) {
        OkHttpClient client = DataModule.createOkHttpClient(app);
        client.setSslSocketFactory(createBadSslSocketFactory());
        return client;
    }

}
