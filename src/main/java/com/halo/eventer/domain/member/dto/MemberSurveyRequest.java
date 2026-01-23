package com.halo.eventer.domain.member.dto;

import com.halo.eventer.domain.member.AgeGroup;
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
    private AgeGroup ageGroup;
    private TransportationType transportationType;

    public MemberSurveyRequest(
            ResidenceType residenceType,
            String residenceRegion,
            String residenceDistrict,
            VisitType visitType,
            Gender gender,
            AgeGroup ageGroup,
            TransportationType transportationType) {
        this.residenceType = residenceType;
        this.residenceRegion = residenceRegion;
        this.residenceDistrict = residenceDistrict;
        this.visitType = visitType;
        this.gender = gender;
        this.ageGroup = ageGroup;
        this.transportationType = transportationType;
    }
}
