package com.example.zzx.baseadapter;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.library.BaseViewHolder;

public class TvViewHolder extends BaseViewHolder<TvEntity> {

    public TvViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    protected View getLayoutView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.item_tv, null);
    }

    @Override
    protected void bind(final TvEntity data) {
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
