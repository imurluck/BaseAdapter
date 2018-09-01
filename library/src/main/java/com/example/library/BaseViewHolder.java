package com.example.library;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

public abstract class BaseViewHolder<D> extends RecyclerView.ViewHolder {

    protected View rootView;

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    protected abstract View getLayoutView(LayoutInflater inflater);

    protected abstract void bind(D data);
}
