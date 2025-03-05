package com.halo.eventer.global.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
public enum ErrorCode {

  // Common Error
  INTERNAL_SERVER_ERROR("C001", "Server Error"),
  INVALID_INPUT_VALUE("C002", "Invalid Input Value"),
  METHOD_NOT_ALLOWED("C003", "Invalid HTTP Method"),
  ELEMENT_NOT_FOUND("C004", "Element Not Found"),
  ELEMENT_DUPLICATED("C005", "Element Duplicated"),
  UNACCEPTABLE_EXTENSION("C007", "Unacceptable Extension"),
  INVALID_JSON_FORMAT("C008", "Invalid JSON Format"),
  MISSING_PARAMETER("C009", "Missing Parameter"),
  INVALID_PARAMETER_TYPE("C010", "Invalid Parameter Type"),
  MISSING_PATH_VARIABLE("C011", "Missing Path Variable"),

  // DownWidget Error
  PERMIT_THREE_ELEMENT("DW001", "Only Permit Three Element"),

  // Vote Error
  ALREADY_LIKE("C006", "Already Like Element"),

  // Monitoring Error
  FESTIVAL_EXPIRED("M001", "Festival Expired"),
  ;

  private final String code;
  private final String message;

  ErrorCode(final String code, final String message) {
    this.message = message;
    this.code = code;
  }
}
