package com.nunsys.consultas.domain.custom;

import org.springframework.beans.factory.annotation.Value;

public interface IPostForCombo {
    Long getId();

    @Value("#{target.title + \" - \" + target.content.substring(0, 8) + \"...\"}")
    String getSummary();
}
