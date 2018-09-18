package com.example.library;

import android.preference.PreferenceActivity;
import android.view.LayoutInflater;
import android.view.View;

public class HeaderEntity implements IEntity<HeaderEntity> {

    private View mHeaderView;

    public HeaderEntity(View headerView) {
        this.mHeaderView = headerView;
    }

    @Override
    public View getLayoutView(LayoutInflater inflater) {
        return mHeaderView;
    }

    @Override
    public void bindView(View rootView, HeaderEntity data) {

    }
}
