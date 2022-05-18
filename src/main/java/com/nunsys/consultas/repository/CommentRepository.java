package com.nunsys.consultas.repository;

import com.nunsys.consultas.domain.Comment;
import com.nunsys.consultas.service.dto.custom.CommentCountDTO;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Comment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>, JpaSpecificationExecutor<Comment> {
    @Query("SELECT c.year, COUNT(c.year) FROM Comment AS c GROUP BY c.year ORDER BY c.year DESC")
    List<Object[]> countTotalCommentsByYearAsListOfObjects();

    @Query(
        "SELECT new com.nunsys.consultas.service.dto.custom.CommentCountDTO(c.year, COUNT(c.year)) " +
        "FROM Comment AS c GROUP BY c.year ORDER BY c.year DESC"
    )
    List<CommentCountDTO> countTotalCommentsByYearAsListOfDtos();
}
