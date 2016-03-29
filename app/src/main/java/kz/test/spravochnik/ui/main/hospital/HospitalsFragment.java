package kz.test.spravochnik.ui.main.hospital;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import kz.test.spravochnik.R;
import kz.test.spravochnik.data.model.hospital.Hospital;
import kz.test.spravochnik.data.model.hospital.HospitalEvents;
import kz.test.spravochnik.ui.main.hospital.adapter.HospitalAdapter;
import kz.test.spravochnik.util.SimpleDividerItemDecoration;

public class HospitalsFragment extends BaseHospitalFragment {

    private Adapter mAdapter;

    @Bind({R.id.recyclerView})
    RecyclerView mRecyclerView;

    private List<Hospital> hospitals;

    public HospitalsFragment() {
        hospitals = new ArrayList();
    }

    public static Fragment newInstance() {
        return new HospitalsFragment();
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.recycler_view_fragment, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    public void onResume() {
        super.onResume();
        getHospitals();
    }

    private void getHospitals() {
        hospitalController.getHospitals();
    }

    private void initViews() {
        mAdapter = new HospitalAdapter(hospitals, (AppCompatActivity) getContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void showMessage(String text) {
    }

    @Subscribe
    public void onHospitalFetchedFailedEventListener(HospitalEvents.GetHospitalFailed event) {
        Log.d("MTEST","gethospitalfailed event");
        mNetworkProgressView.setOnRetryClickListener(new View.OnClickListener() {
            public void onClick(final View view) {
                getHospitals();
                mNetworkProgressView.retry();
            }
        });
        mNetworkProgressView.onError(event.getErrorDisplayText());
    }

    @Subscribe
    public void onHospitalFetchedSuccessEventListener(HospitalEvents.GetHospitalSuccess event) {
        mNetworkProgressView.onSuccess();
        hospitals.clear();
        hospitals.addAll(event.getHospitals());
        mAdapter.notifyDataSetChanged();
    }

}
