package kz.test.spravochnik.data.model;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

public class Building implements Serializable {

    public String getPhone() {
        return phone;
    }

    public String getGeoposition() {
        return geoposition;
    }

    public String getStreet() {
        return street;
    }

    public String getFullName() {
        return fullName;
    }

    public String getBuilding() {
        return building;
    }

    public String getCity() {
        return city;
    }

    @DatabaseField(columnName = "phone")
    @SerializedName("Phone")
    protected String phone;

    @DatabaseField(columnName = "geoposition")
    @SerializedName("Geoposition")
    protected String geoposition;

    @DatabaseField(columnName = "street")
    @SerializedName("Street")
    protected String street;

    @DatabaseField(columnName = "fullName")
    @SerializedName("FullName")
    protected String fullName;

    @DatabaseField(columnName = "building")
    @SerializedName("Building")
    protected String building;

    @DatabaseField(columnName = "city")
    @SerializedName("City")
    protected String city;

}
