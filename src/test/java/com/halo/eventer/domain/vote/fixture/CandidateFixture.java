package com.halo.eventer.domain.vote.fixture;

import java.util.Arrays;

import com.halo.eventer.domain.vote.Candidate;
import com.halo.eventer.domain.vote.Vote;
import com.halo.eventer.domain.vote.dto.request.CandidateBulkDeleteRequest;
import com.halo.eventer.domain.vote.dto.request.CandidateCreateRequest;
import com.halo.eventer.domain.vote.dto.request.CandidateEnableUpdateRequest;
import com.halo.eventer.domain.vote.dto.request.CandidateUpdateRequest;
import com.halo.eventer.domain.vote.dto.request.DisplayOrderUpdateRequest;

import static org.springframework.test.util.ReflectionTestUtils.setField;

@SuppressWarnings("NonAsciiCharacters")
public class CandidateFixture {

    public static final Long 후보_ID_A = 1L;
    public static final Long 후보_ID_B = 2L;
    public static final Long 존재하지_않는_후보_ID = 999L;

    public static Candidate 후보_생성(Vote vote, Long id, int displayOrder) {
        Candidate candidate = Candidate.create(vote, "CODE_" + id, "후보" + id, null, null, null, null, displayOrder);
        setField(candidate, "id", id);
        return candidate;
    }

    public static Candidate 이미지가_있는_후보_생성(Vote vote, Long id, int displayOrder) {
        Candidate candidate = Candidate.create(
                vote, "CODE_" + id, "후보" + id, null, null, "https://image.url/candidate.jpg", null, displayOrder);
        setField(candidate, "id", id);
        return candidate;
    }

    public static CandidateCreateRequest 후보_생성_요청() {
        CandidateCreateRequest request = new CandidateCreateRequest();
        setField(request, "code", "CODE_01");
        setField(request, "displayName", "테스트 후보");
        return request;
    }

    public static CandidateUpdateRequest 후보_수정_요청() {
        CandidateUpdateRequest request = new CandidateUpdateRequest();
        setField(request, "displayName", "수정된 후보");
        return request;
    }

    public static CandidateEnableUpdateRequest 후보_활성화_요청(boolean isEnabled, Long... ids) {
        CandidateEnableUpdateRequest request = new CandidateEnableUpdateRequest();
        setField(request, "candidateIds", Arrays.asList(ids));
        setField(request, "isEnabled", isEnabled);
        return request;
    }

    public static CandidateBulkDeleteRequest 후보_선택_삭제_요청(Long... ids) {
        CandidateBulkDeleteRequest request = new CandidateBulkDeleteRequest();
        setField(request, "candidateIds", Arrays.asList(ids));
        return request;
    }

    public static DisplayOrderUpdateRequest 순서_변경_요청() {
        DisplayOrderUpdateRequest request = new DisplayOrderUpdateRequest();
        setField(request, "candidateIdA", 후보_ID_A);
        setField(request, "candidateIdB", 후보_ID_B);
        return request;
    }
}
