package com.halo.eventer.domain.vote.dto;


import com.halo.eventer.domain.vote.Vote;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class VoteBackOfficeListDto {
    List<VoteBackOfficeGetElementDto> voteList;

    public VoteBackOfficeListDto(List<Vote> voteList){
        this.voteList = voteList.stream().map(VoteBackOfficeGetElementDto::new).collect(Collectors.toList());
    }
}
