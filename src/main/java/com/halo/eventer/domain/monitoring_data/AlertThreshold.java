package com.halo.eventer.domain.monitoring_data;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class AlertThreshold {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double threshold;
    private boolean alertSent;
    private LocalDateTime lastSentTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "monitoring_data_id")
    private MonitoringData monitoringData;

    public AlertThreshold(double threshold, MonitoringData monitoringData) {
        this.threshold = threshold;
        this.alertSent = false;
        this.monitoringData = monitoringData;
    }

    public void setThreshold(double threshold) { this.threshold = threshold; }
    public void setAlertSent(boolean alertSent) { this.alertSent = alertSent; }
    public void setLastSentTime(LocalDateTime lastSentTime) { this.lastSentTime = lastSentTime; }
}
