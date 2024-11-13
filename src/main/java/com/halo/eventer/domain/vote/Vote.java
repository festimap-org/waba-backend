package com.halo.eventer.domain.vote;


import com.halo.eventer.domain.festival.Festival;
import com.halo.eventer.domain.vote.dto.VoteCreateReqDto;
import com.halo.eventer.domain.vote.dto.VoteUpdateReqDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String title;

    @Column(length = 255)
    private String content;

    @JoinColumn(name = "festival_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Festival festival;

    @OneToMany(mappedBy = "vote", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<VoteLike> voteLikes = new ArrayList<>();

    private Long likeCnt;

    public void updateVote(VoteUpdateReqDto reqDto) {
        this.title = reqDto.getTitle();
        this.content = reqDto.getContent();
    }

    public Vote(VoteCreateReqDto voteCreateReqDto, Festival festival) {
        this.title = voteCreateReqDto.getTitle();
        this.content = voteCreateReqDto.getContent();
        this.festival = festival;
        this.likeCnt = 0L;
    }

    public void setLikeCnt() {
        this.likeCnt = this.likeCnt + 1;
    }
}
