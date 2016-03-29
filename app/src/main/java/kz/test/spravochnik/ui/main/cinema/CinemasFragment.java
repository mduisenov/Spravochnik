package kz.test.spravochnik.ui.main.cinema;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import kz.test.spravochnik.R;
import kz.test.spravochnik.data.model.cinema.Cinema;
import kz.test.spravochnik.data.model.cinema.CinemaEvents;
import kz.test.spravochnik.ui.main.cinema.adapter.CinemaAdapter;
import kz.test.spravochnik.util.SimpleDividerItemDecoration;

public class CinemasFragment extends BaseCinemaFragment {

    private Adapter mAdapter;

    @Bind({R.id.recyclerView})
    RecyclerView mRecyclerView;

    private List<Cinema> cinemas;

    public CinemasFragment() {
        cinemas = new ArrayList();
    }

    public static Fragment newInstance() {
        return new CinemasFragment();
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
        getCinemas();
    }

    private void getCinemas() {
        cinemasController.getCinemas();
    }

    private void initViews() {
        mAdapter = new CinemaAdapter(this.cinemas, (AppCompatActivity) getContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(this.mAdapter);
    }

    @Subscribe
    public void onCinemaFetchedFailedEventListener(CinemaEvents.GetCinemaFailed event) {
        mNetworkProgressView.setOnRetryClickListener(new View.OnClickListener() {
            public void onClick(final View view) {
                getCinemas();
                mNetworkProgressView.retry();
            }
        });
        mNetworkProgressView.onError(event.getErrorDisplayText());
    }


    @Subscribe
    public void onCinemaFetchedSuccessEventListener(CinemaEvents.GetCinemaSuccess event) {
        mNetworkProgressView.onSuccess();
        cinemas.clear();
        cinemas.addAll(event.getCinemas());
        mAdapter.notifyDataSetChanged();
    }

}
