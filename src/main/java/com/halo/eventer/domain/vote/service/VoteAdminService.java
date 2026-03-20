package com.halo.eventer.domain.vote.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.halo.eventer.domain.festival.Festival;
import com.halo.eventer.domain.festival.exception.FestivalNotFoundException;
import com.halo.eventer.domain.festival.repository.FestivalRepository;
import com.halo.eventer.domain.vote.Vote;
import com.halo.eventer.domain.vote.dto.request.VoteCreateRequest;
import com.halo.eventer.domain.vote.dto.request.VoteInfoUpdateRequest;
import com.halo.eventer.domain.vote.dto.request.VoteScheduleUpdateRequest;
import com.halo.eventer.domain.vote.dto.response.VoteInfoResponse;
import com.halo.eventer.domain.vote.dto.response.VoteResponse;
import com.halo.eventer.domain.vote.dto.response.VoteScheduleResponse;
import com.halo.eventer.domain.vote.exception.VoteNotFoundException;
import com.halo.eventer.domain.vote.repository.CandidateRepository;
import com.halo.eventer.domain.vote.repository.VoteRedisRepository;
import com.halo.eventer.domain.vote.repository.VoteRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VoteAdminService {

    private final VoteRepository voteRepository;
    private final FestivalRepository festivalRepository;
    private final CandidateRepository candidateRepository;
    private final VoteRedisRepository voteRedisRepository;

    @Transactional
    public void createVote(Long festivalId, VoteCreateRequest request) {
        Festival festival =
                festivalRepository.findById(festivalId).orElseThrow(() -> new FestivalNotFoundException(festivalId));

        Vote vote = Vote.create(festival, request.getTitle());
        voteRepository.save(vote);
    }

    @Transactional(readOnly = true)
    public List<VoteResponse> getVotesByFestival(Long festivalId) {
        return voteRepository.findAllByFestival_Id(festivalId).stream()
                .map(VoteResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteVote(Long voteId) {
        Vote vote = loadVoteOrThrow(voteId);
        candidateRepository.deleteAllByVoteId(voteId);
        voteRepository.delete(vote);
        voteRedisRepository.deleteVoteCache(voteId);
    }

    @Transactional(readOnly = true)
    public VoteInfoResponse getVoteInfo(Long voteId) {
        return VoteInfoResponse.from(loadVoteOrThrow(voteId));
    }

    @Transactional(readOnly = true)
    public VoteScheduleResponse getVoteSchedule(Long voteId) {
        return VoteScheduleResponse.from(loadVoteOrThrow(voteId));
    }

    @Transactional
    public void updateVoteInfo(Long voteId, VoteInfoUpdateRequest request) {
        Vote vote = loadVoteOrThrow(voteId);
        vote.updateInfo(
                request.getTitle(),
                request.getVoteImageUrl(),
                request.getCandidateDefaultImageUrl(),
                request.isShowRank(),
                request.isShowVoteCount(),
                request.isAllowCancel());
    }

    @Transactional
    public void updateVoteSchedule(Long voteId, VoteScheduleUpdateRequest request) {
        Vote vote = loadVoteOrThrow(voteId);
        vote.updateSchedule(
                request.getDisplayStartAt(),
                request.getDisplayEndAt(),
                request.getVoteStartAt(),
                request.getVoteEndAt());
    }

    private Vote loadVoteOrThrow(Long voteId) {
        return voteRepository.findById(voteId).orElseThrow(() -> new VoteNotFoundException(voteId));
    }
}
