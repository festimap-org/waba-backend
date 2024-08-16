package com.halo.eventer.domain.lost_item.dto;

import com.halo.eventer.domain.lost_item.LostItem;
import com.halo.eventer.domain.missing_person.MissingPerson;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LostItemElementDto {
    private Long id;
    private String name;


    public LostItemElementDto(LostItem lostItem) {
        this.id = lostItem.getId();
        this.name = lostItem.getName();
    }
}
