package com.example.library;

import java.util.HashMap;

public class ItemTypePool {


    private HashMap<Class<?>, Class<? extends BaseViewHolder>> mTypeMap;

    public ItemTypePool() {
        this(new HashMap<Class<?>, Class<? extends BaseViewHolder>>());
    }

    public ItemTypePool(HashMap<Class<?>, Class<? extends BaseViewHolder>> itemMap) {
        this.mTypeMap = itemMap;
    }


    public void put(Class<?> entityClzz, Class<? extends BaseViewHolder> viewHolderClazz) {
        if (mTypeMap.containsKey(entityClzz)) {
            return;
        }
        mTypeMap.put(entityClzz, viewHolderClazz);
    }

    public Class<? extends BaseViewHolder> get(Class<?> clazz) {
        if (!mTypeMap.containsKey(clazz)) {
            return null;
        }
        return mTypeMap.get(clazz);
    }

}
