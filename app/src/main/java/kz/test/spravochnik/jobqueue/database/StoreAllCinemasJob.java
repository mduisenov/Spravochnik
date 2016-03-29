package kz.test.spravochnik.jobqueue.database;


import java.util.List;

import kz.test.spravochnik.controllers.cinema.CinemasController;
import kz.test.spravochnik.data.model.cinema.Cinema;
import timber.log.Timber;

public class StoreAllCinemasJob extends BaseDBJob {

    private List<Cinema> cinemas;
    private CinemasController cinemasController;

    public StoreAllCinemasJob(List<Cinema> cinemas, CinemasController cinemasController) {
        this.cinemas = cinemas;
        this.cinemasController=cinemasController;
        Timber.tag("StoreAllCinemasJob");
    }

    @Override
    public void onAdded() {
        Timber.d("StoreAllCinemasJob.onAdded...");
    }

    @Override
    public void onRun() throws Throwable {
        boolean cached = false;
        Timber.d("StoreAllCinemasJob.onRun...");
        // TODO databaseHelper == null
        Timber.d("StoreAllCinemasJob.onRun... databaseHelper "+databaseHelper);
        if (cinemas != null) {
            for (Cinema cinema : cinemas) {
                databaseHelper.getCinemaDao().createOrUpdate(cinema);
            }
        }

//        cinemas = databaseHelper.getCinemaDao().queryBuilder().orderBy(Columns.CINEMA_ID, false).query();

//        eventBus.post(new StoredAllCinemasEvent(list, cached));

    }

    @Override
    protected void onCancel() {
        Timber.d("StoreAllCinemasJob.onCancel...");
    }

    @Override
    protected boolean shouldReRunOnThrowable(Throwable throwable) {
        Timber.d("StoreAllCinemasJob.shouldReRunOnThrowable...");
        Timber.e(throwable, "CRASH!");
        return false;
    }


}
