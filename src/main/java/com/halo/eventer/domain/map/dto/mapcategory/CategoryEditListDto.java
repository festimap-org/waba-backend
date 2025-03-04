package com.halo.eventer.domain.map.dto.mapcategory;

import com.halo.eventer.domain.notice.dto.BannerEditDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Getter
@NoArgsConstructor
public class CategoryEditListDto {
    private List<CategoryEditDto> categoryEditDtos;

    public CategoryEditListDto(List<CategoryEditDto> categoryEditDtos) {
        this.categoryEditDtos= categoryEditDtos;
    }
}
