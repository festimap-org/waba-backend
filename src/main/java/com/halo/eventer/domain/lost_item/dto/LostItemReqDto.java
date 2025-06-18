package com.halo.eventer.domain.lost_item.dto;

import java.time.LocalDate;

import com.halo.eventer.domain.lost_item.LostItem;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LostItemReqDto {
    private String name;
    private String description;
    private String thumbnail;
    private LocalDate findDate;

    public LostItemReqDto(LostItem lostItem) {
        this.name = lostItem.getName();
        this.description = lostItem.getDescription();
        this.thumbnail = lostItem.getThumbnail();
        this.findDate = lostItem.getFindDate();
    }
}
