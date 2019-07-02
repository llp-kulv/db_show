package com.lingrui.db_show.ui;

public enum MainType {
    FIRST(0, "first"), SECOND(1, "second"),
    THIRD(2, "third"), FOURTH(3, "fourth");

    int key;
    String value;

    MainType(int k, String v) {
        this.key = k;
        this.value = v;
    }

    public int getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
