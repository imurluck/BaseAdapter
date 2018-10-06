package com.example.library;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

public class RooterEntity implements IEntity<RooterEntity> {

    private View mRooterView;

    public RooterEntity(View rooterView) {
        this.mRooterView = rooterView;
    }


    @Override
    public int getLayoutId() {
        return R.layout.item_rooter;
    }

    @Override
    public void bindView(BaseAdapter baseAdapter, BaseAdapter.ViewHolder holder,
                         RooterEntity data, int position) {
        RelativeLayout parent = (RelativeLayout) holder.getRootView();
        if (mRooterView.getParent() == null) {
            parent.addView(mRooterView);
        }
    }
}
