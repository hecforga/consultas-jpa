package com.nunsys.consultas.repository.custom;

import com.nunsys.consultas.domain.custom.PostWithCommentsCountFunction;
import java.time.Instant;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostWithCommentsCountFunctionRepository
    extends JpaRepository<PostWithCommentsCountFunction, Long>, JpaSpecificationExecutor<PostWithCommentsCountFunction> {
    @Query(value = "SELECT * FROM f_post_with_comments_count(:yearFrom, :yearTo, :page, :size);", nativeQuery = true)
    List<PostWithCommentsCountFunction> getPostsWithCommentsCountFunction(Integer yearFrom, Integer yearTo, int page, int size);

    @Query(value = "SELECT * FROM f_post_with_comments_count(:yearFrom, :yearTo, -1, -1);", nativeQuery = true)
    List<PostWithCommentsCountFunction> getPostsWithCommentsCountFunction(Integer yearFrom, Integer yearTo);

    @Query(value = "SELECT total FROM f_count_post_with_comments_count(:yearFrom, :yearTo);", nativeQuery = true)
    long countPostsWithCommentsCountFunction(Integer yearFrom, Integer yearTo);
}
