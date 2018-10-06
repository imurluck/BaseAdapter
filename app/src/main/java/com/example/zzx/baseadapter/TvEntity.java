package com.example.zzx.baseadapter;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.library.BaseAdapter;
import com.example.library.IEntity;
/**
 * TV实体类型
 * create by zzx
 * create at 18-10-6
 */
public class TvEntity implements IEntity<TvEntity> {

    String text;

    public TvEntity(String content) {
        this.text = content;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_tv;
    }

    @Override
    public void bindView(final BaseAdapter baseAdapter, final BaseAdapter.ViewHolder holder,
                         final TvEntity data, int position) {
        final TextView tv = holder.getRootView().findViewById(R.id.tv);
        tv.setText(data.text);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(tv.getContext(), data.text, Toast.LENGTH_SHORT).show();
                baseAdapter.remove(holder.getAdapterPosition());
            }
        });
    }
}
