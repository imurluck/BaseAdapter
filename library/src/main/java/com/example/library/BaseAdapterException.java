package com.example.library;

public class BaseAdapterException extends RuntimeException {

    public static final String VIEW_HOLDER_NOT_REGISTER = "this ViewHolder has't registered";

    public static final String ENTITY_HOLDER_LIST_NOT_MAP = "the entity list and viewholder list are not one-to-one correspondence.";

    public BaseAdapterException(String message) {
        super(message);
    }

}
