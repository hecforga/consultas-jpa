package com.nunsys.consultas.web.rest;

import com.nunsys.consultas.domain.custom.IPostForBigCombo;
import com.nunsys.consultas.domain.custom.PostWithCommentsCountFunction;
import com.nunsys.consultas.service.PostQueryService;
import com.nunsys.consultas.service.PostService;
import com.nunsys.consultas.service.PostWithCommentsCountFunctionQueryService;
import com.nunsys.consultas.service.dto.PostCriteria;
import com.nunsys.consultas.service.dto.PostDTO;
import com.nunsys.consultas.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * REST controller for managing {@link com.nunsys.consultas.domain.Post}.
 */
@RestController
@RequestMapping("/api")
public class PostResource {
    private final Logger log = LoggerFactory.getLogger(PostResource.class);

    private static final String ENTITY_NAME = "post";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PostService postService;

    private final PostQueryService postQueryService;

    private final PostWithCommentsCountFunctionQueryService postWithCommentsCountFunctionQueryService;

    public PostResource(
        PostService postService,
        PostQueryService postQueryService,
        PostWithCommentsCountFunctionQueryService postWithCommentsCountFunctionQueryService
    ) {
        this.postService = postService;
        this.postQueryService = postQueryService;
        this.postWithCommentsCountFunctionQueryService = postWithCommentsCountFunctionQueryService;
    }

    /**
     * {@code POST  /posts} : Create a new post.
     *
     * @param postDTO the postDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new postDTO, or with status {@code 400 (Bad Request)} if the post has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/posts")
    public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO) throws URISyntaxException {
        log.debug("REST request to save Post : {}", postDTO);
        if (postDTO.getId() != null) {
            throw new BadRequestAlertException("A new post cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PostDTO result = postService.save(postDTO);
        return ResponseEntity
            .created(new URI("/api/posts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /posts} : Updates an existing post.
     *
     * @param postDTO the postDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated postDTO,
     * or with status {@code 400 (Bad Request)} if the postDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the postDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/posts")
    public ResponseEntity<PostDTO> updatePost(@Valid @RequestBody PostDTO postDTO) throws URISyntaxException {
        log.debug("REST request to update Post : {}", postDTO);
        if (postDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PostDTO result = postService.save(postDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, postDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /posts} : get all the posts.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of posts in body.
     */
    @GetMapping(value = "/posts", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PostDTO>> getAllPosts(PostCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Posts by criteria: {}", criteria);
        Page<PostDTO> page = postQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping(value = "/posts", produces = "application/combo+json")
    public ResponseEntity<List<PostWithCommentsCountFunction>> getAllPostsWithCommentsCount(
        @RequestParam Integer yearFrom,
        @RequestParam Integer yearTo,
        Pageable pageable
    ) {
        log.debug("REST request to get Posts with comments count by yearFrom and yearTo: {} - {}", yearFrom, yearTo);
        Page<PostWithCommentsCountFunction> page = postWithCommentsCountFunctionQueryService.findByYear(yearFrom, yearTo, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping(value = "/posts", produces = "application/big-combo+json")
    public ResponseEntity<List<IPostForBigCombo>> getAllPostsForBigCombo() {
        log.debug("REST request to get Posts for big combo");
        List<IPostForBigCombo> result = postService.findAllForBigComboInterface();
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value = "/posts", produces = "application/title-like+json")
    public ResponseEntity<List<PostDTO>> getAllPostsByTitleLike(@RequestParam Set<String> titles) {
        log.debug("REST request to get Posts by title like");
        List<PostDTO> result = postService.findAllByTitleLike(titles);
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code GET  /posts/count} : count all the posts.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/posts/count")
    public ResponseEntity<Long> countPosts(PostCriteria criteria) {
        log.debug("REST request to count Posts by criteria: {}", criteria);
        return ResponseEntity.ok().body(postQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /posts/:id} : get the "id" post.
     *
     * @param id the id of the postDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the postDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/posts/{id}")
    public ResponseEntity<PostDTO> getPost(@PathVariable Long id) {
        log.debug("REST request to get Post : {}", id);
        Optional<PostDTO> postDTO = postService.findOne(id);
        return ResponseUtil.wrapOrNotFound(postDTO);
    }

    /**
     * {@code DELETE  /posts/:id} : delete the "id" post.
     *
     * @param id the id of the postDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/posts/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        log.debug("REST request to delete Post : {}", id);
        postService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
