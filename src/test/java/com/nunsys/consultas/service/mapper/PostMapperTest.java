package com.nunsys.consultas.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PostMapperTest {
    private PostMapper postMapper;

    @BeforeEach
    public void setUp() {
        postMapper = new PostMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(postMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(postMapper.fromId(null)).isNull();
    }
}
