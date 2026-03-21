package com.halo.eventer.domain.vote.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.halo.eventer.domain.vote.Candidate;
import com.halo.eventer.domain.vote.Vote;
import com.halo.eventer.domain.vote.dto.request.CandidateBulkDeleteRequest;
import com.halo.eventer.domain.vote.dto.request.CandidateCreateRequest;
import com.halo.eventer.domain.vote.dto.request.CandidateEnableUpdateRequest;
import com.halo.eventer.domain.vote.dto.request.CandidateUpdateRequest;
import com.halo.eventer.domain.vote.dto.request.DisplayOrderUpdateRequest;
import com.halo.eventer.domain.vote.dto.response.CandidateListResponse;
import com.halo.eventer.domain.vote.dto.response.CandidateResponse;
import com.halo.eventer.domain.vote.exception.CandidateNotFoundException;
import com.halo.eventer.domain.vote.exception.VoteNotFoundException;
import com.halo.eventer.domain.vote.repository.CandidateRepository;
import com.halo.eventer.domain.vote.repository.VoteRedisRepository;
import com.halo.eventer.domain.vote.repository.VoteRepository;
import com.halo.eventer.global.error.ErrorCode;
import com.halo.eventer.global.error.exception.BaseException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CandidateAdminService {

    private final CandidateRepository candidateRepository;
    private final VoteRepository voteRepository;
    private final VoteRedisRepository voteRedisRepository;

    @Transactional(readOnly = true)
    public List<CandidateListResponse> getCandidates(Long voteId) {
        Vote vote = loadVoteOrThrow(voteId);
        String defaultImageUrl = vote.getCandidateDefaultImageUrl();
        return candidateRepository.findAllByVoteIdOrderByDisplayOrderAsc(voteId).stream()
                .map(c -> CandidateListResponse.from(c, defaultImageUrl))
                .toList();
    }

    @Transactional
    public CandidateResponse addCandidate(Long voteId, CandidateCreateRequest request) {
        Vote vote = loadVoteOrThrow(voteId);
        int nextDisplayOrder = candidateRepository.findMaxDisplayOrderByVoteId(voteId) + 1;
        Candidate candidate = Candidate.create(
                vote,
                request.getCode(),
                request.getDisplayName(),
                request.getRealName(),
                request.getDescription(),
                request.getImageUrl(),
                request.getPhone(),
                nextDisplayOrder);
        candidateRepository.save(candidate);
        voteRedisRepository.initRanking(voteId, candidate.getId(), 0);
        return CandidateResponse.fromAdmin(candidate);
    }

    @Transactional
    public CandidateResponse updateCandidate(Long voteId, Long candidateId, CandidateUpdateRequest request) {
        loadVoteOrThrow(voteId);
        Candidate candidate = loadCandidateOrThrow(candidateId);
        validateCandidateBelongsToVote(candidate, voteId);
        candidate.update(
                request.getDisplayName(),
                request.getRealName(),
                request.getDescription(),
                request.getImageUrl(),
                request.getPhone());
        return CandidateResponse.fromAdmin(candidate);
    }

    @Transactional
    public void updateCandidatesEnabled(Long voteId, CandidateEnableUpdateRequest request) {
        loadVoteOrThrow(voteId);
        List<Candidate> candidates = candidateRepository.findAllByIdInAndVoteId(request.getCandidateIds(), voteId);
        if (candidates.size() != request.getCandidateIds().size()) {
            throw new BaseException(ErrorCode.INVALID_INPUT_VALUE);
        }
        candidates.forEach(c -> c.updateEnabled(request.isEnabled()));
    }

    @Transactional
    public void deleteCandidates(Long voteId, CandidateBulkDeleteRequest request) {
        loadVoteOrThrow(voteId);
        List<Candidate> candidates = candidateRepository.findAllByIdInAndVoteId(request.getCandidateIds(), voteId);
        if (candidates.size() != request.getCandidateIds().size()) {
            throw new BaseException(ErrorCode.INVALID_INPUT_VALUE);
        }
        candidateRepository.deleteAll(candidates);
    }

    @Transactional
    public void updateDisplayOrder(Long voteId, DisplayOrderUpdateRequest request) {
        loadVoteOrThrow(voteId);
        List<Candidate> fetched =
                candidateRepository.findAllById(List.of(request.getCandidateIdA(), request.getCandidateIdB()));
        Map<Long, Candidate> map = fetched.stream().collect(Collectors.toMap(Candidate::getId, c -> c));

        Candidate a = map.get(request.getCandidateIdA());
        Candidate b = map.get(request.getCandidateIdB());
        if (a == null || b == null) {
            throw new BaseException(ErrorCode.INVALID_INPUT_VALUE);
        }
        validateCandidateBelongsToVote(a, voteId);
        validateCandidateBelongsToVote(b, voteId);

        int temp = a.getDisplayOrder();
        a.updateDisplayOrder(b.getDisplayOrder());
        b.updateDisplayOrder(temp);
    }

    private Vote loadVoteOrThrow(Long voteId) {
        return voteRepository.findById(voteId).orElseThrow(() -> new VoteNotFoundException(voteId));
    }

    private Candidate loadCandidateOrThrow(Long candidateId) {
        return candidateRepository.findById(candidateId).orElseThrow(() -> new CandidateNotFoundException(candidateId));
    }

    private void validateCandidateBelongsToVote(Candidate candidate, Long voteId) {
        if (!candidate.getVote().getId().equals(voteId)) {
            throw new BaseException(ErrorCode.INVALID_INPUT_VALUE);
        }
    }
}
