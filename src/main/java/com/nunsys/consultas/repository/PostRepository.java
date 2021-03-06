package com.nunsys.consultas.repository;

import com.nunsys.consultas.domain.Post;
import com.nunsys.consultas.repository.custom.PostRepositoryCustom;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Post entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post>, PostRepositoryCustom {
    <T> List<T> findAllBy(Class<T> type);
}
