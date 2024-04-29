package com.halo.eventer.user.dto;


import com.halo.eventer.user.LostItem;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LostDto {
    private String name;
    private String type;
    private String thumbnail;
    private String findDate;

    public LostDto(LostItem lostItem) {
        this.name = lostItem.getName();
        this.type = lostItem.getType();
        this.thumbnail = lostItem.getThumbnail();
        this.findDate = lostItem.getFindDate();
    }
}
