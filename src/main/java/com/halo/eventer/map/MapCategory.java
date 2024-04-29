package com.halo.eventer.map;



import com.halo.eventer.festival.Festival;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class MapCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String categoryName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "festival_id")
    private Festival festival;

    @OneToMany(mappedBy = "mapCategory", fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    private List<Map> maps = new ArrayList<>();

    public MapCategory(Festival festival, String categoryName) {
        this.festival = festival;
        this.categoryName = categoryName;
    }
}
