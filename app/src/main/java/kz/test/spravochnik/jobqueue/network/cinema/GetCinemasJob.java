package kz.test.spravochnik.jobqueue.network.cinema;

import java.util.List;

import kz.test.spravochnik.controllers.cinema.CinemasController;
import kz.test.spravochnik.data.model.cinema.Cinema;
import timber.log.Timber;

public class GetCinemasJob extends BaseCinemaJob {

    private final CinemasController cinemasController;

    public GetCinemasJob(CinemasController cinemasController) {
        this.cinemasController = cinemasController;
    }

    public void onRun() throws Throwable {
        Timber.d("onRun() ");
        List<Cinema> cinemas;
        cinemas = services.getCinemas();

        if (cinemas != null) {
            for (Cinema cinema : cinemas) {
                Timber.d(cinema.toString());
                databaseHelper.getCinemaDao().createOrUpdate(cinema);
            }
        }
        cinemasController.handleGetCinemasSuccess(cinemas);
    }

    protected void onCancel() {
        super.onCancel();
        Timber.d("onCancel()");
        cinemasController.handleGetCinemaError(getApiException());

    }
}
