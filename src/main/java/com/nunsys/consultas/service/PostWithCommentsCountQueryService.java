package com.nunsys.consultas.service;

import com.nunsys.consultas.domain.custom.PostWithCommentsCount;
import com.nunsys.consultas.domain.custom.PostWithCommentsCount_;
import com.nunsys.consultas.repository.custom.PostWithCommentsCountRepository;
import com.nunsys.consultas.service.dto.PostCriteria;
import io.github.jhipster.service.QueryService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PostWithCommentsCountQueryService extends QueryService<PostWithCommentsCount> {
    private final Logger log = LoggerFactory.getLogger(PostWithCommentsCountQueryService.class);

    private final PostWithCommentsCountRepository postWithCommentsCountRepository;

    public PostWithCommentsCountQueryService(PostWithCommentsCountRepository postWithCommentsCountRepository) {
        this.postWithCommentsCountRepository = postWithCommentsCountRepository;
    }

    @Transactional(readOnly = true)
    public List<PostWithCommentsCount> findByCriteria(PostCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PostWithCommentsCount> specification = createSpecification(criteria);
        return postWithCommentsCountRepository.findAll(specification);
    }

    @Transactional(readOnly = true)
    public Page<PostWithCommentsCount> findByCriteria(PostCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PostWithCommentsCount> specification = createSpecification(criteria);
        return postWithCommentsCountRepository.findAll(specification, page);
    }

    @Transactional(readOnly = true)
    public long countByCriteria(PostCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PostWithCommentsCount> specification = createSpecification(criteria);
        return postWithCommentsCountRepository.count(specification);
    }

    protected Specification<PostWithCommentsCount> createSpecification(PostCriteria criteria) {
        Specification<PostWithCommentsCount> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PostWithCommentsCount_.id));
            }
        }
        return specification;
    }
}
