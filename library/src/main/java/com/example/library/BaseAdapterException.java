package com.example.library;

public class BaseAdapterException extends RuntimeException {

    public static final String INDEX_OUT_OF_RANGE = "the index out of range";

    public static final String ENTITY_TYPE_ERROR = "the item entity has'n impl IEntity interface";

    public BaseAdapterException(String message) {
        super(message);
    }

}
