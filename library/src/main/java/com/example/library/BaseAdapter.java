package com.example.library;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;
/**
 * 通用RecyclerView Adapter
 * 功能点:
 * - 多类型Item,adapter生成速度与原生写法仅相差40ms
 * - 添加头部和尾部
 * - 自动加载更多
 * - 后续待添加...
 * create by zzx
 * create at 18-9-1
 */
public class BaseAdapter extends RecyclerView.Adapter<BaseAdapter.ViewHolder>{

    private static final String TAG = "BaseAdapter";

    /**
     * 数据集合，包含数据实体集，头部尾部实体集，(头部和尾部可以看做多类型中的一种,因此合并)
     */
    protected List<IEntity> mDataList;

    /**
     * 单独保存头部实体集引用序列
     */
    protected List<HeaderEntity> mHeaderList;

    /**
     * 单独保存尾部实体集引用序列
     */
    protected List<RooterEntity> mRooterList;

    /**
     * 是否自动加载更多
     */
    protected boolean autoLoadMore;

    /**
     * 默认spantCount
     */
    private final int DEFAULT_SPANT_COUNT = 1;

    /**
     * spantCount
     */
    private int mSpantCount = DEFAULT_SPANT_COUNT;

    /**
     * 加载更多监听器
     */
    private OnLoadMoreListener mOnLoadMoreListener;

    /**
     * 构造方法，初始化实体集序列
     */
    BaseAdapter() {
        mDataList = new ArrayList<>(0);
        mHeaderList = new ArrayList<>(0);
        mRooterList = new ArrayList<>(0);
    }

    /**
     *
     * @param parent
     * @param viewType item对应的Layout ID
     * @return
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.e(TAG, "onCreateViewHolder: " + viewType);
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new ViewHolder(view);
    }

    /**
     * 绑定数据，交给实体自己去绑定，最大限度自定义化
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.e(TAG, "onBindViewHolder: " + position);
        IEntity entity = (IEntity) mDataList.get(position);
        entity.bindView(this, holder, entity, position);
        if (autoLoadMore && position >= mDataList.size() - 1) {
            if (mOnLoadMoreListener != null) {
                mOnLoadMoreListener.loadMore(this);
            }
        }
    }

    /**
     * 设置加载更多监听器，详情参考{@link OnLoadMoreListener} 和 {@link #onBindViewHolder(ViewHolder, int)}
     * 中的用法
     * @param listener
     */
    public void setOnLoadMoreListener(@Nullable OnLoadMoreListener listener) {
        this.mOnLoadMoreListener = listener;
    }

    /**
     * 返回item的Layout ID
     * 此处摒弃2.0.0中返回position的方式，返回position的方式会造成notifyItemRemove方法异常
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if (!(mDataList.get(position) instanceof IEntity)) {
            throw new BaseAdapterException(BaseAdapterException.ENTITY_TYPE_ERROR);
        }
        return ((IEntity) mDataList.get(position)).getLayoutId();
    }

    public IEntity getItem(int position) {
        return mDataList.get(position);
    }

    public List<IEntity> getDataList() {
        return mDataList;
    }

    /**
     * 在末尾添加一个新的实体
     * @param entity
     */
    public void add(IEntity entity) {
        mDataList.add(mDataList.size() - mRooterList.size(), entity);
        this.notifyItemInserted(mDataList.size() - mRooterList.size() - 1);
    }

    /**
     * 在指定索引处添加一个新的实体
     * @param index
     * @param entity
     */
    public void add(int index, IEntity entity) {
        checkIndex(index);
        mDataList.add(index, entity);
        this.notifyItemInserted(index);
    }

    /**
     * 检查index是否在数据范围类
     * @param index
     */
    private void checkIndex(int index) {
        if (index < mHeaderList.size() || index > mDataList.size() - mRooterList.size()) {
            throw new BaseAdapterException(BaseAdapterException.INDEX_OUT_OF_RANGE);
        }
    }

