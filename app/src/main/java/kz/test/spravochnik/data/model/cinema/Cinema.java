package kz.test.spravochnik.data.model.cinema;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

import kz.test.spravochnik.data.model.Columns;
import kz.test.spravochnik.data.model.db.DatabaseHelper;

@DatabaseTable(tableName = DatabaseHelper.Tables.CINEMAS)
public class Cinema extends kz.test.spravochnik.data.model.Building implements Serializable {

    @DatabaseField(columnName = "website")
    @SerializedName("Website")
    private String website;

    @DatabaseField(columnName = "mall")
    @SerializedName("Mall")
    private String mall;

    @DatabaseField(columnName = "avenue")
    @SerializedName("Avenue")
    private String avenue;

    @DatabaseField(columnName = Columns.CINEMA_ID, id = true)
    @SerializedName("ID")
    private int ID;

    public Cinema() {
    }

    public String getWebsite() {
        return website;
    }

    public String getMall() {
        return mall;
    }

    public String getAvenue() {
        return avenue;
    }

    public int getID() {
        return ID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cinema cinema = (Cinema) o;

        if (ID != cinema.ID) return false;
        if (website != null ? !website.equals(cinema.website) : cinema.website != null)
            return false;
        if (mall != null ? !mall.equals(cinema.mall) : cinema.mall != null) return false;
        return avenue != null ? avenue.equals(cinema.avenue) : cinema.avenue == null;

    }

    @Override
    public int hashCode() {
        int result = website != null ? website.hashCode() : 0;
        result = 31 * result + (mall != null ? mall.hashCode() : 0);
        result = 31 * result + (avenue != null ? avenue.hashCode() : 0);
        result = 31 * result + ID;
        return result;
    }

    @Override
    public String toString() {
        return "Cinema{" +
                "website='" + website + '\'' +
                ", mall='" + mall + '\'' +
                ", avenue='" + avenue + '\'' +
                ", ID=" + ID +
                '}';
    }
}
