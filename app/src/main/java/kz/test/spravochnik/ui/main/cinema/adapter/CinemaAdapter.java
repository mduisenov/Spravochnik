package kz.test.spravochnik.ui.main.cinema.adapter;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import kz.test.spravochnik.R;
import kz.test.spravochnik.data.model.cinema.Cinema;

public class CinemaAdapter extends Adapter<CinemaViewHolder> {

    private final AppCompatActivity mContext;
    private final List<Cinema> cinemas;

    public CinemaAdapter(List<Cinema> cinemas, AppCompatActivity context) {
        this.cinemas = cinemas;
        this.mContext = context;
    }

    public CinemaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CinemaViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.recycler_item, parent, false));
    }

    public void onBindViewHolder(CinemaViewHolder h, int position) {

        Cinema cinema = this.cinemas.get(position);
        h.bind(this.mContext,cinema);
        h.mTextViewCinemaName.setText(cinema.getFullName());
    }


    public int getItemCount() {
        return this.cinemas.size();
    }
}
