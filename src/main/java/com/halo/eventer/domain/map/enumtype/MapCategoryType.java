package com.halo.eventer.domain.map.enumtype;

public enum MapCategoryType {
    FIXED_BOOTH("고정 부스");

    private final String displayName;

    MapCategoryType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
