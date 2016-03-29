package kz.test.spravochnik.data.model.cinema;

import java.util.List;

import kz.test.spravochnik.bus.ApiFailureEvent;
import kz.test.spravochnik.data.api.ApiException;

public class CinemaEvents {

    public static class GetCinemaSuccess {

        private final List<Cinema> cinemas;

        public GetCinemaSuccess(List<Cinema> cinemas) {
            this.cinemas = cinemas;
        }

        public List<Cinema> getCinemas() {
            return this.cinemas;
        }
    }

    public static class GetCinemaFailed extends ApiFailureEvent {
        public GetCinemaFailed(ApiException apiException) {
            super(apiException);
        }
    }
}
