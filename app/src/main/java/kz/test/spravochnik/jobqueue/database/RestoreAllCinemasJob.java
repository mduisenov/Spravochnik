package kz.test.spravochnik.jobqueue.database;

import java.util.List;
import kz.test.spravochnik.controllers.cinema.CinemasController;
import kz.test.spravochnik.data.model.Columns;
import kz.test.spravochnik.data.model.cinema.Cinema;
import timber.log.Timber;

public class RestoreAllCinemasJob extends BaseDBJob {

    private CinemasController cinemasController;

    public RestoreAllCinemasJob(CinemasController cinemasController) {
        this.cinemasController = cinemasController;
        Timber.tag("RestoreAllCinemasJob");
    }

    @Override
    public void onRun() throws Throwable {
        Timber.d("RestoreAllCinemasJob.onRun...");

        List<Cinema> cinemas = databaseHelper.getCinemaDao().queryBuilder().orderBy(Columns.CINEMA_ID, false).query(); //
        cinemasController.restoreCinemas(cinemas);
//        eventBus.post(new StoredAllCinemasEvent(list, cached));

    }

    @Override
    protected void onCancel() {
        Timber.d("RestoreAllCinemasJob.onCancel...");
    }


}
