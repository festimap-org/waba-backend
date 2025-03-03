package com.halo.eventer.infra.naver.sms.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FileDto {
  private String fileId;

  public FileDto(String fileId) {
    this.fileId = fileId;
  }
}
