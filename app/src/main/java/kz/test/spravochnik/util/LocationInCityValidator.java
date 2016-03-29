/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.location.Location
 */
package kz.test.spravochnik.util;

import android.location.Location;

public class LocationInCityValidator {
    private static final int MAX_DISTANCE_FROM_CENTER_IN_METRES = 50000;
    private final Location mCityCenter;

    public LocationInCityValidator(Location location) {
        this.mCityCenter = location;
    }

    public boolean check(Location location) {
        return this.mCityCenter.distanceTo(location) < MAX_DISTANCE_FROM_CENTER_IN_METRES;
    }
}