    /**
     * 在末尾处添加实体集
     * @param entityList
     */
    public <D extends IEntity> void add(List<D> entityList) {
        mDataList.addAll(mDataList.size() - mRooterList.size(), entityList);
        this.notifyItemRangeInserted(mDataList.size() - mRooterList.size() - entityList.size(),
                entityList.size());
    }

    public <D extends IEntity> void add(int index, List<D> entityList) {
        checkIndex(index);
        mDataList.addAll(index, entityList);
        this.notifyItemRangeInserted(index, entityList.size());
    }

    /**
     * 删除一个实体集
     * @param index
     */
    public void remove(final int index) {
        //防止删除同一个index过快
        if (index == -1) {
            return ;
        }
        checkIndex(index);
        mDataList.remove(index);
        notifyItemRemoved(index);
    }

    public void remove(int startPosition, List<IEntity> entityList) {
        mDataList.removeAll(entityList);
        notifyItemRangeRemoved(startPosition + 1, entityList.size());
    }

    public void remove(int startPosition, int count) {
        if (startPosition == -1) {
            return ;
        }
        checkIndex(startPosition);
        if (startPosition + count > mDataList.size()) {
            throw new BaseAdapterException(BaseAdapterException.NOT_ENOUGH_ITEM);
        }

        this.notifyItemRangeRemoved(startPosition, count);
    }

    /**
     * 更新
     * @param index
     * @param entity
     */
    public <D extends IEntity> void update(int index, D entity) {
        if (index < mHeaderList.size() || index >= mDataList.size() - mRooterList.size()) {
            throw new BaseAdapterException(BaseAdapterException.INDEX_OUT_OF_RANGE);
        }
        mDataList.remove(index);
        mDataList.add(index, entity);
        this.notifyItemChanged(index);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 替换所有实体
     * @param entityList
     */
    public <D extends IEntity> void replace(List<D> entityList) {
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

    /**
     * ViewHolder 保存item 根布局
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        View rootView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.rootView = itemView;
        }

        public View getRootView() {
            return rootView;
        }
    }
    /**
     * Builder 规范BaseAdapter的创建
     * create by zzx
     * create at 18-10-6
     */
    public static class Builder {

        private BaseAdapter mAdapter;

        public Builder() {
            mAdapter = new BaseAdapter();
        }


        /**
         * 设置实体集
         * @param dataList
         * @return
         */
        public <D extends IEntity> Builder setDataList(List<D> dataList) {
            mAdapter.mDataList.addAll(mAdapter.mHeaderList.size(), dataList);
            return this;
        }

        /**
         * 添加一个头部
         * @param headerView
         * @return
         */
        public Builder addHeader(View headerView) {
            mAdapter.mHeaderList.add(0, new HeaderEntity(headerView));
            mAdapter.mDataList.add(0, new HeaderEntity(headerView));
            return this;
        }

        /**
         * 添加头部集合
         * @param headerList
         * @return
         */
        public Builder addHeader(List<View> headerList) {
            for (int i = headerList.size() - 1; i >= 0; i--) {
                mAdapter.mHeaderList.add(0, new HeaderEntity(headerList.get(i)));
                mAdapter.mDataList.add(0, new HeaderEntity(headerList.get(i)));
            }
            return this;
        }

        /**
         * 设置是否开启自动加载更多
         * @param autoLoadMore
         * @return
         */
        public Builder autoLoadMore(boolean autoLoadMore) {
            mAdapter.autoLoadMore = autoLoadMore;
            return this;
        }

        /**
         * 添加尾部
         * @param view
         * @return
         */
        public Builder addRooter(View view) {
            mAdapter.mRooterList.add(new RooterEntity(view));
            mAdapter.mDataList.add(new RooterEntity(view));
            return this;
        }

        /**
         * 添加尾部集合
         * @param rooterList
         * @return
         */
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


    /**
     * 自动加载更多的监听器
     */
    public interface OnLoadMoreListener {

        /**
         * 通知外部已经到了当前已经滑到了最后一行，需要加载更多
         * @param baseAdapter
         */
        void loadMore(BaseAdapter baseAdapter);
    }
}
