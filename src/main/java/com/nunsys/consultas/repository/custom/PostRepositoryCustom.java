package com.nunsys.consultas.repository.custom;

import com.nunsys.consultas.domain.Post;
import java.util.List;
import java.util.Set;

public interface PostRepositoryCustom {
    List<Post> findAllByTitleLike(Set<String> titleSet);
}
