package com.halo.eventer.domain.map;



import com.halo.eventer.domain.festival.Festival;
import com.halo.eventer.domain.map.dto.mapcategory.MapCategoryImageDto;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import com.halo.eventer.domain.map.enumtype.MapCategoryType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class MapCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String categoryName;

    private String icon;
    private String pin;
    private int category_rank;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "festival_id")
    private Festival festival;

    @OneToMany(mappedBy = "mapCategory", fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    private List<Map> maps = new ArrayList<>();

    public MapCategory(Festival festival, String categoryName) {
        this.festival = festival;
        this.categoryName = categoryName;
        this.category_rank = 11;
    }

    private MapCategory(MapCategoryType mapCategoryType) {
        this.categoryName = mapCategoryType.getDisplayName();
        this.category_rank = 11;
    }

    public static MapCategory createFixedBooth() {
        return new MapCategory(MapCategoryType.FIXED_BOOTH);
    }

    public static MapCategory of(MapCategoryType mapCategoryType) {
        return new MapCategory(mapCategoryType);
    }

    public void setImage(MapCategoryImageDto mapCategoryImageDto) {
        this.icon = mapCategoryImageDto.getIcon();
        this.pin = mapCategoryImageDto.getPin();
    }

    public void assignFestival(Festival festival) {
        this.festival=festival;
    }
    public void setRank(int rank) {
        this.category_rank = rank;
    }
}
