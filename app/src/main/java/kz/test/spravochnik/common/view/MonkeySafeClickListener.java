/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package kz.test.spravochnik.common.view;

import android.view.View;

import kz.test.spravochnik.util.DeviceUtil;
import timber.log.Timber;

public abstract class MonkeySafeClickListener implements View.OnClickListener {
    public void onClick(View view) {
        if (DeviceUtil.preventDoubleClick()) {
            Timber.d("Click prevented");
            return;
        }
        this.onSafeClick(view);
    }

    public abstract void onSafeClick(View var1);
}

