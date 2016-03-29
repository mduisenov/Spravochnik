package kz.test.spravochnik.util;

import com.google.android.gms.maps.model.LatLng;

import java.util.Arrays;
import java.util.List;

public class GeopisitionFormat {

    public static LatLng getLatLng(String geoposition){
        List<String> geopositions = Arrays.asList(geoposition.split(","));
        float lat = Float.parseFloat(geopositions.get(0));
        float lon = Float.parseFloat(geopositions.get(1));
        return new LatLng(lat,lon);
    }

    public static LatLng getCityCenterLatLng(){
        return new LatLng(51.1667, 71.4333);
    }
}
