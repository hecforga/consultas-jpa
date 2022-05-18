package com.nunsys.consultas.domain.custom;

import org.springframework.beans.factory.annotation.Value;

public interface IPostForBigCombo {
    Long getId();

    @Value("#{target.title + \" - \" + target.content.substring(0, 16) + \"...\"}")
    String getSummary();
}
