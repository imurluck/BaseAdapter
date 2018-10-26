package com.example.library;

public class BaseAdapterException extends RuntimeException {

    public static final String INDEX_OUT_OF_RANGE = "the index out of range";

    public static final String ENTITY_TYPE_ERROR = "the item entity has'n impl IEntity interface";

    public static final String NOT_ENOUGH_ITEM = "there are not enough items";

    public BaseAdapterException(String message) {
        super(message);
    }

}
