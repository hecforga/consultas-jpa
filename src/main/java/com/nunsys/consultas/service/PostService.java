package com.nunsys.consultas.service;

import com.nunsys.consultas.domain.custom.IPostForBigCombo;
import com.nunsys.consultas.domain.custom.IPostForCombo;
import com.nunsys.consultas.service.dto.PostDTO;
import com.nunsys.consultas.service.dto.custom.PostForComboDTO;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.nunsys.consultas.domain.Post}.
 */
public interface PostService {
    /**
     * Save a post.
     *
     * @param postDTO the entity to save.
     * @return the persisted entity.
     */
    PostDTO save(PostDTO postDTO);

    /**
     * Get all the posts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PostDTO> findAll(Pageable pageable);

    List<PostDTO> findAllByTitleLike(Set<String> titleSet);

    List<PostForComboDTO> findAllForComboDto();

    List<IPostForCombo> findAllForComboInterface();

    List<IPostForBigCombo> findAllForBigComboInterface();

    /**
     * Get the "id" post.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PostDTO> findOne(Long id);

    /**
     * Delete the "id" post.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
