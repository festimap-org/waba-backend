package com.halo.eventer.global.common.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum SuccessCode {
  SAVE_SUCCESS("Successfully saved"),
  UPDATE_SUCCESS("Successfully updated");

  private String message;

  SuccessCode(String message) {
    this.message = message;
  }
}
