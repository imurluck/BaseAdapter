package com.example.library;

import android.view.LayoutInflater;
import android.view.View;

public interface IEntity<D extends IEntity> {

    /**
     * 此方法为2.0.0中，摒弃
     * @return
     */
//    View getLayoutView(LayoutInflater inflater);

    /**
     * 返回布局item 的布局id
     * @return
     */
    int getLayoutId();

    /**
     * 绑定View与数据
     * @param baseAdapter
     * @param holder
     * @param data
     * @param position
     */
    void bindView(BaseAdapter baseAdapter, BaseAdapter.ViewHolder holder, D data, int position);
}
