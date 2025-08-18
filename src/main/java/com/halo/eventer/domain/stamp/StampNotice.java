package com.halo.eventer.domain.stamp;

import jakarta.persistence.*;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class StampNotice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String participationNotice;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String privacyConsent;

    @OneToOne(mappedBy = "notice", fetch = FetchType.LAZY)
    private Stamp stamp;

    @Builder
    private StampNotice(Stamp stamp, String participationNotice, String privacyConsent) {
        this.stamp = stamp;
        this.participationNotice = (participationNotice == null) ? "" : participationNotice;
        this.privacyConsent = (privacyConsent == null) ? "" : privacyConsent;
    }

    public void upsert(String participationNotice, String privacyConsent) {
        this.participationNotice = participationNotice;
        this.privacyConsent = privacyConsent;
    }

    public static StampNotice from(Stamp stamp, String participationNotice, String privacyConsent) {
        return StampNotice.builder()
                .stamp(stamp)
                .participationNotice(participationNotice)
                .privacyConsent(privacyConsent)
                .build();
    }
}
