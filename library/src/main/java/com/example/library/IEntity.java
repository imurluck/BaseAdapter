package com.example.library;

import android.view.LayoutInflater;
import android.view.View;

public interface IEntity<D extends IEntity> {

    View getLayoutView(LayoutInflater inflater);

    void bindView(View rootView, D data);
}
