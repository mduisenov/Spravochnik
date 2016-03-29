package kz.test.spravochnik.data.api.service;

import java.util.List;

import kz.test.spravochnik.data.model.cinema.Cinema;
import kz.test.spravochnik.data.model.hospital.Hospital;
import retrofit.http.GET;

public interface Services {

    @GET("/api/v2/hospitals/data")
    List<Hospital> getHospitals();

    @GET("/api/v2/cinema/data")
    List<Cinema> getCinemas();
}
