package com.example.library;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

public class HeaderViewHolder extends BaseViewHolder {

    View headerView;

    public HeaderViewHolder(@Nullable View itemView) {
        super(itemView);
        this.headerView = itemView;
    }

    @Override
    protected View getLayoutView(LayoutInflater inflater) {
        return headerView;
    }

    @Override
    protected void bind(Object data) {

    }

    public static class HeaderEntity {
        View headerView;

        public HeaderEntity(View headerView) {
            this.headerView = headerView;
        }
    }
}
