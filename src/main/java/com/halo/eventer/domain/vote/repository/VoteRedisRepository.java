package com.halo.eventer.domain.vote.repository;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class VoteRedisRepository {

    private final RedisTemplate<String, String> redisTemplate;

    private static final String VOTED_KEY = "vote:%d:voted";
    private static final String RANKING_KEY = "vote:%d:ranking";

    // 투표 여부 확인 (중복 빠른 체크)
    public boolean hasVoted(Long voteId, Long userId) {
        Boolean result = redisTemplate.opsForSet().isMember(votedKey(voteId), userId.toString());
        return Boolean.TRUE.equals(result);
    }

    // 투표 처리 후 Set에 userId 추가
    public void addVotedUser(Long voteId, Long userId) {
        redisTemplate.opsForSet().add(votedKey(voteId), userId.toString());
    }

    // 투표 취소 시 Set에서 userId 제거
    public void removeVotedUser(Long voteId, Long userId) {
        redisTemplate.opsForSet().remove(votedKey(voteId), userId.toString());
    }

    // 랭킹 점수 증가 (득표수 +1)
    public void incrementRanking(Long voteId, Long candidateId) {
        redisTemplate.opsForZSet().incrementScore(rankingKey(voteId), candidateId.toString(), 1);
    }

    // 랭킹 점수 감소 (득표수 -1)
    public void decrementRanking(Long voteId, Long candidateId) {
        redisTemplate.opsForZSet().incrementScore(rankingKey(voteId), candidateId.toString(), -1);
    }

    // 랭킹 조회 (점수 높은 순서)
    public List<ZSetOperations.TypedTuple<String>> getRanking(Long voteId) {
        Set<ZSetOperations.TypedTuple<String>> result =
                redisTemplate.opsForZSet().reverseRangeWithScores(rankingKey(voteId), 0, -1);
        if (result == null) {
            return Collections.emptyList();
        }
        return List.copyOf(result);
    }

    // 랭킹 캐시 초기화 (candidateId → voteCount)
    public void initRanking(Long voteId, Long candidateId, long voteCount) {
        redisTemplate.opsForZSet().add(rankingKey(voteId), candidateId.toString(), voteCount);
    }

    // 랭킹 캐시 존재 여부
    public boolean hasRankingCache(Long voteId) {
        Long size = redisTemplate.opsForZSet().size(rankingKey(voteId));
        return size != null && size > 0;
    }

    // 특정 투표의 캐시 전체 삭제
    public void deleteVoteCache(Long voteId) {
        redisTemplate.delete(votedKey(voteId));
        redisTemplate.delete(rankingKey(voteId));
    }

    private String votedKey(Long voteId) {
        return String.format(VOTED_KEY, voteId);
    }

    private String rankingKey(Long voteId) {
        return String.format(RANKING_KEY, voteId);
    }
}
