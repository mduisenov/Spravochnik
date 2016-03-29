package kz.test.spravochnik.ui.main.map;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import kz.test.spravochnik.R;
import kz.test.spravochnik.util.GeopisitionFormat;
import kz.test.spravochnik.data.model.cinema.Cinema;
import kz.test.spravochnik.data.model.cinema.CinemaEvents;
import kz.test.spravochnik.ui.detail.DetailActivity;
import kz.test.spravochnik.ui.main.cinema.BaseCinemaFragment;

public class CinemaMapFragment extends BaseCinemaFragment implements GoogleMap.OnMarkerClickListener {

    private GoogleMap map;
    private MapView mapView;
    private Bundle mapBundle;
    private List<Cinema> cinemas = new ArrayList<>();

    public CinemaMapFragment() {
    }

    public static Fragment newInstance() {
        return new CinemaMapFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mapBundle = savedInstanceState;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        MapsInitializer.initialize(getActivity());
        mapView = (MapView) rootView.findViewById(R.id.mapcontainer);
        mapView.onCreate(mapBundle);
        map = mapView.getMap();
        return rootView;
    }
    @Override
    public void onResume(){
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause(){
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        mapView.onDestroy();
    }

    private void setCinemasOnMap(List<Cinema> cinemas) {
        for (int i = 0; i < cinemas.size(); i++) {
            MarkerOptions markerOptions = new MarkerOptions().anchor(0.4f, 0.9f)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_film_marker))
                    .position(GeopisitionFormat.getLatLng(cinemas.get(i).getGeoposition()))
                    .title(String.valueOf(i));
            map.addMarker(markerOptions);

        }

        map.setOnMarkerClickListener(this);
    }

    private void moveToCityCenter() {
        // Move the camera instantly to astana with a zoom of 12.
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(GeopisitionFormat.getCityCenterLatLng(), 12));
    }

    private void getCinemas() {
        this.cinemasController.getCinemas();
    }

    @Subscribe
    public void onCinemaFetchedFailedEventListener(CinemaEvents.GetCinemaFailed event) {
        this.mNetworkProgressView.setOnRetryClickListener(new View.OnClickListener() {
            public void onClick(final View view) {
                getCinemas();
                mNetworkProgressView.retry();
            }
        });
        mNetworkProgressView.onError(event.getErrorDisplayText());
    }

    @Subscribe
    public void onCinemaFetchedSuccessEventListener(CinemaEvents.GetCinemaSuccess event) {
        mNetworkProgressView.onSuccess();
        setCinemasOnMap(event.getCinemas());
        moveToCityCenter();
        cinemas = event.getCinemas();
    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        int markerId = Integer.valueOf(marker.getTitle());
        DetailActivity.startActivity(getActivity(), cinemas.get(markerId));
        return true;
    }
}
