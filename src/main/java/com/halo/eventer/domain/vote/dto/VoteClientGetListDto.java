package com.halo.eventer.domain.vote.dto;

import com.halo.eventer.domain.vote.Vote;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class VoteClientGetListDto {
    List<VoteClientGetElementDto> voteList;
    public VoteClientGetListDto(List<Vote> voteList) {
        this.voteList = voteList.stream().map(o->new VoteClientGetElementDto(o)).collect(Collectors.toList());
    }
}
