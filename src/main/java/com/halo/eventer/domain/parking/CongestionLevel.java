package com.halo.eventer.domain.parking;

public enum CongestionLevel {
    LOW("여유"),
    MEDIUM("보통"),
    HIGH("혼잡"),
    FULL("만차");

    private final String description;

    CongestionLevel(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
