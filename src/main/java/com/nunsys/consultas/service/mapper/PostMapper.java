package com.nunsys.consultas.service.mapper;

import com.nunsys.consultas.domain.*;
import com.nunsys.consultas.service.dto.PostDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Post} and its DTO {@link PostDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PostMapper extends EntityMapper<PostDTO, Post> {
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "removeComments", ignore = true)
    Post toEntity(PostDTO postDTO);

    default Post fromId(Long id) {
        if (id == null) {
            return null;
        }
        Post post = new Post();
        post.setId(id);
        return post;
    }
}
