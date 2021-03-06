package com.nunsys.consultas.service;

import com.nunsys.consultas.domain.custom.ICommentCount;
import com.nunsys.consultas.service.dto.CommentDTO;
import com.nunsys.consultas.service.dto.custom.CommentCountDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.nunsys.consultas.domain.Comment}.
 */
public interface CommentService {
    /**
     * Save a comment.
     *
     * @param commentDTO the entity to save.
     * @return the persisted entity.
     */
    CommentDTO save(CommentDTO commentDTO);

    /**
     * Get all the comments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CommentDTO> findAll(Pageable pageable);

    /**
     * Get the "id" comment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CommentDTO> findOne(Long id);

    /**
     * Delete the "id" comment.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    List<Object[]> countByYearAsListOfObjects();

    List<CommentCountDTO> countByYearAsListOfDtos();

    List<ICommentCount> countTotalCommentsByYearAsListOfInterfaces();
}
