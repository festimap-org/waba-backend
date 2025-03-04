package com.halo.eventer.domain.vote.dto;

import com.halo.eventer.domain.vote.Vote;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VoteClientGetListDto {
    List<VoteClientGetElementDto> voteList;
    public VoteClientGetListDto(List<Vote> voteList) {
        this.voteList = voteList.stream().map(o->new VoteClientGetElementDto(o)).collect(Collectors.toList());
    }
}
