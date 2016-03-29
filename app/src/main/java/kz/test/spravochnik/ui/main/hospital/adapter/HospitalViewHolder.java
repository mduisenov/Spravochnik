package kz.test.spravochnik.ui.main.hospital.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.squareup.otto.Bus;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import kz.test.spravochnik.R;
import kz.test.spravochnik.SpravochnikApp;
import kz.test.spravochnik.data.model.hospital.Hospital;
import kz.test.spravochnik.misc.BaseViewHolder;
import kz.test.spravochnik.ui.detail.DetailActivity;

public class HospitalViewHolder extends BaseViewHolder implements OnClickListener {

    @Inject
    Bus mBus;

    @Bind({R.id.textViewPrimary})
    TextView mTextViewAddress;

    @Bind({R.id.textViewFullName})
    TextView mTextViewFullName;

    @Bind({R.id.textViewSecondary})
    TextView mTextViewHospitalName;

    @Bind({R.id.item})
    View mainVew;

    @Bind({R.id.order})
    TextView order;

    private Context mContext;
    private Hospital mHospital;

    public HospitalViewHolder(View itemView) {
        super(itemView);
        SpravochnikApp.get().component().inject(this);
    }

    public void bind(AppCompatActivity context, Hospital hospital) {
        mContext = context;
        mHospital = hospital;
        String hospitalAddress = String.format(context.getResources().getString(R.string.address), hospital.getStreet(), hospital.getBuilding());
        mTextViewFullName.setText(hospital.getFullName());
        mTextViewAddress.setText(hospitalAddress);
    }

    @OnClick({R.id.item})
    public void onCardClicked() {
        DetailActivity.startActivity(mContext, mHospital);
    }

    public void onClick(DialogInterface dialog, int which) {

    }
}
