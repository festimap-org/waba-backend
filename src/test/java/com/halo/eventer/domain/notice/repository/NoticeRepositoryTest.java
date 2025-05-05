package com.halo.eventer.domain.notice.repository;

import com.halo.eventer.domain.festival.Festival;
import com.halo.eventer.domain.festival.FestivalFixture;
import com.halo.eventer.domain.festival.repository.FestivalRepository;
import com.halo.eventer.domain.notice.ArticleType;
import com.halo.eventer.domain.notice.Notice;
import com.halo.eventer.domain.notice.NoticeFixture;
import com.halo.eventer.domain.notice.dto.NoticeCreateReqDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SuppressWarnings("NonAsciiCharacters")
public class NoticeRepositoryTest {

    @Autowired
    private NoticeRepository noticeRepository;

    @Autowired
    private FestivalRepository festivalRepository;

    private Festival festival;
    private List<Notice> pickedNotices = new ArrayList<>();

    @BeforeEach
    void setUp(){
        Festival f = FestivalFixture.축제_엔티티();
        festival = festivalRepository.save(f);
        NoticeCreateReqDto noticeCreateReqDto = NoticeFixture.공지사항_생성_DTO();
        for(int i = 0; i<3;i++){
            Notice n = Notice.from(festival, noticeCreateReqDto);
            setField(n,"updatedAt", LocalDateTime.now());
            setField(n,"createdAt", LocalDateTime.now());
            n.updatePicked(true);
            pickedNotices.add(noticeRepository.save(n));
        }
    }

    @Test
    void picked_notice_리스트_조회(){
        //when
        List<Notice> notices = noticeRepository.findAllByPickedAndFestivalId(festival.getId(),true);

        //then
        assertThat(notices).hasSize(pickedNotices.size());
    }

    @Test
    void 페이징으로_조회하기(){
        //given
        Pageable pageable = PageRequest.of(0, 10);

        //when
        Page<Notice> result = noticeRepository.findAllByTypeAndFestivalIdOrderByUpdatedAtDesc(
                ArticleType.NOTICE, festival.getId(), pageable);

        //then
        assertThat(result.getTotalElements()).isEqualTo(3);
        assertThat(result.getContent().get(0).getId()).isEqualTo(3L);
    }
}
