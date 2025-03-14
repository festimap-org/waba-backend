package com.halo.eventer.domain.image;

import com.halo.eventer.domain.notice.Notice;
import com.halo.eventer.domain.widget_item.WidgetItem;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Image {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String imageUrl;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "notice_id")
  private Notice notice;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "widget_item_id")
  private WidgetItem widgetItem;

  public Image(String image_url) {
    this.imageUrl = image_url;
  }

  public void setImage(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public void setNotice(Notice notice) {
    this.notice = notice;
  }

  public void updateWidgetItem(WidgetItem widgetItem) {
    this.widgetItem = widgetItem;
  }
}
