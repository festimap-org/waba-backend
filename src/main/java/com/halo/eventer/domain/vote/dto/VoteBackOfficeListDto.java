package com.halo.eventer.domain.vote.dto;


import com.halo.eventer.domain.vote.Vote;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VoteBackOfficeListDto {
    List<VoteBackOfficeGetElementDto> voteList;

    public VoteBackOfficeListDto(List<Vote> voteList){
        this.voteList = voteList.stream().map(VoteBackOfficeGetElementDto::new).collect(Collectors.toList());
    }
}
