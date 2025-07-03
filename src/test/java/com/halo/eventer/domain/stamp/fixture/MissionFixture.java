package com.halo.eventer.domain.stamp.fixture;

import java.util.List;

import com.halo.eventer.domain.stamp.Mission;
import com.halo.eventer.domain.stamp.dto.mission.MissionUpdateDto;
import com.halo.eventer.domain.stamp.dto.stamp.MissionSetDto;

import static org.springframework.test.util.ReflectionTestUtils.setField;

@SuppressWarnings("NonAsciiCharacters")
public class MissionFixture {

    public static final Long 미션1 = 1L;
    public static final Long 미션2 = 2L;
    public static final Long 미션3 = 3L;
    public static final Long 미션4 = 4L;
    public static final Long 미션5 = 5L;
    public static final Long 미션6 = 6L;
    public static final Long 미션7 = 7L;

    public static Mission 미션1_생성() {
        return Mission.builder()
                .boothId(1L)
                .title("미션 1")
                .content("미션1입니다.")
                .place("미션1 장소")
                .time("미션1 시간")
                .clearedThumbnail("미션1 성공 사진")
                .notClearedThumbnail("미션1 실패 사진")
                .build();
    }

    public static Mission 미션2_생성() {
        return Mission.builder()
                .boothId(2L)
                .title("미션 2")
                .content("미션2입니다.")
                .place("미션2 장소")
                .time("미션2 시간")
                .clearedThumbnail("미션2 성공 사진")
                .notClearedThumbnail("미션2 실패 사진")
                .build();
    }

    public static Mission 미션3_생성() {
        return Mission.builder()
                .boothId(3L)
                .title("미션 3")
                .content("미션3입니다.")
                .place("미션3 장소")
                .time("미션3 시간")
                .clearedThumbnail("미션3 성공 사진")
                .notClearedThumbnail("미션3 실패 사진")
                .build();
    }

    public static Mission 미션4_생성() {
        return Mission.builder()
                .boothId(4L)
                .title("미션 4")
                .content("미션4입니다.")
                .place("미션4 장소")
                .time("미션4 시간")
                .clearedThumbnail("미션4 성공 사진")
                .notClearedThumbnail("미션4 실패 사진")
                .build();
    }

    public static Mission 미션5_생성() {
        return Mission.builder()
                .boothId(5L)
                .title("미션 5")
                .content("미션5입니다.")
                .place("미션5 장소")
                .time("미션5 시간")
                .clearedThumbnail("미션5 성공 사진")
                .notClearedThumbnail("미션5 실패 사진")
                .build();
    }

    public static Mission 미션6_생성() {
        return Mission.builder()
                .boothId(6L)
                .title("미션 6")
                .content("미션6입니다.")
                .place("미션6 장소")
                .time("미션6 시간")
                .clearedThumbnail("미션6 성공 사진")
                .notClearedThumbnail("미션6 실패 사진")
                .build();
    }

    public static Mission 미션7_생성() {
        return Mission.builder()
                .boothId(7L)
                .title("미션 7")
                .content("미션7입니다.")
                .place("미션7 장소")
                .time("미션7 시간")
                .clearedThumbnail("미션7 성공 사진")
                .notClearedThumbnail("미션7 실패 사진")
                .build();
    }

    public static List<Mission> 모든_미션_생성() {
        return List.of(미션1_생성(), 미션2_생성(), 미션3_생성(), 미션4_생성(), 미션5_생성(), 미션6_생성(), 미션7_생성());
    }

    public static List<MissionSetDto> 미션_셋업_리스트() {
        return List.of(
                new MissionSetDto(1L, "미션 1", "내용 1", "장소 1", "시간 1", "c1.jpg", "nc1.jpg"),
                new MissionSetDto(2L, "미션 2", "내용 2", "장소 2", "시간 2", "c2.jpg", "nc2.jpg"),
                new MissionSetDto(3L, "미션 3", "내용 3", "장소 3", "시간 3", "c3.jpg", "nc3.jpg"),
                new MissionSetDto(4L, "미션 4", "내용 4", "장소 4", "시간 4", "c4.jpg", "nc4.jpg"),
                new MissionSetDto(5L, "미션 5", "내용 5", "장소 5", "시간 5", "c5.jpg", "nc5.jpg"));
    }

    public static Mission 미션_엔티티_생성() {
        return Mission.from(미션_생성_DTO_생성());
    }

    public static MissionSetDto 미션_생성_DTO_생성() {
        MissionSetDto dto = new MissionSetDto();
        setField(dto, "boothId", 1L);
        setField(dto, "title", "미션 제목");
        setField(dto, "content", "미션 설명");
        setField(dto, "place", "A관 1층");
        setField(dto, "time", "10:00 ~ 18:00");
        setField(dto, "clearedThumbnail", "cleared.png");
        setField(dto, "notClearedThumbnail", "not_cleared.png");
        return dto;
    }

    public static MissionUpdateDto 미션_업데이트_DTO_생성() {
        MissionUpdateDto dto = new MissionUpdateDto();
        setField(dto, "boothId", 2L);
        setField(dto, "title", "수정된 제목");
        setField(dto, "content", "수정된 내용");
        setField(dto, "place", "수정된 장소");
        setField(dto, "time", "14:00");
        setField(dto, "clearedThumbnail", "cleared.png");
        setField(dto, "notClearedThumbnail", "not-cleared.png");
        return dto;
    }

    public static MissionUpdateDto 미션_업데이트_DTO_일부수정_생성() {
        MissionUpdateDto dto = new MissionUpdateDto();
        // setField(dto, "boothId", 2L);
        setField(dto, "title", "수정된 제목");
        setField(dto, "content", "수정된 내용");
        // setField(dto, "place", "수정된 장소");
        // setField(dto, "time", "14:00");
        // setField(dto, "clearedThumbnail", "cleared.png");
        // setField(dto, "notClearedThumbnail", "not-cleared.png");
        return dto;
    }
}
