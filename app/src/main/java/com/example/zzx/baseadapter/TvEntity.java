package com.example.zzx.baseadapter;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.library.IEntity;

public class TvEntity implements IEntity<TvEntity> {

    String text;

    public TvEntity(String content) {
        this.text = content;
    }

    @Override
    public View getLayoutView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.item_tv, null);
    }

    @Override
    public void bindView(View rootView, final TvEntity data) {
        final TextView tv = rootView.findViewById(R.id.tv);
        tv.setText(data.text);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(tv.getContext(), data.text, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
