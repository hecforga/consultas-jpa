package com.nunsys.consultas.service.impl;

import com.nunsys.consultas.domain.Post;
import com.nunsys.consultas.domain.custom.IPostForBigCombo;
import com.nunsys.consultas.domain.custom.IPostForCombo;
import com.nunsys.consultas.repository.PostRepository;
import com.nunsys.consultas.service.PostService;
import com.nunsys.consultas.service.dto.PostDTO;
import com.nunsys.consultas.service.dto.custom.PostForComboDTO;
import com.nunsys.consultas.service.mapper.PostMapper;
import com.nunsys.consultas.service.mapper.custom.PostForComboMapper;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Post}.
 */
@Service
@Transactional
public class PostServiceImpl implements PostService {
    private final Logger log = LoggerFactory.getLogger(PostServiceImpl.class);

    private final PostRepository postRepository;

    private final PostMapper postMapper;

    private final PostForComboMapper postForComboMapper;

    public PostServiceImpl(PostRepository postRepository, PostMapper postMapper, PostForComboMapper postForComboMapper) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
        this.postForComboMapper = postForComboMapper;
    }

    @Override
    public PostDTO save(PostDTO postDTO) {
        log.debug("Request to save Post : {}", postDTO);
        Post post = postMapper.toEntity(postDTO);
        post = postRepository.save(post);
        return postMapper.toDto(post);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PostDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Posts");
        return postRepository.findAll(pageable).map(postMapper::toDto);
    }

    @Override
    public List<PostForComboDTO> findAllForComboDto() {
        return postForComboMapper.toDto(postRepository.findAll());
    }

    @Override
    public List<IPostForCombo> findAllForComboInterface() {
        return postRepository.findAllBy(IPostForCombo.class);
    }

    @Override
    public List<IPostForBigCombo> findAllForBigComboInterface() {
        return postRepository.findAllBy(IPostForBigCombo.class);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PostDTO> findOne(Long id) {
        log.debug("Request to get Post : {}", id);
        return postRepository.findById(id).map(postMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Post : {}", id);
        postRepository.deleteById(id);
    }
}
