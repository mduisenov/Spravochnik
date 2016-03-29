package kz.test.spravochnik.ui.main.cinema.adapter;


import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.squareup.otto.Bus;


import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import kz.test.spravochnik.R;
import kz.test.spravochnik.SpravochnikApp;
import kz.test.spravochnik.data.model.cinema.Cinema;
import kz.test.spravochnik.misc.BaseViewHolder;
import kz.test.spravochnik.ui.detail.DetailActivity;

public class CinemaViewHolder extends BaseViewHolder  {

    @Inject
    Bus mBus;

    @Bind({R.id.textViewPrimary})
    TextView mTextViewAddress;

    @Bind({R.id.textViewFullName})
    TextView mTextViewFullName;

    @Bind({R.id.textViewSecondary})
    TextView mTextViewCinemaName;

    @Bind({R.id.item})
    View mainVew;

    @Bind({R.id.order})
    TextView order;

    private Cinema mCinema;
    private Context mContext;

    public CinemaViewHolder(View itemView) {
        super(itemView);
        SpravochnikApp.get().component().inject(this);
    }

    public void bind(Context context, Cinema cinema) {
        mContext = context;
        mCinema = cinema;
        if(cinema.getStreet() != null){
            String cinemaAddress = String.format(mContext.getResources().getString(R.string.address),
                    cinema.getStreet(),
                    cinema.getBuilding());
            mTextViewAddress.setText(cinemaAddress);
        }else{
            mTextViewAddress.setText(cinema.getMall());
        }
        mTextViewFullName.setText(cinema.getFullName());

    }

    @OnClick({R.id.item})
    public void onCardClicked() {
        DetailActivity.startActivity(mContext, mCinema);
    }
}
