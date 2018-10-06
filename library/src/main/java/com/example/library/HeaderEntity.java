package com.example.library;

import android.preference.PreferenceActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

public class HeaderEntity implements IEntity<HeaderEntity> {

    private View mHeaderView;

    public HeaderEntity(View headerView) {
        this.mHeaderView = headerView;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_header;
    }

    @Override
    public void bindView(BaseAdapter baseAdapter, BaseAdapter.ViewHolder holder,
                         HeaderEntity data, int position) {
        if (mHeaderView.getParent() == null) {
            ((RelativeLayout) holder.getRootView()).addView(mHeaderView);
        }
    }
}
