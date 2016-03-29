package kz.test.spravochnik.ui.main.map;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;

import kz.test.spravochnik.ui.BaseFragment;

public abstract class BaseMapFragment extends BaseFragment implements OnMapReadyCallback {


    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private boolean hasGMS = true;
    private MapView mapView;

    public boolean hasGMS() {
        return hasGMS;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        hasGMS = checkPlayServices();
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(getActivity());
        if(result != ConnectionResult.SUCCESS) {
            if(googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(getActivity(), result,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            }

            return false;
        }

        return true;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (this.mapView != null && hasGMS) {
            this.mapView.onDestroy();
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (hasGMS) {
            mapView.onLowMemory();
        }
    }

    public void onMapViewCreated(MapView mapView, Bundle bundle) {
        this.mapView = mapView;
        if (hasGMS) {
            mapView.onCreate(null);
            MapsInitializer.initialize(getActivity());
            mapView.getMapAsync(this);
        }
    }

    @Override
    public void onPause() {
        if (this.mapView != null && hasGMS) {
            this.mapView.onPause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        if (mapView != null && hasGMS) {
            mapView.onResume();
        }
        super.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        if (mapView != null && hasGMS) {
            mapView.onSaveInstanceState(bundle);
        }
        super.onSaveInstanceState(bundle);
    }

    protected void setMapImmutableMode(final GoogleMap googleMap) {
        final UiSettings uiSettings = googleMap.getUiSettings();
        uiSettings.setAllGesturesEnabled(false);
        uiSettings.setCompassEnabled(false);
        uiSettings.setMapToolbarEnabled(false);
        uiSettings.setMyLocationButtonEnabled(false);
        uiSettings.setZoomControlsEnabled(false);
    }
}
