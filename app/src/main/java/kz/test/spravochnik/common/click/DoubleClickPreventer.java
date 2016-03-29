/*
 * Decompiled with CFR 0_110.
 */
package kz.test.spravochnik.common.click;

public class DoubleClickPreventer {
    private long mLastClickTime;
    private final long mTimeDelta;

    public DoubleClickPreventer(final long mTimeDelta) {
        this.mTimeDelta = mTimeDelta;
    }

    public boolean preventDoubleClick() {
        if (System.currentTimeMillis() - this.mLastClickTime < this.mTimeDelta && System.currentTimeMillis() - this.mLastClickTime > 0L) {
            return true;
        }
        this.mLastClickTime = System.currentTimeMillis();
        return false;
    }
}

