package kz.test.spravochnik.data.model.hospital;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

import kz.test.spravochnik.data.model.Columns;
import kz.test.spravochnik.data.model.db.DatabaseHelper;

@DatabaseTable(tableName = DatabaseHelper.Tables.HOSPITALS)
public class Hospital extends kz.test.spravochnik.data.model.Building implements Serializable {

    @DatabaseField(columnName = "managerName")
    @SerializedName("ManagerName")
    private String managerName;

    @DatabaseField(columnName = "managerPosition")
    @SerializedName("ManagerPosition")
    private String managerPosition;

    @DatabaseField(columnName = "cityDistrict")
    @SerializedName("CityDistrict")
    private String cityDistrict;

    @DatabaseField(columnName = "managerPhone")
    @SerializedName("ManagerPhone")
    private String managerPhone;

    @DatabaseField(columnName = Columns.HOSPITAL_ID, id = true)
    @SerializedName("ID")
    private int ID;

    public Hospital() {
    }

    public String getManagerName() {
        return managerName;
    }

    public String getManagerPosition() {
        return managerPosition;
    }

    public String getCityDistrict() {
        return cityDistrict;
    }

    public String getManagerPhone() {
        return managerPhone;
    }

    public int getID() {
        return ID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Hospital hospital = (Hospital) o;

        if (ID != hospital.ID) return false;
        if (managerName != null ? !managerName.equals(hospital.managerName) : hospital.managerName != null)
            return false;
        if (managerPosition != null ? !managerPosition.equals(hospital.managerPosition) : hospital.managerPosition != null)
            return false;
        if (cityDistrict != null ? !cityDistrict.equals(hospital.cityDistrict) : hospital.cityDistrict != null)
            return false;
        return managerPhone != null ? managerPhone.equals(hospital.managerPhone) : hospital.managerPhone == null;

    }

    @Override
    public int hashCode() {
        int result = managerName != null ? managerName.hashCode() : 0;
        result = 31 * result + (managerPosition != null ? managerPosition.hashCode() : 0);
        result = 31 * result + (cityDistrict != null ? cityDistrict.hashCode() : 0);
        result = 31 * result + (managerPhone != null ? managerPhone.hashCode() : 0);
        result = 31 * result + ID;
        return result;
    }

    @Override
    public String toString() {
        return "Hospital{" +
                "managerName='" + managerName + '\'' +
                ", managerPosition='" + managerPosition + '\'' +
                ", cityDistrict='" + cityDistrict + '\'' +
                ", managerPhone='" + managerPhone + '\'' +
                ", ID=" + ID +
                '}';
    }
}
