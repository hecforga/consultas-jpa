package com.nunsys.consultas.service.dto.custom;

public class CommentCountDTO {
    private Integer year;

    private Long total;

    public CommentCountDTO(Integer year, Long total) {
        this.year = year;
        this.total = total;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
