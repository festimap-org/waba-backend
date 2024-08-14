package com.halo.eventer.domain.stamp;

import com.halo.eventer.domain.festival.Festival;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "stamp", indexes = {@Index(name = "idx_uuid", columnList = "uuid")})
public class Stamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String uuid = UUID.randomUUID().toString();

    @Column(nullable = false)
    private String userInfo;        // name + phone


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "festival_id")
    private Festival festival;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "stamp")
    private StampInfo stampInfo;

    public Stamp(String encryptedUserInfo) {
        this.uuid = UUID.randomUUID().toString();
        this.userInfo = encryptedUserInfo;
        this.stampInfo = new StampInfo(this);
    }

}
