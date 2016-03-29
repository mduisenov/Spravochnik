package kz.test.spravochnik.jobqueue;

import android.content.Context;

import com.path.android.jobqueue.JobManager;
import com.path.android.jobqueue.config.Configuration;
import com.path.android.jobqueue.log.CustomLogger;

public class MyJobManager extends JobManager {
    public MyJobManager(final Context context) {
        super(context, new Configuration.Builder(context).customLogger(new CustomLogger() {
            @Override
            public void d(final String s, final Object... array) {
            }

            @Override
            public void e(final String s, final Object... array) {
            }

            @Override
            public void e(final Throwable t, final String s, final Object... array) {
            }

            @Override
            public boolean isDebugEnabled() {
                return false;
            }
        }).minConsumerCount(1).maxConsumerCount(7).loadFactor(3).consumerKeepAlive(15).build());
    }
}
