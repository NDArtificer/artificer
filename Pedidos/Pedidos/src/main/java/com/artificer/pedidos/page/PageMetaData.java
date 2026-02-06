package com.artificer.pedidos.page;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PageMetaData {

    private int size;
    private int number;
    private long totalElements;
    private int totalPages;

    public static <T> PageMetaData brandNewPage(Page<T> page) {
        return new PageMetaData(
                page.getSize(),
                page.getNumber(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    public static <T> Page<T> listToPage(Pageable pageable, List<T> lista) {
        int total = lista.size();
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), total);
        return new PageImpl<>(lista.subList(start, end), pageable, total);
    }

}