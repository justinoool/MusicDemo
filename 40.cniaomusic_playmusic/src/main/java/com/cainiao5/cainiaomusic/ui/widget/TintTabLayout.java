package com.cainiao5.cainiaomusic.ui.widget;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;

import magicasakura.utils.TintManager;
import magicasakura.widgets.AppCompatBackgroundHelper;


/**
 * Created by wm on 2016/10/27.
 */
public class TintTabLayout extends TabLayout {
    private AppCompatBackgroundHelper mBackgroundHelper;

    public TintTabLayout(Context context) {
        super(context);
    }

    public TintTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (isInEditMode()) {
            return;
        }
        TintManager mTintManager = TintManager.get(getContext());

        mBackgroundHelper = new AppCompatBackgroundHelper(this, mTintManager);
        // mBackgroundHelper.loadFromAttribute(attrs, defStyleAttr);
    }

    @Override
    public void setSelectedTabIndicatorColor(@ColorInt int color) {
        super.setSelectedTabIndicatorColor(color);

    }
}
