package com.halo.eventer.domain.widget.util;

import com.halo.eventer.domain.widget.BaseWidget;
import com.halo.eventer.domain.widget.dto.WidgetOrderUpdateRequest;
import com.halo.eventer.domain.widget.entity.DisplayOrderUpdatable;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@NoArgsConstructor
public class DisplayOrderUtils {

  public static <T extends DisplayOrderUpdatable, R extends WidgetOrderUpdateRequest>
      void updateDisplayOrder(List<T> widgets, List<R> orderRequests) {
    Map<Long, Integer> orderMap =
        orderRequests.stream().collect(Collectors.toMap(R::getId, R::getDisplayOrder));

    widgets.stream()
        .filter(w -> orderMap.containsKey(w.getId()))
        .forEach(w -> w.updateDisplayOrder(orderMap.get(w.getId())));
  }
}
