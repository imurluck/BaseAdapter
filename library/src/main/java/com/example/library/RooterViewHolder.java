package com.example.library;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

public class RooterViewHolder extends BaseViewHolder {

    View rooterView;

    public RooterViewHolder(@Nullable View itemView) {
        super(itemView);
        this.rooterView = itemView;
    }

    @Override
    protected View getLayoutView(LayoutInflater inflater) {
        return null;
    }

    @Override
    protected void bind(Object data) {

    }

    public static class RooterEntity {
        View rooterView;

        public RooterEntity(View rooterView) {
            this.rooterView = rooterView;
        }
    }
}
