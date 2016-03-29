package kz.test.spravochnik.ui.main.hospital.adapter;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import kz.test.spravochnik.R;
import kz.test.spravochnik.data.model.hospital.Hospital;

public class HospitalAdapter extends Adapter<HospitalViewHolder> {
    private final AppCompatActivity mContext;
    private final List<Hospital> hospitals;

    public HospitalAdapter(List<Hospital> hospitals, AppCompatActivity context) {
        this.hospitals = hospitals;
        this.mContext = context;
    }

    public HospitalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HospitalViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.recycler_item, parent, false));
    }

    public void onBindViewHolder(HospitalViewHolder h, int position) {
        Hospital hospital = this.hospitals.get(position);
        h.bind(mContext, hospital);
        h.mTextViewHospitalName.setText(hospital.getFullName());
    }

    public int getItemCount() {
        return this.hospitals.size();
    }
}
