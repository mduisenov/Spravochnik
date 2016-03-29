package kz.test.spravochnik.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import butterknife.ButterKnife;
import kz.test.spravochnik.R;
import kz.test.spravochnik.SpravochnikApp;
import kz.test.spravochnik.data.model.Building;
import kz.test.spravochnik.ui.BaseSecondaryActivity;

public class DetailActivity extends BaseSecondaryActivity {

    public static final String EXTRA_BUILDING = "building";

    public static void startActivity(Context context, Building building) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(EXTRA_BUILDING, building);
        context.startActivity(intent);
    }

    @Override
    protected void onInject() {
        SpravochnikApp.get().component().inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_wrapper_to_toolbar);
        ButterKnife.bind(this);
        initView();
        initActionBar();
    }

    private void initView() {
        Fragment fragment = DetailGeneralFragment.newInstance();
        if (getIntent().getSerializableExtra(EXTRA_BUILDING) != null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(EXTRA_BUILDING, getIntent().getSerializableExtra(EXTRA_BUILDING));
            fragment.setArguments(bundle);
        }
        changeFragment(fragment);
    }

}
