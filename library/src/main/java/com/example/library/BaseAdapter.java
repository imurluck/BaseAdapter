package com.example.library;


import android.preference.PreferenceActivity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BaseAdapter extends RecyclerView.Adapter<BaseViewHolder>{

    private List<Object> mDataList;

    private List<HeaderViewHolder.HeaderEntity> mHeaderList;

    private List<RooterViewHolder.RooterEntity> mRooterList;

    private ItemTypePool mItemTypePool;

    private View mTempView;

    private BaseAdapter() {
        mDataList = new ArrayList<>(0);
        mItemTypePool = new ItemTypePool();
        mHeaderList = new ArrayList<>(0);
        mRooterList = new ArrayList<>(0);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Class holderClazz = mItemTypePool.get(mDataList.get(viewType).getClass());
        if (holderClazz == null) {
            throw new BaseAdapterException(BaseAdapterException.VIEW_HOLDER_NOT_REGISTER);
        }
        if (mDataList.get(viewType) instanceof HeaderViewHolder.HeaderEntity) {
            return new HeaderViewHolder(((HeaderViewHolder.HeaderEntity) mDataList.get(viewType)).headerView);
        }
        if (mDataList.get(viewType) instanceof RooterViewHolder.RooterEntity) {
            return new RooterViewHolder(((RooterViewHolder.RooterEntity) mDataList.get(viewType)).rooterView);
        }
        BaseViewHolder holder = null;
        try {
            Constructor constructor = holderClazz.getConstructor(View.class);
            if (mTempView == null) {
                mTempView = new TextView(parent.getContext());
            }
            holder = (BaseViewHolder) constructor.newInstance(mTempView);
            holder.rootView = holder.getLayoutView(LayoutInflater.from(parent.getContext()));
            Field itemView = holderClazz.getField("itemView");
            if (!itemView.isAccessible()) {
                itemView.setAccessible(true);
            }
            itemView.set(holder, holder.rootView);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.bind(mDataList.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public static class Builder {

        private BaseAdapter mAdapter;

        public Builder() {
            mAdapter = new BaseAdapter();
        }

        public Builder register(Class<?> entityClazz, Class<? extends BaseViewHolder> holderClazz) {
            mAdapter.mItemTypePool.put(entityClazz, holderClazz);
            return this;
        }

        public Builder register(List<Class<?>> entityList, List<Class<? extends BaseViewHolder>> holderList) {
            if (entityList.size() != holderList.size()) {
                throw new BaseAdapterException(BaseAdapterException.ENTITY_HOLDER_LIST_NOT_MAP);
            }
            for (int i = 0; i < entityList.size(); i++) {
                mAdapter.mItemTypePool.put(entityList.get(i), holderList.get(i));
            }
            return this;
        }

        public Builder setDataList(List<Object> dataList) {
            mAdapter.mDataList.addAll(mAdapter.mHeaderList.size(), dataList);
            return this;
        }

        public Builder addHeader(View headerView) {
            register(HeaderViewHolder.HeaderEntity.class, HeaderViewHolder.class);
            mAdapter.mHeaderList.add(0, new HeaderViewHolder.HeaderEntity(headerView));
            mAdapter.mDataList.add(0, new HeaderViewHolder.HeaderEntity(headerView));
            return this;
        }

        public Builder addHeader(List<View> headerList) {
            register(HeaderViewHolder.HeaderEntity.class, HeaderViewHolder.class);
            for (int i = headerList.size() - 1; i >= 0; i--) {
                mAdapter.mHeaderList.add(0, new HeaderViewHolder.HeaderEntity(headerList.get(i)));
                mAdapter.mDataList.add(0, new HeaderViewHolder.HeaderEntity(headerList.get(i)));
            }
            return this;
        }

        public Builder addRooter(View view) {
            register(RooterViewHolder.RooterEntity.class, RooterViewHolder.class);
            mAdapter.mRooterList.add(new RooterViewHolder.RooterEntity(view));
            mAdapter.mDataList.add(new RooterViewHolder.RooterEntity(view));
            return this;
        }

        public Builder addRooter(List<View> rooterList) {
            register(RooterViewHolder.RooterEntity.class, RooterViewHolder.class);
            for (int i = rooterList.size() - 1; i >= 0; i--) {
                mAdapter.mRooterList.add(new RooterViewHolder.RooterEntity(rooterList.get(i)));
                mAdapter.mDataList.add(new RooterViewHolder.RooterEntity(rooterList.get(i)));
            }
            return this;
        }

        public BaseAdapter build() {
            return mAdapter;
        }
    }

}
