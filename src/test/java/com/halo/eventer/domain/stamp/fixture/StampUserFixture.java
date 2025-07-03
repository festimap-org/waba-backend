package com.halo.eventer.domain.stamp.fixture;

import com.halo.eventer.domain.stamp.Stamp;
import com.halo.eventer.domain.stamp.StampUser;
import com.halo.eventer.domain.stamp.dto.stampUser.LoginDto;
import com.halo.eventer.domain.stamp.dto.stampUser.SignupDto;
import com.halo.eventer.domain.stamp.dto.stampUser.SignupWithCustomDto;
import com.halo.eventer.domain.stamp.dto.stampUser.SignupWithoutCustomDto;

import static org.springframework.test.util.ReflectionTestUtils.setField;

@SuppressWarnings("NonAsciiCharacters")
public class StampUserFixture {

    private static final String ENCRYPTED_STRING = "encrypted string";

    private static final Long 스탬프유저1 = 1L;
    private static final Long 스탬프유저2 = 2L;
    private static final Long 스탬프유저3 = 3L;
    private static final Long 스탬프유저4 = 4L;
    private static final Long 스탬프유저5 = 5L;
    private static final Long 스탬프유저6 = 6L;
    private static final Long 스탬프유저7 = 7L;

    public static StampUser 스탬프유저1_생성(){
        return new StampUser("encryptedPhone", "encryptedName", 1);
    }

    public static SignupDto 회원가입_DTO_생성() {
        SignupDto signupDto = new SignupDto();
        setField(signupDto, "name", "test name");
        setField(signupDto, "phone", "test phone");
        setField(signupDto, "participantCount", 10);
        return signupDto;
    }

    public static SignupWithoutCustomDto 커스텀_없는_회원가입_DTO_생성() {
        SignupWithoutCustomDto signupDto = new SignupWithoutCustomDto();
        setField(signupDto, "name", "test name");
        setField(signupDto, "phone", "test phone");
        setField(signupDto, "participantCount", 10);
        return signupDto;
    }

    public static SignupWithCustomDto 커스텀_있는_회원가입_DTO_생성() {
        SignupWithCustomDto signupDto = new SignupWithCustomDto();
        setField(signupDto, "name", "test name");
        setField(signupDto, "phone", "test phone");
        setField(signupDto, "participantCount", 10);
        setField(signupDto, "schoolNo", "test school no");
        return signupDto;
    }

    public static LoginDto 로그인_DTO_생성() {
        LoginDto loginDto = new LoginDto();
        setField(loginDto, "name", "test");
        setField(loginDto, "phone", "01012341234");
        return loginDto;
    }

    public static StampUser 스탬프유저_엔티티_생성(Stamp stamp) {
        return new StampUser(ENCRYPTED_STRING, ENCRYPTED_STRING, 10);
    }
}
