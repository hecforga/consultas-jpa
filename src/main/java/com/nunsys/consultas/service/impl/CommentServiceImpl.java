package com.nunsys.consultas.service.impl;

import com.nunsys.consultas.domain.Comment;
import com.nunsys.consultas.repository.CommentRepository;
import com.nunsys.consultas.service.CommentService;
import com.nunsys.consultas.service.dto.CommentDTO;
import com.nunsys.consultas.service.mapper.CommentMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Comment}.
 */
@Service
@Transactional
public class CommentServiceImpl implements CommentService {
    private final Logger log = LoggerFactory.getLogger(CommentServiceImpl.class);

    private final CommentRepository commentRepository;

    private final CommentMapper commentMapper;

    public CommentServiceImpl(CommentRepository commentRepository, CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
    }

    @Override
    public CommentDTO save(CommentDTO commentDTO) {
        log.debug("Request to save Comment : {}", commentDTO);
        Comment comment = commentMapper.toEntity(commentDTO);
        comment = commentRepository.save(comment);
        return commentMapper.toDto(comment);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CommentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Comments");
        return commentRepository.findAll(pageable).map(commentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CommentDTO> findOne(Long id) {
        log.debug("Request to get Comment : {}", id);
        return commentRepository.findById(id).map(commentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Comment : {}", id);
        commentRepository.deleteById(id);
    }
}
