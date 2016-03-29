package kz.test.spravochnik.jobqueue.network.cinema;

import com.path.android.jobqueue.Params;

import javax.inject.Inject;

import kz.test.spravochnik.SpravochnikApp;
import kz.test.spravochnik.data.api.service.Services;
import kz.test.spravochnik.data.model.db.DatabaseHelper;
import kz.test.spravochnik.jobqueue.BaseJob;
import kz.test.spravochnik.jobqueue.Groups;
import kz.test.spravochnik.jobqueue.Priority;

public abstract class BaseCinemaJob extends BaseJob {

    @Inject
    Services services;

    @Inject
    DatabaseHelper databaseHelper;

    protected void onInject() {
        SpravochnikApp.get().component().inject(this);
    }

    public BaseCinemaJob() {
        super(new Params(Priority.UX_HIGH).setGroupId(Groups.DATABASE_JOB));
    }

}
