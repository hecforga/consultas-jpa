package com.nunsys.consultas.service;

import com.nunsys.consultas.domain.custom.PostWithCommentsCountFunction;
import com.nunsys.consultas.repository.custom.PostWithCommentsCountFunctionRepository;
import io.github.jhipster.service.QueryService;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PostWithCommentsCountFunctionQueryService extends QueryService<PostWithCommentsCountFunction> {
    private final PostWithCommentsCountFunctionRepository postWithCommentsCountFunctionRepository;

    public PostWithCommentsCountFunctionQueryService(PostWithCommentsCountFunctionRepository postWithCommentsCountFunctionRepository) {
        this.postWithCommentsCountFunctionRepository = postWithCommentsCountFunctionRepository;
    }

    @Transactional(readOnly = true)
    public List<PostWithCommentsCountFunction> findByYear(Integer yearFrom, Integer yearTo) {
        return postWithCommentsCountFunctionRepository.getPostsWithCommentsCountFunction(yearFrom, yearTo);
    }

    @Transactional(readOnly = true)
    public Page<PostWithCommentsCountFunction> findByYear(Integer yearFrom, Integer yearTo, Pageable page) {
        List<PostWithCommentsCountFunction> list = postWithCommentsCountFunctionRepository.getPostsWithCommentsCountFunction(
            yearFrom,
            yearTo,
            page.getPageNumber(),
            page.getPageSize()
        );
        long total = postWithCommentsCountFunctionRepository.countPostsWithCommentsCountFunction(yearFrom, yearTo);
        return new PageImpl<>(list, page, total);
    }
}
