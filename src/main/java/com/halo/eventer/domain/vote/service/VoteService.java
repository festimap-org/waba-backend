package com.halo.eventer.domain.vote.service;

import com.halo.eventer.domain.festival.Festival;
import com.halo.eventer.domain.festival.service.FestivalService;
import com.halo.eventer.domain.vote.Vote;
import com.halo.eventer.domain.vote.VoteLike;
import com.halo.eventer.domain.vote.dto.VoteCreateReqDto;
import com.halo.eventer.domain.vote.dto.VoteUpdateReqDto;
import com.halo.eventer.domain.vote.repository.VoteLikeRepository;
import com.halo.eventer.domain.vote.repository.VoteRepository;
import com.halo.eventer.global.error.ErrorCode;
import com.halo.eventer.global.error.exception.BaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class VoteService {

    private final VoteRepository voteRepository;
    private final FestivalService festivalService;
    private final VoteLikeRepository voteLikeRepository;

    @Transactional
    public List<Vote> createVote(VoteCreateReqDto dto){
        Festival festival = festivalService.getFestival(dto.getFestivalId());
        voteRepository.save(new Vote(dto,festival));
        return voteRepository.findAllByFestival(festival);
    }

    public List<Vote> getVoteListForBackOffice(Long festivalId) {
        Festival festival = festivalService.getFestival(festivalId);
        return voteRepository.findAllByFestival(festival);
    }

    public Vote getVote(Long voteId) {
        return voteRepository.findById(voteId).orElseThrow(()->new BaseException(ErrorCode.ELEMENT_NOT_FOUND));
    }

    @Transactional
    public Vote updateVote(Long voteId, VoteUpdateReqDto dto) {
        Vote vote = getVote(voteId);
        vote.updateVote(dto);
        return vote;
    }

    @Transactional
    public List<Vote> deleteVoteId(Long voteId,Long festivalId) {
        voteRepository.deleteById(voteId);
        Festival festival = festivalService.getFestival(festivalId);
        return voteRepository.findAllByFestival(festival);
    }

    public List<Vote> getVoteListForClient(Long festivalId) {
        Festival festival = festivalService.getFestival(festivalId);
        return voteRepository.findAllByFestival(festival);
    }

    @Transactional
    public Long increaseLikeCnt(Long voteId, HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if(cookies != null){

            for (Cookie cookie : cookies) {
                log.info("Cookie Name: {} Cookie Value: {}",  cookie.getName() , cookie.getValue());
            }
        }
        else {
            log.info("No cookies sent by the client.");
        }

        String ipAddress = getClientIp(request);
        String userAgent = request.getHeader("User-Agent");
        log.info("ip: {}",ipAddress);
        log.info("User-Agent: {}", userAgent);

        // 쿠키 검증
        boolean hasLiked = hasLikedCookie(request, voteId);
        log.info("쿠키 검증: {}",hasLiked);
        if (hasLiked) {
            throw new BaseException(ErrorCode.ALREADY_LIKE);
        }

        // ip 검증
        Optional<VoteLike> voteLike = voteLikeRepository.findByIpAddressAndVote_Id(ipAddress, voteId,userAgent);
        if(voteLike.isPresent()) {
            log.info("ip 존재: {}", voteLike.get().getIpAddress());
            LocalDateTime lastLikedTime = voteLike.get().getVoteTime();
            LocalDateTime now = LocalDateTime.now();
            if (lastLikedTime.plusHours(24).isAfter(now)) {
                addLikeCookie(response, voteId);
                throw new BaseException(ErrorCode.ALREADY_LIKE);
            }
        }

        Vote vote = getVote(voteId);
        VoteLike like = new VoteLike(ipAddress,vote, userAgent);
        voteLikeRepository.save(like);
        vote.setLikeCnt();

        addLikeCookie(response, voteId);

        return vote.getLikeCnt();
    }

    private boolean hasLikedCookie(HttpServletRequest request, Long voteId) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (("vote_" + voteId).equals(cookie.getName()) && "liked".equals(cookie.getValue())) {
                    return true;
                }
            }
        }
        return false;
    }

    private String getClientIp(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty()) {
            ipAddress = request.getRemoteAddr();
        } else {
            // 다중 프록시 환경에서는 첫 번째 IP가 클라이언트 IP
            ipAddress = ipAddress.split(",")[0];
        }
        return ipAddress;
    }

    private void addLikeCookie(HttpServletResponse response, Long voteId) {
        ResponseCookie cookie = ResponseCookie.from("vote_" + voteId, "liked")
                .maxAge(60 * 60 * 24)
                .path("/")
                .secure(true)
                .sameSite("None")
                .build();


        response.addHeader("Set-Cookie",cookie.toString());
    }
}
