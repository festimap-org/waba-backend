package com.halo.eventer.domain.lost_item;

import javax.persistence.*;

import com.halo.eventer.domain.festival.Festival;
import com.halo.eventer.domain.lost_item.dto.LostItemReqDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class LostItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String thumbnail;
    private String findDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "festival_id")
    private Festival festival;

    private LostItem(LostItemReqDto lostItemReqDto, Festival festival) {
        this.name = lostItemReqDto.getName();
        this.thumbnail = lostItemReqDto.getThumbnail();
        this.findDate = lostItemReqDto.getFindDate();
        this.description= lostItemReqDto.getDescription();
        this.festival = festival;
    }

    public static LostItem of(LostItemReqDto lostDto, Festival festival) {
        return new LostItem(lostDto, festival);
    }

    public void update(LostItemReqDto lostItemReqDto) {
        this.name = lostItemReqDto.getName();
        this.thumbnail = lostItemReqDto.getThumbnail();
        this.findDate = lostItemReqDto.getFindDate();
        this.description = lostItemReqDto.getDescription();
    }
}
