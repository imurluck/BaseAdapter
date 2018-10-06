package com.example.zzx.baseadapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.library.BaseAdapter;
import com.example.library.IEntity;
/**
 * Button实体类型
 * create by zzx
 * create at 18-10-6
 */
public class BtnEntity implements IEntity<BtnEntity> {

    private static final String TAG = "BtnEntity";

    String text;

    public BtnEntity(String content) {
        this.text = content;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_btn;
    }

    @Override
    public void bindView(final BaseAdapter baseAdapter, final BaseAdapter.ViewHolder holder,
                         final BtnEntity data, int position) {
        final Button btn = holder.getRootView().findViewById(R.id.btn);
        btn.setText(data.text);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: " + holder.getAdapterPosition());
                Toast.makeText(btn.getContext(), data.text, Toast.LENGTH_SHORT).show();
//                baseAdapter.remove(holder.getAdapterPosition());
                baseAdapter.add(holder.getAdapterPosition(), new BtnEntity("button add"));
            }
        });
    }
}
