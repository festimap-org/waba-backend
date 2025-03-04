package com.halo.eventer.domain.manager;

import com.halo.eventer.domain.festival.Festival;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Manager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String phoneNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "festival_id")
    private Festival festival;

    public Manager(String phoneNo, Festival festival) {
        this.phoneNo = phoneNo;
        this.festival = festival;
    }
}
