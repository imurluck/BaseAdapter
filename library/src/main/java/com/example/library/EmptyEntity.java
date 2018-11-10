package com.example.library;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import static android.widget.RelativeLayout.CENTER_IN_PARENT;

public class EmptyEntity implements IEntity<EmptyEntity> {

    private View mEmptyView;

    public EmptyEntity(View emptyView) {
        this.mEmptyView = emptyView;
    }


    @Override
    public int getLayoutId() {
        return R.layout.item_empty;
    }

    @Override
    public void bindView(BaseAdapter baseAdapter, BaseAdapter.ViewHolder holder,
                         EmptyEntity data, int position) {
        RelativeLayout parent = (RelativeLayout) holder.getRootView();
        if (mEmptyView.getParent() == null) {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.addRule(CENTER_IN_PARENT);
            parent.addView(mEmptyView, params);
        }
    }
}
