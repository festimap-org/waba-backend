package com.halo.eventer.global.common;

import com.halo.eventer.domain.inquiry.Inquiry;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Getter
@NoArgsConstructor
public class PageInfo {
    private int page;
    private int pageSize;
    private Long totalNumber;
    private int totalPages;

    public PageInfo(int page, int pageSize, Page<Inquiry> inquiry) {
        this.page = page;
        this.pageSize = pageSize;
        this.totalNumber = inquiry.getTotalElements();
        this.totalPages = inquiry.getTotalPages();
    }
}
