package org.nadojob.nadojobbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageDto<T> {
    private List<T> content;
    private Integer countPage;
    private Integer currentPage;
    private Integer maxPageSize;
    private Long totalSize;

}
