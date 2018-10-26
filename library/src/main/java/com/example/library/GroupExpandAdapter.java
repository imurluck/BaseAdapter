package com.example.library;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GroupExpandAdapter extends BaseAdapter {

    private List<IGroupEntity> mGroupList;

    private HashMap<IGroupEntity, Integer> mGroupIndexMap;

    private HashMap<IGroupEntity, Integer> mGroupStateMap;

    private static final int STATE_EXPAND = 1;
    private static final int STATE_CLLOAPSE = 0;

    private OnToogleListener mOnToogleListener;

    /**
     * 构造方法，初始化实体集序列
     */
    private GroupExpandAdapter() {
        super();
    }

    @NonNull
    @Override
    public BaseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseAdapter.ViewHolder holder, int position) {
        if (getItem(position) instanceof IGroupEntity) {
            mGroupIndexMap.put((IGroupEntity) getItem(position), position);
        }
        super.onBindViewHolder(holder, position);
    }


    public void groupList(List<? extends IGroupEntity> groupList) {
        mGroupList.addAll(groupList);
        add(groupList);
    }

    public void setOnToogleListener(@Nullable OnToogleListener listener) {
        this.mOnToogleListener = listener;
    }

    public void toogle(int startPosition, IGroupEntity groupEntity) {
        if (mGroupStateMap.get(groupEntity) == STATE_CLLOAPSE) {
            expand(startPosition, groupEntity);
        } else {
            clloapse(startPosition, groupEntity);
        }
    }

    public void expand(int startPosition, IGroupEntity groupEntity) {
        add(startPosition + 1, groupEntity.getChildList());
        if (mOnToogleListener != null) {
            mOnToogleListener.onExpand(groupEntity);
        }
        mGroupStateMap.put(groupEntity, STATE_EXPAND);
    }

    public void clloapse(int startPosition, IGroupEntity groupEntity) {
        remove(startPosition, groupEntity.getChildList());
        if (mOnToogleListener != null) {
            mOnToogleListener.onClloapse(groupEntity);
        }
        mGroupStateMap.put(groupEntity, STATE_CLLOAPSE);
    }

    private int getGroupIndex(IGroupEntity groupEntity) {
        return getDataList().indexOf(groupEntity);
    }

    public static class Builder {

        private GroupExpandAdapter mGroupExpandAdapter;

        public Builder() {
            mGroupExpandAdapter = new GroupExpandAdapter();
            mGroupExpandAdapter.mGroupList = new ArrayList<>();
            mGroupExpandAdapter.mGroupIndexMap = new HashMap<>();
            mGroupExpandAdapter.mGroupStateMap = new HashMap<>();
        }

        public Builder groupList(List<? extends IGroupEntity> groupList) {
            mGroupExpandAdapter.groupList(groupList);
            for (int i = 0; i < groupList.size(); i++) {
                mGroupExpandAdapter.mGroupStateMap.put(groupList.get(i), STATE_CLLOAPSE);
            }
            return this;
        }

        public GroupExpandAdapter build() {
            return mGroupExpandAdapter;
        }
    }

    public interface OnToogleListener {

        void onExpand(IGroupEntity<? extends IEntity, ? extends IEntity> groupEntity);

        void onClloapse(IGroupEntity<? extends IEntity, ? extends IEntity> groupEntity);
    }
}
