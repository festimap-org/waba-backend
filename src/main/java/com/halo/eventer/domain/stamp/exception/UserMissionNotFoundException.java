package com.halo.eventer.domain.stamp.exception;

import com.halo.eventer.global.error.exception.EntityNotFoundException;

public class UserMissionNotFoundException extends EntityNotFoundException {
    public UserMissionNotFoundException(Long id) {
        super(String.format("UserMission with %s is not found", id));
    }
}
