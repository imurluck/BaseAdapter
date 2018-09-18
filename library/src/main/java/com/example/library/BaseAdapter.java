package com.example.library;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

public class BaseAdapter extends RecyclerView.Adapter<BaseAdapter.ViewHolder>{

    private List<Object> mDataList;

    private List<HeaderEntity> mHeaderList;

    private List<RooterEntity> mRooterList;


    private BaseAdapter() {
        mDataList = new ArrayList<>(0);
        mHeaderList = new ArrayList<>(0);
        mRooterList = new ArrayList<>(0);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Object entity = mDataList.get(viewType);
        if (!(entity instanceof IEntity)) {
            throw new BaseAdapterException(BaseAdapterException.ENTITY_TYPE_ERROR);
        }

        View view = ((IEntity) entity).getLayoutView(LayoutInflater.from(parent.getContext()));
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        IEntity entity = (IEntity) mDataList.get(position);
        entity.bindView(holder.rootView, entity);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void add(Object entity) {
        mDataList.add(mDataList.size() - mRooterList.size());
    }

    public void add(List<Object> entityList) {
        mDataList.addAll(mDataList.size() - mRooterList.size(), entityList);
    }

    public void remove(int index) {
        if (index < mHeaderList.size() || index >= mDataList.size() - mRooterList.size()) {
            throw new BaseAdapterException(BaseAdapterException.INDEX_OUT_OF_RANGE);
        }
        mDataList.remove(index);
    }

    public void update(int index, Object entity) {
        if (index < mHeaderList.size() || index >= mDataList.size() - mRooterList.size()) {
            throw new BaseAdapterException(BaseAdapterException.INDEX_OUT_OF_RANGE);
        }
        mDataList.remove(index);
        mDataList.add(index, entity);
        this.notifyItemChanged(index);
    }

    public void replace(List<Object> entityList) {
        mDataList.clear();
        mDataList.addAll(mHeaderList);
        mDataList.addAll(entityList);
        mDataList.addAll(mRooterList);
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        View rootView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.rootView = itemView;
        }
    }

    public static class Builder {

        private BaseAdapter mAdapter;

        public Builder() {
            mAdapter = new BaseAdapter();
        }


        public Builder setDataList(List<Object> dataList) {
            mAdapter.mDataList.addAll(mAdapter.mHeaderList.size(), dataList);
            return this;
        }

        public Builder addHeader(View headerView) {
            mAdapter.mHeaderList.add(0, new HeaderEntity(headerView));
            mAdapter.mDataList.add(0, new HeaderEntity(headerView));
            return this;
        }

        public Builder addHeader(List<View> headerList) {
            for (int i = headerList.size() - 1; i >= 0; i--) {
                mAdapter.mHeaderList.add(0, new HeaderEntity(headerList.get(i)));
                mAdapter.mDataList.add(0, new HeaderEntity(headerList.get(i)));
            }
            return this;
        }

        public Builder addRooter(View view) {
            mAdapter.mRooterList.add(new RooterEntity(view));
            mAdapter.mDataList.add(new RooterEntity(view));
            return this;
        }

        public Builder addRooter(List<View> rooterList) {
            for (int i = rooterList.size() - 1; i >= 0; i--) {
                mAdapter.mRooterList.add(new RooterEntity(rooterList.get(i)));
                mAdapter.mDataList.add(new RooterEntity(rooterList.get(i)));
            }
            return this;
        }

        public BaseAdapter build() {
            return mAdapter;
        }
    }

}
