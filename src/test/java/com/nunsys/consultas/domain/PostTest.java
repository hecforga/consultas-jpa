package com.nunsys.consultas.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.nunsys.consultas.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class PostTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Post.class);
        Post post1 = new Post();
        post1.setId(1L);
        Post post2 = new Post();
        post2.setId(post1.getId());
        assertThat(post1).isEqualTo(post2);
        post2.setId(2L);
        assertThat(post1).isNotEqualTo(post2);
        post1.setId(null);
        assertThat(post1).isNotEqualTo(post2);
    }
}
