package com.halo.eventer.domain.monitoring_data;

import com.halo.eventer.domain.festival.Festival;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Entity
@Getter
public class MonitoringData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int maxCapacity;

    private String alertPhone1;
    private String alertPhone2;
    private String alertPhone3;
    private String alertPhone4;
    private String alertPhone5;

    private int maxCount;

    private String latestTimestamp;

    @OneToMany(mappedBy = "monitoringData", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<AlertThreshold> alertThresholds;

    @OneToOne
    private Festival festival;

    public MonitoringData(Festival festival) {
        this.festival = festival;
        this.alertThresholds = new ArrayList<>();
        this.maxCount = 0;
    }

    public void setMaxCapacity(int maxCapacity) { this.maxCapacity = maxCapacity; }
    public void setAlertThresholds(AlertThreshold alertThresholds) { this.alertThresholds.add(alertThresholds); }

    public void setAlertPhone1(String alertPhone1) { this.alertPhone1 = alertPhone1; }
    public void setAlertPhone2(String alertPhone2) { this.alertPhone2 = alertPhone2; }
    public void setAlertPhone3(String alertPhone3) { this.alertPhone3 = alertPhone3; }
    public void setAlertPhone4(String alertPhone4) { this.alertPhone4 = alertPhone4; }
    public void setAlertPhone5(String alertPhone5) { this.alertPhone5 = alertPhone5; }

    public void setMaxCount(int maxCount) { this.maxCount = maxCount; }
    public void setLatestTimestamp(String latestTimestamp) { this.latestTimestamp = latestTimestamp; }

}
