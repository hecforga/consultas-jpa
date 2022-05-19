package com.nunsys.consultas.repository.custom;

import com.nunsys.consultas.domain.custom.PostWithCommentsCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PostWithCommentsCountRepository
    extends JpaRepository<PostWithCommentsCount, Long>, JpaSpecificationExecutor<PostWithCommentsCount> {}
