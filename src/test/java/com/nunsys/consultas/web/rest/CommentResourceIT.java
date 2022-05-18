package com.nunsys.consultas.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.nunsys.consultas.ConsultasApp;
import com.nunsys.consultas.domain.Comment;
import com.nunsys.consultas.domain.Post;
import com.nunsys.consultas.repository.CommentRepository;
import com.nunsys.consultas.service.CommentQueryService;
import com.nunsys.consultas.service.CommentService;
import com.nunsys.consultas.service.dto.CommentCriteria;
import com.nunsys.consultas.service.dto.CommentDTO;
import com.nunsys.consultas.service.mapper.CommentMapper;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CommentResource} REST controller.
 */
@SpringBootTest(classes = ConsultasApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CommentResourceIT {
    private static final Integer DEFAULT_YEAR = 1;
    private static final Integer UPDATED_YEAR = 2;
    private static final Integer SMALLER_YEAR = 1 - 1;

    private static final Boolean DEFAULT_APPROVED = false;
    private static final Boolean UPDATED_APPROVED = true;

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentQueryService commentQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCommentMockMvc;

    private Comment comment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Comment createEntity(EntityManager em) {
        Comment comment = new Comment().year(DEFAULT_YEAR).approved(DEFAULT_APPROVED).content(DEFAULT_CONTENT);
        // Add required entity
        Post post;
        if (TestUtil.findAll(em, Post.class).isEmpty()) {
            post = PostResourceIT.createEntity(em);
            em.persist(post);
            em.flush();
        } else {
            post = TestUtil.findAll(em, Post.class).get(0);
        }
        comment.setPost(post);
        return comment;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Comment createUpdatedEntity(EntityManager em) {
        Comment comment = new Comment().year(UPDATED_YEAR).approved(UPDATED_APPROVED).content(UPDATED_CONTENT);
        // Add required entity
        Post post;
        if (TestUtil.findAll(em, Post.class).isEmpty()) {
            post = PostResourceIT.createUpdatedEntity(em);
            em.persist(post);
            em.flush();
        } else {
            post = TestUtil.findAll(em, Post.class).get(0);
        }
        comment.setPost(post);
        return comment;
    }

    @BeforeEach
    public void initTest() {
        comment = createEntity(em);
    }

    @Test
    @Transactional
    public void createComment() throws Exception {
        int databaseSizeBeforeCreate = commentRepository.findAll().size();
        // Create the Comment
        CommentDTO commentDTO = commentMapper.toDto(comment);
        restCommentMockMvc
            .perform(post("/api/comments").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(commentDTO)))
            .andExpect(status().isCreated());

        // Validate the Comment in the database
        List<Comment> commentList = commentRepository.findAll();
        assertThat(commentList).hasSize(databaseSizeBeforeCreate + 1);
        Comment testComment = commentList.get(commentList.size() - 1);
        assertThat(testComment.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testComment.isApproved()).isEqualTo(DEFAULT_APPROVED);
        assertThat(testComment.getContent()).isEqualTo(DEFAULT_CONTENT);
    }

    @Test
    @Transactional
    public void createCommentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commentRepository.findAll().size();

        // Create the Comment with an existing ID
        comment.setId(1L);
        CommentDTO commentDTO = commentMapper.toDto(comment);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommentMockMvc
            .perform(post("/api/comments").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(commentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Comment in the database
        List<Comment> commentList = commentRepository.findAll();
        assertThat(commentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = commentRepository.findAll().size();
        // set the field null
        comment.setYear(null);

        // Create the Comment, which fails.
        CommentDTO commentDTO = commentMapper.toDto(comment);

        restCommentMockMvc
            .perform(post("/api/comments").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(commentDTO)))
            .andExpect(status().isBadRequest());

        List<Comment> commentList = commentRepository.findAll();
        assertThat(commentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkApprovedIsRequired() throws Exception {
        int databaseSizeBeforeTest = commentRepository.findAll().size();
        // set the field null
        comment.setApproved(null);

        // Create the Comment, which fails.
        CommentDTO commentDTO = commentMapper.toDto(comment);

        restCommentMockMvc
            .perform(post("/api/comments").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(commentDTO)))
            .andExpect(status().isBadRequest());

        List<Comment> commentList = commentRepository.findAll();
        assertThat(commentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContentIsRequired() throws Exception {
        int databaseSizeBeforeTest = commentRepository.findAll().size();
        // set the field null
        comment.setContent(null);

        // Create the Comment, which fails.
        CommentDTO commentDTO = commentMapper.toDto(comment);

        restCommentMockMvc
            .perform(post("/api/comments").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(commentDTO)))
            .andExpect(status().isBadRequest());

        List<Comment> commentList = commentRepository.findAll();
        assertThat(commentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllComments() throws Exception {
        // Initialize the database
        commentRepository.saveAndFlush(comment);

        // Get all the commentList
        restCommentMockMvc
            .perform(get("/api/comments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(comment.getId().intValue())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].approved").value(hasItem(DEFAULT_APPROVED.booleanValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)));
    }

    @Test
    @Transactional
    public void getComment() throws Exception {
        // Initialize the database
        commentRepository.saveAndFlush(comment);

        // Get the comment
        restCommentMockMvc
            .perform(get("/api/comments/{id}", comment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(comment.getId().intValue()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.approved").value(DEFAULT_APPROVED.booleanValue()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT));
    }

    @Test
    @Transactional
    public void getCommentsByIdFiltering() throws Exception {
        // Initialize the database
        commentRepository.saveAndFlush(comment);

        Long id = comment.getId();

        defaultCommentShouldBeFound("id.equals=" + id);
        defaultCommentShouldNotBeFound("id.notEquals=" + id);

        defaultCommentShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCommentShouldNotBeFound("id.greaterThan=" + id);

        defaultCommentShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCommentShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    public void getAllCommentsByYearIsEqualToSomething() throws Exception {
        // Initialize the database
        commentRepository.saveAndFlush(comment);

        // Get all the commentList where year equals to DEFAULT_YEAR
        defaultCommentShouldBeFound("year.equals=" + DEFAULT_YEAR);

        // Get all the commentList where year equals to UPDATED_YEAR
        defaultCommentShouldNotBeFound("year.equals=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void getAllCommentsByYearIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commentRepository.saveAndFlush(comment);

        // Get all the commentList where year not equals to DEFAULT_YEAR
        defaultCommentShouldNotBeFound("year.notEquals=" + DEFAULT_YEAR);

        // Get all the commentList where year not equals to UPDATED_YEAR
        defaultCommentShouldBeFound("year.notEquals=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void getAllCommentsByYearIsInShouldWork() throws Exception {
        // Initialize the database
        commentRepository.saveAndFlush(comment);

        // Get all the commentList where year in DEFAULT_YEAR or UPDATED_YEAR
        defaultCommentShouldBeFound("year.in=" + DEFAULT_YEAR + "," + UPDATED_YEAR);

        // Get all the commentList where year equals to UPDATED_YEAR
        defaultCommentShouldNotBeFound("year.in=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void getAllCommentsByYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        commentRepository.saveAndFlush(comment);

        // Get all the commentList where year is not null
        defaultCommentShouldBeFound("year.specified=true");

        // Get all the commentList where year is null
        defaultCommentShouldNotBeFound("year.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommentsByYearIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commentRepository.saveAndFlush(comment);

        // Get all the commentList where year is greater than or equal to DEFAULT_YEAR
        defaultCommentShouldBeFound("year.greaterThanOrEqual=" + DEFAULT_YEAR);

        // Get all the commentList where year is greater than or equal to UPDATED_YEAR
        defaultCommentShouldNotBeFound("year.greaterThanOrEqual=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void getAllCommentsByYearIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commentRepository.saveAndFlush(comment);

        // Get all the commentList where year is less than or equal to DEFAULT_YEAR
        defaultCommentShouldBeFound("year.lessThanOrEqual=" + DEFAULT_YEAR);

        // Get all the commentList where year is less than or equal to SMALLER_YEAR
        defaultCommentShouldNotBeFound("year.lessThanOrEqual=" + SMALLER_YEAR);
    }

    @Test
    @Transactional
    public void getAllCommentsByYearIsLessThanSomething() throws Exception {
        // Initialize the database
        commentRepository.saveAndFlush(comment);

        // Get all the commentList where year is less than DEFAULT_YEAR
        defaultCommentShouldNotBeFound("year.lessThan=" + DEFAULT_YEAR);

        // Get all the commentList where year is less than UPDATED_YEAR
        defaultCommentShouldBeFound("year.lessThan=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void getAllCommentsByYearIsGreaterThanSomething() throws Exception {
        // Initialize the database
        commentRepository.saveAndFlush(comment);

        // Get all the commentList where year is greater than DEFAULT_YEAR
        defaultCommentShouldNotBeFound("year.greaterThan=" + DEFAULT_YEAR);

        // Get all the commentList where year is greater than SMALLER_YEAR
        defaultCommentShouldBeFound("year.greaterThan=" + SMALLER_YEAR);
    }

    @Test
    @Transactional
    public void getAllCommentsByApprovedIsEqualToSomething() throws Exception {
        // Initialize the database
        commentRepository.saveAndFlush(comment);

        // Get all the commentList where approved equals to DEFAULT_APPROVED
        defaultCommentShouldBeFound("approved.equals=" + DEFAULT_APPROVED);

        // Get all the commentList where approved equals to UPDATED_APPROVED
        defaultCommentShouldNotBeFound("approved.equals=" + UPDATED_APPROVED);
    }

    @Test
    @Transactional
    public void getAllCommentsByApprovedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commentRepository.saveAndFlush(comment);

        // Get all the commentList where approved not equals to DEFAULT_APPROVED
        defaultCommentShouldNotBeFound("approved.notEquals=" + DEFAULT_APPROVED);

        // Get all the commentList where approved not equals to UPDATED_APPROVED
        defaultCommentShouldBeFound("approved.notEquals=" + UPDATED_APPROVED);
    }

    @Test
    @Transactional
    public void getAllCommentsByApprovedIsInShouldWork() throws Exception {
        // Initialize the database
        commentRepository.saveAndFlush(comment);

        // Get all the commentList where approved in DEFAULT_APPROVED or UPDATED_APPROVED
        defaultCommentShouldBeFound("approved.in=" + DEFAULT_APPROVED + "," + UPDATED_APPROVED);

        // Get all the commentList where approved equals to UPDATED_APPROVED
        defaultCommentShouldNotBeFound("approved.in=" + UPDATED_APPROVED);
    }

    @Test
    @Transactional
    public void getAllCommentsByApprovedIsNullOrNotNull() throws Exception {
        // Initialize the database
        commentRepository.saveAndFlush(comment);

        // Get all the commentList where approved is not null
        defaultCommentShouldBeFound("approved.specified=true");

        // Get all the commentList where approved is null
        defaultCommentShouldNotBeFound("approved.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommentsByContentIsEqualToSomething() throws Exception {
        // Initialize the database
        commentRepository.saveAndFlush(comment);

        // Get all the commentList where content equals to DEFAULT_CONTENT
        defaultCommentShouldBeFound("content.equals=" + DEFAULT_CONTENT);

        // Get all the commentList where content equals to UPDATED_CONTENT
        defaultCommentShouldNotBeFound("content.equals=" + UPDATED_CONTENT);
    }

    @Test
    @Transactional
    public void getAllCommentsByContentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commentRepository.saveAndFlush(comment);

        // Get all the commentList where content not equals to DEFAULT_CONTENT
        defaultCommentShouldNotBeFound("content.notEquals=" + DEFAULT_CONTENT);

        // Get all the commentList where content not equals to UPDATED_CONTENT
        defaultCommentShouldBeFound("content.notEquals=" + UPDATED_CONTENT);
    }

    @Test
    @Transactional
    public void getAllCommentsByContentIsInShouldWork() throws Exception {
        // Initialize the database
        commentRepository.saveAndFlush(comment);

        // Get all the commentList where content in DEFAULT_CONTENT or UPDATED_CONTENT
        defaultCommentShouldBeFound("content.in=" + DEFAULT_CONTENT + "," + UPDATED_CONTENT);

        // Get all the commentList where content equals to UPDATED_CONTENT
        defaultCommentShouldNotBeFound("content.in=" + UPDATED_CONTENT);
    }

    @Test
    @Transactional
    public void getAllCommentsByContentIsNullOrNotNull() throws Exception {
        // Initialize the database
        commentRepository.saveAndFlush(comment);

        // Get all the commentList where content is not null
        defaultCommentShouldBeFound("content.specified=true");

        // Get all the commentList where content is null
        defaultCommentShouldNotBeFound("content.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommentsByContentContainsSomething() throws Exception {
        // Initialize the database
        commentRepository.saveAndFlush(comment);

        // Get all the commentList where content contains DEFAULT_CONTENT
        defaultCommentShouldBeFound("content.contains=" + DEFAULT_CONTENT);

        // Get all the commentList where content contains UPDATED_CONTENT
        defaultCommentShouldNotBeFound("content.contains=" + UPDATED_CONTENT);
    }

    @Test
    @Transactional
    public void getAllCommentsByContentNotContainsSomething() throws Exception {
        // Initialize the database
        commentRepository.saveAndFlush(comment);

        // Get all the commentList where content does not contain DEFAULT_CONTENT
        defaultCommentShouldNotBeFound("content.doesNotContain=" + DEFAULT_CONTENT);

        // Get all the commentList where content does not contain UPDATED_CONTENT
        defaultCommentShouldBeFound("content.doesNotContain=" + UPDATED_CONTENT);
    }

    @Test
    @Transactional
    public void getAllCommentsByPostIsEqualToSomething() throws Exception {
        // Get already existing entity
        Post post = comment.getPost();
        commentRepository.saveAndFlush(comment);
        Long postId = post.getId();

        // Get all the commentList where post equals to postId
        defaultCommentShouldBeFound("postId.equals=" + postId);

        // Get all the commentList where post equals to postId + 1
        defaultCommentShouldNotBeFound("postId.equals=" + (postId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCommentShouldBeFound(String filter) throws Exception {
        restCommentMockMvc
            .perform(get("/api/comments?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(comment.getId().intValue())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].approved").value(hasItem(DEFAULT_APPROVED.booleanValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)));

        // Check, that the count call also returns 1
        restCommentMockMvc
            .perform(get("/api/comments/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCommentShouldNotBeFound(String filter) throws Exception {
        restCommentMockMvc
            .perform(get("/api/comments?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCommentMockMvc
            .perform(get("/api/comments/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingComment() throws Exception {
        // Get the comment
        restCommentMockMvc.perform(get("/api/comments/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateComment() throws Exception {
        // Initialize the database
        commentRepository.saveAndFlush(comment);

        int databaseSizeBeforeUpdate = commentRepository.findAll().size();

        // Update the comment
        Comment updatedComment = commentRepository.findById(comment.getId()).get();
        // Disconnect from session so that the updates on updatedComment are not directly saved in db
        em.detach(updatedComment);
        updatedComment.year(UPDATED_YEAR).approved(UPDATED_APPROVED).content(UPDATED_CONTENT);
        CommentDTO commentDTO = commentMapper.toDto(updatedComment);

        restCommentMockMvc
            .perform(put("/api/comments").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(commentDTO)))
            .andExpect(status().isOk());

        // Validate the Comment in the database
        List<Comment> commentList = commentRepository.findAll();
        assertThat(commentList).hasSize(databaseSizeBeforeUpdate);
        Comment testComment = commentList.get(commentList.size() - 1);
        assertThat(testComment.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testComment.isApproved()).isEqualTo(UPDATED_APPROVED);
        assertThat(testComment.getContent()).isEqualTo(UPDATED_CONTENT);
    }

    @Test
    @Transactional
    public void updateNonExistingComment() throws Exception {
        int databaseSizeBeforeUpdate = commentRepository.findAll().size();

        // Create the Comment
        CommentDTO commentDTO = commentMapper.toDto(comment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommentMockMvc
            .perform(put("/api/comments").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(commentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Comment in the database
        List<Comment> commentList = commentRepository.findAll();
        assertThat(commentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteComment() throws Exception {
        // Initialize the database
        commentRepository.saveAndFlush(comment);

        int databaseSizeBeforeDelete = commentRepository.findAll().size();

        // Delete the comment
        restCommentMockMvc
            .perform(delete("/api/comments/{id}", comment.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Comment> commentList = commentRepository.findAll();
        assertThat(commentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
