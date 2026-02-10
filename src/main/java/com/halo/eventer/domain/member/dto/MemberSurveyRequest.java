package com.halo.eventer.domain.member.dto;

import java.time.LocalDate;

import com.halo.eventer.domain.member.Gender;
import com.halo.eventer.domain.member.ResidenceType;
import com.halo.eventer.domain.member.TransportationType;
import com.halo.eventer.domain.member.VisitType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberSurveyRequest {
    private ResidenceType residenceType;
    private String residenceRegion;
    private String residenceDistrict;
    private VisitType visitType;
    private Gender gender;
    private LocalDate birthDate;
    private TransportationType transportationType;

    public MemberSurveyRequest(
            ResidenceType residenceType,
            String residenceRegion,
            String residenceDistrict,
            VisitType visitType,
            Gender gender,
            LocalDate birthDate,
            TransportationType transportationType) {
        this.residenceType = residenceType;
        this.residenceRegion = residenceRegion;
        this.residenceDistrict = residenceDistrict;
        this.visitType = visitType;
        this.gender = gender;
        this.birthDate = birthDate;
        this.transportationType = transportationType;
    }
}
