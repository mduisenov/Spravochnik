package kz.test.spravochnik.ui.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.jetbrains.annotations.NotNull;

import butterknife.Bind;
import kz.test.spravochnik.R;
import kz.test.spravochnik.SpravochnikApp;
import kz.test.spravochnik.util.GeopisitionFormat;
import kz.test.spravochnik.common.view.MonkeySafeClickListener;
import kz.test.spravochnik.data.model.Building;
import kz.test.spravochnik.data.model.cinema.Cinema;
import kz.test.spravochnik.data.model.hospital.Hospital;
import kz.test.spravochnik.helper.UiHelper;
import kz.test.spravochnik.ui.main.map.BaseMapFragment;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link } interface
 * to handle interaction events.
 * Use the {@link DetailGeneralFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailGeneralFragment extends BaseMapFragment {

    public static final float GOOD_LOOKING_ZOOM_LEVEL = 16.0f;

    private Building building;

    @Bind(R.id.map_container)
    FrameLayout mapContainerFL;

    @Bind(R.id.map_clickable)
    View mapClickableAreaView;

    @Bind(R.id.map_placeholder)
    View mapPlaceView;

    @Bind(R.id.building_address)
    TextView buildingAddressTxt;

    @Bind(R.id.building_location)
    TextView buildingLocationTxt;

    @Bind(R.id.building_phone)
    TextView buildingPhoneTxt;

    @Bind(R.id.manager_header)
    TextView managerHeaderTxt;

    @Bind(R.id.manager_container)
    RelativeLayout managerContainerRL;

    @Bind(R.id.manager_name)
    TextView managerNameTxt;

    @Bind(R.id.manager_phone)
    TextView managerPhoneTxt;

    @Bind(R.id.ic_building_grey)
    ImageView buildingIcon;

    @Bind(R.id.building_full_name)
    TextView buildingFullName;

    @Override
    protected void onInject() {
        SpravochnikApp.get().component().inject(this);
    }

    public DetailGeneralFragment() {
        // Required empty public constructor
    }

    public static DetailGeneralFragment newInstance() {
        return new DetailGeneralFragment();
    }

    @NotNull
    private MapView createMapView() {
        GoogleMapOptions googleMapOptions = new GoogleMapOptions();
        googleMapOptions.camera(CameraPosition.fromLatLngZoom(getPosition(), GOOD_LOOKING_ZOOM_LEVEL));
        googleMapOptions.liteMode(true);
        googleMapOptions.mapToolbarEnabled(false);
        return new MapView(getActivity(), googleMapOptions);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_general, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            initDatas();
            fillCaseUI();
            fillCommonUI();
        }
        setUpMapView(savedInstanceState);
    }

    private void fillCaseUI() {
        if(building instanceof Hospital){
            managerContainerRL.setVisibility(View.VISIBLE);
            managerHeaderTxt.setVisibility(View.VISIBLE);
            String managerName = String.format(getActivity().getResources().getString(R.string.manager_name_position),
                    ((Hospital)building).getManagerName(),
                    ((Hospital)building).getManagerPosition());
            managerNameTxt.setText(managerName);
            managerPhoneTxt.setText(((Hospital)building).getManagerPhone());
            buildingIcon.setBackgroundResource(R.drawable.ic_hospital_building_default);
            UiHelper.setTitle(getActivity(), R.string.hospital);
        }else {
            managerContainerRL.setVisibility(View.GONE);
            managerHeaderTxt.setVisibility(View.GONE);
            buildingIcon.setBackgroundResource(R.drawable.ic_film_default);
            UiHelper.setTitle(getActivity(), R.string.cinema);
        }
    }

    private void fillCommonUI() {
        if(building.getStreet() == null){
            buildingAddressTxt.setText(((Cinema)building).getMall());
        }else{
            String address = String.format(getActivity().getResources().getString(R.string.address),
                    building.getStreet(),
                    building.getBuilding());

            buildingAddressTxt.setText(address);
        }
        buildingLocationTxt.setText(building.getCity());
        buildingPhoneTxt.setText(building.getPhone().replace(",","\n").replace(" ",""));
        buildingFullName.setText(building.getFullName());

    }

    private void initDatas() {
        if (getArguments().getSerializable(DetailActivity.EXTRA_BUILDING) != null) {
            building = (Building) getArguments().getSerializable(
                    DetailActivity.EXTRA_BUILDING);
        }
    }

    private void setUpMapView(Bundle bundle) {
        if (hasGMS()) {
            final MapView mapView = createMapView();
            mapContainerFL.addView(mapView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            onMapViewCreated(mapView, bundle);
            mapClickableAreaView.setOnClickListener(new MonkeySafeClickListener() {

                @Override
                public void onSafeClick(View view) {
                       // TODO show full screen map fragment
                }
            });
            return;
        }
        mapPlaceView.setVisibility(View.GONE);
    }

    @NotNull
    private LatLng getPosition() {
        return GeopisitionFormat.getLatLng(building.getGeoposition());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        setMapImmutableMode(googleMap);
        googleMap.addMarker(new MarkerOptions()
                .anchor(0.4f, 0.9f)
                .icon(BitmapDescriptorFactory.fromResource(getMarker()))
                .position(getPosition()));
    }

    private int getMarker(){
        if(building instanceof Hospital){
            return R.drawable.ic_map_hospital_marker;
        }
        else {
            return R.drawable.ic_map_film_marker;
        }
    }

}