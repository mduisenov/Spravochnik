package kz.test.spravochnik.ui;

import android.view.MenuItem;

public abstract class BaseSecondaryActivity extends BaseMainActivity {

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != android.R.id.home) {
            return super.onOptionsItemSelected(item);
        }
        finish();
        return true;
    }

    @Override
    protected void initActionBar() {
        super.initActionBar();
        getSupportActionBar().setLogo(null);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
    }
}
