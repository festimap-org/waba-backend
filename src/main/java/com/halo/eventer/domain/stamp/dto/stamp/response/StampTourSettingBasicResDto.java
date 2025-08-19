package com.halo.eventer.domain.stamp.dto.stamp.response;

import com.halo.eventer.domain.stamp.Stamp;
import com.halo.eventer.domain.stamp.dto.stamp.enums.AuthMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StampTourSettingBasicResDto {
    private long stampTourId;
    private String title;
    private AuthMethod authenticationMethod;
    private String boothAdminPassword;

    public static StampTourSettingBasicResDto from(Stamp stamp) {
        return new StampTourSettingBasicResDto(
                stamp.getId(), stamp.getTitle(), stamp.getAuthMethod(), stamp.getBoothAdminPassword());
    }
}
