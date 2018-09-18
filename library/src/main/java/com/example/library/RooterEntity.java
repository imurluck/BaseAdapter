package com.example.library;

import android.view.LayoutInflater;
import android.view.View;

public class RooterEntity implements IEntity<RooterEntity> {

    private View mRooterView;

    public RooterEntity(View rooterView) {
        this.mRooterView = rooterView;
    }


    @Override
    public View getLayoutView(LayoutInflater inflater) {
        return mRooterView;
    }

    @Override
    public void bindView(View rootView, RooterEntity data) {

    }
}
