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

import java.util.List;

import kz.test.spravochnik.R;
import kz.test.spravochnik.util.GeopisitionFormat;
import kz.test.spravochnik.data.model.hospital.Hospital;
import kz.test.spravochnik.data.model.hospital.HospitalEvents;
import kz.test.spravochnik.ui.detail.DetailActivity;
import kz.test.spravochnik.ui.main.hospital.BaseHospitalFragment;

public class HospitalMapFragment extends BaseHospitalFragment implements GoogleMap.OnMarkerClickListener {

    private GoogleMap map;
    private MapView mapView;
    private Bundle mapBundle;
    private List<Hospital> hospitals;

    public HospitalMapFragment() {
    }

    public static Fragment newInstance() {
        return new HospitalMapFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
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
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    private void fetchHospitals() {
        hospitalController.getHospitals();
    }

    @Subscribe
    public void onHospitalFetchedFailedEventListener(HospitalEvents.GetHospitalFailed event) {
        mNetworkProgressView.setOnRetryClickListener(new View.OnClickListener() {
            public void onClick(final View view) {
                fetchHospitals();
                mNetworkProgressView.retry();
            }
        });
        mNetworkProgressView.onError(event.getErrorDisplayText());
    }

    @Subscribe
    public void onHospitalFetchedSuccessEventListener(HospitalEvents.GetHospitalSuccess event) {
        mNetworkProgressView.onSuccess();
        setDataOnMap(event.getHospitals());
        moveToCityCenter();
        hospitals = event.getHospitals();
    }

    private void setDataOnMap(List<Hospital> hospitals) {
        for (int i = 0; i < hospitals.size(); i++) {
            map.addMarker(new MarkerOptions().anchor(0.4f, 0.9f)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_hospital_marker))
                    .position(GeopisitionFormat.getLatLng(hospitals.get(i).getGeoposition()))
                    .title(String.valueOf(i)));
        }
        map.setOnMarkerClickListener(this);
    }

    private void moveToCityCenter() {
        // Move the camera instantly to astana with a zoom of 12.
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(GeopisitionFormat.getCityCenterLatLng(), 12));
    }
    @Override
    public boolean onMarkerClick(Marker marker) {
        int markerId = Integer.valueOf(marker.getTitle());
        DetailActivity.startActivity(getActivity(), hospitals.get(markerId));
        return true;
    }
}
