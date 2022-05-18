package com.nunsys.consultas.service.mapper.custom;

import com.nunsys.consultas.domain.Post;
import com.nunsys.consultas.service.dto.custom.PostForComboDTO;
import com.nunsys.consultas.service.mapper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {})
public interface PostForComboMapper extends EntityMapper<PostForComboDTO, Post> {
    @Mapping(target = "title", ignore = true)
    @Mapping(target = "content", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "removeComments", ignore = true)
    Post toEntity(PostForComboDTO postForComboDTO);

    default Post fromId(Long id) {
        if (id == null) {
            return null;
        }
        Post post = new Post();
        post.setId(id);
        return post;
    }
}
